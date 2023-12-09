package com.example.myrcapp.Chat

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrcapp.Chat.Adapters.MessageAdapter
import com.example.myrcapp.Models.Message
import com.example.myrcapp.R
import com.example.myrcapp.R.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.System as System1

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView = findViewById(R.id.recyclerView)
        messageAdapter = MessageAdapter()

        recyclerView.adapter = messageAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)

        // Get the chatId from the intent
        val chatId = intent.getStringExtra("chatId")

        if (chatId != null) {
            // Fetch the list of messages for the selected chat
            fetchMessageList(chatId)
        }

        sendButton.setOnClickListener {
            val messageContent = messageEditText.text.toString()
            if (messageContent.isNotEmpty() && chatId != null) {
                // Send the message to the selected chat
                sendMessage(chatId, messageContent)
                messageEditText.text.clear()
            }
        }
    }

    private fun fetchMessageList(chatId: String) {
        firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    // Handle error
                    return@addSnapshotListener
                }

                val messageList = value?.documents?.mapNotNull { document ->
                    document.toObject(Message::class.java)
                }

                messageAdapter.submitList(messageList)
                // Scroll to the last message when the list updates
                recyclerView.scrollToPosition(messageList?.size?.minus(1) ?: 0)
            }
    }

    private fun sendMessage(chatId: String, content: String) {
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserUid != null) {
            val message = Message(currentUserUid, content, System1.currentTimeMillis())

            firestore.collection("chats")
                .document(chatId)
                .collection("messages")
                .add(message)
        }
    }
}
