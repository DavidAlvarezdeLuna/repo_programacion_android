package com.example.ejercicio_firebase_con_permisos

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance() //Variable con la que accederemos a Firestore. Será una instancia a la bd.

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
        val email = bundle?.getString("email").toString()
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

        btn_guardar.setOnClickListener {
            //Se guardarán en modo HashMap (clave, valor).
            var user = hashMapOf(
                "provider" to prov,
                "nombre" to txt_nombre.text.toString(),
                "apellido" to txt_apellido.text.toString(),
                "roles" to arrayListOf(1, 2, 3)
            )

            db.collection("users")
                .document(email) //Será la clave del documento.
                .set(user).addOnSuccessListener {
                    Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                }




            //Otra forma
            /*
            db.collection("users")
                .add(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Almacenado",Toast.LENGTH_SHORT).show()
                    Log.e("Fernando", "Documento añadido con ID: ${it.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("Fernando", "Error adding document", e.cause)
                }
             */

        }

        btn_recuperar.setOnClickListener {
            var roles : ArrayList<Int>
            db.collection("users").document(email).get().addOnSuccessListener { //aqui puedes poner document ->
                //Si encuentra el documento será satisfactorio este listener y entraremos en él.
                txt_nombre.append(it.get("nombre") as String?)
                txt_apellido.append(it.get("apellido") as String?)
                roles = it.get("roles") as ArrayList<Int>
                Toast.makeText(this, "Recuperado", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
            }
        }

        btn_eliminar.setOnClickListener {
            db.collection("users").document(email).delete()
            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
        }

    }
}