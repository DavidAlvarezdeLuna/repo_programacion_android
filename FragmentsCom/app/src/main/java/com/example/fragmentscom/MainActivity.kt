package com.example.fragmentscom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity(), FragmentA.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Los fragments en realidad no se hablan. El Main se usa como un intermediario que es donde se realiza el intercambio de información.
     */
    override fun onFragmentInteraction(texto: String) {
        var manager:FragmentManager = supportFragmentManager
        var fragment:FragmentB = manager?.findFragmentById(R.id.fragmentB) as FragmentB
        fragment.onFragmentInteraction(texto)
    }
}