package com.example.coinplaygame

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/** Starting Screen **/
class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Storing initial values in database
        val editor = sharedPreferences.edit()
        editor.putString("admin", "admin1234")
        editor.putString("guest", "guest1234")
        editor.putInt("totalCoins", 21)
        editor.apply()

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragment: Fragment? = fragmentManager.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragmentContainer, LoginFragment())
            fragmentTransaction.commit()
        }
    }

    companion object {
        const val PREFS_NAME = "UserCredentials"
    }
}