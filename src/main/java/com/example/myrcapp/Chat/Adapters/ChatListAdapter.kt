package com.example.myrcapp.Chat.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrcapp.Models.Chat // Replace with your actual package name
import com.example.myrcapp.R

class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {

    private var chatList: List<Chat> = mutableListOf()
    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    fun submitList(chats: List<Chat?>?) {
        if (chats != null) {
            chatList = chats as List<Chat>
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(chat.chatId)
        }

        val participantNameTextView: TextView = holder.itemView.findViewById(R.id.participantNameTextView)
        val lastMessageTextView: TextView = holder.itemView.findViewById(R.id.lastMessageTextView)

        participantNameTextView.text = "${chat.participants}"
        lastMessageTextView.text = "Last Message: ${chat.lastMessage}" // Replace with actual last message logic
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

