package com.example.ejemploautenticacionfirebase

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var btCerrarSesion: Button
    private lateinit var txtCorreo: TextView
    private lateinit var txtProveedor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        title = "Inicio"


        btCerrarSesion = findViewById(R.id.btCerrarSesion)
        txtCorreo = findViewById(R.id.txtEmail)
        txtProveedor = findViewById(R.id.txtProveedor)

        val bundle:Bundle? = intent.extras
        txtCorreo.text = bundle?.getString("email")
        val prov:String = bundle?.getString("provider").toString()
        txtProveedor.text = bundle?.getString("provider").toString()

        //Guardado de datos para toda la aplicación en la sesión.
        val prefs: SharedPreferences.Editor? = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs?.putString("email",bundle?.getString("email"))
        prefs?.putString("provider",bundle?.getString("provider"))
        prefs?.apply () //Con estos datos guardados en el fichero de sesión, aunque la app se detenga tendremos acceso a los mismos.

        btCerrarSesion.setOnClickListener {
            val prefs: SharedPreferences.Editor? = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs?.clear() //Al cerrar sesión borramos los datos
            prefs?.apply ()
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}