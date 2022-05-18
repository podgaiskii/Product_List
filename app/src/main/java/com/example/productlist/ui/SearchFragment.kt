package com.example.productlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.example.productlist.base.BaseFragment
import com.example.productlist.databinding.FragmentSearchBinding
import com.example.productlist.db.ProductsDatabase
import com.example.productlist.model.Post
import com.example.productlist.model.mapToModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchBinding

    private val adapter by lazy { PostsAdapter(requireContext()) }

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                search(query)
                return false
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.items = emptyList()
        binding.rvResults.adapter = adapter
    }

    private fun search(query: String?) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            setData(
                if (query.isNullOrEmpty()) {
                    emptyList()
                } else {
                    withContext(Dispatchers.IO) {
                        with(ProductsDatabase.getInstance(requireContext())) {
                            postDAO.search(query).mapNotNull { postEntity ->
                                val owner = userDAO.getById(postEntity.ownerId)
                                if (owner != null) {
                                    postEntity.mapToModel(owner)
                                } else null
                            }
                        }
                    }
                }
            )
        }
    }

    private fun setData(posts: List<Post>) {
        adapter.items = posts
    }
}