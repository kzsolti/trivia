package com.adaptionsoft.games.uglytrivia

/**
 * Created by zsolt on 15. 10. 31..
 */
class Player {

	String name
	int place = 0
	int purse = 0
	boolean inPenaltyBox = false

	void advance(int roll) {
		place += roll
		if (place > 11) {
			place -= 12
		}
	}

	void awardCoin() {
		purse++
	}

	boolean isWinner() {
		purse == 6
	}

}
