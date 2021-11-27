package com.example.transiciones

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

//https://www.develou.com/usar-transiciones-en-android-con-material-design/
//https://academiaandroid.com/proyecto-android-transiciones-y-animaciones-con-material-design/
//https://developer.android.com/training/transitions/start-activity?hl=es-419
//https://developer.android.com/training/animation
//https://developer.android.com/guide/topics/graphics/drawable-animation --> Animar mapas de bits.
//https://developer.android.com/training/animation/layout
//https://developer.android.com/training/transitions
//https://developer.android.com/training/transitions/start-activity
//https://medium.com/android-news/the-complete-android-splash-screen-guide-c7db82bce565

class MainActivity : AppCompatActivity() {
    lateinit var bluetoothAnimation: AnimationDrawable
    var pausar = false
    lateinit var ed1 : EditText
    lateinit var ed2 : EditText
    lateinit var ed3 : EditText
    lateinit var tx : TextView
    lateinit var btAnt : Button
    lateinit var btSig : Button
    lateinit var btFlipper : Button
    lateinit var imagen : ImageView
    lateinit var miView : ViewAnimator



    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Transiciones)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        miView = findViewById(R.id.vaMiViewAnimeitor)
        ed1 = findViewById(R.id.ed1)
        ed2 = findViewById(R.id.ed2)
        ed3 = findViewById(R.id.ed3)
        tx = findViewById(R.id.txtTexto)
        btAnt = findViewById(R.id.btnAnterior)
        btSig = findViewById(R.id.btnSiguiente)
        btFlipper = findViewById(R.id.btFlipper)
        imagen = findViewById(R.id.img)

        imagen.apply {
            setBackgroundResource(R.drawable.bluetooth_animation)
            bluetoothAnimation = background as AnimationDrawable
        }
        imagen.setOnClickListener {
            if (!pausar) {
                bluetoothAnimation.start()
                pausar = true
            } else {
                bluetoothAnimation.stop()
                pausar = false
            }
        }
    }


    fun siguiente(view:View){
        Toast.makeText(this,miView.currentView.id.toString(),Toast.LENGTH_SHORT).show()
        miView.showNext()
    }

    fun anterior(view:View){
        Toast.makeText(this,miView.currentView.id.toString(),Toast.LENGTH_SHORT).show()
        miView.showPrevious()
    }

    fun irFlipper(view:View){
        var intentVF = Intent(this, EjemploViewFlipper::class.java)
        startActivity(intentVF)
    }





/*    private fun crossfade() {
        contenidoView.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }
        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        loadingView.animate()
            .alpha(0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    loadingView.visibility = View.GONE
                }
            })
    }


 */

}