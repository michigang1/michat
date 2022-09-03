package me.michigang1.michat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import me.michigang1.michat.databinding.ReceiveBinding
import me.michigang1.michat.databinding.SendBinding

class MessageAdapter(private val context: Context, private val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemReceive = 1
    private val itemSent = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == itemReceive) {
            val binding = ReceiveBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ReceiveViewHolder(binding)
        } else {
            val binding = SendBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return SentViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if (holder.javaClass == SentViewHolder::class.java) {
            val viewHolder = holder as SentViewHolder
            viewHolder.sentMessage.text = currentMessage.message.toString()
        } else {
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

    class SentViewHolder(binding: SendBinding) : RecyclerView.ViewHolder(binding.root) {
        val sentMessage: TextView = binding.txtSentMessage
    }

    class ReceiveViewHolder(binding: ReceiveBinding) : RecyclerView.ViewHolder(binding.root) {
        val receiveMessage: TextView = binding.txtReceiveMessage
    }
}
