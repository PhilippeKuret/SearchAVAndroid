package com.example.seach_av_491.model


data class File(
        val id: String,
        val title: String,
        val filePath: String,
        val dateAdded: String,
        val type: String,
        val flag: String,
        val userId: String,
        val user: User,
        val reviewerId: String,
        val description: String,
        val thumbnailPath: String,
        val duration: String,
        val fileFlag: Int
)