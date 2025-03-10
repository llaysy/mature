package com.example.mature

import android.app.Application
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        createSupabaseClient(
            supabaseUrl = "https://igzjltwtzxppjjzvubgp.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlnempsdHd0enhwcGpqenZ1YmdwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDE0OTQ1MTcsImV4cCI6MjA1NzA3MDUxN30.1U2Xv7gISdK081aJ-RkJ_1eg7fPfud8IQX3KrtsGr2o"
        ) {
            install(GoTrue)
            install(Postgrest)
            install(Storage)
        }
    }
}