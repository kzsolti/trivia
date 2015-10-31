package com.adaptionsoft.games.uglytrivia

import com.adaptionsoft.games.trivia.runner.GameRunner
import com.adaptionsoft.games.trivia.runner.RollProvider
import groovy.ui.SystemOutputInterceptor

/**
 * Created by zsolt on 15. 10. 31..
 */
class GameTest extends GroovyTestCase {

	void testGenerateInputs() {
		def random = new Random()
		100.times {
			def i ->
				new File("input_${i}.txt").withWriter {
					writer ->
						100.times {
							writer.append("${random.nextInt(5) + 1},${random.nextInt(9)}\n")
						}
				}
		}
	}

	void testGenerateOutputs() {
		100.times {
			//println new File("test.txt").absolutePath
			new File("src/test/resources/input/input_${it}.txt").withReader {
				reader ->
					def game = new Game()
					game.add("Chet")
					game.add("Pat")
					game.add("Sue")
					new File("src/test/resources/output/output_${it}.txt").withWriter {
						def writer ->
							def interceptor = new SystemOutputInterceptor({
								def output ->
									writer.append(output)
									false
							})
							interceptor.start()
							GameRunner.runGame(game, new RollProvider() {

								def rolls

								@Override
								int getFirstRoll() {
									rolls = reader.readLine().split(',')
									rolls[0].toInteger()
								}

								@Override
								int getSecondRoll() {
									rolls[1].toInteger()
								}
							})
							interceptor.stop()
					}
			}
		}

	}

	void testBlackBox() {
		100.times {
			new File("src/test/resources/input/input_${it}.txt").withReader {
				reader ->
					def game = new Game()
					game.add("Chet")
					game.add("Pat")
					game.add("Sue")
					def log = new StringBuffer()
					def outFile = new File("src/test/resources/output/output_${it}.txt")
					def interceptor = new SystemOutputInterceptor({
						def output ->
							log.append(output)
							false
					})
					interceptor.start()
					GameRunner.runGame(game, new RollProvider() {

						def rolls

						@Override
						int getFirstRoll() {
							rolls = reader.readLine().split(',')
							rolls[0].toInteger()
						}

						@Override
						int getSecondRoll() {
							rolls[1].toInteger()
						}
					})
					interceptor.stop()
					assert outFile.text == log.toString()
			}
		}

	}

}
