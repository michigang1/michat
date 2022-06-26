package me.michigang1.michat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private val context: Context, private val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemReceive = 1
    private val itemSent = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == itemReceive) {
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
            return UserAdapter.UserViewHolder(view) }
        else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.send, parent, false)
            return UserAdapter.UserViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if (holder.javaClass == SentViewHolder::class.java) {
            val viewHolder = holder as SentViewHolder
            viewHolder.sentMessage.text = currentMessage.message.toString()
        }
        else {
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.receiveMessage.text = currentMessage.message.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        return if (currentUser == currentMessage.senderID) itemSent
        else itemReceive
    }

    override fun getItemCount() = messageList.size

    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sentMessage: TextView = itemView.findViewById(R.id.txt_sent_message)

    }

    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val receiveMessage: TextView = itemView.findViewById(R.id.txt_receive_message)
    }

}