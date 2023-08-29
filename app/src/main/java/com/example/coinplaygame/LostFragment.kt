package com.example.coinplaygame

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


/**
 * Show this screen to the user when he/she has lost the game
 */
class LostFragment : Fragment() {

    private lateinit var totalGamesPlayed: TextView
    private lateinit var totalGamesLost: TextView
    private lateinit var playAgainButton: AppCompatButton
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // This line is important to indicate that the fragment has an options menu
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lost_screen, container, false)

        sharedPreferences = requireActivity().getSharedPreferences(
            MainActivity.PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        totalGamesPlayed = view.findViewById(R.id.totalGamesPlayed)
        totalGamesLost = view.findViewById(R.id.totalGamesLost)
        playAgainButton = view.findViewById(R.id.playAgainButton)

        val username: String? = sharedPreferences.getString("username", "")
        val totalPlayed: Int = sharedPreferences.getInt(username+"_totalGamesPlayed", 0)
        val totalLost: Int = sharedPreferences.getInt(username+"_totalGamesLost", 0)

        totalGamesPlayed.text = "total games played: $totalPlayed"
        totalGamesLost.text = "total games lost: $totalLost"


        playAgainButton.setOnClickListener {
            showGamePlayScreen()
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle menu item clicks here
        // Handle menu item clicks here
        val id = item.itemId
        return if (id == R.id.Logout) {
            showLoginScreen()
            true
        } else super.onOptionsItemSelected(item)
    }

    // again show game play screen in which user will play the game.
    private fun showGamePlayScreen() {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragment: Fragment? = fragmentManager.findFragmentById(R.id.fragmentContainer)

        fragment?.let {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, GamePlayFragment()).addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    // show login screen
    private fun showLoginScreen() {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragment: Fragment? = fragmentManager.findFragmentById(R.id.fragmentContainer)

        fragment?.let {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, LoginFragment()).addToBackStack(null)
            fragmentTransaction.commit()
        }

        val editor = sharedPreferences.edit()
        editor.putString("username", "")
        editor.apply()
    }
}