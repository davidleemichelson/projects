#ifndef LengthAI_H_
#define LengthAI_H_


#include <string>
#include <map>

#include "Move.h"
#include "Dictionary.h"
#include "Board.h"
#include "Player.h"
#include "AbstractAI.h"
#include "BaseAI.h"

class LengthAI : public BaseAI {
 public:
  
  void initialize (Dictionary* dict) {
    BaseAI::initialize(dict);
    _name = "LengthAI";
  }
 
  Move getMove (const Board & board, const Player & player, std::map<char, int> initialTileCount) {
    for(unsigned int i = 0; i < _possibleMoves.size(); i++) {
      delete _possibleMoves[i];
    }
    _possibleMoves.clear();
    _handSize = player.getHandTiles().size();
    std::string temp = player.getHand();
    std::string prefix = "";
    std::string hand = "";
    for(unsigned int i = 0; i < temp.size(); i++) { // 63, 65-90
      if(temp[i] == 63 || (temp[i] > 64 && temp[i] < 91)) {
        std::stringstream ss; std::string dummy; ss << temp[i]; ss >> dummy;
        hand += dummy;
      }
    } 
    // std::cout << hand << std::endl;
    int rows = board.getRows(); int cols = board.getColumns();
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < cols; j++) {
        if (!(board.getSquare(j,i)->isOccupied())) {
          horizontalMoveHelper(i,j,i,j,board,player,prefix,hand);
          verticalMoveHelper(i,j,i,j,board,player,prefix,hand);
        }
      }
    }
    // std::cout << _possibleMoves.size() << " possible moves" << std::endl;
    for(unsigned int i = 0; i < _possibleMoves.size(); i++) {
      std::vector<Tile*> tiles = _possibleMoves[i]->tileVector();
      // std::cout << i << std::endl;
      for(unsigned int j = 0; j < tiles.size(); j++) {
        // std::cout << tiles[j]->getLetter() << " ";
      }
      // std::cout << _possibleMoves[i]->x() << " " <<_possibleMoves[i]->y() <<  std::endl;
    }
    if(_possibleMoves.size() == 0) {Move move; return move;} // pass if there are no possible moves
    // std::cout << board.getDisplay() << std::endl;

    int longestIndex; int maxTiles = 0;
    for(unsigned int i = 0; i < _possibleMoves.size(); i++) {
      // std::cout << "_possibleMoves[i]->tileVector().size() " << _possibleMoves[i]->tileVector().size() <<std::endl;
      if ((int)_possibleMoves[i]->tileVector().size() > maxTiles) {
        maxTiles = _possibleMoves[i]->tileVector().size();
        // std::cout << "New max: " << maxTiles << std::endl;
        longestIndex = i;
      }
    }
    /* TO DO: FIND MOVE WITH HIGHEST SCORE AND USE THAT ONE */

    return *_possibleMoves[longestIndex];    // now, find which move is the best move
  }
  bool horizontalValid(int x,int y, const std::string & prefix, const Player & player, Move* & move, const Board & board, bool & goodMove) {
    goodMove = false;
    std::vector<std::string> words;
    try {
      if(_prefixes.find(prefix) == _prefixes.end()) {
        // std::cout << "Bad prefix: " << prefix << std::endl;
        return false;
      }
      move = new Move(x,y,true,prefix,player); int dummy = 0; // Will throw exception if doesn't work
      words = board.getWords(*move,dummy);
      for(unsigned int i = 0; i < words.size(); i++) {
        goodMove = false;
        if(_prefixes.find(words[i]) == _prefixes.end()) {
          // std::cout << "Bad prefix 1: " << prefix << std::endl;
          return false;
        }
        if(_dict->isLegalWord(words[i])) {
          goodMove = true;
          for(unsigned int j = 0; j < words.size(); j++) {
            if(!_dict->isLegalWord(words[j])) {
              goodMove = false;
            }
          }
        }
      }
    }
    catch (MoveException & me) {
      if (me.getMessage() == "NONEIGHBOR") {
        // std:: cout << "No neighbor prefix: " << prefix << std::endl;
        return true;

      // std::cout << me.getMessage() << " invalid horizontal move." << std::endl;
      // std::cout << "Bad prefix: " << prefix << std::endl;
      return false;
    }
    // std::cout << "actulaly made it out" << std::endl;
    return true;
  }
  return true;
}
  bool verticalValid(int x,int y, const std::string & prefix, const Player & player, Move* & move, const Board & board, bool & goodMove) {
    goodMove = false;
    std::vector<std::string> words;
    try {
      if(_prefixes.find(prefix) == _prefixes.end()) {
        // std::cout << "Bad prefix: " << prefix << std::endl;
        return false;
      }
      move = new Move(x,y,false,prefix,player); int dummy = 0; // Will throw exception if doesn't work
      words = board.getWords(*move,dummy);
      for(unsigned int i = 0; i < words.size(); i++) {
        goodMove = false;
        if(_prefixes.find(words[i]) == _prefixes.end()) {
          // std::cout << "Bad prefix 1: " << prefix << std::endl;
          return false;
        }
        if(_dict->isLegalWord(words[i])) {
          goodMove = true;
          for(unsigned int j = 0; j < words.size(); j++) {
            if(!_dict->isLegalWord(words[j])) {
              goodMove = false;
            }
          }
        }
      }
    }
    catch (MoveException & me) {
      if (me.getMessage() == "NONEIGHBOR") {
        // std:: cout << "No neighbor prefix: " << prefix << std::endl;
        return true;
      }
      // std::cout << me.getMessage() << " invalid horizontal move." << std::endl;
      // std::cout << "Bad prefix: " << prefix << std::endl;
      return false;
    }
    // std::cout << "actulaly made it out" << std::endl;
    return true;
  }
  void horizontalMoveHelper(int row, int col, int y, int x, const Board & board, const Player & player, std::string prefix, std::string hand) {
    // std::cout << "ROW " << row << " COL " << col << std::endl;
    // std::cout << prefix << std::endl;
    if(col >= board.getColumns()) {
      // std::cout << "COL IS " << col << " AND WE ARE RETURNING BABY" << std::endl;
      return;
    }
    else {
        for(unsigned int i = 0; i < hand.size(); i++) {
          if(hand[i] == '?') {
            std::string alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            for(unsigned int j = 0; j < alphabet.size(); j++) {
              prefix += alphabet[j];
              // std::cout << prefix << std::endl;
              Move* tempMove = NULL; // tempMove to add to member vector of moves if valid
              bool goodMove = false;
              if(horizontalValid(x,y,prefix,player,tempMove,board,goodMove)) { // if this is a valid move        
                std::string goodHand = hand;
                hand.erase(hand.begin()+i);
                std::string dummyHand = hand;
                hand = goodHand;
                if(goodMove) {
                  _possibleMoves.push_back(tempMove);
                  // std::cout << "Pushing back good move! " << std::endl;
                }
                horizontalMoveHelper(row,col+1,y,x,board,player,prefix,dummyHand);
              }
              prefix.erase(prefix.end()-1);
            }
          }
          else {
            prefix += hand[i]; // update prefix based on loop (which letter in the hand are we looking at)
            // std::cout << prefix << std::endl;
            Move* tempMove = NULL; // tempMove to add to member vector of moves if valid
            bool goodMove = false;
            if(horizontalValid(x,y,prefix,player,tempMove,board,goodMove)) { // if this is a valid move        
              std::string goodHand = hand;
              hand.erase(hand.begin()+i);
              std::string dummyHand = hand;
              hand = goodHand;
              if(goodMove) {
                _possibleMoves.push_back(tempMove);
                // std::cout << "Pushing back good move! " << std::endl;
              }
              horizontalMoveHelper(row,col+1,y,x,board,player,prefix,dummyHand);
            }
            prefix.erase(prefix.end()-1);
          }
        }
      }
  }


  void verticalMoveHelper(int row, int col, int y, int x, const Board & board, const Player & player, std::string prefix, std::string hand) {
    // std::cout << "ROW " << row << " COL " << col << std::endl;
    // std::cout << prefix << std::endl;
    if(row >= board.getRows()) {
      // std::cout << "COL IS " << col << " AND WE ARE RETURNING BABY" << std::endl;
      return;
    }
    else {
      if(!(board.getSquare(col,row)->isOccupied())) { // We can place a letter if the space is not occupied
        for(unsigned int i = 0; i < hand.size(); i++) {
          if(hand[i] == '?') {
            std::string alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            for(unsigned int j = 0; j < alphabet.size(); j++) {
              prefix += hand[i]; // update prefix based on loop (which letter in the hand are we looking at)
              // std::cout << prefix << std::endl;
              Move* tempMove = NULL; // tempMove to add to member vector of moves if valid
              bool goodMove = false;
              if(verticalValid(x,y,prefix,player,tempMove,board,goodMove)) { // if this is a valid move
                
                std::string goodHand = hand;
                hand.erase(hand.begin()+i);
                std::string dummyHand = hand;
                hand = goodHand;
                if(goodMove) {
                  _possibleMoves.push_back(tempMove);
                  // std::cout << "Pushing back good move! " << std::endl;
                }
                verticalMoveHelper(row+1,col,y,x,board,player,prefix,dummyHand);
              }
              prefix.erase(prefix.end()-1);
            }
          }
          prefix += hand[i]; // update prefix based on loop (which letter in the hand are we looking at)
          // std::cout << prefix << std::endl;
          Move* tempMove = NULL; // tempMove to add to member vector of moves if valid
          bool goodMove = false;
          if(verticalValid(x,y,prefix,player,tempMove,board,goodMove)) { // if this is a valid move
            
            std::string goodHand = hand;
            hand.erase(hand.begin()+i);
            std::string dummyHand = hand;
            hand = goodHand;
            if(goodMove) {
              _possibleMoves.push_back(tempMove);
              // std::cout << "Pushing back good move! " << std::endl;
            }
            verticalMoveHelper(row+1,col,y,x,board,player,prefix,dummyHand);
          }
          prefix.erase(prefix.end()-1);
        }
      }
    }
  }
};

#endif

