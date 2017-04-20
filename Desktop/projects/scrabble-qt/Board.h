/*
 * Board.h
 *
 *  Created on: Sep 18, 2016
 *      Author: kempe
 */

// The following is a suggestion for how to proceed.
// Feel free to make any changes you like.

#ifndef BOARD_H_
#define BOARD_H_

#include <string>
#include <vector>
#include "Square.h"
#include "Move.h"
#include "Bag.h"
#include "Player.h"
#include "Dictionary.h"

class Board {
public:
	Board (std::string board_file_name);

	// What else will this need?
	void printBoard();
	std::string doMove(Move*, bool*, Bag*, Player*, Dictionary*);

	int getX();
	int getY();

	std::string getLabel(int, int);

private:
	int _x, _y;
	int _startx, _starty;

	// What else will this need?
	std::vector<std::vector<Square> > _board;

};


#endif /* BOARD_H_ */
