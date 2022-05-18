package com.example.productlist.model

import com.example.productlist.db.entity.UserEntity
import com.example.productlist.network.dto.UserDTO

fun UserDTO.mapToEntity() = UserEntity(
    id = id,
    title = title,
    firstName = firstName,
    lastName = lastName,
    picture = picture
)

fun UserEntity.mapToModel() = User(
    id = id,
    title = title,
    firstName = firstName,
    lastName = lastName,
    picture = picture
)