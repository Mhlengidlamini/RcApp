package com.example.myrcapp.Chat.Adapters


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrcapp.Chat.ChatActivity
import com.example.myrcapp.Models.User
import com.example.myrcapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userListAdapter: UserListAdapter
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        recyclerView = findViewById(R.id.recyclerViewUsers)
        userListAdapter = UserListAdapter()

        recyclerView.adapter = userListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch and display the list of users
        fetchUserList()

        // Set click listener for user item

    }

    private fun fetchUserList() {
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserUid != null) {
            firestore.collection("users")
                .whereNotEqualTo("uid", currentUserUid) // Exclude the current user from the list
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        // Handle error
                        return@addSnapshotListener
                    }

                    val userList = value?.documents?.mapNotNull { document ->
                        document.toObject(User::class.java)
                    }

                    userListAdapter.submitList(userList)
                }
        }
    }
}
