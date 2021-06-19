package com.thirathawat.mystock

import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pixplicity.easyprefs.library.Prefs
import com.thirathawat.mystock.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
        if (Prefs.getBoolean(PREFS_KEY_IS_LOGIN, false)) toHomeActivity()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupWidget()
    }

    private fun setupWidget() {
        binding.loginButtonLogin.setOnClickListener {
            validate()
        }
        binding.loginScrollview.apply {
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
        }
    }

    private fun validate() {
        val username = binding.loginEdittextUsername.text.toString()
        val password = binding.loginEdittextPassword.text.toString()
        if (username.isEmpty() || password.isEmpty()) {
            showToast("Username or Password is Empty")
        } else {
            if (username == "tirathawat@gmail.com" && password == "password") {
                Prefs.putBoolean(PREFS_KEY_IS_LOGIN, true)
                Prefs.putString(PREFS_KEY_USERNAME, username)
                toHomeActivity()
            } else {
                showToast("Username or Password incorrect")
            }
        }
    }

    private fun toHomeActivity() {
        Intent(applicationContext, HomeActivity::class.java).run {
            startActivity(this)
            finish()
        }
    }
}