/*
 * Dictionary.h
 *
 *  Created on: Sep 18, 2016
 *      Author: kempe
 */

// The following is a suggestion for how to proceed.
// Feel free to make any changes you like.

#ifndef DICTIONARY_H_
#define DICTIONARY_H_

#include <string>
#include <set>

class Dictionary {
public:
	Dictionary (std::string dictionary_file_name);

	bool contains(std::string word);

	// what else will this need?

	// set of strings to hold all the words -- private?
private:
	std::set<std::string> words;
};


#endif /* DICTIONARY_H_ */
