package com.example.contactossms

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

//https://www.develou.com/acceder-los-contactos-de-android/
//https://www.tutorialspoint.com/how-to-read-all-contacts-in-android
class MainActivity : AppCompatActivity() {
    private val permissionRequest = 101
    lateinit var btnEnviar: Button
    lateinit var editTextNumero: EditText
    lateinit var editTextMensaje: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNumero = findViewById(R.id.edNumero)
        editTextMensaje = findViewById(R.id.edCuerpo)
        btnEnviar = findViewById(R.id.btnEnviar)


    }

    fun enviar(view: View){
        val pm = this.packageManager
        //Esta es una comprobación previa para ver si mi dispositivo puede enviar sms o no.
        if (!pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY) && !pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_CDMA)) {
            Toast.makeText(this,"Lo sentimos, tu dispositivo probablemente no pueda enviar SMS...",Toast.LENGTH_SHORT).show()
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
        val myNumber: String = editTextNumero.text.toString().trim()
        val myMsg: String = editTextMensaje.text.toString()
        if (myNumber == "" || myMsg == "") {
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show()
        } else {
            if (TextUtils.isDigitsOnly(myNumber)) {
                val smsManager: SmsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(myNumber, null, myMsg, null, null)
                Toast.makeText(this, "Mensaje enviado...", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "El número no es correcto...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Esta función se lanzará automáticamente cuando se acepten/denieguen, la primera vez, los permisos.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionRequest) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                miMensaje();
            } else {
                Toast.makeText(this, "No tienes los permisos requeridos...",
                    Toast.LENGTH_SHORT).show();
            }
        }
    }

    fun listarContactos(view : View){
        var intentListado = Intent(this, ListadoContactos::class.java)
        startActivity(intentListado)
    }
}