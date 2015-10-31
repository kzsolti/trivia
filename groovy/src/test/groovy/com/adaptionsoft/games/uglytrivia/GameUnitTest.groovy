package com.adaptionsoft.games.uglytrivia

import groovy.ui.SystemOutputInterceptor

/**
 * Created by zsolt on 15. 10. 31..
 */
class GameUnitTest extends GroovyTestCase {

	void testTypo() {
		def game = new Game()
		game.add('Dummy')
		game.roll(1)
		def buffer = new StringBuffer()
		def interceptor = new SystemOutputInterceptor({
			output ->
				buffer.append(output)
				false
		})
		interceptor.start()
		game.wasCorrectlyAnswered()
		interceptor.stop()
		assert buffer.toString().startsWith('Answer was correct!!!!')
	}

	void testExitPenalty() {
		def game = new Game()
		game.add('Dummy')
		game.roll(1)
		game.wrongAnswer()
		assert game.currentPlayer.inPenaltyBox
		game.roll(1)
		game.wasCorrectlyAnswered()
		assert !game.currentPlayer.inPenaltyBox
	}

	void testFailedPenalty() {
		def game = new Game()
		game.add('Dummy')
		game.roll(1)
		game.wrongAnswer()
		assert game.currentPlayer.inPenaltyBox
		game.roll(2)
		game.wasCorrectlyAnswered()
		assert game.currentPlayer.inPenaltyBox
	}
}
