package com.example.myrcapp.Chat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.myrcapp.Chat.Adapters.ChatListAdapter
import com.example.myrcapp.Models.Chat
import com.example.myrcapp.R

class ChatListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatListAdapter: ChatListAdapter
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        recyclerView = findViewById(R.id.recyclerView)
        chatListAdapter = ChatListAdapter()

        recyclerView.adapter = chatListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        chatListAdapter.setOnItemClickListener { chatId ->
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("chatId", chatId)
            startActivity(intent)
        }

        // Fetch the list of chats for the current user
        fetchChatList()
    }

    private fun fetchChatList() {
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserUid != null) {
            firestore.collection("chats")
                .whereArrayContains("participants", currentUserUid)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        // Handle error
                        return@addSnapshotListener
                    }

                    val chatList = value?.documents?.map { document ->
                        document.toObject(Chat::class.java)
                    }

                    if (chatList != null) {
                        chatListAdapter.submitList(chatList)
                    }
                }
        }
    }

}
