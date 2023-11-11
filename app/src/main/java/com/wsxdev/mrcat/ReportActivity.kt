package com.wsxdev.mrcat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.widget.Toolbar

class ReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val toolbar = findViewById<Toolbar>(R.id.toolbarReport)
        setSupportActionBar(toolbar)

        // AQUÍ SE CONFIGURA EL BOTÓN DE RETROCESO PERSONALIZADO
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val editText = findViewById<EditText>(R.id.EditTextReport)
        editText.setSelection(0)
        editText.requestFocus()



    }
}