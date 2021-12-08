package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.biblioshare.AccueilActivity.Companion.currentUser
import com.example.biblioshare.modele.ChatMessage
import com.example.biblioshare.modele.ChatOtherUserItem
import com.example.biblioshare.modele.ChatUserItem
import com.example.biblioshare.modele.UserMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_conversation.*

class ConversationActivity : AppCompatActivity() {

    val adapter = GroupAdapter<GroupieViewHolder>()
    val messageList: MutableList<ChatMessage> = mutableListOf()

    var otherUser: UserMessage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)

        recyclerview_chatlog.adapter = adapter

        otherUser = intent.getParcelableExtra<UserMessage>(RechercheDetailActivity.USER_KEY)

        supportActionBar?.title = otherUser?.username
        listenForMessages()


        send_button_chatlog.setOnClickListener {
            Log.d("conversationD", "Attempt to send message")
            if (otherUser != null) {
                sendMessage()
            }
        }
    }

    private fun listenForMessages() {
        val currentUser = AccueilActivity.currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("message").document(currentUser.uid).collection(otherUser!!.uid).orderBy("timestamp")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("conversationD", "Listen failed.", e)
                    return@addSnapshotListener
                }
                adapter.clear()
                messageList.clear()
                for (document in value!!) {
                    Log.d("conversationD", "${document.id} => ${document.data}")
                    val chatMessage = document.toObject<ChatMessage>()
                    if (chatMessage != null) {
                        messageList.add(chatMessage)
                        /*Log.d("conversationD", "Message found : ${chatMessage.text}")
                        if (chatMessage.userId == currentUser.uid) {
                            adapter.add(ChatUserItem(chatMessage.text, currentUser))
                        } else {
                            adapter.add(ChatOtherUserItem(chatMessage.text, otherUser!!))
                        }*/
                    }
                }
                for(chatMessage in messageList) {
                    Log.d("conversationD", "Message found : ${chatMessage.text}")
                    if (chatMessage.userId == currentUser.uid) {
                        adapter.add(ChatUserItem(chatMessage.text, currentUser))
                    } else {
                        adapter.add(ChatOtherUserItem(chatMessage.text, otherUser!!))
                    }
                }

            }
    }

    private fun sendMessage() {
        val text = message_edittext_chatlog.text.toString()
        if(text == ""){
            Log.d("conversationD", "Failed to send message : Empty message")
            return
        }
        val userId = FirebaseAuth.getInstance().uid
        val otherUserId = otherUser?.uid ?: return

        if(userId == null) return

        val db = FirebaseFirestore.getInstance()
        val refSend = db.collection("message").document(userId).collection(otherUserId)
        val timeInMillis = System.currentTimeMillis() / 1000

        val chatMessageSend = ChatMessage(text, otherUserId, userId, timeInMillis)
        refSend.add(chatMessageSend)
            .addOnSuccessListener { documentReference ->
                Log.d("conversationD", "DocumentSnapshot written with ID: ${documentReference.id}")
                val refReceive = db.collection("message").document(otherUserId).collection(userId)
                val chatMessageReceive = ChatMessage(text, otherUserId, userId, timeInMillis)
                refReceive.add(chatMessageReceive)
                    .addOnSuccessListener {
                        val latestMessageSendRef = db.collection("latestmessage").document(userId).collection("message").document(otherUserId)
                        latestMessageSendRef.set(chatMessageSend)
                            .addOnSuccessListener {
                                val latestMessageSendRef = db.collection("latestmessage").document(otherUserId).collection("message").document(userId)
                                latestMessageSendRef.set(chatMessageReceive)
                                    .addOnSuccessListener {
                                        message_edittext_chatlog.text.clear()
                                        recyclerview_chatlog.scrollToPosition(adapter.itemCount - 1)
                                        Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show()
                                    }
                            }
                    }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_action -> {
                val intent = Intent(this, AccueilActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}