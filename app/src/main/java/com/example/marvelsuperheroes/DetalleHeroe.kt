package com.example.marvelsuperheroes

import com.example.marvelsuperheroes.DatosSuperHeroe
import android.database.DataSetObserver
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.marvelsuperheroes.R
import android.widget.Toast
import android.content.Intent
import com.example.marvelsuperheroes.DetalleHeroe
import android.provider.AlarmClock
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.example.marvelsuperheroes.ImagenSuperHeroe
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelsuperheroes.MainActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import org.json.JSONArray
import com.example.marvelsuperheroes.CustomAdapter
import org.json.JSONException
import com.android.volley.VolleyError
import com.example.marvelsuperheroes.DatosRespuesta

class DetalleHeroe : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.heroe_detail)
        val intent = intent
        val descripcion = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE)
        val name = intent.getStringExtra(Intent.EXTRA_TITLE)
        val heroe_description = findViewById<TextView>(R.id.heroe_description)
        heroe_description.text = descripcion
        val heroe_name = findViewById<TextView>(R.id.heroe_name)
        heroe_name.text = name
        val volver = findViewById<Button>(R.id.volver)
        volver.setOnClickListener { finish() }
    }
}