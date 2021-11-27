package com.example.micolornote

import Auxiliar.ConexionBBDD
import Modelo.Contacto
import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Ventana_SMS : AppCompatActivity() {

    private val permissionRequest = 101

    val REQUEST_READ_CONTACTS = 79
    var mobileArray: ArrayList<String> = ArrayList()

    var seleccionado:Int = -1
    var lista_contactos: ArrayList<Contacto> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_sms)

        var lista: ListView = findViewById(R.id.listViewContactos)
        var boton_Enviar_SMS:Button = findViewById(R.id.btn_enviar_sms)
        var texto_telefono_SMS:TextView = findViewById(R.id.lbl_telefono_SMS)
        var texto_contacto_SMS:TextView = findViewById(R.id.txt_contacto_SMS)
        var texto_a_enviar:TextView = findViewById(R.id.txt_a_enviar)

        lista_contactos = obtenerListaContactos()

        var texto_sms:String = intent.getSerializableExtra("texto_sms") as String
        texto_a_enviar.text = texto_sms

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            for(Contacto in lista_contactos){
                mobileArray.add(Contacto.nombre)
            }

        } else {
            requestPermission();
        }
        var adaptador: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.tabla_contactos, R.id.txt_contacto, mobileArray)
        lista.adapter = adaptador


        lista.onItemClickListener = object: AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                vista: View?,
                pos: Int,
                idElemnto: Long
            ) {
                if(seleccionado == pos){
                    seleccionado = -1
                    boton_Enviar_SMS.isEnabled = false
                    texto_contacto_SMS.text = ""
                    texto_telefono_SMS.text = "0"
                }else{
                    seleccionado = pos
                    boton_Enviar_SMS.isEnabled = true
                    texto_contacto_SMS.text = lista_contactos.get(seleccionado).nombre
                    texto_telefono_SMS.text = lista_contactos.get(seleccionado).lista_numeros!![0]
                }

            }
        }
    }


    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS),REQUEST_READ_CONTACTS)
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS),REQUEST_READ_CONTACTS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mobileArray = this.obtenerContactos()
            } else {
                Toast.makeText(
                    this, R.string.sms_no_tienes_permisos,
                    Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    fun obtenerContactos():ArrayList<String> {
        var listaNombre:ArrayList<String>? = ArrayList()
        var cr = this.contentResolver
        var cur: Cursor? = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null,null)
        if (cur != null){
            if (cur.count > 0){
                while(cur!=null && cur.moveToNext()){
                    var id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID).toInt())
                    var nombre = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME).toInt())
                    listaNombre!!.add(nombre)
                    if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER).toInt()) > 0) {
                        val pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null)
                        //Sacamos todos los números de ese contacto.
                        while (pCur!!.moveToNext()) {
                            val phoneNo = pCur!!.getString(pCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER).toInt())
                            //Esto son los números asociados a ese contacto. Ahora mismo no hacemos nada con ellos.
                        }
                        pCur!!.close()
                    }
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        return listaNombre!!
    }


    fun obtenerListaContactos():ArrayList<Contacto> {

        //var listaNombre:ArrayList<String>? = ArrayList()

        var listaContactos:ArrayList<Contacto>? = ArrayList()

        var cr = this.contentResolver
        var cur: Cursor? = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null,null)
        if (cur != null){
            if (cur.count > 0){
                while(cur!=null && cur.moveToNext()){
                    var id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID).toInt())
                    var nombre = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME).toInt())
                    //listaNombre!!.add(nombre)
                    if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER).toInt()) > 0) {
                        var listaNums:ArrayList<String>? = ArrayList()
                        val pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null)
                        //Sacamos todos los números de ese contacto.
                        while (pCur!!.moveToNext()) {
                            val phoneNo = pCur!!.getString(pCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER).toInt())
                            listaNums!!.add(phoneNo)
                            //Esto son los números asociados a ese contacto. Ahora mismo no hacemos nada con ellos.
                        }

                        var contact:Contacto = Contacto(id,nombre,listaNums)
                        listaContactos!!.add(contact)

                        pCur!!.close()
                    }
                }
            }
        }
        if (cur != null) {
            cur.close();
        }

        return listaContactos!!

    }

    fun enviar(view: View){
        val pm = this.packageManager
        //Esta es una comprobación previa para ver si mi dispositivo puede enviar sms o no.
        if (!pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY) && !pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_CDMA)) {
            Toast.makeText(this,R.string.sms_no_puede_enviar,Toast.LENGTH_SHORT).show()
        }
        else {
            val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                miMensaje()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), permissionRequest)
            }
        }
    }

    private fun miMensaje() {

        var texto_telefono_SMS:TextView = findViewById(R.id.lbl_telefono_SMS)
        var texto_a_enviar:TextView = findViewById(R.id.txt_a_enviar)
        var myNumber:String


        if(texto_telefono_SMS.text.startsWith("+")){
            myNumber = texto_telefono_SMS.text.toString().trim().substring(1)
        }else{
            myNumber = texto_telefono_SMS.text.toString().trim()
        }

        val myMsg: String = texto_a_enviar.text.toString()
        if (myNumber == "0" || myMsg == "") {
            Toast.makeText(this, R.string.sms_seleccionar_contacto, Toast.LENGTH_SHORT).show()
        } else {
            if (TextUtils.isDigitsOnly(myNumber)) {

                val dialogo: AlertDialog.Builder = AlertDialog.Builder(this)
                dialogo.setIcon(R.drawable.cartero_sms)
                dialogo.setTitle(R.string.ventana_sms_titulo)
                dialogo.setMessage(R.string.ventana_sms_mensaje)
                dialogo.setCancelable(false)
                dialogo.setPositiveButton(R.string.ventana_si,
                    DialogInterface.OnClickListener { dialogo, id ->
                        val smsManager: SmsManager = SmsManager.getDefault()
                        smsManager.sendTextMessage(myNumber, null, myMsg, null, null)
                        Toast.makeText(this, R.string.sms_mensaje_enviado, Toast.LENGTH_SHORT).show()
                    })
                dialogo.setNegativeButton(R.string.ventana_no,
                    DialogInterface.OnClickListener { dialogo, id -> "cancelar" })
                dialogo.show()


            } else {
                Toast.makeText(this, R.string.sms_numero_no_correcto, Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun volver(view: View){
        finish();
    }

}