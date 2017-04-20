/*
 * Dictionary.cpp
 *
 *  Created on: Sep 18, 2016
 *      Author: kempe
 */

// The following is a suggestion for how to proceed.
// Feel free to make any changes you like.

#include <string>
#include <fstream>
#include <iostream>
#include <stdexcept>
#include <algorithm>
#include "Dictionary.h"

using namespace std;

Dictionary::Dictionary (string dictionary_file_name)
{
	ifstream dictFile (dictionary_file_name.c_str());
	string word;

	if (dictFile.is_open())
	{
		while (getline (dictFile, word))
		{
			// word.erase(word.length()-1); // remove end-of-line character
			
			// What do you want to do with the word?

			// Insert word into the dictionary set, "words"
			words.insert(words.end(), word);
		
		}
		dictFile.close ();
	}
	else throw invalid_argument("Cannot open file: " + dictionary_file_name);
}

bool Dictionary::contains(string word) {
	string fixWord = word;
	transform(fixWord.begin(), fixWord.end(), fixWord.begin(), ::tolower);
	return (words.find(word) != words.end());
}