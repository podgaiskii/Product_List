package com.example.productlist.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "users",
    primaryKeys = ["id"],
)
data class UserEntity(

    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "firstName")
    val firstName: String,

    @ColumnInfo(name = "lastName")
    val lastName: String,

    @ColumnInfo(name = "picture")
    val picture: String,
)