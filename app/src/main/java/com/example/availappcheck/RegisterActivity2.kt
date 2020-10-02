package com.example.availappcheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register2.*

class RegisterActivity2 : AppCompatActivity() {
    private lateinit var nombre: EditText
    private lateinit var apellido: EditText
    private lateinit var email: EditText
    private lateinit var contraseña : EditText
    private lateinit var cedula : EditText


    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        nombre=findViewById(R.id.txtnombre)
        apellido=findViewById(R.id.txtapellido)
        email=findViewById(R.id.txtemail)
        contraseña=findViewById(R.id.EdidTextContraseña)
        cedula=findViewById(R.id.txtcedula
        )


        database= FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        dbReference=database.reference.child("User")
        title = "Crear Cuenta"

        buttoncrear.setOnClickListener(){
            if( txtemail.text.isNotEmpty() &&  EdidTextContraseña.text.isNotEmpty() ){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(txtemail.text.toString(),EdidTextContraseña.text.toString()).addOnCompleteListener {
                    if(EdidTextContraseña.length()<8){
                        showAlet()
                    }

                    if (it.isSuccessful){
                        if(EdidTextContraseña.length()<8){
                            showAlet()
                        }else {
                            crearNuevaCuenta()
                        }
                    }

                    else{
                        showAlet1()

                    }

                }}}
    }

    private fun crearNuevaCuenta(){
        val nombre:String=txtnombre.text.toString()
        val apellido:String=txtapellido.text.toString()
        val email:String=txtemail.text.toString()
        val contraseña:String=EdidTextContraseña.text.toString()
        val cedula:String=txtcedula.text.toString()


        if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellido) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(contraseña) && !TextUtils.isEmpty(cedula) && contraseña.length>=8 ){


            auth.createUserWithEmailAndPassword(email,contraseña).addOnCompleteListener(this){
                    task ->
                if(task.isComplete){
                    val user: FirebaseUser?=auth.currentUser
                    verificacion(user)

                    val userBD=dbReference.child(cedula)
                    userBD.child("nombre").setValue(nombre)
                    userBD.child("apellido").setValue(apellido)
                    userBD.child("correo").setValue(email)
                    userBD.child("contraseña").setValue(contraseña)
                    userBD.child("Cedula").setValue(cedula)



                }
            }


        }

    }

    private fun verificacion(user: FirebaseUser?){
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(){ task ->
                if(task.isComplete){
                    Toast.makeText(getApplicationContext(), "Cuenta creada con éxito. Email de confirmación enviado", Toast.LENGTH_LONG).show();
                    accion()
                }else{
                    Toast.makeText(getApplicationContext(), "Error al enviar email de confirmación", Toast.LENGTH_LONG).show();
                }
            }
        }

    }
    private fun accion(){

        val intent = Intent(this,MainActivity::class.java)

        startActivity(intent)
    }

    private fun showAlet() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error de contraseña")
        builder.setMessage("LA contraseña debe ser mayor a 7 dígitos.")
        builder.setPositiveButton("Aceptar",null)
        val dialogo: AlertDialog = builder.create()
        dialogo.show()
    }
    private fun showAlet1() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Datos erroneos o ya se ha verificado una cuenta con éste Email")
        builder.setPositiveButton("Aceptar",null)
        val dialogo: AlertDialog = builder.create()
        dialogo.show()
    }
}