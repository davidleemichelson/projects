#ifndef GAME_H_
#define GAME_H_

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <cstdlib>
#include <stdexcept>
#include <algorithm>
#include "Tile.h"
#include "Dictionary.h"
#include "Bag.h"
#include "Board.h"
#include "Player.h"
#include "Move.h"
#include <QApplication>

class Game {
public:
	Game(Dictionary &, Board &, Bag &, int);
	~Game();

	void readConfigFile(std::string, std::string &, std::string &, std::string &, unsigned int &);
	void setNumPlayers(int num);
	void makePlayers(std::vector<std::string> &);
	bool pass(); 
	std::string exchange(std::string &, bool &);
	std::string place(std::string &, std::string &, int, int, bool&); 
	bool isOver();
	void endGameEval(); 
	std::string getCurrPlayerName();
	std::string getPlayerName(int);
	int getPlayerScore(int);
	int getNumPlayers(); 
	std::string getCurrentHand();
	int getCurrPlayer();

private:
	Dictionary* _dict;
	Board* _board;
	Bag* _bag;
	int _numPlayers;
	std::vector<Player*> _players;
	int _currPlayer;
	int _passCounter;
	int _numTiles;
	int _lastPlayerIndex;

};

#endif