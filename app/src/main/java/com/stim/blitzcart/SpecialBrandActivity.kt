package com.stim.blitzcart



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.stim.blitzcart.databinding.ActivitySpecialBrandBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

enum class UserType {
    USER,
    BRAND_OWNER
}

class SpecialBrandActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpecialBrandBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpecialBrandBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            // Show a dialog to determine user or brand owner
            showUserOrOwnerDialog()
        }
    }

    private fun showUserOrOwnerDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Role")
            .setItems(arrayOf("User", "Brand Owner")) { _, which ->
                when (which) {
                    0 -> {
                        // User selected, proceed with user login
                        showLoginDialog(UserType.USER)
                    }
                    1 -> {
                        // Brand Owner selected, show pop-up for owner ID
                        showOwnerIdPopup()
                    }
                }
            }
        builder.show()
    }

    private fun showLoginDialog(userType: UserType) {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Authentication successful, check user type and redirect accordingly
                    if (userType == UserType.USER) {
                        startActivity(Intent(this, SpecialBrandProductActivity::class.java))
                    } else {
                        startActivity(Intent(this, SpecialBrandOwnerActivity::class.java))
                    }
                } else {
                    showSignUpPrompt()
                }
            }
    }

    private fun showSignUpPrompt() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Authentication Failed")
            .setMessage("Invalid email or password. Click on Sign Up to create a new account.")
            .setPositiveButton("Sign Up") { _, _ ->
                // Navigate to SignUpActivity
                val intent = Intent(this@SpecialBrandActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showOwnerIdPopup() {
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(R.layout.popup_owner_id)
            .setTitle("Enter Brand Owner ID")

        val alertDialog = dialogBuilder.create()

        val ownerIDEditText: EditText? = alertDialog.findViewById(R.id.ownerIDEditText)

        alertDialog.findViewById<Button?>(R.id.submitButton)?.setOnClickListener {
            val ownerID = ownerIDEditText?.text.toString()

            // Check the ownerID in Firebase Realtime Database
            checkOwnerID(ownerID)

            // Dismiss the pop-up
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun checkOwnerID(ownerID: String) {
        val databaseReference = FirebaseDatabase.getInstance().reference.child("brandOwners")
        databaseReference.child(ownerID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Owner ID is valid, open SpecialBrandOwnerActivity
                    Log.d("SpecialBrandActivity", "Owner ID is valid. Starting SpecialBrandOwnerActivity.")
                    startActivity(Intent(this@SpecialBrandActivity, SpecialBrandOwnerActivity::class.java))
                } else {
                    // Owner ID is not valid, show an error message or handle as needed
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
            }
        })
    }


}