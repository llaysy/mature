package com.example.mature

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class MainActivity : AppCompatActivity() {
    private val supabase = createSupabaseClient(
        supabaseUrl = "https://igzjltwtzxppjjzvubgp.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlnempsdHd0enhwcGpqenZ1YmdwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDE0OTQ1MTcsImV4cCI6MjA1NzA3MDUxN30.1U2Xv7gISdK081aJ-RkJ_1eg7fPfud8IQX3KrtsGr2o"
    ) {
        install(Postgrest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        showSplashScreen()
    }

    private fun showSplashScreen() {
        // Загрузка данных из Supabase
        loadDataFromSupabase()

        // Отображаем сплеш-экран в течение 3 секунд
        Handler(Looper.getMainLooper()).postDelayed({
            showOnboarding1()
        }, 3000)
    }

    private fun showOnboarding1() {
        setContentView(R.layout.onboarding_1)

        // Обработка нажатия на кнопку "Далее" в onboarding_1.xml
        findViewById<MaterialButton>(R.id.startButton).setOnClickListener {
            showOnboarding2()
        }
    }

    private fun showOnboarding2() {
        setContentView(R.layout.onboarding_2)

        // Обработка нажатия на кнопку "Далее" в onboarding_2.xml
        findViewById<MaterialButton>(R.id.startButton1).setOnClickListener {
            showOnboarding3()
        }
    }

    private fun showOnboarding3() {
        setContentView(R.layout.onboarding_3)

        // Обработка нажатия на кнопку "Далее" в onboarding_3.xml
        findViewById<MaterialButton>(R.id.startButton2).setOnClickListener {
            showSignupActivity()
        }
    }

    private fun showSignupActivity() {
        startActivity(Intent(this, SignupActivity::class.java))
        finish()
    }

    private fun loadDataFromSupabase() {
        // Код для загрузки данных из Supabase
    }
}