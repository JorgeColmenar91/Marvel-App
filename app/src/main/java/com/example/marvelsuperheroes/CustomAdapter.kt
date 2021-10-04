package com.example.marvelsuperheroes

import android.database.DataSetObserver
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Toast
import android.content.Intent
import android.provider.AlarmClock
import android.widget.TextView
import com.squareup.picasso.Picasso
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ListAdapter
import java.util.ArrayList

class CustomAdapter(var context: Context, var arrayList: ArrayList<DatosSuperHeroe>?) :
    ListAdapter {
    override fun areAllItemsEnabled(): Boolean {
        return false
    }

    override fun isEnabled(i: Int): Boolean {
        return true
    }

    override fun registerDataSetObserver(dataSetObserver: DataSetObserver) {}
    override fun unregisterDataSetObserver(dataSetObserver: DataSetObserver) {}
    override fun getCount(): Int {
        return arrayList!!.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View? {
        var view = view
        val heroe = arrayList!![i]
        if (view == null) {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.mylist, null)
            view.setOnClickListener {
                var toast: Toast
                if (heroe?.description== "") {
                    val intent = Intent(context, DetalleHeroe::class.java)
                    intent.putExtra(Intent.EXTRA_TITLE, heroe?.name)
                    intent.putExtra(
                        AlarmClock.EXTRA_MESSAGE,
                        context.resources.getString(R.string.noDetalle)
                    )
                    context.startActivity(intent)
                } else {
                    val intent = Intent(context, DetalleHeroe::class.java)
                    intent.putExtra(Intent.EXTRA_TITLE, heroe?.name)
                    intent.putExtra(AlarmClock.EXTRA_MESSAGE, heroe?.description)
                    context.startActivity(intent)
                }
            }
            val nombre = view.findViewById<TextView>(R.id.name)
            val imagen = view.findViewById<ImageView>(R.id.icon)
            nombre.text = heroe?.name
            val ruta = heroe?.datosImagen?.path?.replace("http:", "https:")
            Picasso.with(context)
                .load(ruta)
                .into(imagen)
        }
        return view
    }

    override fun getItemViewType(i: Int): Int {
        return i
    }

    override fun getViewTypeCount(): Int {
        return arrayList!!.size
    }

    override fun isEmpty(): Boolean {
        return false
    }
}