package com.example.consultasfirestore

import Adaptadores.MiAdaptadorRecycler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ProxyFileDescriptorCallback
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.*
import java.lang.Exception
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val TAG = "Fernando"
    var miArray:ArrayList<User> = ArrayList()  //Este será el arrayList que se usará para el adapter del RecyclerView o de la ListView.


    //RecyclerView
    lateinit var recycler_users : RecyclerView
    var miAdapter = MiAdaptadorRecycler(miArray, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //--------------- Sin subcolecciones --------------
        //Descomenta los crear para ver su efecto en la base de datos Firestore.
        //crearUnoAutomaticoConIndiceAleatorio()
        //crearUnoAutomaticoConIndiceNoAleatorio()
        //crearCamposArray()
        //crearCamposArray()
        //crearCamposArray()
        mostrarTodos()
        //Esto no muestra nada por el problema de los hilos. Se debe llamar al notifyDataSetChanged dentro del método que recupera los datos (ver comentarios en: mostrarTdos(), mostrarTodosSubColeccion y getTodos).
        //Log.e(TAG,"----------------")
        //for(e in miArray){
        //    Log.e(TAG,e.toString())
        //}
        //Log.e(TAG,"----------------")


        //--------------- Con subcolecciones --------------
        //crearUnaColeccionConSubColeccion()
        //mostrarTodosSubColeccion()




        //--------------------- Posibles soluciones si queremos el AL aquí ---------------------
        //---------------- SUPER CHAPUZA: NO USAR!!!!! SOLO CON FINES DIDÁCTICOS ----------
/*

        Log.e(TAG,"----------------")
        getTodos()
        GlobalScope.launch(context = Dispatchers.Main) {
            //Esto es una chapuza para ver lo de los hilos que rellenan la información.
            //Lo que hacemos es retrasar el hilo principal que está ejecutándose en onCreate
            //y dar tiempo a que getTodos acabe. Una chapuza!!!
            delay(1000)
            for(e in miArray){
                Log.e(TAG,e.toString())
            }
            Log.e(TAG,"----------------")
        }

*/

        //---------------- Usando corrutinas: esperamos que la función acabe --------------

        runBlocking {
            val job : Job = launch(context = Dispatchers.Default) {
                val datos : QuerySnapshot = getDataFromFireStore() as QuerySnapshot //Obtenermos la colección
                obtenerDatos(datos as QuerySnapshot?)  //'Destripamos' la colección y la metemos en nuestro ArrayList
            }
            //Con este método el hilo principal de onCreate se espera a que la función acabe y devuelva la colección con los datos.
            job.join() //Esperamos a que el método acabe: https://dzone.com/articles/waiting-for-coroutines
        }

        Log.e(TAG,"----------------")
        for(e in miArray){
            Log.e(TAG,e.toString())
        }
        Log.e(TAG,"----------------")
        //Aquí se pondría en el setAdapter del RecyclerView.

        //recyclerView
        recycler_users = findViewById(R.id.recyclerView_usuarios) as RecyclerView
        recycler_users.setHasFixedSize(true)
        recycler_users.layoutManager = LinearLayoutManager(this)


        recycler_users.adapter = miAdapter

    }//Fin onCreate




    //*****************************************************************************************************************
    //********************** Métodos de creación/modificación/borrado de datos ****************************************
    //*****************************************************************************************************************
    /**
     * Este método crea datos al azar con índice aleatorio y usando la opción add.
     */
    fun crearUnoAutomaticoConIndiceAleatorio(){
        // Create a new user with a first and last name
        val nombres = listOf("Alvaro","Jorge","Elena","Chema")
        val randomNombre = nombres.random()
        val apellidos = listOf("Montoya","Heredia","Pescailla","Flores")
        val randomApellido = apellidos.random()
        val edades = listOf(18, 23, 45, 67, 34, 47, 41)
        val edadRandom = edades.random()
        //val profesion = "Estudiante"

        var user = hashMapOf(
            "first" to randomNombre,
            "last" to randomApellido,
            "age" to edadRandom
            //"profesion" to profesion
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.e(TAG, "Documento añadido con ID: ${it.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e.cause)
            }
    }

    //----------------------------------------------------------------------------------------
    /**
     * Este método crea datos al azar usando set y poniendo como índice del documento el DNI.
     */
    fun crearUnoAutomaticoConIndiceNoAleatorio(){
        // Create a new user with a first and last name
        val nombres = listOf("Alvaro","Jorge","Elena","Chema")
        val randomNombre = nombres.random()
        val apellidos = listOf("Montoya","Heredia","Pescailla","Flores")
        val randomApellido = apellidos.random()
        val edades = listOf(18, 23, 45, 67, 34, 47, 41)
        val edadRandom = edades.random()
        val random = Random()
        val DNI = random.nextInt(100)

        var user = hashMapOf(
            "first" to randomNombre,
            "last" to randomApellido,
            "age" to edadRandom
        )

        // Add a new document with a generated ID
        db.collection("users")
            .document(DNI.toString())  //Si hubiera un campo duplicado, lo reemplaza.
            .set(user)
            .addOnSuccessListener {
                Log.e(TAG, "Documento añadido.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e.cause)
            }
    }


    //----------------------------------------------------------------------------------------
    /**
     * Este método crea un documento que dentro tiene un campo de tipo array (para los roles).
     */
    fun crearCamposArray(){
        // Create a new user with a first and last name
        val nombres = listOf("Alvaro","Jorge","Elena","Chema")
        val randomNombre = nombres.random()
        val apellidos = listOf("Montoya","Heredia","Pescailla","Flores")
        val randomApellido = apellidos.random()
        val edades = listOf(18, 23, 45, 67, 34, 47, 41)
        val edadRandom = edades.random()
        val random = Random()
        val DNI = random.nextInt(100)

        var user = hashMapOf(
            "first" to randomNombre,
            "last" to randomApellido,
            "age" to edadRandom,
            "roles" to arrayListOf(1, 2, 3)
        )

        // Add a new document with a generated ID
        db.collection("users")
            .document(DNI.toString())  //Si hubiera un campo duplicado, lo remplaza.
            .set(user)
            .addOnSuccessListener {
                Log.e(TAG, "Documento añadido.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e.cause)
            }
    }

    //----------------------------------------------------------------------------------------
    /**
     * Este método crea una subcolección dentro del documento para los roles. Se desaconseja según la
     * documentación consultada, ya que complica las consultas, pero su uso sería el siguiente.
     */
    fun crearUnaColeccionConSubColeccion(){
        // Create a new user with a first and last name
        val nombres = listOf("Alvaro","Jorge","Elena","Chema")
        val randomNombre = nombres.random()
        val apellidos = listOf("Montoya","Heredia","Pescailla","Flores")
        val randomApellido = apellidos.random()
        val edades = listOf(18, 23, 45, 67, 34, 47, 41)
        val edadRandom = edades.random()
        val random = Random()
        val DNI = random.nextInt(100)

        var user = hashMapOf(
            "first" to randomNombre,
            "last" to randomApellido,
            "age" to edadRandom,
        )

        // Añadimos el nuevo documento.
        db.collection("usersSubCol")
            .document(DNI.toString())  //Si hubiera un campo duplicado, lo remplaza.
            .set(user)
            .addOnSuccessListener {
                Log.e(TAG, "Documento añadido.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e.cause)
            }

        //Añadimos una subcolección con sus roles.
        db.collection("usersSubCol")
            .document(DNI.toString())
            .collection("rolesAsignados")
            .document("roles")
            .set(hashMapOf(
                "rols" to arrayListOf<Int>(1,2)
            ))
    }


    //----------------------------------------------------------------------------------------
    /**
     * https://firebase.google.com/docs/firestore/manage-data/delete-data?hl=es-419
     * Método que borrará el documento que se le pase por parámetro.
     */
    fun borrarDocumentos(d:String){
        db.collection("users").document(d)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "Documento borrado.!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error al borrar el documento.", e) }
    }


    //----------------------------------------------------------------------------------------
    /**
     * Método que borra un campo dado de un documento.
     */
    fun borrarCampoDocumento(d:String, c:String){
        val docRef = db.collection("users").document(d)

        val updates = hashMapOf<String, Any>(
            c to FieldValue.delete()
        )

        docRef.update(updates).addOnCompleteListener {
            Log.d(TAG, "Campo borrado.!")
        }
    }



    //********************************************************************************************
    //********************** Métodos de consulta de datos ****************************************
    //********************************************************************************************

    //********************************************************************************************
    /*
    Firestore, al igual que todas las APIs de google, devuelve un objeto Task en todas las operaciones asíncronas. Esta clase tiene 3 metodos principales:

    addOnSuccessListener: agrega un callback que se ejecuta sólo si la operacion fue exitosa
    addOnFailureListener: agrega un callback que se ejecuta cuando la operacion falla
    addOnCompleteListener: agrega un callback que se ejecuta en ambos casos

    Como dato importante: rellenaremos el array dentro de la consulta, pero la consulta es asíncrona, se realiza
    en un hilo independiente por lo que veremos que si mostramos el array después de hacer la consulta: NO TIENE NADA.
    Esto es porque la muestra del array se hace mientras que se está rellenando el array en la consulta.
    Una solución es hacer corutinas y funciones suspend.
    Otra solución, ya que lo usaremos para rellenar recyclerViews y listViews es hacer un notifySetChanged dentro de la consulta, cuando esta acabe y
    dentro del recyclerView podremos manipular los datos (ver ejemplos abajo).


    Y otra solución es usar una función suspend que será esperada por el hilo principal hasta que acabe (ver bloque de onCreate: runBlocking).

    */


    /**
     * Esta función accede a la colección users. Si el documento tiene roles los carga, en su defecto carga un array vacío (por coherencia de datos).
     * Dentro de esta función se rellena en el listener el arrayList que deberemos asignar al RecyclerView y, dentro del listener, cuando acabe
     * la carga de datos, notificaremos los cambios del adaptador para que cargue el RV (comentado el punto indicado de la notificación).
     */
    fun mostrarTodos(){
        var miArray:ArrayList<User> = ArrayList()
        //Coger todos los elementos de la colección.
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var roles : ArrayList<Int>

                    if (document.get("roles") != null){
                         roles = document.get("roles") as ArrayList<Int>
                    }
                    else {
                         roles = arrayListOf()
                    }
                    var al = User(
                        document.get("age").toString(),
                        document.get("first").toString(),
                        document.get("last").toString(),
                        roles
                    )
                    Log.e(TAG, al.toString())
                    miArray.add(al)
                }
                Log.e(TAG,miArray.toString())
                //Aquí se llamaría a: miAdaptador.notifyDataSetChanged() !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                miAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error: ", exception)
            }

            //De esta forma no se rellena el miArray porque se llega a esta línea mientras addOnSuccessListener todavía está funcionando. Los hilos y sus cosejas.
            //Esto es lo que se explicaba en el comentario anterior.
            Log.e(TAG,"----- ******  -----")
            for(e in miArray){
                Log.e(TAG,e.toString())
            }
            Log.e(TAG,"----- ******  -----")
    }


    //-------------------------------------------------------------------------------------------
    /**
     * Este método carga los datos, exactamente igual que el de antes, pero usando otro listener: addSnapshotListener.
     * Dentro de esta función se rellena en el listener el arrayList que deberemos asignar al RecyclerView y, dentro del listener, cuando acabe
     * la carga de datos, notificaremos los cambios del adaptador para que cargue el RV (comentado el punto indicado de la notificación).
     */
    fun getTodos() {

        db.collection("users").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    Log.e(TAG,error.message.toString())
                }
                else {
                    for(dc:DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            //miAr.add(dc.document.toObject(User::class.java))
                            var roles : ArrayList<Int>

                            if (dc.document.get("roles") != null){
                                roles = dc.document.get("roles") as ArrayList<Int>
                            }
                            else {
                                roles = arrayListOf()
                            }
                            var al = User(
                                dc.document.get("age").toString(),
                                dc.document.get("first").toString(),
                                dc.document.get("last").toString(),
                                roles
                            )
                            //Log.e(TAG, al.toString())
                            miArray.add(al)
                        }
                    }
                    //Log.e(TAG,miArray.toString())
                    //Aquí se llamaría a: miAdaptador.notifyDataSetChanged() !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    miAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    //-------------------------------------------------------------------------------------------
    /**
     * Este método carga los datos de la colección usersSubCol que tiene los roles como una subcolección.
     * Dentro de esta función se rellena en el listener el arrayList que deberemos asignar al RecyclerView y, dentro del listener, cuando acabe
     * la carga de datos, notificaremos los cambios del adaptador para que cargue el RV (comentado el punto indicado de la notificación).
     */
    fun mostrarTodosSubColeccion(){

        //Coger todos los elementos de la colección.
        db.collection("usersSubCol")
            .get()
            .addOnSuccessListener { result ->
                //val all = result.documents.toMutableList()
                //Log.e("AL", all.toString())
                var roles : ArrayList<Int> = arrayListOf()
                for (document in result) {
                    //Ejecutamos una subconsulta para la subcollection
                    val col = db.collection("usersSubCol").document(document.id).collection("rolesAsignados")
                    col.get().addOnSuccessListener { resul ->
                        for (d in resul) {
                            roles = d.get("rols") as ArrayList<Int>
                        }
                        var al = User(
                            document.get("age").toString(),
                            document.get("first").toString(),
                            document.get("last").toString(),
                            roles
                        )
                        Log.e(TAG, al.toString())
                        miArray.add(al)
                        //Log.e(TAG,miArray.toString())
                        //Aquí se llamaría a: miAdaptador.notifyDataSetChanged() !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        miAdapter.notifyDataSetChanged()
                    //En este caso, otro problema de usar las subcolecciones es que llamaríamos varias veces al notify (una por cada documento cargado).
                    }
                }
                }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error: ", exception)
            }
    }



    //------------------------------------------------------------------------------------------
    /**
     * Este método es una función suspend que esperará a que la consulta se realiza. Será llamada
     * en un scope (entorno) de corrutinas. Hilos (ver onCreate, runBlocking).
     */
    suspend fun getDataFromFireStore()  : QuerySnapshot? {
        return try{
            val data = db.collection("users")
                //.whereEqualTo("age", 41)
                .whereGreaterThanOrEqualTo("age",40)  //https://firebase.google.com/docs/firestore/query-data/order-limit-data?hl=es-419
                .orderBy("age", Query.Direction.DESCENDING)
                //.limit(4) //Limita la cantidad de elementos mostrados.
                .get()
                .await()
            data
        }catch (e : Exception){
            null
        }
    }

    /**
     * Función que recuperará los datos obtenidos del método: getDataFromFireStore().
     * Llamada también desde el entorno de corrutinas: (ver onCreate, runBlocking).
     */
    private fun obtenerDatos(datos: QuerySnapshot?) {
        for(dc:DocumentChange in datos?.documentChanges!!){
            if (dc.type == DocumentChange.Type.ADDED){
                //miAr.add(dc.document.toObject(User::class.java))
                var roles : ArrayList<Int>

                if (dc.document.get("roles") != null){
                    roles = dc.document.get("roles") as ArrayList<Int>
                }
                else {
                    roles = arrayListOf()
                }
                var al = User(
                    dc.document.get("age").toString(),
                    dc.document.get("first").toString(),
                    dc.document.get("last").toString(),
                    roles
                )
                //Log.e(TAG, al.toString())
                miArray.add(al)
            }
        }
    }

}
