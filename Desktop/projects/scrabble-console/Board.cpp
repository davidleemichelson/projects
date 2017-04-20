/*
 * Board.cpp
 *
 *  Created on: Sep 18, 2016
 *      Author: kempe
 */

// The following is a suggestion for how to proceed.
// Feel free to make any changes you like.

#include <string>
#include <fstream>
#include <iostream>
#include <sstream>
#include <stdexcept>
#include <algorithm>
#include "Board.h"

using namespace std;

Board::Board (string board_file_name)
{
	ifstream boardFile (board_file_name.c_str());
	string row;

	_x = _y = _startx = _starty = 0; // to appease compiler
	if (!boardFile.is_open())
		throw invalid_argument("Cannot open file: " + board_file_name);
	getline (boardFile, row);
	stringstream s1 (row);
	s1 >> _x >> _y;
	getline (boardFile, row);
	stringstream s2 (row);
	s2 >> _startx >> _starty;
	_startx --; _starty --;  // coordinates from 0 in array
	// Anything else you need to initialize?

	for (int i = 0 ; i < _y; ++ i)
	{
		vector<Square> tempRow;
		getline (boardFile, row);
		for (int j = 0; j < _x; ++ j)
		{
			// Fill in the following based on how you store the board.
			if (i == _starty && j == _startx) {
				Square tempSquare("***", 2, 1); // 2x word multipler for start square
				tempRow.push_back(tempSquare);
			}
			else switch (row[j]) {
			case '.' : {
				Square tempSquare("...", 1, 1);
				tempRow.push_back(tempSquare);
			}
			break;
			case '2' : {
				Square tempSquare("2L ", 1, 2);
				tempRow.push_back(tempSquare);
			}
			break;
			case '3' : {
				Square tempSquare("3L ", 1, 3);
				tempRow.push_back(tempSquare);
			}
			break;
			case 'd' : {
				Square tempSquare("2W ", 2, 1);
				tempRow.push_back(tempSquare); 
			}
			break;
			case 't' : {
				Square tempSquare("3W ", 3, 1);
				tempRow.push_back(tempSquare);
			}
			break;
			default:
				string error = "Improper character in Board file: ";
				throw invalid_argument(error + row[j]);
			}
		}
		_board.push_back(tempRow);
	}
	boardFile.close ();
}


void Board::printBoard() {
	for(int i = 1; i <= _x; i++) {
		if(i <= 10) {
			cout << "   " << i;
		}
		else {
			cout << "  " << i;
		}
	}
	cout << endl;
	for(unsigned int i = 0; i < _board.size(); i++) {
		if (i+1 < 10) { cout << i+1 << "  "; }
		else { cout << i+1 << " ";}
		for(unsigned int j = 0; j < _board[i].size(); j++) {
			cout << _board[i][j].getSquareLetter() << " ";
		}
		cout << endl;
	}
}

