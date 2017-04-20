#ifndef BaseAI_H_
#define BaseAI_H_


#include <string>
#include <map>
#include <iostream>

#include "Move.h"
#include "Dictionary.h"
#include "Board.h"
#include "Player.h"
#include "AbstractAI.h"


class BaseAI : public AbstractAI {
 public:
  std::string getName() {
  	  return _name;
  }
  // returns the name by which the AI would like to be called

  void initialize (Dictionary* dict) {
    _name = "BaseAI";
  	_dict = dict; // keep pointer to the dictionary so we can keep using it
  	_allWords = dict->allWords();
  	std::set<std::string>::iterator it; 
  	for(it = _allWords.begin(); it != _allWords.end(); it++) {
    	for(unsigned int i = 0; i < it->size(); i++) {
      		_prefixes.insert(it->substr(0, i));
    	}
  	}
  }
  /* you can use this function to do pre-processing, such as 
     constructing maps for fast lookup.
     We will call initilalize on your AI before asking it for its first move. */

  Move getMove (const Board & board, const Player & player, std::map<char, int> initialTileCount) {
  	// std::cout << "Got move!" << std::endl;
  	Move move;
  	return move;
  }
  /* This will be the main function doing the work.
     You will get the board and player, and have hopefully stored the dictionary.
     You also get a map giving you the initial number of each type of tiles in the bag.
     (The board class allows you to find out how many of each type have been placed.)
     You should return a Move object describing the move your AI wants to make.
  */
 protected:
   Dictionary* _dict;
   Board* _board;
   Player* _player;
   std::string _name;
   std::set<std::string> _prefixes;
   std::set<std::string> _allWords;
   std::vector<Move*> _possibleMoves;
   int _handSize;
};
#endif