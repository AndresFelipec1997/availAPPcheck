package com.example.availappcheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_mi_negocio.*
import kotlinx.android.synthetic.main.negocioregistrado.view.*

class MiNegocio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_negocio)

        val adapter = GroupAdapter<ViewHolder>()

       /*val nombre= intent.getStringExtra(ListaNegocio.USER_kEY)
        val direccion= intent.getStringExtra(ListaNegocio.USER_kEY2)
        val imagen= intent.getStringExtra(ListaNegocio.USER_kEY3)*/

        val negocio = intent.getParcelableExtra<Negocio>(ListaNegocio.USER_kEY)
        if (negocio != null) {
            nombretext.setText(negocio.nombre)
        }
        if (negocio != null) {
            direcciontext.setText(negocio.direccion)
        }

        if (negocio != null) {
            Picasso.get().load(negocio.imagen).into(IMAGENNEGO)
        }
        adapter.add(NegocioItem())



    }
}

class NegocioItem(): Item<ViewHolder>(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
      /*  viewHolder.itemView.nombrenegocio.text = negocio.nombre

        Picasso.get().load(negocio.imagen).into(viewHolder.itemView.imagennegocio)*/

    }
    override fun getLayout(): Int {
        return  R.layout.activity_mi_negocio
    }


}