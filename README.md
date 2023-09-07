# CoinPlayGame
This is a simple turn based game being played between Artificial Intelligence (AI - the Mobile
App) and a human player. This code ensures that the AI always wins. 

Rules for the game are as follows:
− There are 21 coins placed in middle of screen.
− The app asks the player to pick 1 or 2 or 3 or 4 coins (any one quantity at a time).
− After the player picks, AI should pick automatically
− Whoever is forced to pick up the last coin loses the game.

There are three screens in this game:
1. Login Screen (LoginFragment) - It allows users to login using the below accounts which are stored locally in a database in device memory using SharedPreferences.
  a. username: admin, password: admin1234
  b. username: guest, password: guest1234
2. Game Play Screen (GamePlayFragment) - It implements logic as mentioned above. Used buttons / pickers
wherever necessary and also used different colors to make the screen appealing.
3. Lost Screen (LostFragment) - This screen gets visible after the game ends, which shows players current game
and past games history including total games played and total lost games, etc. Also added the ‘Play again’ button which takes the user to the
Game Play screen.
