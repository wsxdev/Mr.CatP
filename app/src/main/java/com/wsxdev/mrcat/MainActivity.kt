package com.wsxdev.mrcat

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toogle: ActionBarDrawerToggle

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    private var doubleBackToExitPressedOnce = false

    private lateinit var noConnectionDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Retraso de 2 segundos antes de verificar la conexión y mostrar el popup
        Handler(Looper.getMainLooper()).postDelayed({
            // Verificar la conexión y mostrar el popup si no hay conexión

            if (!isInternetAvailable(this)) {
                noConnectionDialog.show()
            }
        }, 2000)

        noConnectionDialog = Dialog(this)
        noConnectionDialog.setContentView(R.layout.popup_verfication_conection)
        noConnectionDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        noConnectionDialog.setCancelable(true)

        val retryButton = noConnectionDialog.findViewById<Button>(R.id.retryButton)
        retryButton.setOnClickListener {
            // Lógica para reintentar la conexión
            noConnectionDialog.dismiss()
        }



        bottomNavigationView = findViewById(R.id.bottom_navigation)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Establecer el título de la Toolbar
        supportActionBar?.title = ""

        drawer = findViewById(R.id.drawer_layout)

        toogle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toogle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.fragment_home, R.id.fragment_historial, R.id.fragment_favorites, R.id.fragment_notes
        ), drawer)

        bottomNavigationView.setOnItemSelectedListener { item ->
            val enterAnimation = R.anim.fade_in_fragments_btn_nav
            val exitAnimation = R.anim.fade_out_fragments_btn_nav
            val popEnterAnimation = R.anim.fade_in_fragments_btn_nav
            val popExitAnimation = R.anim.fade_out_fragments_btn_nav

            val options = NavOptions.Builder()
                .setEnterAnim(enterAnimation)
                .setExitAnim(exitAnimation)
                .setPopEnterAnim(popEnterAnimation)
                .setPopExitAnim(popExitAnimation)
                .build()

            when (item.itemId) {
                R.id.item_home -> {
                    navController.navigate(R.id.fragment_home, null, options)
                }
                R.id.item_historial -> {
                    navController.navigate(R.id.fragment_historial, null, options)
                }
                R.id.item_favorite -> {
                    navController.navigate(R.id.fragment_favorites, null, options)
                }
                R.id.item_notes -> {
                    navController.navigate(R.id.fragment_notes, null, options)
                }
            }
            true
        }

        setupActionBarWithNavController(navController, appBarConfiguration)


        // VERIFICACIÓN DE DOBLE PULSACIÓN ATRAS, PARA SALIR DE LA APP
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START)
                } else {
                    if (doubleBackToExitPressedOnce) {
                        finish() // Cierra la aplicación
                    } else {
                        doubleBackToExitPressedOnce = true
                        Toast.makeText(this@MainActivity, "Pulse de nuevo para salir", Toast.LENGTH_SHORT).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            doubleBackToExitPressedOnce = false
                        }, 2000) // Restablece la bandera después de 2 segundos
                    }
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)

    }




    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val intent: Intent
        when(item.itemId){

            R.id.nav_drawer_inicio -> {
                navController.navigate(R.id.fragment_home)
                bottomNavigationView.selectedItemId = R.id.item_home
                drawer.closeDrawer(GravityCompat.START)
            }

            R.id.nav_drawer_ajustes -> {
                // Iniciar la actividad de ajustes
                intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_drawer_info -> {
                // Inicia la actividad de información del NavView
                intent = Intent(this, InfoActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_drawer_reportar_problema -> {
                // Inicia la actividad de Reportar Problema del NavView
                intent = Intent(this, ReportActivity::class.java)
                startActivity(intent)
            }

        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toogle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toogle.onConfigurationChanged(newConfig)
    }

    // MENÚ DE TOOLBAR, TRES PUNTOS
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent

        when (item.itemId) {
            R.id.optionSettingToolbar -> {
                intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.optionRefreshToolbar -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

}