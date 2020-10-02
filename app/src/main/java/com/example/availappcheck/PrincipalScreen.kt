package com.example.availappcheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_principal_screen.*
import kotlinx.android.synthetic.main.activity_register2.*

class PrincipalScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_screen)

        val bundle: Bundle? = intent.extras
        var email: String? = bundle?.getString("email")

        val nombre: String? = bundle?.getString("nombre")

        setup(email ?: "", nombre ?: "")


        cerrar.setOnClickListener() {

            FirebaseAuth.getInstance().signOut()
            onBackPressed()

        }


    }

    private fun setup(email: String?, nombre: String) {
        title = "inicio"
        emailView.text = email
        nombreView.text = nombre


    }




}