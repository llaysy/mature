package com.example.mature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mature.SupabaseClient.client
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var signUpButton: TextView
    private lateinit var resetPasswordText: TextView
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        signUpButton = findViewById(R.id.signUpButton)
        resetPasswordText = findViewById(R.id.resetPasswordText)
        backButton = findViewById(R.id.backButton)
    }

    private fun setupClickListeners() {
        // Кнопка входа
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            login(this, email, password)
        }

        // Кнопка регистрации
        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            signIn(this, email, password)
        }

        // Кнопка восстановления пароля
        resetPasswordText.setOnClickListener {
            // Здесь можно добавить логику для восстановления пароля
            Toast.makeText(this, "Функция восстановления пароля", Toast.LENGTH_SHORT).show()
        }

        // Кнопка назад
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun signIn(context: Context, email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                client.gotrue.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
                saveToken(context)
                navigateToHome()
            } catch (e: Exception) {
                Log.e("SignInError", "Ошибка входа: ${e.message}", e)
                Toast.makeText(this@SignupActivity, "Ошибка регистрации: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(context: Context, email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                client.gotrue.loginWith(Email) {
                    this.email = email
                    this.password = password
                }
                saveToken(context)
                navigateToHome()
            } catch (e: Exception) {
                Toast.makeText(this@SignupActivity, "Ошибка входа: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveToken(context: Context) {
        lifecycleScope.launch {
            val accessToken = client.gotrue.currentAccessTokenOrNull()
            val sharedPref = SharedPreferenceHelper(context)
            sharedPref.saveStringData("accessToken", accessToken)
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish() // Закрываем текущую активити, чтобы пользователь не мог вернуться назад
    }

    private fun isUserLoggedIn(context: Context) {
        lifecycleScope.launch {
            try {
                val token = getToken(context)
                if (!token.isNullOrEmpty()) {
                    client.gotrue.retrieveUser(token)
                    client.gotrue.refreshCurrentSession()
                    saveToken(context)
                    navigateToHome()
                }
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }

    private fun getToken(context: Context): String? {
        val sharedPref = SharedPreferenceHelper(context)
        return sharedPref.getStringData("accessToken")
    }

    fun logout() {
        lifecycleScope.launch {
            try {
                client.gotrue.logout()
                val sharedPref = SharedPreferenceHelper(this@SignupActivity)
                sharedPref.saveStringData("accessToken", null)
            } catch (e: Exception) {
                Toast.makeText(this@SignupActivity, "Ошибка выхода: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}