package com.example.availappcheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        iniciar.setOnClickListener {

            if (editTextTextPersonName.text.isEmpty() ||  EdidTextContraseña.text.isEmpty()){

                showAlet()

            }

           else if( editTextTextPersonName.text.isNotEmpty() &&  EdidTextContraseña.text.isNotEmpty() ){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextTextPersonName.text.toString(),EdidTextContraseña.text.toString()).addOnCompleteListener {

                    val ref= FirebaseDatabase.getInstance().getReference("/User")

                      if(it.isSuccessful){

                        home( it.result?.user?.email ?:"", it.result?.user?.displayName ?:"", )



                    }else {
                        showAlet()

                    }

                }

            }
        }



    }

    private fun showAlet() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("se a producido error de autentificación")
        builder.setPositiveButton("aceptar",null)
        val dialogo: AlertDialog = builder.create()
        dialogo.show()
    }

    private fun home (email: String, nombre: String){
        EdidTextContraseña.text.clear()

        val homeIntent = Intent(this, PrincipalScreen::class.java).apply {
            putExtra("email",email)
            putExtra("nombre",nombre)

        }

        startActivity(homeIntent)

    }







}

