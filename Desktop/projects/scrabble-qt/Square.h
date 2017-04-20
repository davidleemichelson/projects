// Make a square class to make a 2D array of squares instead of chars
// Encapsulate multiplier, tile, etc.
#ifndef SQUARE_H_
#define SQUARE_H_
#include <string>


class Square {
public:
	Square() {
		_squareLetter = "";
		_wordMult = 1;
		_letterMult = 1;
	}
	Square(std::string squareLetter, int wordMult, int letterMult) {
		_squareLetter = squareLetter;
		_wordMult = wordMult;
		_letterMult = letterMult;
	}

	int getWordMult() {
		return _wordMult;
	}

	int getLetterMult() {
		return _letterMult;
	}

	std::string getSquareLetter() {
		return _squareLetter;
	}

	void setSquareLetter(std::string newSquareLetter) {
		_squareLetter = newSquareLetter;
	}

	bool isCovered() {
		return(_squareLetter != "..." && _squareLetter != "3L " && _squareLetter != "2L " &&
			_squareLetter != "3W " && _squareLetter != "2W ");
	}

private:
	int _wordMult;
	int _letterMult;
	std::string _squareLetter;
};

#endif