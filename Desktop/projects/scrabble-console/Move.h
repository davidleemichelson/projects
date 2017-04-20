#ifndef MOVE_H
#define MOVE_H

#include <string>
#include "Bag.h"


class Move {
public:
	Move();
	Move(std::string);
	Move(std::string, std::string);
	Move(std::string, std::string, int, int, std::string);
	std::string getMoveCommand();
	std::string getExchangeLetters();
	std::string getPlaceDirection();
	std::string	getPlaceLetters();
	int getRow();
	int getCol();





private:
	std::string _command;
	std::string _placeDirection;
	std::string _exchangeLetters;
	std::string _placeLetters;
	int _row;
	int _col;
};

#endif

// Pass
// Exchange
// Place

