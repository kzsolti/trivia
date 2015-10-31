
package com.adaptionsoft.games.trivia.runner
import com.adaptionsoft.games.uglytrivia.Game

public class GameRunner {

	private static boolean notAWinner

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
		boolean isFirstRound = true
		notAWinner = false
		while (isFirstRound || notAWinner) {

			aGame.roll(provider.firstRoll)

			if (provider.secondRoll == 7) {
				notAWinner = aGame.wrongAnswer()
			} else {
				notAWinner = aGame.wasCorrectlyAnswered()
			}

			isFirstRound = false
		}
	}
}
