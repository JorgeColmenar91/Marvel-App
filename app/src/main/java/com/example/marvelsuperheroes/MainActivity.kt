package com.example.marvelsuperheroes

import android.widget.Toast
import android.content.Context
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import org.json.JSONArray
import org.json.JSONException
import java.lang.StringBuilder
import java.net.MalformedURLException
import java.net.URL
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    val PUBLIC_KEY_MARVEL = "46af74bf3c8fdf77f6c287b257e52833"
    val PRIVATE_KEY_MARVEL = "53797796eb8961f79622998ad2a7de48a472b52b"
    val URL = "https://gateway.marvel.com:443/"
    val LISTAR_HEROES = "v1/public/characters?"
    val DATA = "data"
    val RESULTS = "results"
    val TS = "ts=1"
    private var list: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list = findViewById(R.id.list)
        recuperarListaSuperHeroes(this)
    }

    private fun recuperarListaSuperHeroes(context: Context) {
        val conexion = "$URL$LISTAR_HEROES$TS&apikey=$PUBLIC_KEY_MARVEL&hash=" + md5(
            "1$PRIVATE_KEY_MARVEL$PUBLIC_KEY_MARVEL"
        )
        try {
            val url = URL(conexion)
            val queue = Volley.newRequestQueue(context)
            queue.start()
            val jsonObjectRequest =
                JsonObjectRequest(Request.Method.GET, url.toString(), null, { response ->
                    try {
                        val data = response[DATA] as JSONObject
                        val jsonArray = data[RESULTS] as JSONArray
                        val arrayList = modelarDatos(jsonArray)?.resultado
                        val customAdapter = CustomAdapter(context, arrayList)
                        list!!.adapter = customAdapter
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }) {
                    val toast = Toast.makeText(context, R.string.errorConexion, Toast.LENGTH_LONG)
                    toast.show()
                }
            queue.add(jsonObjectRequest)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
    }

    private fun modelarDatos(listaSinTratar: JSONArray): DatosRespuesta {
        val ID = "id"
        val NAME = "name"
        val DESCRIPCION = "description"
        val THUMBNAIL = "thumbnail"
        val PATH = "path"
        val EXTENSION = "extension"
        val salida = DatosRespuesta()
        val listaHeroes = ArrayList<DatosSuperHeroe>()
        for (i in 0 until listaSinTratar.length()) {
            val heroe = DatosSuperHeroe()
            val imagen = ImagenSuperHeroe()
            var objetoSinTratar: JSONObject? = null
            try {
                objetoSinTratar = listaSinTratar.getJSONObject(i)
                heroe.id = objetoSinTratar.getString(ID)
                heroe.name = objetoSinTratar.getString(NAME)
                heroe.description = objetoSinTratar.getString(DESCRIPCION)
                val imagenSinTratar = objetoSinTratar.getJSONObject(THUMBNAIL)
                imagen.path = imagenSinTratar[PATH] as String + "." + imagenSinTratar[EXTENSION]
                heroe.datosImagen = imagen
                listaHeroes.add(heroe)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        salida.resultado = listaHeroes
        return salida
    }

    companion object {
        private fun md5(s: String): String {
            val MD5 = "MD5"
            try {
                // Create MD5 Hash
                val digest = MessageDigest
                    .getInstance(MD5)
                digest.update(s.toByteArray())
                val messageDigest = digest.digest()

                // Create Hex String
                val hexString = StringBuilder()
                for (aMessageDigest in messageDigest) {
                    var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                    while (h.length < 2) h = "0$h"
                    hexString.append(h)
                }
                return hexString.toString()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return ""
        }
    }
}