void Board::doMove(Move* move, bool* validCommand, Bag* bag, Player* player, Dictionary* dict) {
	if(move->getMoveCommand() == "PASS") {
		*validCommand = true;
	}
	// Evaluate Exchange command
	else if(move->getMoveCommand() == "EXCHANGE") {
		bool validExchange = true;
		string exchangeLetters = move->getExchangeLetters();
		if(player->playerHasTiles(exchangeLetters, "EXCHANGE")) {
			player->playerExchangeTiles(exchangeLetters, bag, validExchange);
			if (!validExchange) {
				cout << "You must have all the tiles you wish to exchange." << endl;
				cout << "Please try again." << endl;
				*validCommand = false;
				return;
			}
			else {
				*validCommand = true;
				return;
			}
		}
		else {
			cout << "You must have all the tiles you wish to exchange." << endl;
			cout << "Please try again." << endl;
			*validCommand = false;
			return;
		}
	}
	else if(move->getMoveCommand() == "PLACE") { // if the command is place 
		if(move->getRow() > _y || move->getCol() > _x || move->getRow() < 1 || move->getCol() < 1) {
			cout << "One or more of the tiles placed would be out of bounds of the board 3. Please try again.\n";
			*validCommand = false;
			return;
		}
		string placeLetters = move->getPlaceLetters();
		string removeFromHandLetters = move->getPlaceLetters();
		for(unsigned int j = 0; j < removeFromHandLetters.size(); j++) { // To help remove tiles from player hand, delete dummy letters after question marks in hand string 
			if(removeFromHandLetters[j] == '?') {
				removeFromHandLetters.erase(removeFromHandLetters.begin() + j + 1);
				j++;
			}
		}
		if(player->playerHasTiles(placeLetters, "PLACE")) { // if the player has the tiles he/she wishes to place
			transform(placeLetters.begin(), placeLetters.end(), placeLetters.begin(), ::tolower);
			if(_board[_starty][_startx].getSquareLetter() == "***") { // First move
				if (placeLetters.size() == 1) {
					cout << "One letter words are not valid first moves. Please try again." << endl;
					*validCommand = false;
					return;
				}
				unsigned int x = _startx+1;
				unsigned int y = _starty+1;
				unsigned int col = move->getCol();
				unsigned int row = move->getRow();
				unsigned int wordSize = placeLetters.size();
				int preScore = 0;
				int totalWordMultiplier = 1;
				if(move->getPlaceDirection() == "-") { 
					if(row != y || (col + wordSize - 1) < x || col > x) { // if not in the same row as the start or if the word won't reach the start
						cout << "For the first move of the game, one of your tiles must be on the start square." << endl;
						cout << "Please try another move." << endl;
						*validCommand = false; // reprompt for move in scrabble.cpp
						return;
					}
					else {
						if(dict->contains(placeLetters)) { //  Don't have to check other tiles; first move. Go straight to dictionary check.
							for(unsigned int i = 0; i < wordSize; i++) { // Calculate score of word
								int wordMultiplier = _board[row-1][col+i-1].getWordMult();
								totalWordMultiplier *= wordMultiplier;

								int preLetterScore = player->getTileScore(toupper(placeLetters[i]));
								int letterMult = _board[row-1][col+i-1].getLetterMult();
								int totalLetterScore = preLetterScore * letterMult;
								preScore += totalLetterScore;

								string temp;
								stringstream ss;
								ss << placeLetters[i] << player->getTileScore(toupper(placeLetters[i])); // capital version of a char
								ss >> temp;
								temp = temp + " ";
								transform(temp.begin(), temp.end(), temp.begin(), ::toupper);
								_board[row-1][col+i-1].setSquareLetter(temp); // Place "tiles" on the board
							}

							int totalScore = preScore * totalWordMultiplier; // calculate total score of first word
							if((int)removeFromHandLetters.size() == player->getHandSize()) { // if player uses every tile in hand, add 50 points to score
								totalScore += 50;
							}
							player->playerSetScore(totalScore + player->playerGetScore()); // finally, update player's score 
							player->playerRemoveTiles(removeFromHandLetters, bag); // remove tiles from player's hand (this function draws as well)
						}
						else {
							cout << "Word not found in dictionary 3. Please try again." << endl;
							*validCommand = false;
							return;
						}
					}
				}
				else if(move->getPlaceDirection() == "|") {
					if(col != x || (row + wordSize-1) < y || row > y)  {
						cout << "For the first move of the game, one of your tiles must be on the start square." << endl;
						cout << "Please try another move." << endl;
						*validCommand = false;
						return;
					}
					else {
						if(dict->contains(placeLetters)) { // only have to check if it is in dictionary since it is first move, no other tiles on board
							for(unsigned int i = 0; i < wordSize; i++) {
								int wordMultiplier = _board[row+i-1][col-1].getWordMult();
								totalWordMultiplier *= wordMultiplier;

								int preLetterScore = player->getTileScore(toupper(placeLetters[i]));
								int letterMult = _board[row+i-1][col-1].getLetterMult();
								int totalLetterScore = preLetterScore * letterMult;
								preScore += totalLetterScore;

								string temp;
								stringstream ss;
								ss << placeLetters[i] << player->getTileScore(toupper(placeLetters[i])); // capital version
								ss >> temp;
								temp = temp + " ";
								transform(temp.begin(), temp.end(), temp.begin(), ::toupper);
								_board[row+i-1][col-1].setSquareLetter(temp);
							}
							
							int totalScore = preScore * totalWordMultiplier;
							if((int)removeFromHandLetters.size() == player->getHandSize()) {
								totalScore += 50;
							}
							player->playerSetScore(totalScore + player->playerGetScore());
							player->playerRemoveTiles(removeFromHandLetters, bag);
						}
						else {
							cout << "Word not found in dictionary 4. Please try again." << endl;
							*validCommand = false;
							return;
						}
					}
				}
			}

			else { // IF NOT THE FIRST MOVE // There will be lots of seg faults here, will have to fix them
				unsigned int col = move->getCol(); // These two variables keep track of the col and 
				unsigned int row = move->getRow(); // row picked by the user to begin placing tiles
				
				if(_board[row-1][col-1].isCovered()) { // This if statement checks if the player is trying to place on a tiled square
					cout << "The square you selected is already covered by a tile." << endl;
					cout << "Please try again." << endl;
					*validCommand = false;
					return;
				}
				// If the player desires to make a horizontal move not on the first turn
				else if(move->getPlaceDirection() == "-") {
					if((int)(col + placeLetters.size()) - 1 > _x || (int)(col+placeLetters.size()) - 1 < 0) {
						cout << "One or more of the tiles placed would be out of bounds of the board 1. Please try again.\n";
						*validCommand = false;
						return;
					}
					bool adjacent = false; // boolean to see if the desired move has no adjacent tiles; if so, we will later return
										   // i.e., if adjacent is *still* false after the upcoming evaluations, we will return
					// Loop to make placeLetters easier to work with by adding any tiles that would be inbetween the tiles we wish
					// to place to the placeLetters string. (i.e. if there's already a K and we do POEMON)
					// Will also use this loop to gather scores of whatever letters are already on the board and add to the east-west score
					int alreadyOnBoardScore = 0;
					for(unsigned int i = 0; i < placeLetters.size(); i++) { // accounts for letters already on board
						//if (row-1 < _y && col+i-1 < _x) {
							if(_board[row-1][col+i-1].isCovered()) { // if there is a covered tile where our word would go
								adjacent = true;
								stringstream ss; string temp;
								ss << _board[row-1][col+i-1].getSquareLetter()[0]; ss >> temp;
								placeLetters.insert(i, temp); // insert it into the placeLetters string to more easily determine the move's validity

								stringstream ss1;
								int tempScore;
								ss1 << _board[row-1][col+i-1].getSquareLetter()[1];
								ss1 >> tempScore;
								alreadyOnBoardScore += tempScore;
							}
					}

					// Check east and west neighbors of the word
					// calculate potential scores of each word
					// wait till end to apply scores, if we make it to end, no invalid words were proposed, we can setplayerscore
					// note - commented out update of letter score and word score by multipliers when checking neighbors because
					// only use neighbor scores by face value
					/************************************************EAST AND WEST*************************************************************/
					string eastWestPlaceLetters = placeLetters;
					int eastWestWordScore = 0 + alreadyOnBoardScore;
					if((int)(col-2) > 0) {
						if(_board[row-1][col-1-1].isCovered()) { // if proposed word's western neighbor is covered
							adjacent = true;
							int i = 0;
							while(_board[row-1][col-1-1-i].isCovered()) { // looking at all western neighbors of the string
								stringstream ss; string temp;
								ss << _board[row-1][col-1-1-i].getSquareLetter()[0]; ss >> temp; // turn char into string for easy prepend to string
								eastWestPlaceLetters.insert(0, temp); // prepend char to proposed string

								int letterScore; stringstream ss1; 
								ss1 << _board[row-1][col-1-1-i].getSquareLetter()[1]; // get score value of letter we're currently looking at
								ss1 >> letterScore;

								eastWestWordScore += letterScore; // add this letter's score to the word's entire score
								i++; // increment i to move to the next easternmost tile (if the tile is covered)
								if((int)(col-2-i) < 0) {
									break;
								}
							}
						}
					}
					int pos = col - 1 + placeLetters.size();
					if(pos < _x) {
						if(_board[row-1][col-1+placeLetters.size()].isCovered()) { // if proposed word's eastern neighbord is covered
							adjacent = true;
							int j = 0;
							while(_board[row-1][col-1+placeLetters.size()+j].isCovered()) { // looking at all eastern neighbors of the string
								stringstream ss; string temp;
								ss << _board[row-1][col-1+placeLetters.size()+j].getSquareLetter()[0]; ss >> temp; // get char as a string for easy append
								eastWestPlaceLetters.insert(eastWestPlaceLetters.size(), temp); // append char to proposed string 

								// note: now we have a string that includes the proposed placement, any letters that were already placed inbetween,
								// and its western and eastern neighbors. we can now halfway determine if it's a valid play. next just need to make
								// sure all northern and southern neighbors are in agreement.

								int letterScore; stringstream ss1;
								ss1 << _board[row-1][col-1+placeLetters.size()+j].getSquareLetter()[1]; ss1 >> letterScore;

								eastWestWordScore += letterScore;
								j++; // increment j to move to the next easternmost tile (if the tile is covered)
								if((int)(col-1+placeLetters.size()+j) >= _x) {
									break;
								}
							}
						}
					}
					transform(eastWestPlaceLetters.begin(), eastWestPlaceLetters.end(), eastWestPlaceLetters.begin(), ::tolower); // for purposes of checking dictionary
					
					// If place letters + east and west neighbors does not form a word, invalid command, exit
					if(!dict->contains(eastWestPlaceLetters)) {
						cout << "Word " << eastWestPlaceLetters << " not found in dictionary 1. Please try again." << endl;
						*validCommand = false;
						return;
					}

					// now, to check if all north and south neighbors are in agreement. sum the score of any words made by the north and south
					// neighbors. if all words formed are valid, we can place the letters, add northsouth eastwest scores, and set the player score.
					// otherwise, the function will exit and reprompt the user for another move.
					/***************************************************NORTH AND SOUTH**************************************************/
					int totalNorthSouthWordScore = 0; 
					// check north and south neighbors of the letters we are actually putting down
					for(unsigned int i = 0; i < placeLetters.size(); i++) { // need to use a loop since we are not just checking two ends (E/X)
						if(_board[row-1][col-1+i].isCovered()) {
							continue;
						}
						stringstream sss;									// we need to check the two ends (N/S) for every letter 
						sss << placeLetters[i]; // letter we are currently looking at
						string northSouthPlaceLetters;
						sss >> northSouthPlaceLetters; // letter we are currently looking at is the beginning of the string to which we will
													   // append and prepend letters
						//int northSouthWordMult = 1;
						int northSouthWordScore = 0;
						int northSouthWordMult = 1;
						// the multiple of this word would be any multiple found by the potential square of letter we are looking at
						if(!_board[row-1][col-1+i].isCovered()) {
							northSouthWordMult = _board[row-1][col-1+i].getWordMult();
						}
						else {
							northSouthWordMult = 1; // still 1...
						}

						northSouthWordScore = player->getTileScore(toupper(placeLetters[i])); // add score of letter we are looking at
						// check north neigbor, -1 from row; south neighbor will be +1 from row
						if((int)(row-2) > 0) { 
							if(_board[row-1-1][col-1+i].isCovered()) { // if north neighbor is covered, append all northern letters to back
								adjacent = true;
								int j = 0;
								while(_board[row-1-1-j][col-1+i].isCovered()) { // while northern neighbors still exist
									stringstream ss; string temp;
									ss << _board[row-1-1-j][col-1+i].getSquareLetter()[0]; ss >> temp;
									northSouthPlaceLetters.insert(0, temp); // append them to the back of the string

									stringstream ss2; int letterScore;
									ss2 << _board[row-1-1-j][col-1+i].getSquareLetter()[1]; // get neighbor letter score as int
									ss2 >> letterScore;

									northSouthWordScore += letterScore; // update total score before multiplying by word mult
									j++; // increment j so we can look at next northern letter until we hit a blank space
									if((int)(row-2-j) < 0) {
										break;
									}

								}
							}
						}
						// check south neighbor, +1 from row
						if((int)(row) < _x) {
							if(_board[row-1+1][col-1+i].isCovered()) { // checking south
								adjacent = true;
								int k = 0;
								while(_board[row-1+1+k][col-1+i].isCovered()) {
									stringstream ss; string temp;
									ss << _board[row-1+1+k][col-1+i].getSquareLetter()[0]; ss >> temp;
									northSouthPlaceLetters.insert(northSouthPlaceLetters.size(), temp);
									// add letter's potential score to northSouthScore
									// update the word multiplier
									stringstream ss2; int letterScore;
									ss2 << _board[row-1+1+k][col-1+i].getSquareLetter()[1];
									ss2 >> letterScore;

									northSouthWordScore += letterScore;
									k++;
									if((int)(row+k) >= _x) {
										break;
									}
								}
							}
						}
						if(northSouthPlaceLetters.size() == 1) {
							northSouthWordScore = 0;
						}
						// now, we have a vertical word attached to the current letter we are looking at, let's see if it's a valid
						// word. if it is not, we must return because this is an invalid place command.
						// if it is, we can continue looking at the other letters. if we make it past this if statement, through
						// the entire loop, then all north/south neighbors of desired place make valid letters and we can place
						// the letters on the board and update the player's score.
						transform(northSouthPlaceLetters.begin(), northSouthPlaceLetters.end(), northSouthPlaceLetters.begin(), ::tolower); // for purposes of checking dictionary
						if(!dict->contains(northSouthPlaceLetters)) {
							if(northSouthPlaceLetters.size() == 1) { // meaining no north/south neighbors. onto the next one.
							 	continue;
							} 
							cout << "Word " << northSouthPlaceLetters << " not found in dictionary 2. Please try again." << endl;
							*validCommand = false;
							return;
						}
						else {
							if(northSouthPlaceLetters.size() == 1) { // meaining no north/south neighbors. onto the next one. this watched out for "a"
								continue;
							}
							else {
								northSouthWordScore *= northSouthWordMult;
								totalNorthSouthWordScore += northSouthWordScore; // update score, then move to next letter
							}
						}
					}
					// we have exited the loop, either every word created is valid, or there are no adjacent tiles. if no adjacent, invalid.
					// otherwise, we can place letters and set score.
					if(!adjacent) {
						cout << "At least one tile must be placed adjacent to a tile already on the board. Please try again.\n";
						*validCommand = false;
						return;
					} 
					// no else since we return here
					int actuallyPlacingScore = 0;
					int actuallyPlacingMult = 1;
					for(unsigned int i = 0; i < placeLetters.size(); i++) {  // for each letter we want to place
						if(!_board[row-1][col-1+i].isCovered()) { // if the tile is not covered (i.e., if the "placeLetter" is not ALREADY there)
							stringstream ss; string temp;
							ss << placeLetters[i];
							ss >> temp; // make the letter we are looking at into a string for setSquareLetter
							stringstream ss1; string temp1;
							ss1 << player->getTileScore(toupper(placeLetters[i])); 
							ss1 >> temp1; // get tile score from player's hand in order to also put it onto the board;

							// find square multiplier, tile score, multiply, add to total

							stringstream ss2;
							ss2 << player->getTileScore(toupper(placeLetters[i])); 
							int letterScore;
							ss2 >> letterScore;

							stringstream ss3;
							ss3 << _board[row-1][col-1+i].getLetterMult();
							int letterMult;
							ss3 >> letterMult;


							letterScore *= letterMult;
							actuallyPlacingScore += letterScore;

							int wordMult;
							stringstream ss4;
							ss4 << _board[row-1][col-1+i].getWordMult();
							ss4 >> wordMult;
							actuallyPlacingMult *= wordMult;

							transform(temp.begin(), temp.end(), temp.begin(), ::toupper);
							temp = temp + temp1 + " ";
							_board[row-1][col-1+i].setSquareLetter(temp);
						}
					}
					
					// Ok, placed all the tiles, now we need to remove the tiles from the player's hand.
					// note, placeletters has changed; however, we maintained an original copy with
					// removeFromHandLetters. Use this to remove tiles from hand.
					
					eastWestWordScore += actuallyPlacingScore;
					eastWestWordScore *= actuallyPlacingMult;
					int totalScore = totalNorthSouthWordScore + eastWestWordScore;
					if((int)removeFromHandLetters.size() == player->getHandSize()) {
						totalScore += 50;
					}
					player->playerSetScore(totalScore + player->playerGetScore());
					player->playerRemoveTiles(removeFromHandLetters, bag);
					*validCommand = true;
				}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////				
				else if(move->getPlaceDirection() == "|") {
					if((int)(row + placeLetters.size()) - 1 > _y || (int)(row+placeLetters.size()) - 1 < 0) {
						cout << "One or more of the tiles placed would be out of bounds of the board 2. Please try again.\n";
						*validCommand = false;
						return;
					}
					bool adjacent = false; // boolean to see if the desired move has no adjacent tiles; if so, we will later return
										   // i.e., if adjacent is *still* false after the upcoming evaluations, we will return
					// Loop to make placeLetters easier to work with by adding any tiles that would be inbetween the tiles we wish
					// to place to the placeLetters string. (i.e. if there's already a K and we do POEMON)
					// Will also use this loop to gather scores of whatever letters are already on the board and add to the east-west score
					int alreadyOnBoardScore = 0;
					for(unsigned int i = 0; i < placeLetters.size(); i++) { // accounts for letters already on board
						if(_board[row+i-1][col-1].isCovered()) { // if there is a covered tile where our word would go
							adjacent = true;
							stringstream ss; string temp;
							ss << _board[row+i-1][col-1].getSquareLetter()[0]; ss >> temp;
							placeLetters.insert(i, temp); // insert it into the placeLetters string to more easily determine the move's validity

							stringstream ss1;
							int tempScore;
							ss1 << _board[row+i-1][col-1].getSquareLetter()[1];
							ss1 >> tempScore;
							alreadyOnBoardScore += tempScore;
						}
					}


					// Check east and west neighbors of the word
					// calculate potential scores of each word
					// wait till end to apply scores, if we make it to end, no invalid words were proposed, we can setplayerscore
					// note - commented out update of letter score and word score by multipliers when checking neighbors because
					// only use neighbor scores by face value
					/***********************************ACTUALLY NORTH AND SOUTH CONTRARY TO WHAT VARIABLE NAMES ARE****************************************/
					string eastWestPlaceLetters = placeLetters;
					int eastWestWordScore = 0 + alreadyOnBoardScore;
					//int eastWestWordMult = 1;
					if((int)(row-2) > 0) { // CHECK NORTH
						if(_board[row-1-1][col-1].isCovered()) { // if proposed word's western neighbor is covered
							adjacent = true;
							int i = 0;
							while(_board[row-1-1-i][col-1].isCovered()) { // looking at all western neighbors of the string
								stringstream ss; string temp;
								ss << _board[row-1-1-i][col-1].getSquareLetter()[0]; ss >> temp; // turn char into string for easy prepend to string
								eastWestPlaceLetters.insert(0, temp); // prepend char to proposed string

								int letterScore; stringstream ss1; 
								ss1 << _board[row-1-1-i][col-1].getSquareLetter()[1]; // get score value of letter we're currently looking at
								ss1 >> letterScore;
								eastWestWordScore += letterScore; // add this letter's score to the word's entire score
								i++; // increment i to move to the next easternmost tile (if the tile is covered)
								if((int)(row-2-i) < 0)  {
									break;
								}
							}
						}
					}
					int pos = row - 1 + placeLetters.size();
					if(pos < _y) {
						if(_board[row-1+placeLetters.size()][col-1].isCovered()) { // if proposed word's eastern neighbord is covered
							adjacent = true;
							int j = 0;
							while(_board[row-1+placeLetters.size()+j][col-1].isCovered()) { // looking at all eastern neighbors of the string
								stringstream ss; string temp;
								ss << _board[row-1+placeLetters.size()+j][col-1].getSquareLetter()[0]; ss >> temp; // get char as a string for easy append
								eastWestPlaceLetters.insert(eastWestPlaceLetters.size(), temp); // append char to proposed string 
								// note: now we have a string that includes the proposed placement, any letters that were already placed inbetween,
								// and its western and eastern neighbors. we can now halfway determine if it's a valid play. next just need to make
								// sure all northern and southern neighbors are in agreement.
								int letterScore; stringstream ss1;
								ss1 << _board[row-1+placeLetters.size()+j][col-1].getSquareLetter()[1]; ss1 >> letterScore;
								eastWestWordScore += letterScore;
								j++; // increment j to move to the next easternmost tile (if the tile is covered)
								if((int)(row-1+placeLetters.size()+j) >= _y) {
									break;
								}
							}
						}
					}
					transform(eastWestPlaceLetters.begin(), eastWestPlaceLetters.end(), eastWestPlaceLetters.begin(), ::tolower); // for purposes of checking dictionary
					
					// If place letters + east and west neighbors does not form a word, invalid command, exit
					if(!dict->contains(eastWestPlaceLetters)) {
						cout << "Word " << eastWestPlaceLetters << " not found in dictionary 1. Please try again." << endl;
						*validCommand = false;
						return;
					}
					// now, to check if all north and south neighbors are in agreement. sum the score of any words made by the north and south
					// neighbors. if all words formed are valid, we can place the letters, add northsouth eastwest scores, and set the player score.
					// otherwise, the function will exit and reprompt the user for another move.
					/***************************************************ACTUALLY EAST AND WEST**************************************************/
					int totalNorthSouthWordScore = 0; 
					// check north and south neighbors of the letters we are actually putting down
					for(unsigned int i = 0; i < placeLetters.size(); i++) { // need to use a loop since we are not just checking two ends (E/X)
						if(_board[row-1+i][col-1].isCovered()) {
							continue;
						}
						stringstream sss;									// we need to check the two ends (N/S) for every letter 
						sss << placeLetters[i]; // letter we are currently looking at
						string northSouthPlaceLetters;
						sss >> northSouthPlaceLetters; // letter we are currently looking at is the beginning of the string to which we will
													   // append and prepend letters
						int northSouthWordScore = 0;
						int northSouthWordMult = 1;
						// the multiple of this word would be any multiple found by the potential square of letter we are looking at
						if(!_board[row-1+i][col-1].isCovered()) {
							northSouthWordMult = _board[row-1+i][col-1].getWordMult();
						}
						else {
							northSouthWordMult = 1; // still 1...
						}

						northSouthWordScore = player->getTileScore(toupper(placeLetters[i])); // add score of letter we are looking at
						// check north neigbor, -1 from row; south neighbor will be +1 from row
						if((int)(col-2) > 0) {
							if(_board[row-1+i][col-1-1].isCovered()) { // if north neighbor is covered, append all northern letters to back
								adjacent = true;
								int j = 0;
								while(_board[row-1+i][col-1-1-j].isCovered()) { // while northern neighbors still exist
									stringstream ss; string temp;
									ss << _board[row-1+i][col-1-1-j].getSquareLetter()[0]; ss >> temp;
									northSouthPlaceLetters.insert(0, temp); // append them to the back of the string

									stringstream ss2; int letterScore;
									ss2 << _board[row-1+i][col-1-1-j].getSquareLetter()[1]; // get neighbor letter score as int
									ss2 >> letterScore;
									northSouthWordScore += letterScore; // update total score before multiplying by word mult
									j++; // increment j so we can look at next northern letter until we hit a blank space
									if((int)(col-2-j) < 0) {
										break;
									}
								}
							}
						}
						// check south neighbor, +1 from row
						if((int)col < _x) {
							if(_board[row-1+i][col-1+1].isCovered()) {
								adjacent = true;
								int k = 0;
								while(_board[row-1+i][col-1+1+k].isCovered()) {
									stringstream ss; string temp;
									ss << _board[row-1+i][col-1+1+k].getSquareLetter()[0]; ss >> temp;
									northSouthPlaceLetters.insert(northSouthPlaceLetters.size(), temp);
									
									// add letter's potential score to northSouthScore
									// update the word multiplier
									stringstream ss2; int letterScore;
									ss2 << _board[row-1+i][col-1+1+k].getSquareLetter()[1];
									ss2 >> letterScore;

									northSouthWordScore += letterScore;
									k++;
									if((int)(col+k) >= _x) {
										break;
									}
								}
							}
						}
						if(northSouthPlaceLetters.size() == 1) {
							northSouthWordScore = 0;
						}
						// now, we have a vertical word attached to the current letter we are looking at, let's see if it's a valid
						// word. if it is not, we must return because this is an invalid place command.
						// if it is, we can continue looking at the other letters. if we make it past this if statement, through
						// the entire loop, then all north/south neighbors of desired place make valid letters and we can place
						// the letters on the board and update the player's score.
						transform(northSouthPlaceLetters.begin(), northSouthPlaceLetters.end(), northSouthPlaceLetters.begin(), ::tolower); // for purposes of checking dictionary
						if(!dict->contains(northSouthPlaceLetters)) {
							if(northSouthPlaceLetters.size() == 1) { // meaining no north/south neighbors. onto the next one.
							 	continue;
							} 
							*validCommand = false;
							return;
						}
						else {
							if(northSouthPlaceLetters.size() == 1) { // meaining no north/south neighbors. onto the next one. this watched out for "a"
								continue;
							}
							else {
								northSouthWordScore *= northSouthWordMult;
								totalNorthSouthWordScore += northSouthWordScore; // update score, then move to next letter
							}
						}
					}
					// we have exited the loop, either every word created is valid, or there are no adjacent tiles. if no adjacent, invalid.
					// otherwise, we can place letters and set score.
					if(!adjacent) {
						cout << "At least one tile must be placed adjacent to a tile already on the board. Please try again.\n";
						*validCommand = false;
						return;
					} 
					// no else since we return here
					int actuallyPlacingScore = 0;
					int actuallyPlacingMult = 1;
					for(unsigned int i = 0; i < placeLetters.size(); i++) {  // for each letter we want to place
						if(!_board[row-1+i][col-1].isCovered()) { // if the tile is not covered (i.e., if the "placeLetter" is not ALREADY there)
							stringstream ss; string temp;
							ss << placeLetters[i];
							ss >> temp; // make the letter we are looking at into a string for setSquareLetter
							stringstream ss1; string temp1;
							ss1 << player->getTileScore(toupper(placeLetters[i])); 
							ss1 >> temp1; // get tile score from player's hand in order to also put it onto the board;

							// find square multiplier, tile score, multiply, add to total

							stringstream ss2;
							ss2 << player->getTileScore(toupper(placeLetters[i])); 
							int letterScore;
							ss2 >> letterScore;

							stringstream ss3;
							ss3 << _board[row-1+i][col-1].getLetterMult();
							int letterMult;
							ss3 >> letterMult;


							letterScore *= letterMult;
							actuallyPlacingScore += letterScore;

							int wordMult;
							stringstream ss4;
							ss4 << _board[row-1+i][col-1].getWordMult();
							ss4 >> wordMult;
							actuallyPlacingMult *= wordMult;


							transform(temp.begin(), temp.end(), temp.begin(), ::toupper);
							temp = temp + temp1 + " ";
							_board[row-1+i][col-1].setSquareLetter(temp);
						}
					}
					
					// Ok, placed all the tiles, now we need to remove the tiles from the player's hand.
					// note, placeletters has changed; however, we maintained an original copy with
					// removeFromHandLetters. Use this to remove tiles from hand.
					
					eastWestWordScore += actuallyPlacingScore;
					eastWestWordScore *= actuallyPlacingMult;
					int totalScore = totalNorthSouthWordScore + eastWestWordScore;
					if((int)removeFromHandLetters.size() == player->getHandSize()) {
						totalScore += 50;
					}
					player->playerSetScore(totalScore + player->playerGetScore());
					player->playerRemoveTiles(removeFromHandLetters, bag);
					*validCommand = true;
				}
			}
		}
		else {
			cout << "You must have the tiles that you wish to place." << endl;
			cout << "Please try again." << endl;
			*validCommand = false;
			return;
		}
		*validCommand = true;
	}
	else {
		cout << "Did not recognize your command, please try again." << endl << endl;
	}
}