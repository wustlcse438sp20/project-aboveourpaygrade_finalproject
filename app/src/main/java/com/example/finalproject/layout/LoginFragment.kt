package com.example.finalproject.layout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            if (txtUsername.text.toString() == "" || txtPassword.text.toString() == "") {
                val toast = Toast.makeText(
                    context,
                    "Please enter a username and password",
                    Toast.LENGTH_SHORT
                )
                toast.show()
                return@setOnClickListener
            }

            try {
                mAuth.signInWithEmailAndPassword(
                    txtUsername.text.toString(),
                    txtPassword.text.toString()
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                context, "Login successful!", Toast.LENGTH_LONG
                            ).show()

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
                                "Login failed!$failureType",
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
}