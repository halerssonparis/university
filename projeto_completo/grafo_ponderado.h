#ifndef GRAFO_PONDERADO_H
#define GRAFO_PONDERADO_H
#include <vector>
#include <QString>
#include "grafo.h"

using namespace std;

struct vertice {
    QString nome;
    int cor = -1;
    int saturacao = 0;
    int grau = 0;
};

struct lista_adj_peso {
    int peso = 0;
    QString nome;
};

struct soluction {
    int a;
    int b;
    QString vertice_a;
    QString vertice_b;
    int peso;
};
struct spanning_tree_prim{
    vector<soluction> soluctions;
    vector<int> control;
};
struct arvore {
    int index;
    int floresta_pertencente;
};
struct spanning_tree_kruskal {
    vector<soluction> soluctions;
    vector<soluction> control;
    vector<arvore> floresta;
};


class Grafo_Ponderado
{
public:
    Grafo_Ponderado();

    QString type;
    vector<vector<int> > matriz_adj;
    vector<vertice> nomes;
    vector<vector<lista_adj_peso> > lista_adj;
    vector<soluction> ligacao;

    //Funcoes Padroes
    bool inserir_vertice(QString nome);
    bool remover_vertice (QString nome);
    bool inserir_ligacao(QString origem, QString destino, int peso);
    bool remover_ligacao(QString origem, QString destino);

    //Funcoes auxiliares
    bool existe_ligacao(QString origem, QString destino);
    bool vizinhos_cores_diferente(int cor, vector<lista_adj_peso> lista_aux, vector<vertice> nomes_aux);
    bool esta_conectado(int vertice, vector<unsigned int> out_control);
    bool tem_ciclo3();
    int consultarPeso(int origem, int destino);
    int retornar_vertice(QString nome);
    void aumenta_saturacao_vizinhos(int cor, vector<lista_adj_peso> lista_aux, vector<vertice> &nomes_aux);
    QString retornar_vertice(int index_vertice);
    vector<int> obterVerticesAdj(int origem);
    vector<soluction> trocar_indice_por_nome_vertice(vector<soluction> vetor);
    vector<QString> retorna_aresta(QString nome);
    spanning_tree_kruskal init_tree();

    //Funcoes de Coloracao
    vector<vertice> welsh_Powell();
    vector<vertice> dsatur();

    //funcoes de caminho
    void dijkstra(QString origem);
    vector<soluction> prim(QString origemString);
    vector<soluction> kruskal();

    //Funcoes de planaridade
    QString verifica_planaridade();

};

#endif // GRAFO_PONDERADO_H
