#include "backend.h"
#include <QDebug>
#include <vector>

BackEnd::BackEnd()
{

}

bool BackEnd::add_vertice(QString nome)
{
    return grafo.inserir_vertice(nome);
}

bool BackEnd::add_aresta(QString origem, QString destino, int peso)
{
    return grafo.inserir_ligacao(origem, destino, peso);
}

void BackEnd::add_arco()
{

}

bool BackEnd::remove_vertice(QString nome)
{
    return grafo.remover_vertice(nome);
}

bool BackEnd::remove_aresta(QString origem, QString destino)
{
    return grafo.remover_ligacao(origem, destino);
}

void BackEnd::remove_arco()
{

}

vector<vertice> BackEnd::welsh_powell()
{
    return grafo.welsh_Powell();
}

vector<vertice> BackEnd::dsatur()
{
    return grafo.dsatur();
}

vector<soluction> BackEnd::prim(QString origemString)
{
    return grafo.prim(origemString);
}

vector<soluction> BackEnd::kruskal()
{
    return grafo.kruskal();
}

QString BackEnd::verifica_planaridade()
{
    return grafo.verifica_planaridade();
}
