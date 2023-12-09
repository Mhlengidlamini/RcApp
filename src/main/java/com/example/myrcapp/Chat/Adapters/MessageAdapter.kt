package com.example.myrcapp.Chat.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.example.myrcapp.Models.Message // Replace with your actual package name
import com.example.myrcapp.R

import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var messageList: List<Message> = mutableListOf()
    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    fun submitList(messages: List<Message>?) {
        if (messages != null) {
            messageList = messages
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageList[position]

        val messageTextView: TextView = holder.itemView.findViewById(R.id.messageTextView)
        val timestampTextView: TextView = holder.itemView.findViewById(R.id.timestampTextView)

        messageTextView.text = message.content
        timestampTextView.text = formatTimestamp(message.timestamp)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("MMM d, yyyy 'at' h:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}


