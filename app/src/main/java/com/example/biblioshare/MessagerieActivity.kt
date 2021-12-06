package com.example.biblioshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.biblioshare.modele.Chat
import com.example.biblioshare.modele.UserMessage
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_messagerie.*
import kotlinx.android.synthetic.main.latest_messages_row.view.*


class MessagerieActivity : AppCompatActivity() {

    companion object {
        var currentUser: UserMessage? = null
        val USER_KEY = "USER_KEY" //mettre ça dans l'activity de base
    }

    val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messagerie)

        recyclerview_latestmessages.adapter = adapter
        recyclerview_latestmessages.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        adapter.setOnItemClickListener { item, view ->
            val latestMessageItem = item as LatestMessageRow
            Log.d("LatestMessages", "")

            val intentChatLog = Intent(view.context, MessagerieActivity::class.java)
            intentChatLog.putExtra(USER_KEY, latestMessageItem.chatPartnerUser)
            startActivity(intentChatLog)
        }

        fetchCurrentUser()

        listenForLatestMessages()

    }

    class LatestMessageRow(val chatMessage: Chat): Item<GroupieViewHolder>() {
        var chatPartnerUser: UserMessage? = null
        override fun bind(p0: GroupieViewHolder, p1: Int) {
            p0.itemView.textView_message_latest_messages_row.text = chatMessage.text

            val chatPartnerId: String = if (chatMessage.userId == currentUser?.uid) {
                chatMessage.otherUserId
            } else {
                chatMessage.userId
            }

            /*val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
            ref.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    chatPartnerUser = snapshot.getValue(User::class.java)
                    p0.itemView.textView_username_latest_messages_row.text = chatPartnerUser?.username
                    Picasso.get().load(chatPartnerUser?.profileImageUrl).into(p0.itemView.imageView_latest_messages_row)
                    Log.d("LatestMessages", "ChatPartner : ${chatPartnerUser?.username}, ${chatPartnerUser?.uid}")
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })*/
        }
        override fun getLayout(): Int {
            return R.layout.latest_messages_row
        }
    }

    val latestMessagesMap = HashMap<String, Chat>()

    private fun refreshRecyclerViewMessages() {
        adapter.clear()
        latestMessagesMap.values.forEach{
            adapter.add(LatestMessageRow(it))
        }
    }

    private fun listenForLatestMessages() {
        val currentUserUID = currentUser?.uid ?: return
        /*val ref = FirebaseDatabase.getInstance().getReference("/latest-messages/$currentUserUID")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java) ?: return

                latestMessagesMap[snapshot.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java) ?: return

                latestMessagesMap[snapshot.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }
            override fun onChildRemoved(snapshot: DataSnapshot) {

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })*/
    }

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        /*val ref = FirebaseDatabase.getInstance().getReference(("/users/$uid"))
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                currentUser = snapshot.getValue(User::class.java)
                Log.d("LatestMessages", "current user : ${currentUser?.username}")
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })*/
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