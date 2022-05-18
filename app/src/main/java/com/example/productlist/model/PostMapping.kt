package com.example.productlist.model

import com.example.productlist.db.entity.PostEntity
import com.example.productlist.db.entity.UserEntity
import com.example.productlist.network.dto.PostDTO
import java.text.SimpleDateFormat
import java.util.*

private const val TAGS_SEPARATOR = ", "

fun PostDTO.mapToEntity() = PostEntity(
    id = id,
    text = text,
    image = image,
    likes = likes,
    tags = tags.joinToString(TAGS_SEPARATOR),
    publishDate = publishDate,
    ownerId = owner.id
)

fun PostEntity.mapToModel(owner: UserEntity) = Post(
    id = id,
    text = text,
    image = image,
    likes = likes,
    tags = tags.split(TAGS_SEPARATOR),
    publishDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(publishDate)!!,
    owner = owner.mapToModel()
)