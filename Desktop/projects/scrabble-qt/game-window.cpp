#include "game-window.h"
#include<QHBoxLayout>
#include<QVBoxLayout>
#include<QFormLayout>
#include<QMessageBox>
#include<QSpinBox>
#include<QLabel>
#include<QString>
#include<iostream>
#include<sstream>
#include<string>

using namespace std;

GameWindow::GameWindow(Game & game, Board & board) {
	g = &game;
	b = &board;
	numPlayers = g->getNumPlayers();
	pressedX = -1;
	pressedY = -1;
	gameOver = false;

	setWindowTitle(QString::fromStdString("Scrabble"));
	mainLayout = new QHBoxLayout;
	boardLayout = new QGridLayout;

	boardX = b->getX(); boardY = b->getY();

	// Save dynamic pushbuttons in 2d array, then fill gridlayout with them
	boardButtons = new QPushButton**[boardY]; // board with Y rows
	for(int i = 0; i < boardY; i++) {
		boardButtons[i] = new QPushButton*[boardX];
		for(int j = 0; j < boardX; j++) {
			string label = b->getLabel(i, j);
			QPushButton* temp = new QPushButton(QString::fromStdString(label));
			if (label == "***") {
				temp->setText("S");
				temp->setStyleSheet("background-color: #96ceb4");
			}
			else if (label == "2L ") {
				temp->setStyleSheet("background-color: #ffcc5c");
			}
			else if (label == "3L ") {
				temp->setStyleSheet("background-color: #ff6f69");
			}
			else if (label == "2W ") {
				temp->setStyleSheet("background-color: #68c4af");
			}
			else if (label == "3W ") {
				temp->setStyleSheet("background-color: #96ead7");
			}
			else if (label == "...") {
				temp->setText("");
				temp->setStyleSheet("background-color: #ffeead");
			}
			else {
				// if new label, i.e. new letter on board, change to new tile
				temp->setStyleSheet("background-color: #0074bc");
				temp->setEnabled(false);
			}
			temp->setFixedSize(40, 40);
			temp->setCheckable(true);
			boardButtons[i][j] = temp;
			connect(boardButtons[i][j], SIGNAL(clicked()), this, SLOT(tilePressed()));
		}
	}
	for(int i = 0; i < boardY; i++) { // loop through each row
		for(int j = 0; j < boardX; j++) { // each char in each row
			boardLayout->addWidget(boardButtons[i][j], i, j, 0);
		}
	}

	mainLayout->addLayout(boardLayout);
	playerLayout = new QVBoxLayout;
	string whoseTurn = g->getCurrPlayerName() + ", it's your turn!";
	currentPlayer = new QLabel(QString::fromStdString(whoseTurn));

	QFont font = currentPlayer->font();
	font.setPointSize(20);
	font.setBold(true);
    currentPlayer->setAlignment(Qt::AlignCenter);
	currentPlayer->setFont(font);

	string currScores = "Current Scores:\n";
	for(int i = 0; i < numPlayers; i++) {
		currScores += g->getPlayerName(i); currScores += ": ";
		stringstream ss; ss << g->getPlayerScore(i); string temp; ss >> temp; temp += "\n";
		currScores += temp;
	}
	playerScores = new QLabel(QString::fromStdString(currScores));
	playerScores->wordWrap();
	playerScores->setAlignment(Qt::AlignCenter);
	playerScores->setFont(font);
	playerLayout->addWidget(currentPlayer);
	playerLayout->addWidget(playerScores);
	string hand = "Current hand:\n" + g->getCurrentHand();
	playerHand = new QLabel(QString::fromStdString(hand));
	font.setPointSize(15);
	playerHand->wordWrap();
	playerHand->setAlignment(Qt::AlignCenter);
	playerHand->setFont(font);

	playerLayout->addWidget(playerHand);
	tiles = new QLineEdit;
	playerLayout->addWidget(tiles);

	moves = new QComboBox;
	moves->addItem("Place Vertically");
	moves->addItem("Place Horizontally");
	moves->addItem("Exchange");
	moves->addItem("Pass");
	playerLayout->addWidget(moves);
	play = new QPushButton("Play Move");
	playerLayout->addWidget(play);
	connect(play, SIGNAL(clicked()), this, SLOT(playPressed()));

	mainLayout->addLayout(playerLayout);

	setLayout(mainLayout);
}
void GameWindow::update() {
	tiles->clear();
	string whoseTurn = g->getCurrPlayerName() + ", it's your turn!"; // seg fault because instantiates window before getting player names, need to wait until after
	currentPlayer->setText(QString::fromStdString(whoseTurn));
	string currScores = "Current Scores:\n";
	for(int i = 0; i < numPlayers; i++) {
		currScores += g->getPlayerName(i); currScores += ": ";
		stringstream ss; ss << g->getPlayerScore(i); string temp; ss >> temp; temp += "\n";
		currScores += temp;
	}
	playerScores->setText(QString::fromStdString(currScores));
	string hand = "Current hand:\n" + g->getCurrentHand();
	playerHand->setText(QString::fromStdString(hand));

	// Update board
	for(int i = 0; i < boardY; i++) {
		for(int j = 0; j < boardX; j++) {
			boardButtons[i][j]->setChecked(false);
			string label = b->getLabel(i, j);
			if (label == "***") {
				boardButtons[i][j]->setText("S");
				boardButtons[i][j]->setStyleSheet("background-color: #96ceb4");
			}
			else if (label == "2L ") {
				boardButtons[i][j]->setStyleSheet("background-color: #ffcc5c");
			}
			else if (label == "3L ") {
				boardButtons[i][j]->setStyleSheet("background-color: #ff6f69");
			}
			else if (label == "2W ") {
				boardButtons[i][j]->setStyleSheet("background-color: #68c4af");
			}
			else if (label == "3W ") {
				boardButtons[i][j]->setStyleSheet("background-color: #96ead7");
			}
			else if (label == "...") {
				boardButtons[i][j]->setText("");
				boardButtons[i][j]->setStyleSheet("background-color: #ffeead");
			}
			else {
				// If new label, i.e. new letter on board, change to new tile
				boardButtons[i][j]->setText(QString::fromStdString(label));
				boardButtons[i][j]->setEnabled(false);
				boardButtons[i][j]->setStyleSheet("color: black; background-color: #BBA3D0");

			}
			boardButtons[i][j]->setFixedSize(40, 40);
			boardButtons[i][j]->setCheckable(true);
		}
	}
}

