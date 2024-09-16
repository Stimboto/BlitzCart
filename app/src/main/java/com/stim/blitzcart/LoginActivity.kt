// LoginActivity.kt
package com.stim.blitzcart

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)
        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        val textViewSignUpPrompt: TextView = findViewById(R.id.textViewSignUpPrompt)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and password are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if the user exists in Firebase Authentication
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // User exists, proceed to HomeActivity
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()  // Optional: Finish the LoginActivity if needed
                        // Inside your LoginActivity.kt or wherever you handle login
                        // After successful login
                        val sessionManager = SessionManager(this)
                        sessionManager.saveLoginStatus(true)

                    } else {
                        // User doesn't exist or authentication failed
                        // Show a pop-up message or handle the error as needed
                        showSignUpPrompt()
                    }
                }
        }

        textViewSignUpPrompt.setOnClickListener {
            // Clicking on the sign-up prompt, navigate to SignUpActivity
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showSignUpPrompt() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Authentication Failed")
            .setMessage("Invalid email or password. Click on Sign Up to create a new account.")
            .setPositiveButton("Sign Up") { _, _ ->
                // Navigate to SignUpActivity
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}