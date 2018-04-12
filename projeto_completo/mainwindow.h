#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QGraphicsScene>
#include <QGraphicsView>
#include "backend.h"
#include "grafico_v.h"

namespace Ui {
    class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

private slots:
    void on_radio_Orientado_clicked();
    void on_radio_NOrientado_clicked();
    void add_vertice(QString nome);
    void add_aresta(QString origem, QString destino, int peso);
    void add_arco();
    void remove_vertice(QString nome);
    void remove_aresta(QString origem, QString destino);
    void remove_arco();
    void welsh_powell();
    void dsatur();
    void prim();
    void kruskal();
    void verificaPlanaridade();

private:
    Ui::MainWindow *ui;
    Grafico_V view;
    BackEnd back_Control;

};

#endif // MAINWINDOW_H
