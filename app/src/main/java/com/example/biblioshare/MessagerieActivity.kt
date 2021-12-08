package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.biblioshare.modele.ChatMessage
import com.example.biblioshare.modele.UserMessage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_messagerie.*
import kotlinx.android.synthetic.main.latest_messages_row.view.*


class MessagerieActivity : AppCompatActivity() {

    val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messagerie)

        recyclerview_latestmessages.adapter = adapter
        recyclerview_latestmessages.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        adapter.setOnItemClickListener { item, view ->
            val latestMessageItem = item as LatestMessageRow
            Log.d("LatestMessages", "")

            val intentChatLog = Intent(view.context, ConversationActivity::class.java)
            intentChatLog.putExtra(RechercheDetailActivity.USER_KEY, latestMessageItem.chatPartnerUser)
            startActivity(intentChatLog)
        }

        listenForLatestMessages()
    }

    /*private fun dummyConversation() {
        val dummyUser = UserMessage("soQ2rWHHbnQV0EokV8vwhbO1xhD2", "test3")
        val intentChatLog = Intent(this, ConversationActivity::class.java)
        intentChatLog.putExtra(RechercheDetailActivity.USER_KEY, dummyUser)
        startActivity(intentChatLog)
    }*/

    class LatestMessageRow(val chatMessage: ChatMessage): Item<GroupieViewHolder>() {
        var chatPartnerUser: UserMessage? = null
        override fun bind(p0: GroupieViewHolder, p1: Int) {
            p0.itemView.textView_message_latest_messages_row.text = chatMessage.text

            val chatPartnerId: String = if (chatMessage.userId == AccueilActivity.currentUser?.uid) {
                chatMessage.otherUserId
            } else {
                chatMessage.userId
            }

            val db = FirebaseFirestore.getInstance()
            val userDocRef = db.collection("usermessage").document(chatPartnerId)
            userDocRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("messagerie", "DocumentSnapshot data: ${document.data}")
                        chatPartnerUser = document.toObject<UserMessage>()
                        p0.itemView.textView_username_latest_messages_row.text = chatPartnerUser?.username
                    } else {
                        Log.d("messagerie", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("messagerie", "get failed with ", exception)
                }
        }

        override fun getLayout(): Int {
            return R.layout.latest_messages_row
        }
    }

    val latestMessagesMap = HashMap<String, ChatMessage>()

    private fun refreshRecyclerViewMessages() {
        adapter.clear()
        latestMessagesMap.values.forEach{
            adapter.add(LatestMessageRow(it))
        }
    }

    private fun listenForLatestMessages() {
        val currentUserUID = AccueilActivity.currentUser?.uid ?: return

        val db = FirebaseFirestore.getInstance()

        db.collection("latestmessage").document(currentUserUID).collection("message")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("conversationD", "Listen failed.", e)
                    return@addSnapshotListener
                }
                adapter.clear()
                for (document in value!!) {
                    Log.d("conversationD", "${document.id} => ${document.data}")
                    val chatMessage = document.toObject<ChatMessage>()
                    if (chatMessage != null) {
                        Log.d("ChatLog", "Message found : ${chatMessage.text}")
                        latestMessagesMap[document.id] = chatMessage
                        refreshRecyclerViewMessages()
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
                Log.d("ACTION", "MAISON")
                val intent = Intent(this, AccueilActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}