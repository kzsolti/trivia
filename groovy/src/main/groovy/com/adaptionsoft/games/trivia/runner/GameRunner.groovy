
package com.adaptionsoft.games.trivia.runner
import com.adaptionsoft.games.uglytrivia.Game

public class GameRunner {

	public static void main(String[] args) {
	    	    
		Game aGame = new Game()
		
		aGame.add("Chet")
		aGame.add("Pat")
		aGame.add("Sue")
		
		Random rand = new Random()

		runGame(aGame, new RollProvider() {
			@Override
			int getFirstRoll() {
				rand.nextInt(5) + 1
			}

			@Override
			int getSecondRoll() {
				rand.nextInt(9)
			}
		})
	}

	public static void runGame(Game aGame, RollProvider provider) {
		boolean gameIsWon = false
		while (!gameIsWon) {
			aGame.roll(provider.firstRoll)
			if (provider.secondRoll == 7) {
				gameIsWon = aGame.wrongAnswer()
			} else {
				gameIsWon = aGame.wasCorrectlyAnswered()
			}
		}
	}
}
