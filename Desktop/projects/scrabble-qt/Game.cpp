#include <string>
#include <fstream>
#include <iostream>
#include <sstream>
#include <stdexcept>
#include <algorithm>
#include "Game.h"
#include "start-window.h"
#include "game-window.h"
#include <QEventLoop>
using namespace std;

Game::Game(Dictionary & dict, Board & board, Bag & bag, int numTiles) {
	_dict = &dict;
	_board = &board;
	_bag = &bag;
	_numTiles = numTiles; 
	_numPlayers = 0;
	_currPlayer = 0;
	_passCounter = 0;
}

Game::~Game() {
	for(int i = 0; i < _numPlayers; i++) {
		_players[i]->nukeHand();
	}
	for(int i = 0; i <_numPlayers; i++) {
		delete _players[i];
	}
}
void Game::setNumPlayers(int n) {
	_numPlayers = n;
}

void Game::makePlayers(vector<string> & names) {
	for(int i = 0; i < _numPlayers; i++) {
		Player* temp = new Player(names[i], _bag, _numTiles);
		_players.push_back(temp);
	}
	for(int i = 0; i < _numPlayers; i++) {
		cout << _players[i]->getName() << endl;
	}
}

// Check after every turn
bool Game::isOver() {
	bool gameOver = false;
	if(_passCounter == _numPlayers) {
		gameOver = true;
	}
	else if(_bag->tilesRemaining() == 0) {
		for(int i = 0; i < _numPlayers; i++) {
			if(_players[i]->playerHandIsEmpty()) {
				_lastPlayerIndex = i;
				gameOver = true;
			}
		}
	}
	return gameOver;
}
string Game::getCurrPlayerName() {
	return _players[_currPlayer]->getName();
}
string Game::getPlayerName(int i) {
	return _players[i]->getName();
}
string Game::getCurrentHand() {
	return _players[_currPlayer]->getPlayerHand();
}
int Game::getPlayerScore(int i) {
	return(_players[i]->playerGetScore());
}
int Game::getNumPlayers() {
	return _numPlayers;
}
int Game::getCurrPlayer() {
	return _currPlayer;
}
bool Game::pass() {
	if (_currPlayer != _numPlayers-1) {
		_currPlayer++;
		_passCounter++;
	}
	else {
		_passCounter++;
		if(isOver()) {
			return true;
		}
		else {
			_currPlayer = 0;
			_passCounter = 0;
		}
	}
	return false;
}
string Game::exchange(string & exchangeLetters, bool & validCommand) {
	transform(exchangeLetters.begin(), exchangeLetters.end(), exchangeLetters.begin(), ::toupper);
	Move exchangeMove("EXCHANGE", exchangeLetters);
	_board->doMove(&exchangeMove, &validCommand, _bag, _players[_currPlayer], _dict);
	string newHand = getCurrentHand();
	if(validCommand == true) {
		if(_currPlayer != _numPlayers-1) {
			_currPlayer++;
			return newHand;
		}
		else {
			_currPlayer = 0;
			_passCounter = 0;
			return newHand;
		}
	}
	else {	
		return "";
	}
}
string Game::place(string & placeLetters, string & placeDirection, int row, int col, bool & validCommand) { 
	string message = "";
	transform(placeLetters.begin(), placeLetters.end(), placeLetters.begin(), ::toupper);
	Move placeMove("PLACE", placeDirection, row, col, placeLetters);
	message = _board->doMove(&placeMove, &validCommand, _bag, _players[_currPlayer], _dict);
	if(validCommand == true) {
		if(_currPlayer != _numPlayers-1) {
			_currPlayer++;
		}
		else {
			_currPlayer = 0;
			_passCounter = 0;
		}
	}
	if(isOver()) {
		endGameEval();
	}
	return message;
}

void Game::endGameEval() {
	int addToLastPlayer = 0;
	for(int i = 0; i < _numPlayers; i++) {
		if(i != _lastPlayerIndex) {
			int temp = _players[i]->getHandScore();
			addToLastPlayer += temp;
			_players[i]->playerSetScore(_players[i]->playerGetScore() - temp);
			cout << addToLastPlayer << endl;
		}
	}
	_players[_lastPlayerIndex]->playerSetScore(_players[_lastPlayerIndex]->playerGetScore() + addToLastPlayer);
	cout << addToLastPlayer << endl;
	cout << _players[_lastPlayerIndex]->playerGetScore() << endl;
}


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
		throw invalid_argument("Cannot open file: " + config_file_name + "\n");
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

int main(int nargs, char **args) {
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
			Bag bag (bagFileName, 794);

			Game game(dict, board, bag, numTiles);

			QApplication app (nargs, args);
  			StartWindow startWindow(game);
  			startWindow.show();

  		    QEventLoop loop;
    		QObject::connect(&startWindow, SIGNAL(openGameWindow()), &loop, SLOT(quit()));
  			loop.exec();

	  		GameWindow gameWindow(game, board);
	  		gameWindow.show();
	   		return app.exec();
	   	}
		catch (invalid_argument & e)
		{ cout << "Fatal Error! " << e.what(); }
		return 1;
}