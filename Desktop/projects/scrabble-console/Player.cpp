#include "Player.h"
#include <iostream>
#include <sstream>

using namespace std;

Player::Player(string name, Bag* bag, int numTiles) {
	_name = name;
	_score = 0;
	playerDrawTiles(bag->drawTiles(numTiles));
}

int Player::getHandSize() {
	return _hand.size();
}

void Player::playerDrawTiles(set<Tile*> drawSet) {
	_hand.insert(drawSet.begin(), drawSet.end());
}

int Player::getTileScore(char letter) {
	set<Tile*>::iterator it;
	for(it = _hand.begin(); it != _hand.end(); it++) {
		if((*it)->getLetter() == letter) {
			return (*it)->getPoints();
		}
		else if((*it)->getUse() == letter) {
			return 0;
		}
	}
	return -1;
}


void Player::playerPrintTiles() {
	set<Tile*>::iterator it;
	for(it = _hand.begin(); it != _hand.end(); it++) {
		cout << "[" << (*it)->getLetter() << ", ";
		cout << (*it)->getPoints() << "] ";
	}
	cout << endl;
}

void Player::playerSetScore(int score) {
	_score = score;
}

Move Player::getMove(string playerCommand) {
	stringstream ss;
	ss << playerCommand;
	string option;
	ss >> option;
	if(option == "PASS") {
		Move move(option);
		return move;
	}
	else if(option == "EXCHANGE") {
		string exchangeTiles;
		ss >> exchangeTiles;
		Move move(option, exchangeTiles);
		return move;
	}
	else if(option == "PLACE") {
		string direction;
		ss >> direction;
		int row = 0;
		int col = 0;
		ss >> row;
		ss >> col;
		string placeme;
		ss >> placeme;
		Move move(option, direction, row, col, placeme);
		return move;
	}
	else {
		Move move;
		return move;
	}
}

string Player::getName() {
	return _name;
}

int Player::playerGetScore() {
	return _score;
}

void Player::playerExchangeTiles(string lettersToExchange, Bag* bag, bool& validExchange) {
	vector<Tile*> tilesToExchange;
	for(unsigned int i = 0; i < lettersToExchange.size(); i++) {
		set<Tile*>::iterator it;
		for(it = _hand.begin(); it != _hand.end(); it++) {
			if((*it)->getLetter() == lettersToExchange[i]) {
				tilesToExchange.push_back(*it);
				_hand.erase(*it);
				break;
			}
		}
	}
	bag->addTiles(tilesToExchange);
	if(lettersToExchange.size() > tilesToExchange.size()) { // to deal with "exchange aa" exchanging more if player only has 1 a
		for(unsigned int i = 0; i < tilesToExchange.size(); i++) {
			_hand.insert(tilesToExchange[i]);
		}
		validExchange = false;
		return;
	}
	playerDrawTiles(bag->drawTiles(tilesToExchange.size()));
	playerPrintTiles();
}

void Player::playerRemoveTiles(string tilesToRemove, Bag* bag) {
	for(unsigned int i = 0; i < tilesToRemove.size(); i++) {
		set<Tile*>::iterator it;
		for(it = _hand.begin(); it != _hand.end(); it++) {
			if((*it)->getLetter() == tilesToRemove[i]) {
				Tile* temp = (*it);
				_hand.erase(*it);
				delete temp;
				break;
			}
		}
	}
	unsigned int drawCounter = 0;
	while(bag->tilesRemaining() > 0 && drawCounter < tilesToRemove.size()) {
		playerDrawTiles(bag->drawTiles(1));
		drawCounter++;
	}
}

bool Player::playerHandIsEmpty() {
	return (_hand.empty());
}

bool Player::playerHasTiles(string& letters, string command) {
	if(command == "EXCHANGE") {
		bool inHand = false;
		for(unsigned int i = 0; i < letters.size(); i++) {
			inHand = false;
			set<Tile*>::iterator it;
			for(it = _hand.begin(); it != _hand.end(); it++) {
				if((*it)->getLetter() == letters[i]) {
					inHand = true;
					break;
				}
			}
			if(!inHand) {
				return inHand;
			}
		}
		return true;
	}
	else { // command is place // does not account for two of same letter  // NOTE - THIS BROKE EVERYTHING. Fix it NOW.
		vector<Tile*> placeTiles;
		bool inHand = false;
		for(unsigned int i = 0; i < letters.size(); i++) { // iterating through each letter of proposed place statement
			inHand = false;
			set<Tile*>::iterator it;
			for(it = _hand.begin(); it != _hand.end(); it++) { // loop through each item in hand to see if there is a tile with the letter we are looking at 
				if((*it)->getLetter() == letters[i]) { // if there is a tile with the letter we are looking at
					placeTiles.push_back(*it); // push it to this vector so we can save it and add it back to the hand if need be
					inHand = true; // in hand is true because we did find the letter; inHand will be re-assigned to false upon next iteration, so if something is not found in the set, inHand will be false
					if((*it)->isBlank()) { // if the tile is a question mark (a blank tile)
						(*it)->useAs(letters[i+1]); // update its useas
						letters.erase(letters.begin() + i); // and delete the question mark from the proposed place statement
					}
					_hand.erase(*it);
					break;
				}
			}
		}
		if (!inHand) { 
			for(unsigned int i = 0; i < placeTiles.size(); i++) {
				_hand.insert(placeTiles[i]);
			}
			return false;
		}
		else if (letters.size() > placeTiles.size()) {
			inHand = false;
			for(unsigned int i = 0; i < placeTiles.size(); i++) {
				_hand.insert(placeTiles[i]);
			}
			return false;
		}
		else {
			for(unsigned int i = 0; i < placeTiles.size(); i++) {
				_hand.insert(placeTiles[i]);
			}
			return true;
		}
	}
}

int Player::getHandScore() {
	set<Tile*>::iterator it;

	int handScore = 0;

	for(it = _hand.begin(); it != _hand.end(); it++) {
		int temp = (*it)->getPoints();
		handScore += temp;
	}
	return handScore;
}

void Player::nukeHand() {
	set<Tile*>::iterator it;
	for(it = _hand.begin(); it != _hand.end(); it++) {
		Tile* temp = *it;
		delete temp;
	}
}
