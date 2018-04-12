#ifndef CONTROLADOR_H
#define CONTROLADOR_H

#include <QMainWindow>

namespace Ui {
    class Controlador;
}

class Controlador : public QMainWindow
{
    Q_OBJECT

public:
    explicit Controlador(QWidget *parent = 0);
    ~Controlador();

private:
    Ui::Controlador * ui;
};

#endif // CONTROLADOR_H
