package com.example.productlist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "posts",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["ownerId"]
        )
    ]
)
data class PostEntity(

    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "text")
    val text: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "likes")
    val likes: Int,

    @ColumnInfo(name = "tags")
    val tags: String,

    @ColumnInfo(name = "publishDate")
    val publishDate: String,

    @ColumnInfo(name = "ownerId")
    val ownerId: String
)