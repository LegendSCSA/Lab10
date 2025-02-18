package com.shafie.lab10

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shafie.lab10.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        setContentView(view)

        binding.signupBtn.setOnClickListener {
            val email = binding.regEmailEditText.text.toString()
            val password = binding.regPassword.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task ->
                    if(task.isSuccessful){

                        val newPerson = hashMapOf(
                            "name" to binding.regNameEditText.text.toString(),
                            "city" to binding.regCityEditText.text.toString(),
                            "country" to binding.regCountryEditText.text.toString(),
                            "phone" to binding.regPhone.text.toString(),
                            "email" to binding.regEmailEditText.text.toString()
                        )

                        db.collection("users")
                            .document(auth.currentUser!!.uid)
                            .set(newPerson)

                        Snackbar.make(binding.root,"Successfully Registered",
                            Snackbar.LENGTH_LONG).show()
                        finish()
                    }
                    else{
                        Snackbar.make(binding.root, "Registration Failed",
                            Snackbar.LENGTH_LONG).show()
                    }
                }
        }
    }
}