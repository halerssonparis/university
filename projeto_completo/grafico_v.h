#ifndef GRAFICO_V_H
#define GRAFICO_V_H
#include <QGraphicsScene>
#include <QGraphicsView>
#include <QCursor>
#include <QList>
#include "vertice_gui_info.h"
#include "ligacao.h"
#include <vector>
#include "grafo_ponderado.h"
#include <QBrush>


class Grafico_V : public QGraphicsView
{
    Q_OBJECT
public:
    Grafico_V();
    QList<Vertice_GUI_Info> lista_de_vertice;
    QList<Ligacao> lista_de_ligacao;
    QList<QBrush> opcao_de_cores;
    bool pode_inserir =  false;

    void interface_add_vertice_window(bool estado, QString nome);
    //void interface_add_arco();
    void interface_add_aresta_window(bool estado, QString origem, QString destino, int peso);
    void interface_remove_vertice_window(bool estado, QString nome);
    void interface_remove_aresta_window(bool estado, QString origem, QString destino);
    void colore_grafo(vector<vertice> resultado);
    void colore_grafo_direcao(vector<soluction> result);
    void mostrar_resultado_planaridade(QString resultado);

protected:
    void mousePressEvent(QMouseEvent *e) override;
    bool existe_erro();

public slots:
    void interface_add_vertice();
    void interface_add_arco();
    void interface_add_aresta();
    void interface_remove_vertice();
    void interface_remove_aresta();
    void resetar_cores();

signals:
    void sinal_inclusao_vertice(QString nome);
    void sinal_inclusao_aresta(QString origem, QString destino, int peso);
    void sinal_exclusao_vertice(QString nome);
    void sinal_exclusao_ligacao(QString origem, QString destino);

private:
    QGraphicsScene scene;

    // QWidget interface

};

#endif // GRAFICO_V_H
