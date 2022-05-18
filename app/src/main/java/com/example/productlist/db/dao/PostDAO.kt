package com.example.productlist.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.productlist.db.entity.PostEntity

@Dao
interface PostDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts: List<PostEntity>)

    @Query("SELECT * FROM posts")
    suspend fun getAll(): List<PostEntity>

    @Query("SELECT * FROM posts WHERE text LIKE '%' || :query || '%'")
    suspend fun search(query: String): List<PostEntity>
}