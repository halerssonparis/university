#include "grafico_v.h"
#include <QDebug>
#include <QGraphicsRectItem>
#include <QContextMenuEvent>
#include <QString>
#include <QInputDialog>
#include <QDir>
#include <QMessageBox>
#include <QGraphicsLineItem>
#include <QGraphicsTextItem>
#include <QPoint>
#include <vector>
#include <grafo_ponderado.h>

Grafico_V::Grafico_V() : QGraphicsView(), scene()
{
    setSceneRect(0,0, 740, 340);
    setFixedSize(770,570);
    setViewportUpdateMode(QGraphicsView::FullViewportUpdate);
    setScene(&scene);

    //ISSO É SÓ PARA TESTES. MODIFICAR DEPOIS.
    opcao_de_cores.push_back(QBrush(Qt::green));
    opcao_de_cores.push_back(QBrush(Qt::red));
    opcao_de_cores.push_back(QBrush(Qt::blue));
    opcao_de_cores.push_back(QBrush(Qt::gray));
    opcao_de_cores.push_back(QBrush(Qt::yellow));
    opcao_de_cores.push_back(QBrush(Qt::magenta));

}


void Grafico_V::mousePressEvent(QMouseEvent *e)
{
    if (e->button() == Qt::LeftButton)
    {
        if (pode_inserir)
        {
            lista_de_vertice.at(lista_de_vertice.size()-1).vertice->setPos(mapToScene(e->pos()));
            lista_de_vertice.at(lista_de_vertice.size()-1).texto->setPos(mapToScene(e->pos()));
            scene.addItem(lista_de_vertice.at(lista_de_vertice.size()-1).vertice);
            scene.addItem(lista_de_vertice.at(lista_de_vertice.size()-1).texto);
            pode_inserir = false;
        }
    }
}

bool Grafico_V::existe_erro()
{
    if (pode_inserir) {
        QMessageBox ms;
        ms.critical(0, "Error", "Clique na tela para adicionar o vertice antes de fazer outra ação!");
        ms.setFixedSize(300,200);
        return true;
    }
}

void Grafico_V::interface_add_vertice()
{
    if (existe_erro()) {
        return;
    }

    bool ok;
    QString v;
    v = QInputDialog::getText(this, tr("Inserir Vertice"),
                                   tr("Nome do Vertice: "), QLineEdit::Normal,
                                   QDir::home().dirName(), &ok);

    if (ok)
    {
        emit sinal_inclusao_vertice(v);
    }
}

//EMITE UM SINAL PRO BACKEND, SE O BACKEND RETORNAR TRUE, ELE VOLTA NESSA FUNÇÃO PELO MAINWINDOW E ADICIONA
void Grafico_V::interface_add_vertice_window(bool estado, QString nome)
{
    if (estado) {
        pode_inserir = true;
        Vertice_GUI_Info v;
        v.nome = nome;
        v.vertice = new Vertice();
        v.vertice->setRect(-25,-25,50,50);
        v.vertice->setBrush(QBrush(Qt::white));

        v.texto = new QGraphicsTextItem();
        v.texto->setPlainText(nome);
        v.texto->setFont(QFont("times"));
        lista_de_vertice.push_back(v);
    }
}


void Grafico_V::interface_add_arco()
{

}


void Grafico_V::interface_add_aresta()
{
    if (existe_erro()) {
        return;
    }

    bool ok;
    int peso;
    QString nome_vertice_destino;
    QString nome_vertice_origem = QInputDialog::getText(this, tr("Inserir Aresta"),
                                              tr("Vertice Origem: "), QLineEdit::Normal,
                                              QDir::home().dirName(), &ok);
    if (ok) {
        nome_vertice_destino = QInputDialog::getText(this, tr("Inserir Aresta"),
                                                  tr("Vertice Destino: "), QLineEdit::Normal,
                                                  QDir::home().dirName(), &ok);
        if (ok) {
            peso = QInputDialog::getInt(this, tr("Inserir Aresta"),
                                                      tr("Peso do Vertice: "), peso,
                                                      0, 9999, 1, &ok);
        }
    }
    if (ok) {
        emit sinal_inclusao_aresta(nome_vertice_origem, nome_vertice_destino, peso);
    }
}


void Grafico_V::interface_add_aresta_window(bool estado, QString origem, QString destino, int peso)
{
    if (estado)
    {
       /* for (unsigned int i = 0; lista_de_ligacao.size(); i++) {
            if (lista_de_ligacao.at(i).origem == origem && lista_de_ligacao.at(i).destino == destino ||
                lista_de_ligacao.at(i).destino == origem && lista_de_ligacao.at(i).origem == destino) {
                //MENSAGEM QUE JÁ EXISTE UM VERTICE
                return;
            }
        }*/
        Ligacao line;

        QPointF _origem, _destino;

        for (int i = 0; i < lista_de_vertice.size(); i++) {

            if (origem == lista_de_vertice.at(i).nome) {
                 _origem = lista_de_vertice.at(i).vertice->pos();
                 line.origem = lista_de_vertice.at(i).nome;
            }
            if (destino == lista_de_vertice.at(i).nome) {
                _destino = lista_de_vertice.at(i).vertice->pos();
                line.destino = lista_de_vertice.at(i).nome;
            }
        }

        line.line = new QGraphicsLineItem();
        line.line->setLine(QLineF(_origem, _destino));
        line.line->setPen(QPen(Qt::black, 2));

        line.texto = new QGraphicsTextItem();
        line.texto->setPlainText(QString::number(peso));
        line.texto->setFont(QFont("times"));
        line.texto->setPos(((_origem.x()/2) + (_destino.x()/2)), ((_origem.y()/2) + (_destino.y()/2)));

        lista_de_ligacao.push_back(line);
        scene.addItem(lista_de_ligacao.at(lista_de_ligacao.size()-1).line);
        scene.addItem(lista_de_ligacao.at(lista_de_ligacao.size()-1).texto);
    }
}


