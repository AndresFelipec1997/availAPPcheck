package com.example.availappcheck

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_registrar_negocio.*
import java.util.*


class registrarNegocio : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var direccion: EditText
    private lateinit var email: EditText
    private lateinit var contraseña : EditText
   var rl: String?=null


    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_negocio)

        nombre=findViewById(R.id.nombrenegocio)
        direccion=findViewById(R.id.direccionnegocio)
        email=findViewById(R.id.emailnegocio)
        contraseña=findViewById(R.id.contraseñanegocio)

        database= FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        dbReference=database.reference.child("negocio")
        title = "Crear Cuenta"

        val nombreMegocio = nombrenegocio.text.toString()
        val direccion = direccionnegocio.text.toString()
        val imagenPerfil: String? = null
        imagebuton.setOnClickListener{

            val intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent, 0)
        }

        crearbuton.setOnClickListener(){
            if( emailnegocio.text.isNotEmpty() &&  contraseñanegocio.text.isNotEmpty() ){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    emailnegocio.text.toString(),
                    contraseñanegocio.text.toString()
                ).addOnCompleteListener {
                    if(contraseñanegocio.length()<8){
                        showAlet()
                    }

                    if (it.isSuccessful){
                        if(contraseñanegocio.length()<8){
                            showAlet()
                        }else {
                            subirImagenFirebase()

                        }
                    }

                    else{
                        showAlet1()

                    }

                }}}
    }

    private fun subirImagenFirebase(){
        if (selectedPhotoUri==null) return
        val filename = UUID.randomUUID().toString()
        val ref=FirebaseStorage.getInstance().getReference("/imagenes/$filename")
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("registrarNegocio", "subida con exito ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    // it.toString()
                    Log.d("registrarNegocio", "url de la imagen $it" )
                    Log.d("url","url guardada $rl")
                    crearNuevaCuenta(it.toString())
                }


            }

    }

    private fun crearNuevaCuenta(imageb: String){
        val nombre:String=nombrenegocio.text.toString()
        val direccion:String=direccionnegocio.text.toString()
        val email:String=emailnegocio.text.toString()
        val contraseña:String=contraseñanegocio.text.toString()





        if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(direccion) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(
                contraseña
            ) && contraseña.length>=8 ){


            auth.createUserWithEmailAndPassword(email, contraseña).addOnCompleteListener(this){ task ->
                if(task.isComplete){
                    val negocio: FirebaseUser?=auth.currentUser
                    verificacion(negocio)

                    val negocioBD=dbReference.child(nombre)
                    negocioBD.child("nombre").setValue(nombre)
                    negocioBD.child("direccion").setValue(direccion)
                    negocioBD.child("correo").setValue(email)
                    negocioBD.child("contraseña").setValue(contraseña)
                    negocioBD.child("imagen").setValue(imageb)



                }
            }


        }

    }



    private fun verificacion(negocio: FirebaseUser?){
        if (negocio != null) {

            negocio.sendEmailVerification().addOnCompleteListener(){ task ->
                if(task.isComplete){
                  //  subirImagenFirebase()
                    Toast.makeText(
                        getApplicationContext(),
                        "Cuenta creada con éxito. Email de confirmación enviado",
                        Toast.LENGTH_LONG
                    ).show();
                    accion()
                }else{
                    Toast.makeText(
                        getApplicationContext(),
                        "Error al enviar email de confirmación",
                        Toast.LENGTH_LONG
                    ).show();
                }
            }
        }

    }

    private fun accion(){

        val intent = Intent(this, PrincipalScreen::class.java)

        startActivity(intent)
    }

    private fun showAlet() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error de contraseña")
        builder.setMessage("LA contraseña debe ser mayor a 7 dígitos.")
        builder.setPositiveButton("Aceptar", null)
        val dialogo: AlertDialog = builder.create()
        dialogo.show()
    }
    private fun showAlet1() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Datos erroneos o ya se ha verificado una cuenta con éste Email")
        builder.setPositiveButton("Aceptar", null)
        val dialogo: AlertDialog = builder.create()
        dialogo.show()
    }




    var selectedPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK &&  data != null ){

            selectedPhotoUri=  data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

             circleimageview.setImageBitmap(bitmap)
            imagebuton.alpha=0f
            //val bitmamDrawable = BitmapDrawable(bitmap)
           // imagebuton.setBackgroundDrawable(bitmamDrawable)

        }
    }




    }

@Parcelize
class Negocio(val uid: String, val nombre: String, val direccion: String, val correo: String, val imagen: String):
    Parcelable {

    constructor(): this("","","","","")
}







