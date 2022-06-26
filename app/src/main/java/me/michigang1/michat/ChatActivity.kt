package me.michigang1.michat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ChatActivity : AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var btnSend: ImageView
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val intent = Intent()
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        supportActionBar?.title = name

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        messageBox = findViewById(R.id.messageBox)
        btnSend = findViewById(R.id.btnSend)
        messageRecyclerView = findViewById(R.id.messageRecyclerView)

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)
        messageRecyclerView.run {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = messageAdapter
        }



    }
}