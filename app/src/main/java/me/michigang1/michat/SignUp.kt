package me.michigang1.michat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import me.michigang1.michat.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignUpBinding.inflate(layoutInflater)
        mAuth = FirebaseAuth.getInstance()
        supportActionBar?.hide()
        setContentView(viewBinding.root)
        viewBinding.apply {
            btnLogin.setOnClickListener {
                val intent = Intent(this@SignUp, Login::class.java)
                startActivity(intent)
            }

            btnSignUp.setOnClickListener {
                val name = editName.text.toString()
                val email = editEmail.text.toString()
                val password = editPassword.text.toString()
                toSignUp(name, email, password)
            }
        }
    }

    private fun toSignUp(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    updateUI(mAuth.currentUser)
                    finish()
                } else updateUI(mAuth.currentUser)
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("users").child("user: $uid").setValue(User(name, email, uid))
    }

    private fun updateUI(account: FirebaseUser?) {
        if (account != null) {
            Toast.makeText(this, "You signed in successfully", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "You didn't signed in", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
