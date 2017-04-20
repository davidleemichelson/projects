#include <QWidget>
#include <QPushButton>
#include <QLineEdit>
#include <QSpinBox>
#include <QFormLayout>
#include <QCloseEvent>
#include <QVBoxLayout>
#include <QCloseEvent>

#include "Game.h"

class StartWindow : public QWidget
{
	Q_OBJECT
public:
    StartWindow(Game &);

public slots:
	void continuePressed ();
	void quitPressed ();
	void donePressed ();

signals:
	void openGameWindow();
	void shutDown();


private:
	QPushButton *quitButton, *continueButton;
	QSpinBox *number;
	QVBoxLayout* mainLayout;
	QVBoxLayout* nameLayout;
	QFormLayout* nameFormLayout;
	QWidget* nameWindow;
	QList<QLineEdit*>* lines;
	int numberPlayers;
	std::vector<std::string> names;
	Game* g;
};



