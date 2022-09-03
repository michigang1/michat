package me.michigang1.michat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.michigang1.michat.databinding.UserLayoutBinding

class UserAdapter(private val context: Context, private val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        with(holder) {
            binding.txtName.text = currentUser.name
            itemView.setOnClickListener {
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("name", currentUser.name)
                intent.putExtra("uid", currentUser.uid)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = userList.size

    class UserViewHolder(val binding: UserLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val textName: TextView = itemView.findViewById(R.id.txt_name)
    }
}
