package com.example.ejquedadas

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance() //Variable con la que accederemos a Firestore. Será una instancia a la bd.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bundle:Bundle? = intent.extras
        txtEmail.text = bundle?.getString("email")
        val email = bundle?.getString("email").toString()
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

        btnGuardar.setOnClickListener {
            //Se guardarán en modo HashMap (clave, valor).
            var user = hashMapOf(
                "provider" to prov,
                "nombre" to txtNombre.text.toString(),
                "apellido" to txtApellidos.text.toString()
                //"roles" to arrayListOf(1, 2, 3)
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


        btnRecuperar.setOnClickListener {
            //var roles : ArrayList<Int>
            db.collection("users").document(email).get().addOnSuccessListener {
                //Si encuentra el documento será satisfactorio este listener y entraremos en él.
                txtNombre.append(it.get("nombre") as String?)
                txtApellidos.append(it.get("apelllido") as String?)
                //roles = it.get("roles") as ArrayList<Int>
                Toast.makeText(this, "Recuperado", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
            }
        }

        btnEliminar.setOnClickListener {
            db.collection("users").document(email).delete()
            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
        }

    }

}