void Grafico_V::interface_remove_vertice()
{
    if (lista_de_vertice.empty()) {
        //MENSAGEM "NAO HÁ VERTICES NA TELA"
        return;
    }

    if (existe_erro()) {
        return;
    }
    bool ok;
    QString nome = QInputDialog::getText(this, tr("Remove Vertice"),
                                              tr("Nome do Vertice: "), QLineEdit::Normal,
                                              QDir::home().dirName(), &ok);
    if (ok) {
        emit sinal_exclusao_vertice(nome);
    }


}


void Grafico_V::interface_remove_vertice_window(bool estado, QString nome)
{
    if (estado) {
        for (unsigned int i = 0; i < lista_de_vertice.size(); i++) {
            if (lista_de_vertice.at(i).nome == nome) {
                delete lista_de_vertice.at(i).vertice;
                delete lista_de_vertice.at(i).texto;
                lista_de_vertice.erase(lista_de_vertice.begin() + i);
            }
        }
    }

    //REMOVE AS ARESTA ADJACENTE AO VERTICE REMOVIDO ( se for orientado tem que verificar destino! )
    for (unsigned int i = 0; i < lista_de_ligacao.size(); i++) {
        if (lista_de_ligacao.at(i).origem == nome || lista_de_ligacao.at(i).destino == nome) {
            delete lista_de_ligacao.at(i).line;
            delete lista_de_ligacao.at(i).texto;
            lista_de_ligacao.erase(lista_de_ligacao.begin() + i);
            i--;
        }
    }
}

void Grafico_V::interface_remove_aresta_window(bool estado, QString origem, QString destino)
{
    if (lista_de_ligacao.empty()) {
        //ENVIAR MENSAGEM DEPOIS;
        return;
    }
    if (estado)
    {
        for (unsigned int i = 0; i < lista_de_ligacao.size(); i++) {
            if ((lista_de_ligacao.at(i).origem == origem && lista_de_ligacao.at(i).destino == destino) ||
                (lista_de_ligacao.at(i).destino == origem && lista_de_ligacao.at(i).destino == origem))
            {
                    delete lista_de_ligacao.at(i).line;
                    delete lista_de_ligacao.at(i).texto;
                    lista_de_ligacao.erase(lista_de_ligacao.begin() + i);
                    return;
            }
        }
    }
}

void Grafico_V::interface_remove_aresta()
{
    bool ok;
    QString destino;
    QString origem = QInputDialog::getText(this, tr("Remove Ligação"),
                                              tr("Origem: "), QLineEdit::Normal,
                                              QDir::home().dirName(), &ok);

    if (ok)
    {
        destino = QInputDialog::getText(this, tr("Remove Ligação"),
                                                  tr("Destino: "), QLineEdit::Normal,
                                                  QDir::home().dirName(), &ok);

        if(ok)
        {
            emit sinal_exclusao_ligacao(origem, destino);
        }
    }
}

void Grafico_V::resetar_cores()
{
    for (unsigned int i = 0; i < lista_de_vertice.size(); i++) {
        lista_de_vertice.at(i).vertice->setBrush(QBrush(Qt::white));
    }

    for (unsigned int i = 0; i < lista_de_ligacao.size(); i++) {
        lista_de_ligacao.at(i).line->setPen(QPen(Qt::black));
    }
}

void Grafico_V::colore_grafo(vector<vertice> resultado)
{
    resetar_cores();

    for (unsigned int i = 0; i < lista_de_vertice.size(); i++) {
        for (unsigned int j = 0; j < resultado.size(); j++){
            if (lista_de_vertice.at(i).nome == resultado.at(j).nome) {
                lista_de_vertice.at(i).vertice->setBrush(opcao_de_cores.at(resultado.at(j).cor));
            }
        }
    }
}

void Grafico_V::colore_grafo_direcao(vector<soluction> result)
{
    if (result.empty() || lista_de_ligacao.empty())
        return;

    resetar_cores();

    for(unsigned int i = 0; i < lista_de_ligacao.size(); i++) {
        for (unsigned int j = 0; j < result.size(); j++) {
            if(((result.at(j).vertice_a == lista_de_ligacao.at(i).origem)   &&
                (result.at(j).vertice_b == lista_de_ligacao.at(i).destino)) ||
                (result.at(j).vertice_a == lista_de_ligacao.at(i).destino)  &&
                (result.at(j).vertice_b == lista_de_ligacao.at(i).origem)) {
                lista_de_ligacao.at(i).line->setPen(QPen(Qt::red));
            }
        }
    }
}

void Grafico_V::mostrar_resultado_planaridade(QString resultado)
{
    QMessageBox ms;
    ms.setText(resultado);
    ms.setFixedSize(400,200);
    ms.exec();
}
