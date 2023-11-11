package com.wsxdev.mrcat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val toolbar = findViewById<Toolbar>(R.id.toolbarInfo)
        setSupportActionBar(toolbar)

        // Configura la acción de retroceso con tu ícono personalizado
        toolbar.setNavigationIcon(R.drawable.ic_back)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}