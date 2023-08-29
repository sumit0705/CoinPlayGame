package com.example.coinplaygame

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/** Screen in which user will play the game**/
class GamePlayFragment: Fragment() {
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var gridLayout: GridLayout
    private lateinit var sharedPreferences: SharedPreferences
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_play_screen, container, false)

        sharedPreferences = requireActivity().getSharedPreferences(
            MainActivity.PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        generateToast("Welcome to the Coins Game, Select the number of coins")
        button1 = view.findViewById(R.id.button1)
        button2 = view.findViewById(R.id.button2)
        button3 = view.findViewById(R.id.button3)
        button4 = view.findViewById(R.id.button4)
        gridLayout = view.findViewById(R.id.gridLayout)
        addDelay()

        button1.setOnClickListener {
            updateGridLayout()
            updateCoins(1)
        }

        button2.setOnClickListener {
            updateGridLayout()
            updateCoins(2)
        }
        button3.setOnClickListener {
            updateGridLayout()
            updateCoins(3)
        }
        button4.setOnClickListener {
            updateGridLayout()
            updateCoins(4)
        }
        return view
    }

    private fun updateGridLayout() {
        gridLayout.alpha = 0.1F
        gridLayout.isClickable = false
    }

    private fun showGridLayout() {
        gridLayout.alpha = 1F
        gridLayout.isClickable = true
    }

    /**
     * play the game and user selects the
     * [selectedCoins] number of coins selected by user
     */

    private fun updateCoins(selectedCoins: Int) {
        var totalCoins: Int = sharedPreferences.getInt("totalCoins", 0)

        if (selectedCoins > totalCoins) {
            generateToast("You have selected $selectedCoins coins, but total coins available are: $totalCoins, Select again")
            addDelay()
            return
        }

        if (totalCoins > 0) {
            // First player's turn
            generateToast("You have selected $selectedCoins coins, now AI's turn")
            addDelay()
            totalCoins -= selectedCoins
            updateTotalCoins(totalCoins)
            if (totalCoins <= 0) {
                generateToast("Player 1 picks the last coin. Player 2 wins!")
                val username: String? = sharedPreferences.getString("username", "")
                val totalLost: Int = sharedPreferences.getInt(username+"_totalGamesLost", 0)
                val editor = sharedPreferences.edit()
                editor.putInt(username+"_totalGamesLost", totalLost +1)
                editor.apply()
                updateData()
                return
            }

            // AI's turn (Second player)
            val aiChoice: Int = calculateAIChoice(totalCoins)
            generateToast("AI picks $aiChoice coins.")
            totalCoins -= aiChoice
            updateTotalCoins(totalCoins)
            if (totalCoins == 0) {
                generateToast("AI picks the last coin. Player 1 wins!")
                updateData()
                return
            }
        }
    }

    private fun calculateAIChoice(remainingCoins: Int): Int {
        // strategy is to leave the user with a multiple of 5 plus 1 coin
        var aiChoice = (remainingCoins - 1) % 5
        if (aiChoice == 0) {
            // If the remaining coins is already a multiple of 5 plus 1, AI picks randomly
            aiChoice = 1 + (Math.random() * 4).toInt()
        }
        return aiChoice
    }

    private fun generateToast(msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun updateData() {
        val username: String? = sharedPreferences.getString("username", "")
        val totalPlayed: Int = sharedPreferences.getInt(username+"_totalGamesPlayed", 0)
        val editor = sharedPreferences.edit()
        editor.putInt(username+"_totalGamesPlayed", totalPlayed + 1)
        editor.putInt("totalCoins", 21)
        editor.apply()
        showLostScreen()
        generateToast("Game over!")
    }

    private fun updateTotalCoins(coins :Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("totalCoins", coins)
        editor.apply()
    }

    // Added a delay of 3 seconds.
    private fun addDelay() {
        handler.postDelayed({
            showGridLayout()
        }, 3000) // 3 seconds
    }

    // show lost screen when user has lost the game.
    private fun showLostScreen() {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragment: Fragment? = fragmentManager.findFragmentById(R.id.fragmentContainer)

        fragment?.let {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, LostFragment()).addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}