package me.michigang1.michat

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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

    @RequiresApi(Build.VERSION_CODES.R)
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
                createAccount(name, email, password)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun createAccount(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this@SignUp) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    updateUI(mAuth.currentUser)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("users").child("user: $uid").setValue(User(name, email, uid))
    }

    private fun updateUI(account: FirebaseUser?) {
        if (account != null) {
            Toast.makeText(
                this,
                "You signed up successfully",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(
                this,
                "Authentication failed.",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}
