package com.example.test

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.test.Modle.GeneralData
import java.util.*

class splashActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences // Corrected the variable name
    private var isNightModeOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE) // Use the correct name for SharedPreferences

        // Retrieve the value of isNightModeOn from SharedPreferences
        isNightModeOn = sharedPreferences.getBoolean("night_mode", AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)

        // Retrieve the value of currentLanguage from SharedPreferences
        val currentLanguage = sharedPreferences.getString("language", "")

        // Set the night mode
        AppCompatDelegate.setDefaultNightMode(if (isNightModeOn) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)

        // Set the locale based on the selected language
        setLocale(this, currentLanguage)
        var isLoggedIn=sharedPreferences.getBoolean("isChcekedLogin", false)
        val thread = Thread {
            try {
                Thread.sleep(3000)
                if (!isLoggedIn) {
                   // saveIsCheckedLoginToPreferences(true)
                    // User is not logged in, redirect to LoginScreen
                    startActivity(Intent(this@splashActivity, LoginScreen::class.java))
                } else {

                    // User is logged in, redirect to MainActivity
                    startActivity(Intent(this@splashActivity, MainActivity::class.java))
                }
                finish()
            } catch (e: Exception) {
                // Handle any exceptions here
            }
        }
        thread.start()
    }

    // Function to save the isChcekedLogin value to SharedPreferences
    private fun saveIsCheckedLoginToPreferences(isChecked: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isChcekedLogin", isChecked)
        editor.apply()
    }

    // Function to check if the user is logged in
    private fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isChcekedLogin", false)
    }

    private fun setLocale(context: Context, languageCode: String?) {
        if (languageCode == "ar") {
            GeneralData.selectedLanguage = "ae"
        } else {
            if (languageCode != null) {
                GeneralData.selectedLanguage = languageCode
            }
        }
        if (!languageCode.isNullOrEmpty()) {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)

            val configuration = Configuration()
            configuration.setLocale(locale)

            context.resources.updateConfiguration(
                configuration,
                context.resources.displayMetrics
            )
        }
    }
}
