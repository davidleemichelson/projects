/*
 * Scrabble.cpp
 *
 *  Created on: Sep 18, 2016
 *      Author: kempe
 */

// The following is a suggestion for how to proceed.
// Feel free to make any changes you like.

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <cstdlib>
#include <stdexcept>
#include <algorithm>
#include <QApplication>

#include "Tile.h"
#include "Dictionary.h"
#include "Bag.h"
#include "Board.h"
#include "Player.h"

using namespace std;

void readConfigFile (string config_file_name,
					 string & dictionary_file_name,
					 string & board_file_name,
					 string & bag_file_name,
					 unsigned int & hand_size)
{
	ifstream configFile (config_file_name.c_str());
	string line;
    bool number = false, board = false, tiles = false, dictionary = false;

	if (!configFile.is_open())
		throw invalid_argument("Cannot open file: " + config_file_name);
	while (getline (configFile, line))
	{
		stringstream ss (line);
		string parameter;
		ss >> parameter;
		if (parameter == "NUMBER:")
			{ ss >> hand_size; number = true; }
		else if (parameter == "BOARD:")
		    { ss >> board_file_name; board = true; }
		else if (parameter == "TILES:")
			{ ss >> bag_file_name; tiles = true; }
		else if (parameter == "DICTIONARY:")
			{ ss >> dictionary_file_name; dictionary = true; }
	}
	if (!number)
		throw invalid_argument("Hand size not specified in config file");
	if (!board)
		throw invalid_argument("Board file name not specified in config file");
	if (!tiles)
		throw invalid_argument("Bag file name not specified in config file");
	if (!dictionary)
		throw invalid_argument("Dictionary file name not specified in config file");
}

int main (int nargs, char **args)
{
	if (nargs < 2 || nargs > 2)
		cout << "Usage: Scrabble <config-filename>\n";
	try {
		string dictionaryFileName, boardFileName, bagFileName;
		unsigned int numTiles;

		readConfigFile (args[1],
						dictionaryFileName, boardFileName, bagFileName,
						numTiles);

		Dictionary dict (dictionaryFileName);
		Board board (boardFileName);
		Bag bag (bagFileName, 794); // second argument is random seed
								   // note: not really random, use time(NULL)
		// Good luck!
	
		QApplication app(argc, argv);
		int numPlayers;

		StartWindow startWindow(numPlayers);



		cout << "=========WELCOME TO SCRABBLE=========" << endl;
		cout << "Please enter number of players (1-8): ";
		cin >> numPlayers;
		
		// Set player names
		vector<Player> players;
		for(int i = 1; i <= numPlayers; i++) {
			cout << "Please enter Player " << i << "'s name: ";
			string playerName;
			if(i == 1) {cin.ignore();} // so as not to store one of the names as blank
			getline(cin, playerName);
			Player temp(playerName, &bag, numTiles);
			players.push_back(temp);
		}

		
		// Now, let's begin the game!
		// While loop - while game is not over
		bool passGameOver = false;
		bool gameOver = false;
		int lastPlayerIndex = 0;
		while(!gameOver && !passGameOver) {
			// Loop through each player, round robin
			int passCounter = 0;
			for(unsigned int i = 0; i < players.size(); i++) {
				bool validCommand = false;
					// Will re-prompt player if player enters invalid command
					// Run until valid command given, then go to next player
					while(!validCommand) {
						cout << endl << endl << players[i].getName() << ", it's your turn!" << endl;
						cout << "Current Scores:" << endl;
						for(unsigned int j = 0; j < players.size(); j++) {
							cout << players[j].getName() << ": " << players[j].playerGetScore() << endl;
						}
						board.printBoard();
						cout << "To pass your turn, type PASS.\n" <<
							"To discard tiles, type EXCHANGE, followed by a string of those tiles.\n" <<
							"To place a word, type PLACE, followed by the following:\n" <<
		   					"\tfirst, either a | or - for vertical/horizontal placement;\n" <<
		   					"\tsecond, the row (from the top), the column (from the left),\n" <<
		   					"\tthird, a sequence of letters to place;\n" <<
		   					"\tto use a blank tile, type ? followed by the letter you want to use it for.\n";
		   				cout << "Your current tiles: ";
		   				players[i].playerPrintTiles();
						string playerCommand;
						getline(cin, playerCommand);

						transform(playerCommand.begin(), playerCommand.end(), playerCommand.begin(), ::toupper);

						Move playerMove = players[i].getMove(playerCommand); 

						if(playerMove.getMoveCommand() == "PASS") {
							passCounter++;
						}

						board.doMove(&playerMove, &validCommand, &bag, &(players[i]), &dict);



					}
				// After turn, check if the game is over.
				if(players[i].playerHandIsEmpty() && bag.tilesRemaining() == 0) {
					lastPlayerIndex = i;
					gameOver = true;
					break;
				}
			}
			if (passCounter == numPlayers) {
				passGameOver = true;
				cout << "GAME OVER!" << endl;
				cout << "Final Player Scores:" << endl;
				for(unsigned int j = 0; j < players.size(); j++) {
					cout << players[j].getName() << ": " << players[j].playerGetScore() << endl;
				}
			}
		}
		// GAME ENDED NOT BY PASSING, BUT BY SOMEBODY RUNNING OUT OF TILES WITH AN EMPTY BAG
		// SUBTRACT EVERYBODY'S SCORES BY THEIR HAND SCORE, AND ADD THE SUM OF THOSE TO THE
		// LAST PLAYER'S SCORE
		if(!passGameOver) {
			int addToLastPlayer = 0;
			for(unsigned int i = 0; i < players.size(); i++) {
				if((int)i != lastPlayerIndex) {
					int temp = players[i].getHandScore();
					addToLastPlayer += temp;
					players[i].playerSetScore(players[i].playerGetScore() - temp);
				}
			}
			players[lastPlayerIndex].playerSetScore(players[lastPlayerIndex].playerGetScore() + addToLastPlayer);
			for(unsigned int k = 0; k < players.size(); k++) {
				players[k].nukeHand();
			}
			cout << "GAME OVER!" << endl;
			cout << "Final Player Scores:" << endl;
			for(unsigned int j = 0; j < players.size(); j++) {
				cout << players[j].getName() << ": " << players[j].playerGetScore() << endl;
			}
			return 0;
		}
		else {
			return 0;
		}
	}
	catch (invalid_argument & e)
	{ cout << "Fatal Error! " << e.what(); }

	return 1;
}
