package com.example.myrcapp.Models

data class Chat(
    val participants: List<User> = emptyList(),
    val chatId: String = "",
    val lastMessage: String = ""
)

