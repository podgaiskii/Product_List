package com.example.productlist.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.productlist.R
import com.example.productlist.databinding.ListItemLoadingBinding
import com.example.productlist.databinding.ListItemNoPostsBinding
import com.example.productlist.databinding.ListItemPostBinding
import com.example.productlist.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class PostsAdapter(
    context: Context
) : RecyclerView.Adapter<PostsAdapter.PostHolder>() {

    private val inflater = LayoutInflater.from(context)

    var items: List<Post>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setLoading() {
        items = null
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            items == null -> PostViewType.LOADING.value
            items!!.isEmpty() -> PostViewType.EMPTY.value
            else -> PostViewType.ITEM.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder =
        when (viewType) {
            PostViewType.LOADING.value -> PostHolder.LoadingHolder(
                ListItemLoadingBinding.inflate(inflater, parent, false)
            )
            PostViewType.EMPTY.value -> PostHolder.EmptyHolder(
                ListItemNoPostsBinding.inflate(inflater, parent, false)
            )
            else -> PostHolder.ItemHolder(
                ListItemPostBinding.inflate(inflater, parent, false)
            )
        }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        if (holder is PostHolder.ItemHolder) {
            holder.bind(items!![position])
        }
    }

    override fun getItemCount(): Int = items?.size ?: 1

    sealed class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        class EmptyHolder(
            binding: ListItemNoPostsBinding
        ) : PostHolder(binding.root)

        class LoadingHolder(
            binding: ListItemLoadingBinding
        ) : PostHolder(binding.root)

        class ItemHolder(
            private val binding: ListItemPostBinding
        ) : PostHolder(binding.root) {

            companion object {
                @SuppressLint("ConstantLocale")
                private val dateFormat =
                    SimpleDateFormat("dd.MM.yyyy 'at' HH:mm", Locale.getDefault())
            }

            fun bind(post: Post) {
                binding.user = with(post.owner) { "$firstName $lastName" }
                binding.date = dateFormat.format(post.publishDate)
                binding.text = post.text
                binding.likes = post.likes.toString()


                Glide.with(binding.ivProfile)
                    .asBitmap()
                    .load(post.owner.picture)
                    .circleCrop()
                    .placeholder(R.drawable.ic_account_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.ivProfile)

                Glide.with(binding.ivImage)
                    .asBitmap()
                    .load(post.image)
                    .apply(RequestOptions().transform(RoundedCorners(16)))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .addListener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.ivImage.isVisible = false
                            return false
                        }

                        override fun onResourceReady(
                            resource: Bitmap?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.ivImage.isVisible = true
                            return false
                        }
                    })
                    .into(binding.ivImage)
            }
        }
    }
}

private enum class PostViewType(val value: Int) {
    LOADING(0), EMPTY(1), ITEM(2);
}