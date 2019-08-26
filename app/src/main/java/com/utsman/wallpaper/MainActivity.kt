package com.utsman.wallpaper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.utsman.core.ConnectivityViewModel
import com.utsman.unsplash.UnsplashFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val connectivityViewModel by lazy {
        ViewModelProviders.of(this)[ConnectivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val unsplashFragment = UnsplashFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_frame, unsplashFragment)
            .commitNow()

    }

    override fun onStart() {
        super.onStart()
        connectivityViewModel.getConnectivity().observe(this, Observer { isConnected ->
            Snackbar.make(main_layout, "Network connecting status: $isConnected", Snackbar.LENGTH_SHORT).show()
        })
    }

}