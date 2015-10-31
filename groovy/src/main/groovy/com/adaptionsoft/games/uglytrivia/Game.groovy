package com.adaptionsoft.games.uglytrivia

public class Game {

	private List<Player> players = []
	int currentPlayer = 0
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
		println players.get(currentPlayer).name + " is the current player"
		println "They have rolled a " + roll
		
		if (players.get(currentPlayer).inPenaltyBox) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true
				println players.get(currentPlayer).name + " is getting out of the penalty box"
				movePlayer(roll)
				askQuestion()
			} else {
				println players.get(currentPlayer).name + " is not getting out of the penalty box"
				isGettingOutOfPenaltyBox = false
			}
		} else {
			movePlayer(roll)
			askQuestion()
		}
	}

	private void movePlayer(int roll) {
		players.get(currentPlayer).advance(roll)
		println "${players.get(currentPlayer).name}'s new location is ${players.get(currentPlayer).place}"
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
		switch (players.get(currentPlayer).place % 4) {
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

	public boolean wasCorrectlyAnswered() {
		if (players.get(currentPlayer).inPenaltyBox) {
			if (isGettingOutOfPenaltyBox) {
				println "Answer was correct!!!!"
				players.get(currentPlayer).awardCoin()
    			println "${players.get(currentPlayer).name} now has ${players.get(currentPlayer).purse} Gold Coins."
				def winner = didPlayerWin()
				nextPlayer()
				return winner
			} else {
				nextPlayer()
				return true
			}
		} else {
			println "Answer was corrent!!!!"
			players.get(currentPlayer).awardCoin()
			println "${players.get(currentPlayer).name} now has ${players.get(currentPlayer).purse} Gold Coins."
			def winner = didPlayerWin()
			nextPlayer()
			return winner
		}
	}
	
	public boolean wrongAnswer(){
		println "Question was incorrectly answered"
		println players.get(currentPlayer).name + " was sent to the penalty box"
		players.get(currentPlayer).inPenaltyBox = true
		nextPlayer()
		return true
	}

	private void nextPlayer() {
		currentPlayer++
		if (currentPlayer == players.size()) currentPlayer = 0
	}

	private boolean didPlayerWin() {
		!players.get(currentPlayer).isWinner()
	}
}
