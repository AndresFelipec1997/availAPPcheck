package com.example.availappcheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_lista_negocio.*
import kotlinx.android.synthetic.main.negocioregistrado.view.*

class ListaNegocio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_negocio)

        atrasbutton.setOnClickListener(){
            val intent = Intent(this,PrincipalScreen::class.java)

            startActivity(intent)


        }

     verificarnegocio()
     val adapter = GroupAdapter<ViewHolder>()


        listanegocio_view.adapter=adapter

        fetchser()


    }

    companion object{

        val USER_kEY = "USER_KEY"

    }

    private fun fetchser() {
        val ref=FirebaseDatabase.getInstance().getReference("/negocio")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()
                snapshot.children.forEach {
                 Log.d("negocios",it.toString())
                    val negocio = it.getValue(Negocio::class.java)
                    if (negocio != null){
                        adapter.add(NegocioList(negocio))
                    }

                }
                
                adapter.setOnItemClickListener { item, view ->

                    val neociolist = item as NegocioList
                    val intent = Intent(view.context,MiNegocio::class.java)
                    intent.putExtra(USER_kEY,neociolist.negocio)

                    startActivity(intent)
                }

                listanegocio_view.adapter=adapter
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun verificarnegocio() {
        val uid = FirebaseAuth.getInstance()
        if (uid == null){
            Toast.makeText(
                getApplicationContext(),
                "No hay negocios para mostrar",
                Toast.LENGTH_LONG
            ).show();
            val intent= Intent(this,PrincipalScreen::class.java)
            startActivity(intent)

        }

    }



}

class NegocioList(val negocio: Negocio): Item<ViewHolder>(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.nombrenegocio.text = negocio.nombre

        Picasso.get().load(negocio.imagen).into(viewHolder.itemView.imagennegocio)

    }
    override fun getLayout(): Int {
        return  R.layout.negocioregistrado
    }


}

