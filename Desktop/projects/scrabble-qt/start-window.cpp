#include "start-window.h"
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QFormLayout>
#include <QSpinBox>
#include <QLabel>
#include <QString>
#include <iostream>
#include <sstream>
#include <string>
using namespace std;

StartWindow::StartWindow(Game& game) {
	g = &game;

	mainLayout = new QVBoxLayout;
	mainLayout->addWidget(new QLabel ("Welcome to Scrabble!"));
	number = new QSpinBox();
	number->setMinimum(1); number->setMaximum(8);
	number->setValue(1); number->setSingleStep(1);
	QFormLayout* formLayout = new QFormLayout;
	formLayout->addRow("&Number of Players:", number);
	mainLayout->addLayout(formLayout);

	QHBoxLayout* buttonLayout = new QHBoxLayout;
	quitButton = new QPushButton("&Quit");
	continueButton = new QPushButton("&Continue");
	buttonLayout->addWidget(continueButton);
	buttonLayout->addWidget(quitButton);

	connect (continueButton, SIGNAL(clicked()), this, SLOT(continuePressed()));
 	connect (quitButton, SIGNAL(clicked()), this, SLOT(quitPressed()));

	mainLayout->addLayout(buttonLayout);
	setLayout(mainLayout);
}



void StartWindow::continuePressed () {
	numberPlayers = number->value();
	g->setNumPlayers(numberPlayers);
	hide();
	nameWindow = new QWidget;
	nameLayout = new QVBoxLayout;
	nameLayout->addWidget(new QLabel ("Please enter player names"));
	nameFormLayout = new QFormLayout;
	lines = new QList<QLineEdit*>;
	for(int i = 0; i < numberPlayers; i++) {
		QLineEdit* line = new QLineEdit;
		stringstream ss; ss << i+1; string num; ss >> num;
		string temp = "Player " + num;
		nameFormLayout->addRow(QString::fromStdString(temp), line);
		lines->append(line);
	}
	nameLayout->addLayout(nameFormLayout);
	QPushButton* doneButton = new QPushButton("&Done");
	nameLayout->addWidget(doneButton);
	nameWindow->setLayout(nameLayout);
	nameWindow->show();
 	connect (doneButton, SIGNAL(clicked()), this, SLOT(donePressed()));
}

void StartWindow::quitPressed () {
	close ();
}

void StartWindow::donePressed () {
	for(int i = 0; i < numberPlayers; i++) {
		names.push_back(lines->at(i)->text().toStdString());
	}
	g->makePlayers(names);
	// close this window and open the game window
	emit openGameWindow();
	nameWindow->close();
	close();
}
