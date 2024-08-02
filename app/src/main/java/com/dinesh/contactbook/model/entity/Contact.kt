package com.dinesh.contactbook.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey
    val id: String,
    val name: String?,
    val mobileNumber: String?,
    val imageUri: String?
)
