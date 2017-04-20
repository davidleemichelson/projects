#include <QWidget>
#include <QPushButton>
#include <QLineEdit>
#include <QSpinBox>
#include <QFormLayout>
#include <QCloseEvent>
#include <QVBoxLayout>
#include <QLabel>
#include <QComboBox>
#include "Game.h"
#include "Board.h"
#include "Player.h"

class GameWindow : public QWidget {
	Q_OBJECT
public:
	GameWindow(Game &, Board &);
	void update();
	void gameOverWindow();
	void endGameEval();

public slots:
	void tilePressed();
	void playPressed();

private:
	int boardX; int boardY;
	QHBoxLayout* mainLayout;
	QGridLayout* boardLayout;
	QPushButton*** boardButtons; // so we can access the buttons on the board and their indices 
	QVBoxLayout* playerLayout;
	QLabel* currentPlayer;
	QLabel* playerScores;
	QLineEdit* tiles;
	QLabel* playerHand;
	QComboBox* moves;
	QPushButton* play;
	Game* g;
	Board* b;
	int numPlayers;
	bool gameOver;
	int pressedX;
	int pressedY;
};