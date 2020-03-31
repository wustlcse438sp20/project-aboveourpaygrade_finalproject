package com.example.finalproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_signup.*
import java.text.DecimalFormat


class SignupFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = Firebase.database
        mAuth = FirebaseAuth.getInstance();
        btnSignUp.setOnClickListener {
            if (txtUsername.text.toString() == "" || txtPassword.text.toString() == "") {
                val toast = Toast.makeText(
                    context,
                    "Please enter a username and password",
                    Toast.LENGTH_SHORT
                )
                toast.show()
                return@setOnClickListener
            }

            if (txtPassword.text.toString().length < 6) {
                val toast = Toast.makeText(
                    context,
                    "Password must be at least 6 characters",
                    Toast.LENGTH_SHORT
                )
                toast.show()
                return@setOnClickListener
            }

            try {
                mAuth.createUserWithEmailAndPassword(
                    txtUsername.text.toString(),
                    txtPassword.text.toString()
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Registration successful!", Toast.LENGTH_LONG)
                                .show()

                            database.getReference(id(mAuth.currentUser!!.email!!)).setValue(500)


                            val fos = context?.openFileOutput("login", Context.MODE_PRIVATE)
                            fos!!.write(txtUsername.text.toString().toByteArray())
                            fos.close()

                            val intent = Intent(context, MainActivity::class.java)
                            intent.putExtra("player_name", txtUsername.text.toString())
                            startActivity(intent)

                        } else {

                            var failureType = " Please try again later."

                            try {
                                failureType = task.exception.toString().split(":")[1]
                            } catch (e: Exception) {

                            }
                            Toast.makeText(
                                context,
                                "Registration failed!$failureType",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Something went horribly wrong. Ensure valid inputs and try again.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun id(s: String): String {
        var result = ""
        val form = DecimalFormat("000")
        for (c in s.toCharArray()) {
            result += form.format(c.toInt())
        }
        return result
    }
}