void GameWindow::tilePressed() {
	QPushButton *called = (QPushButton*) QObject::sender();
	// Uncheck all other buttons
	for(int i = 0; i < boardY; i++) {
		for(int j = 0; j < boardX; j++) {
			boardButtons[i][j]->setChecked(false);
		}
	}
	called->setChecked(true);
	for (int y = 0; y < boardY ; y++) {
		for (int x = 0; x < boardX; x++) {
			if (boardButtons[y][x] == called) {
				pressedY = y;
				pressedX = x;
			}
		}
	}
}

void GameWindow::playPressed() {
	QString str = tiles->text();

	if(moves->currentText().toStdString() == "Pass") {
		QMessageBox passMsg;
		passMsg.setText("You passed your turn.");
		passMsg.setStandardButtons(QMessageBox::Ok); 
		passMsg.exec();
		close();
		if(g->pass() == true) { // if game is over from passing, just need to show game over window, no evaluation needed
			close();
			gameOverWindow();
		}
		else {
			update();
			show();
		}
	}
	else if(moves->currentText().toStdString() == "Exchange") {
		bool validCommand = false;
		QMessageBox exchangeMsg;
		string exchangeLetters = str.toStdString();
		if(tiles->text().size() == 0) {
			exchangeMsg.setText("You must select which tiles you wish to exchange. Try again.");
			exchangeMsg.setStandardButtons(QMessageBox::Ok);
			exchangeMsg.exec();
		}
		else {
		// Move exchangeMove("EXCHANGE", )
			string newHand = g->exchange(exchangeLetters, validCommand);
			if(validCommand == false) {
				exchangeMsg.setText("You must have all the tiles you wish to exchange. Try again.");
				exchangeMsg.setStandardButtons(QMessageBox::Ok); 
				exchangeMsg.exec();
			}
			else {
				// new hand:
				QString qnewHand = QString::fromStdString(newHand);
				QString str2 = "You exchanged tiles: " + str + "\nNew hand: " + qnewHand;
				exchangeMsg.setText(str2);
				exchangeMsg.setStandardButtons(QMessageBox::Ok); 
				exchangeMsg.exec();
				close();
				update();
				show();
			}
		}
	}
	else if(moves->currentText().toStdString() == "Place Horizontally") {
		int curr = g->getCurrPlayer();
		int preScore = g->getPlayerScore(curr);
		bool validCommand = false;
		QMessageBox placeMsg;
		string placeLetters = str.toStdString();
		string direction = "-";
		string message = g->place(placeLetters, direction, pressedY+1, pressedX+1, validCommand);
		if(validCommand == false) {
			placeMsg.setText(QString::fromStdString(message));
			placeMsg.setStandardButtons(QMessageBox::Ok); 
			placeMsg.exec();
		}
		else {
			int newScore = g->getPlayerScore(curr);
			int turnScore = newScore - preScore;
			stringstream ss; string turnScoreString; ss << turnScore; ss >> turnScoreString;
			stringstream ss1; string newScoreString; ss1 << newScore; ss1 >> newScoreString;
			QString str2 = "You placed tiles: " + str + "\nScore earned: " +
				QString::fromStdString(turnScoreString) +  "\nNew score: " +
				QString::fromStdString(newScoreString);
			placeMsg.setText(str2);
			placeMsg.setStandardButtons(QMessageBox::Ok); 
			placeMsg.exec();
			if(g->isOver()) {
				close();
				gameOverWindow();
			}
			else {
				close();
				update();
				show();
			}
		}
	}
	else if(moves->currentText().toStdString() == "Place Vertically") {
		int curr = g->getCurrPlayer();
		int preScore = g->getPlayerScore(curr);
		bool validCommand = false;
		QMessageBox placeMsg;
		string placeLetters = str.toStdString();
		string direction = "|";
		string message = g->place(placeLetters, direction, pressedY+1, pressedX+1, validCommand);
		if(validCommand == false) {
			// get right error message
			placeMsg.setText(QString::fromStdString(message));
			placeMsg.setStandardButtons(QMessageBox::Ok); 
			placeMsg.exec();
		}
		else {
			int newScore = g->getPlayerScore(curr);
			int turnScore = newScore - preScore;
			stringstream ss; string turnScoreString; ss << turnScore; ss >> turnScoreString;
			stringstream ss1; string newScoreString; ss1 << newScore; ss1 >> newScoreString;
			QString str2 = "You placed tiles: " + str + "\nScore earned: " +
				QString::fromStdString(turnScoreString) +  "\nNew score: " +
				QString::fromStdString(newScoreString);
			// score earned this round
			// new score
			placeMsg.setText(str2);
			placeMsg.setStandardButtons(QMessageBox::Ok); 
			placeMsg.exec();
			if(g->isOver()) {
				close();
				gameOverWindow();
			}
			else {
				close();
				update();
				show();
			}
		}
	}
}
void GameWindow::gameOverWindow() {
	QWidget* endWindow = new QWidget;
	QVBoxLayout* endLayout = new QVBoxLayout;
	QLabel* endLabel = new QLabel("GAME OVER!");
	QFont font = endLabel->font();
	font.setPointSize(20);
	font.setBold(true);
    endLabel->setAlignment(Qt::AlignCenter);
	endLabel->setFont(font);

	endLayout->addWidget(endLabel);
	string currScores = "Final Scores:\n";
	int winningScore = 0;
	string winners = "Winners:\n";
	for(int i = 0; i < numPlayers; i++) {
		currScores += g->getPlayerName(i); currScores += ": ";
		stringstream ss; ss << g->getPlayerScore(i); string temp; ss >> temp; temp += "\n";
		currScores += temp;
		if (g->getPlayerScore(i) > winningScore) {
			winningScore = g->getPlayerScore(i);
		}
	}
	for(int i = 0; i < numPlayers; i++) {
		if (g->getPlayerScore(i) == winningScore) {
			winners += g->getPlayerName(i); winners += "\n";
		}
	}
	QLabel* endScores = new QLabel(QString::fromStdString(currScores));
	QLabel* endWinners = new QLabel(QString::fromStdString(winners));
	endScores->setAlignment(Qt::AlignCenter);
	endScores->setFont(font);
    endWinners->setAlignment(Qt::AlignCenter);
	endWinners->setFont(font);

	endLayout->addWidget(endScores);
	endLayout->addWidget(endWinners);
	QPushButton* exitButton = new QPushButton;
	exitButton->setText("Exit");
	endLayout->addWidget(exitButton);
	endWindow->setLayout(endLayout); endWindow->show();
	connect(exitButton, SIGNAL(clicked()), endWindow, SLOT(close()));
}
