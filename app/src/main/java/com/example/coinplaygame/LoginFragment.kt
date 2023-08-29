package com.example.coinplaygame

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/** Login Screen **/
class LoginFragment : Fragment() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: AppCompatButton
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_screen, container, false)

        username = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password)
        loginButton = view.findViewById(R.id.loginButton)
        sharedPreferences = requireActivity().getSharedPreferences(
            MainActivity.PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )

        loginButton.setOnClickListener {
            val uname = username.text.toString().trim()
            val upassword = password.text.toString().trim()
            if(uname.isEmpty()) {
                username.error = "Username cannot be empty"
            }
            else if(upassword.isEmpty()) {
                password.error = "Password cannot be empty"
            } else {
                val storedPassword: String? = sharedPreferences.getString(uname, "")

                storedPassword?.let {
                    if (it == upassword) {
                        //Successful login
                        Toast.makeText(
                            context,
                            "Logged in successfully",
                            Toast.LENGTH_SHORT
                        ).show();

                        val editor = sharedPreferences.edit()
                        editor.putString("username", uname)
                        editor.apply()
                        showGamePlayScreen()
                    } else {
                        //Failed login
                        Toast.makeText(
                            context,
                            "Login failed. Please check your credentials.",
                            Toast.LENGTH_SHORT
                        ).show();
                    }
                }

            }
        }

        return view
    }

    // show game play screen in which user will play the game.
    private fun showGamePlayScreen() {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragment: Fragment? = fragmentManager.findFragmentById(R.id.fragmentContainer)

        fragment?.let {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, GamePlayFragment()).addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}