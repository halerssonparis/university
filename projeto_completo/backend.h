#ifndef BACKEND_H
#define BACKEND_H
#include "grafo_ponderado.h"
#include <vector>
#include "grafo.h"

class BackEnd
{

public:
    BackEnd();

    bool add_vertice(QString nome);
    bool add_aresta(QString origem, QString destino, int peso);
    void add_arco();
    bool remove_vertice(QString nome);
    bool remove_aresta(QString origem, QString destino);
    void remove_arco();
    vector<vertice> welsh_powell();
    vector<vertice> dsatur();
    vector<soluction> prim(QString origemString);
    vector<soluction> kruskal();
    QString verifica_planaridade();

private:
    Grafo_Ponderado grafo;

};

#endif // BACKEND_H
