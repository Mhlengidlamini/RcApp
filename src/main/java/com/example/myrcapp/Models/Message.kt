package com.example.myrcapp.Models

data class Message(
    val senderUid: String = "", // UID of the message sender
    val content: String = "",   // Content of the message
    val timestamp: Long = 0      // Timestamp of when the message was sent
)

