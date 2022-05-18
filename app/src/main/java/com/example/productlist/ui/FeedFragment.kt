package com.example.productlist.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.productlist.base.BaseFragment
import com.example.productlist.databinding.FragmentFeedBinding
import com.example.productlist.db.ProductsDatabase
import com.example.productlist.model.Post
import com.example.productlist.model.mapToEntity
import com.example.productlist.model.mapToModel
import com.example.productlist.network.Network
import com.example.productlist.network.dto.ModelListDTO
import com.example.productlist.network.dto.PostDTO
import com.example.productlist.network.dto.UserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedFragment : BaseFragment() {

    private lateinit var binding: FragmentFeedBinding

    private val adapter by lazy { PostsAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPosts.adapter = adapter

        lifecycleScope.launch {
            val posts: List<Post>

            try {
                withContext(Dispatchers.IO) {
                    val postsList = Network.dummyApi.getPosts()
                    val users = postsList.data.map { it.owner }
                    insertDataToDB(users, postsList.data)
                }
            } catch (e: Exception) {
                showError(e.message ?: "Data loading error")
                Log.e("FEED", "Server loading error:", e)
            }

            withContext(Dispatchers.IO) {
                posts = try {
                    getDataFromDB()
                } catch (e: Exception) {
                    showError(e.message ?: "Data loading error")
                    Log.e("FEED", "Database loading error:", e)
                    emptyList()
                }
            }

            adapter.items = posts
        }
    }

    private suspend fun insertDataToDB(users: List<UserDTO>, posts: List<PostDTO>) {
        with(ProductsDatabase.getInstance(requireContext())) {
            userDAO.insert(users.map { it.mapToEntity() })
            postDAO.insert(posts.map { it.mapToEntity() })
        }
    }

    private suspend fun getDataFromDB(): List<Post> =
        with(ProductsDatabase.getInstance(requireContext())) {
            postDAO.getAll().mapNotNull { postEntity ->
                val owner = userDAO.getById(postEntity.ownerId)
                if (owner != null) {
                    postEntity.mapToModel(owner)
                } else null
            }
        }
}