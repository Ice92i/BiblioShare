package com.example.biblioshare.modele

import com.example.biblioshare.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_other_user_row.view.*
import kotlinx.android.synthetic.main.chat_user_row.view.*

class ChatOtherUserItem(val text: String,  val user: UserMessage): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textView_other_user_row.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_other_user_row
    }
}

class ChatUserItem(val text: String,  val user: UserMessage): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textView_user_row.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_user_row
    }
}

class ChatMessage(val text: String, val otherUserId: String, val userId: String) {
    constructor() : this("", "", "")
}