package me.michigang1.michat
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import me.michigang1.michat.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var viewBinding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        mAuth = FirebaseAuth.getInstance()
        supportActionBar?.hide()
        setContentView(viewBinding.root)
        viewBinding.apply {
            btnSignUp.setOnClickListener {
                val intent = Intent(this@Login, SignUp::class.java)
                startActivity(intent)
            }

            btnLogin.setOnClickListener {
                val email = editEmail.text.toString()
                val password = editPassword.text.toString()

                toLogIn(email, password)
            }
        }
    }

    private fun toLogIn(email: String?, password: String?) {
        mAuth.signInWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val currentUser = mAuth.currentUser
                    updateUI(currentUser)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(account: FirebaseUser?) {
        if (account != null) {
            Toast.makeText(this, "You logged in successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}
