package com.example.contactossms

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import android.R.id




class ListadoContactos : AppCompatActivity() {
    val REQUEST_READ_CONTACTS = 79

    var mobileArray: ArrayList<String> = ArrayList()

    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_contactos)

        var lista: ListView = findViewById(R.id.listViewContactos)

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            mobileArray = this.obtenerContactos()
        } else {
            requestPermission();
        }
        var adaptador: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.item_layout, R.id.txtItem, mobileArray)
        lista.adapter = adaptador
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS),REQUEST_READ_CONTACTS)
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)) {
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
                    this, "No tienes los permisos requeridos...",
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
                        val pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null)
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


    fun volver(view: View){
        finish();
    }



}