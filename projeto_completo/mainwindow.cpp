#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QDebug>
#include <QCoreApplication>
#include <QLayout>
#include <QString>
#include <QInputDialog>
#include <QDir>

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow), view()
{
    ui->setupUi(this);

    //Conexão dos MenuBar's
    connect(ui->actionAdd_Vertice, SIGNAL(triggered()), &view, SLOT(interface_add_vertice()));
    connect(ui->actionAdd_Arco, SIGNAL(triggered()), &view, SLOT(interface_add_arco()));
    connect(ui->actionAdd_Aresta, SIGNAL(triggered()), &view, SLOT(interface_add_aresta()));
    connect(ui->actionRemove_Vertice, SIGNAL(triggered()), &view, SLOT(interface_remove_vertice()));
    connect(ui->actionRemove_Aresta, SIGNAL(triggered()), &view, SLOT(interface_remove_aresta()));
    connect(ui->actionVerificar_Planaridade, SIGNAL(triggered()), this, SLOT(verificaPlanaridade()));

    //Conexão com BackEnd
    connect(&view, SIGNAL(sinal_inclusao_vertice(QString)), this, SLOT(add_vertice(QString)));
    connect(&view, SIGNAL(sinal_inclusao_aresta(QString, QString, int)), this, SLOT(add_aresta(QString,QString,int)));
    connect(&view, SIGNAL(sinal_exclusao_vertice(QString)), this, SLOT(remove_vertice(QString)));
    connect(&view, SIGNAL(sinal_exclusao_ligacao(QString,QString)), this, SLOT(remove_aresta(QString,QString)));
    connect(ui->button_welsh_powell, SIGNAL(clicked()), this, SLOT(welsh_powell()));
    connect(ui->button_dsatur, SIGNAL(clicked()), this, SLOT(dsatur()));
    connect(ui->button_reseta_cor, SIGNAL(clicked()), &view, SLOT(resetar_cores()));
    connect(ui->button_prim, SIGNAL(clicked()), this, SLOT(prim()));
    connect(ui->button_kruskal, SIGNAL(clicked()), this, SLOT(kruskal()));

    //Habilitar após o trabalho!
    ui->radio_Orientado->setDisabled(true);

    ui->radio_NOrientado->setChecked(true);
    on_radio_NOrientado_clicked();
    ui->centralWidget->layout()->addWidget(&view);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::add_vertice(QString nome)
{
    view.interface_add_vertice_window(back_Control.add_vertice(nome), nome);
}

void MainWindow::add_aresta(QString origem, QString destino, int peso)
{
    view.interface_add_aresta_window(back_Control.add_aresta(origem, destino, peso), origem, destino, peso);
}

void MainWindow::add_arco()
{

}

void MainWindow::remove_vertice(QString nome)
{
    view.interface_remove_vertice_window(back_Control.remove_vertice(nome), nome);
}

void MainWindow::remove_aresta(QString origem, QString destino)
{
    view.interface_remove_aresta_window(back_Control.remove_aresta(origem, destino), origem, destino);
}

void MainWindow::remove_arco()
{

}

void MainWindow::welsh_powell()
{
    view.colore_grafo(back_Control.welsh_powell());
}

void MainWindow::dsatur()
{
    view.colore_grafo(back_Control.dsatur());
}

void MainWindow::prim()
{
    bool ok;
    QString origemString = QInputDialog::getText(this, tr("Prim"),
                                              tr("Vertice Origem: "), QLineEdit::Normal,
                                              QDir::home().dirName(), &ok);
    if (ok) {
        view.colore_grafo_direcao(back_Control.prim(origemString));
    }
}

void MainWindow::kruskal()
{
    view.colore_grafo_direcao(back_Control.kruskal());
}

void MainWindow::verificaPlanaridade()
{
    view.mostrar_resultado_planaridade(back_Control.verifica_planaridade());
}

void MainWindow::on_radio_Orientado_clicked()
{
    ui->actionAdd_Arco->setEnabled(true);
    ui->actionAdd_Vertice->setEnabled(true);
    ui->actionRemove_Vertice->setEnabled(true);
    ui->actionRemove_Arca->setEnabled(true);

    ui->actionAdd_Aresta->setDisabled(true);
    ui->actionRemove_Aresta->setDisabled(true);
}

void MainWindow::on_radio_NOrientado_clicked()
{

    ui->actionAdd_Arco->setEnabled(false);
    ui->actionAdd_Vertice->setEnabled(true);
    ui->actionRemove_Vertice->setEnabled(true);
    ui->actionRemove_Arca->setEnabled(false);

    ui->actionAdd_Aresta->setEnabled(true);
    ui->actionRemove_Aresta->setEnabled(true);
}
