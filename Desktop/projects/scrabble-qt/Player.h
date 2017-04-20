#ifndef PLAYER_H
#define PLAYER_H

#include <string>
#include <vector>
#include "Tile.h"
#include "Bag.h"
#include "Move.h"

class Player {
public:
	Player(std::string, Bag*, int);
	int getHandSize();
	std::string getName();
	int playerGetScore();
	void playerSetScore(int);
	void playerDrawTiles(std::set<Tile*>);
	void playerPrintTiles();
	void playerExchangeTiles(std::string, Bag*, bool&);
	void playerRemoveTiles(std::string, Bag*);
	int getTileScore(char);
	Move getMove(std::string);
	bool playerHandIsEmpty();
	bool playerHasTiles(std::string&, std::string);
	int getHandScore();
	void nukeHand();
	std::string getPlayerHand();



private:
	std::string _name;
	int _score;
	std::set<Tile*> _hand;
};

#endif