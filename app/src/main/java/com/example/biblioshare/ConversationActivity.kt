package com.example.biblioshare

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.biblioshare.modele.UserMessage
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_conversation.*

class ConversationActivity : AppCompatActivity() {

    val adapter = GroupAdapter<GroupieViewHolder>()

    var otherUser: UserMessage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)

        recyclerview_chatlog.adapter = adapter

        //otherUser = intent.getParcelableExtra<UserMessage>()NewMessageActivity.USER_KEY)

        supportActionBar?.title = otherUser?.username
        listenForMessages()

        send_button_chatlog.setOnClickListener {
            Log.d("ChatLog", "Attempt to send message")
            if (otherUser != null) {
                sendMessage()
            }
        }
    }

    private fun listenForMessages() {
        val currentUser = LatestMessagesActivity.currentUser ?: return
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/${currentUser.uid}/${otherUser?.uid}")
        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)
                if (chatMessage != null) {
                    Log.d("ChatLog", "Message found : ${chatMessage.text}")
                    if(chatMessage.userId == currentUser.uid) {
                        adapter.add(ChatUserItem(chatMessage.text, currentUser))
                    }
                    else {
                        adapter.add(ChatOtherUserItem(chatMessage.text, otherUser!!))
                    }

                }
                recyclerview_chatlog.scrollToPosition(adapter.itemCount - 1)
            }
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }
            override fun onChildRemoved(snapshot: DataSnapshot) {

            }
        })

    }

    private fun sendMessage() {
        val text = message_edittext_chatlog.text.toString()
        if(text == ""){
            Log.d("ChatLog", "Failed to send message : Empty message")
            return
        }
        val userId= FirebaseAuth.getInstance().uid
        val otherUserId = otherUser?.uid ?: return

        if(userId == null) return

        val referenceSend = FirebaseDatabase.getInstance().getReference("/user-messages/$userId/$otherUserId").push()
        val chatMessageSend = ChatMessage(referenceSend.key!!, text, otherUserId, userId, System.currentTimeMillis() / 1000)
        referenceSend.setValue(chatMessageSend)
            .addOnSuccessListener {
                val referenceReceive = FirebaseDatabase.getInstance().getReference("/user-messages/$otherUserId/$userId").push()
                val chatMessageReceive = ChatMessage(referenceReceive.key!!, text, otherUserId, userId, System.currentTimeMillis() / 1000)
                referenceReceive.setValue(chatMessageReceive)
                    .addOnSuccessListener {
                        val latestMessageSendRef = FirebaseDatabase.getInstance().getReference("latest-messages/$userId/$otherUserId")
                        latestMessageSendRef.setValue(chatMessageSend)
                            .addOnSuccessListener {
                                val latestMessageReceiveRef = FirebaseDatabase.getInstance().getReference("latest-messages/$otherUserId/$userId")
                                latestMessageReceiveRef.setValue(chatMessageReceive)
                                    .addOnSuccessListener {
                                        message_edittext_chatlog.text.clear()
                                        recyclerview_chatlog.scrollToPosition(adapter.itemCount - 1)
                                    }
                            }
                    }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_retour, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.retour_action -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}