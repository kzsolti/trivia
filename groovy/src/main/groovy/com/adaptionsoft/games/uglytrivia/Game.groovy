package com.adaptionsoft.games.uglytrivia

public class Game {

	private List<Player> players = []
	boolean isGettingOutOfPenaltyBox

	def popQuestions = new LinkedList()
	def scienceQuestions = new LinkedList()
	def sportsQuestions = new LinkedList()
	def rockQuestions = new LinkedList()

	public Game() {
		50.times {
			def i ->
				popQuestions.addLast(createQuestion('Pop Question', i))
				scienceQuestions.addLast(createQuestion('Science Question', i))
				sportsQuestions.addLast(createQuestion('Sports Question', i))
				rockQuestions.addLast(createQuestion('Rock Question', i))
		}
	}

	private static String createQuestion(String topic, int i) {
		"$topic $i"
	}

	public void add(String playerName) {
	    players.add(new Player(name: playerName))
	    println playerName + " was added"
	    println "They are player number " + players.size()
	}

	public void roll(int roll) {
		println currentPlayer.name + " is the current player"
		println "They have rolled a " + roll
		
		if (currentPlayer.inPenaltyBox) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true
				println currentPlayer.name + " is getting out of the penalty box"
				movePlayer(roll)
				askQuestion()
			} else {
				println currentPlayer.name + " is not getting out of the penalty box"
				isGettingOutOfPenaltyBox = false
			}
		} else {
			movePlayer(roll)
			askQuestion()
		}
	}

	private void movePlayer(int roll) {
		currentPlayer.advance(roll)
		println "${currentPlayer.name}'s new location is ${currentPlayer.place}"
		println "The category is " + currentCategory()
	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			println popQuestions.removeFirst()
		if (currentCategory() == "Science")
			println scienceQuestions.removeFirst()
		if (currentCategory() == "Sports")
			println sportsQuestions.removeFirst()
		if (currentCategory() == "Rock")
			println rockQuestions.removeFirst()		
	}

	private String currentCategory() {
		switch (currentPlayer.place % 4) {
			case 0:
				return 'Pop'
			case 1:
				return 'Science'
			case 2:
				return 'Sports'
			default:
				return 'Rock'
		}
	}

	public void wasCorrectlyAnswered() {
		if (!currentPlayer.inPenaltyBox || isGettingOutOfPenaltyBox) {
			println "Answer was correct!!!!"
			currentPlayer.inPenaltyBox = false
			currentPlayer.awardCoin()
			println "${currentPlayer.name} now has ${currentPlayer.purse} Gold Coins."
		}
	}
	
	public void wrongAnswer(){
		println "Question was incorrectly answered"
		println currentPlayer.name + " was sent to the penalty box"
		currentPlayer.inPenaltyBox = true
	}

	public void nextPlayer() {
		players.add(players.remove(0))
	}

	public Player getCurrentPlayer() {
		players[0]
	}

	public boolean didPlayerWin() {
		currentPlayer.isWinner()
	}
}
