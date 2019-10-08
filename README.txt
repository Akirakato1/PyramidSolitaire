Change in BasicPyramidSolitare:
Added try catch line 309 in isGameOver.
Was an issue in assignment2 because exception left uncaught.

Changed getCardAt line 352 so it returns a copy and not reference.

Changed getDrawCards line 375 so it returns a copy and not reference.

Changed line 63 from assigning reference to copying the actual deck in startGame.

Change in Card class

Equals method bug fix: == on String (suite) to .equals : line 56.

