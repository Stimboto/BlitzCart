// SignUpActivity.kt
package com.stim.blitzcart

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val editTextName: EditText = findViewById(R.id.editTextNameSignUp)
        val editTextEmail: EditText = findViewById(R.id.editTextEmailSignUp)
        val editTextPassword: EditText = findViewById(R.id.editTextPasswordSignUp)
        val buttonSignUp: Button = findViewById(R.id.buttonSignUpSignUp)

        buttonSignUp.setOnClickListener {
            val name = editTextName.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                // Handle case where any field is empty
                // Show a toast message or other UI feedback
                return@setOnClickListener
            }

            // Create a new user with email and password
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registration success, update UI
                        val user = auth.currentUser

                        // Store user data in Firebase Realtime Database
                        user?.let {
                            val userId = it.uid
                            val usersRef = database.reference.child("users").child(userId)
                            usersRef.child("name").setValue(name)
                            usersRef.child("email").setValue(email)
                        }

                        // Show a toast message indicating successful registration
                        // You might want to show a dialog or navigate to a different screen
                        // depending on your UI design and user experience
                        // For now, let's just show a toast
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

                        // Finish the SignUpActivity
                        finish()
                    } else {
                        // If registration fails, display a message to the user
                        // Handle the error appropriately (e.g., show an error message)
                        // You might want to log the error for debugging purposes
                        Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
