#include "Move.h"
#include <iostream>

using namespace std;


// Default constructor
Move::Move() { 
	_command = "";
	_placeDirection = "";
	_row = 0;
	_col = 0;
	_exchangeLetters = "";
	_placeLetters = "";
}
// Pass constructor
Move::Move(string command) { 
	_command = command;
}
// Exchange constructor
Move::Move(string command, string exchangeLetters) {
	_command = command;
	_exchangeLetters = exchangeLetters;
}
// Place constructor
Move::Move(string command, string placeDirection, int row, int col, string placeLetters) {
	_command = command;
	_placeDirection = placeDirection;
	_row = row;
	_col = col;
	_placeLetters = placeLetters;
}

string Move::getMoveCommand() {
	return _command;
}
string Move::getExchangeLetters() {
	return _exchangeLetters;
}
string Move::getPlaceDirection() {
	return _placeDirection;
}
string Move::getPlaceLetters() {
	return _placeLetters;
}

int Move::getRow() {
	return _row;
}
int Move::getCol() {
	return _col;
}
