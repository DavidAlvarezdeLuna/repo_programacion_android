package com.example.datosfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        val email = bundle?.getString("email").toString()
        txtCorreo.text = bundle?.getString("email")
        val prov:String = bundle?.getString("provider").toString()
        txtProveedor.text = bundle?.getString("provider").toString()

        btCerrarSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

        btGuardar.setOnClickListener {
            //Se guardarán en modo HashMap (clave, valor).
            var user = hashMapOf(
                "provider" to prov,
                "address" to txtDireccion.text.toString(),
                "phone" to txtTfno.text.toString()
                //"roles" to arrayListOf(1, 2, 3)
            )

            db.collection("users")
                .document(email) //Será la clave del documento.
                .set(user).addOnSuccessListener {
                    Toast.makeText(this, "Almacenado",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "Ha ocurrido un error",Toast.LENGTH_SHORT).show()
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


        btRecuperar.setOnClickListener {
            //var roles : ArrayList<Int>
            db.collection("users").document(email).get().addOnSuccessListener {
                    //Si encuentra el documento será satisfactorio este listener y entraremos en él.
                    txtDireccion.append(it.get("address") as String?)
                    txtTfno.append(it.get("phone") as String?)
                    //roles = it.get("roles") as ArrayList<Int>
                Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Algo ha ido mal al recuperar",Toast.LENGTH_SHORT).show()
            }
        }

        btEliminar.setOnClickListener {
            db.collection("users").document(email).delete()
            Toast.makeText(this, "Eliminado",Toast.LENGTH_SHORT).show()
        }
    }
}