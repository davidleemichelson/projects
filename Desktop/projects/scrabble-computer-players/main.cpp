#include <vector> 
#include <map>
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <cstdlib>
#include <stdexcept>
#include <algorithm>

#include "Game.h"
#include "Util.h"
#include "Exceptions.h"
#include "Dictionary.h"
#include "LengthAI.h"
#include "ScoreAI.h"

using namespace std;

int main(int nargs, char **args) {
	if (nargs < 2 || nargs > 2)
		cout << "Usage: Scrabble <config-filename>\n";
	try {
		Game game (args[1]);
		Board* board = game.getBoard();
		Dictionary* dict = game.getDictionary();
		map<char,int> initialTiles = game.initialTileCount();
		ScoreAI scoreAI;
		LengthAI lengthAI;

		cout << "=========WELCOME TO SCRABBLE=========" << endl << "Please enter number of players (1-8): ";
		int numPlayers; cin >> numPlayers;
		for(int i = 1; i <= numPlayers; i++) {
			cout << "Please enter Player " << i << "'s name: ";
			string playerName; if(i == 1) {cin.ignore();} // so as not to store one of the names as blank
			getline(cin, playerName);
			if(strtoupper(playerName.substr(0, 4)) == "CPUS") {
				cout << "Score CPU added - Initializing AI ..." << endl;
				playerName = "CPUS" + playerName.substr(4, playerName.size());
				scoreAI.initialize(dict);
			}
			else if(strtoupper(playerName.substr(0, 4)) == "CPUL") {
				cout << "Length CPU added - Initializing AI ..." << endl;
				playerName = "CPUL" + playerName.substr(4, playerName.size());
				lengthAI.initialize(dict);
			}
			game.addPlayer(playerName);
		}
		while(!game.isFinished()) {
			Player* currPlayer = game.getCurrentPlayer(); cout << "\nCurrent Scores:\n";
			for(unsigned int i = 0; i < game.getScores().size(); i++) {
				cout << game.getScores()[i].first << ": " << game.getScores()[i].second << endl;
			} 
			/* print UI */ cout << endl << game.getCurrentPlayer()->getName() << ", it's your turn!\n" << board->getDisplay() << "To pass your turn, type PASS.\nTo discard tiles, type EXCHANGE, followed by a string of those tiles.\nTo place a word, type PLACE, followed by the following:\n\tfirst, either a | or - for vertical/horizontal placement;\n\tsecond, the row (from the top), the column (from the left),\n\tthird, a sequence of letters to place;\n\tto use a blank tile, type ? followed by the letter you want to use it for.\nYour current tiles: " << game.getCurrentPlayer()->getHand() << endl;
			string isCpu = strtoupper((game.getCurrentPlayer()->getName()).substr(0, 4));
			if(isCpu == "CPUS") { 
				cout << "Doing CPUS move!" << endl;
				try {
				game.makeMove(scoreAI.getMove(*board, *currPlayer, initialTiles));
				} catch (MoveException &me) {
					std::cout << "CPU Failed to make a move, exception: " << me.getMessage() << std::endl;
					Move move; game.makeMove(move);
				}
				/* code to find CPUS move and do it */
			}
			else if(isCpu ==  "CPUL") {
				cout << "Doing CPUL move!" << endl;
				game.makeMove(lengthAI.getMove(*board, *currPlayer, initialTiles));
				/* code to find CPUL move and do it */ 
			}
			else {
				string playerCommand;
				getline(cin, playerCommand);
				try {
					Move move(strtoupper(playerCommand), *(game.getCurrentPlayer()));
					game.makeMove(move);
				}
				catch (MoveException & me) {
					cout << "Move error: " << me.getMessage() << endl << "Please try again." << endl;
					continue;
				}
			}
			// TO DO: print score earned and stuff 
			cout << "Score earned: " << game.getRecentScore() << endl;
			cout << "Words formed: " << endl;
			vector<string> recentWords = game.getRecentWords(); 
			for(unsigned int i = 0; i < recentWords.size(); i++) {
				cout << recentWords[i] << " ";
			}
			cout << endl << "Recent draw: " << game.getRecentDraw() << endl;
			game.finalizeMove();
		}
	// 		int getRecentScore () const;
	// /* Returns the score of the most recent move. 
	//    This is helpful for displaying to a player what their
	//    score for the last move was. */

	// std::vector<std::string> getRecentWords() const;
	//  Returns the vector of all the words formed by the most recent
	//    PLACE move. This is useful for displaying to a player what
	//    words they formed. 

	// std::string getRecentDraw() const;
	// /* Returns the most recent draw of tiles to be added to a hand.
	//    This is useful for showing players what tiles they just drew
	//    to add to their hand. */










		cout << "Game over." << endl << "Final scores: " << endl;
		if (game.getCurrentPlayer()->numberofTiles() == 0 && game.bagEmpty()) {
			for(unsigned int i = 0; i < game.finalSubtraction().size(); i++) {
				cout << game.finalSubtraction()[i].first << ": " << game.finalSubtraction()[i].second << endl;
			}
		}
		else {
			for(unsigned int i = 0; i < game.getScores().size(); i++) {
				cout << game.getScores()[i].first << ": " << game.getScores()[i].second << endl;
			} 
		}


		// TO DO: end game evaluations

		// string boardDisplay = board->getDisplay();


		return 0;

	}
	catch (invalid_argument & e)
	{ cout << "Fatal Error! " << e.what(); }

	return 1;


}