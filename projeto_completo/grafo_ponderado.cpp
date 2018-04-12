#include "grafo_ponderado.h"
using namespace std;
#include <QList>

Grafo_Ponderado::Grafo_Ponderado()
{
    this->type = "nao orientado";
}

bool Grafo_Ponderado::inserir_vertice(QString nome) {
    if (!(nomes.empty())) {
        for (unsigned int i = 0; i < nomes.size(); i++) {
            if (nomes.at(i).nome == nome) {
                return false;
            }
        }
    }

    vertice v;
    v.nome = nome;
    nomes.push_back(v);

    for (unsigned int i = 0; i < matriz_adj.size(); i++) {
        matriz_adj.at(i).push_back(0);
    }
    matriz_adj.push_back(vector<int>(matriz_adj.size() + 1));

    vector<lista_adj_peso> add;
    lista_adj_peso add2;
    add2.nome = nome;
    add.push_back(add2);
    lista_adj.push_back(add);
    return true;
}

bool Grafo_Ponderado::remover_vertice (QString nome) {
    if (lista_adj.empty()) {
        return false;
    }

    int posicao;

    for(unsigned int i = 0; i < matriz_adj.size(); i++) {
        if (this->nomes.at(i).nome == nome) {
            posicao = i;
            break;
        }
    }

    //deleta a ultima posicao de cada linha
    for(unsigned int i = 0; i < matriz_adj.size(); i++) {
        matriz_adj.at(i).erase(matriz_adj.at(i).begin()+posicao);
    }

    matriz_adj.erase(matriz_adj.begin()+posicao);
    lista_adj.erase(lista_adj.begin() + posicao);
    nomes.erase(nomes.begin() + posicao);


    for(unsigned int i = 0; i < lista_adj.size(); i++){
        for(unsigned int j = 1; j < lista_adj.at(i).size(); j++){
            if (lista_adj.at(i).at(j).nome == nome) {
                lista_adj.at(i).erase(lista_adj.at(i).begin() + j);
                nomes.at(i).grau -= 1;
            }
        }
    }
    return true;
}

bool Grafo_Ponderado::inserir_ligacao(QString origem, QString destino, int peso) {
    if (nomes.empty()) { return false; }

    int _origem = -1, _destino = -1;
    bool existe_aresta;

    for (unsigned int i = 0; i < nomes.size(); i++) {
        if (nomes[i].nome == origem) {
            _origem = i;
        }
        if (nomes[i].nome == destino) {
            _destino = i;
        }
    }
    if (_origem == -1 || _destino == -1) { return false; }

    for (unsigned int i = 0; i < lista_adj.at(_origem).size(); i++) {
        if (lista_adj.at(_origem).at(i).nome == destino) {
            existe_aresta = true;
            break;
        }
    }

    if (type == "nao orientado")
    {
        matriz_adj.at(_origem).at(_destino) = peso;
        matriz_adj.at(_destino).at(_origem) = peso;

        if(!existe_aresta) {
            nomes.at(_origem).grau += 1;
            nomes.at(_destino).grau += 1;

            lista_adj_peso add2;
            add2.nome = destino;
            add2.peso = peso;
            lista_adj.at(_origem).push_back(add2);
            add2.nome = origem;
            lista_adj.at(_destino).push_back(add2);

            soluction l;
            l.a = _origem;
            l.b = _destino;
            l.peso = peso;
            ligacao.push_back(l);

            return true;
        }
    }
    else if (type == "orientado") {
        matriz_adj.at(_origem).at(_destino) = peso;

        if(!existe_aresta) {
            nomes.at(_origem).grau += 1;
            nomes.at(_destino).grau += 1;

            lista_adj_peso add2;
            add2.nome = destino;
            add2.peso = peso;
            lista_adj.at(_origem).push_back(add2);
            add2.nome = origem;
            lista_adj.at(_destino).push_back(add2);
            return true;
        }
    }
}

bool Grafo_Ponderado::remover_ligacao(QString origem, QString destino) {
    int _origem = -1, _destino = -1;

    for (unsigned int i = 0; i < nomes.size(); i++) {
        if (nomes[i].nome == origem) {
            _origem = i;
        }
        if (nomes[i].nome == destino) {
            _destino = i;
        }
    }
    if (_origem != -1 && _destino != -1) {
        if (type == "nao orientado") {
            matriz_adj.at(_origem).at(_destino)= 0;
            matriz_adj.at(_destino).at(_origem)= 0;
            nomes.at(_origem).grau -= 1;
            nomes.at(_destino).grau -= 1;

            //ESSE FOR É SÓ PRA TIRAR DA LISTA (POR ISSO NÃO PRECISA DIMINUIR O GRAU)
            for (unsigned int i = 0; i < lista_adj.at(_origem).size(); i++){
                if (lista_adj.at(_origem).at(i).nome == destino) {
                    lista_adj.at(_origem).erase(lista_adj.at(_origem).begin() + i);
                    lista_adj.at(_destino).erase(lista_adj.at(_destino).begin() + i);
                }
            }
            return true;
        }
        else if (type == "orientado"){
            matriz_adj.at(_origem).at(_destino)= 0;
            nomes.at(_origem).grau -= 1;
            nomes.at(_destino).grau -= 1;

            //ESSE FOR É SÓ PRA TIRAR DA LISTA (POR ISSO NÃO PRECISA DIMINUIR O GRAU)
            for (unsigned int i = 0; i < lista_adj.at(_origem).size(); i++){
                if (lista_adj.at(_origem).at(i).nome == destino) {
                    lista_adj.at(_origem).erase(lista_adj.at(_origem).begin() + i);
                }
            }
            return true;
        }
    }
    return false;
}

//retorno nao é boolean
QString Grafo_Ponderado::retornar_vertice(int index_vertice) {
    for (unsigned int i = 0; i < nomes.size(); i++) {
        if(index_vertice == retornar_vertice(nomes.at(i).nome)) {
            return nomes.at(i).nome;
        }
    }
}

bool Grafo_Ponderado::existe_ligacao(QString origem, QString destino) {
    int _origem = -1, _destino = -1;

    for (unsigned int i = 0; i < nomes.size(); i++){
        if (origem == nomes.at(i).nome){ _origem = i; }
        if (destino == nomes.at(i).nome) { _destino = i; }
    }

    if (_origem != -1 && _destino != -1) {
        if(matriz_adj.at(_origem).at(_destino) != 0) { return true; }
    }
    return false;
}


vector<QString> Grafo_Ponderado::retorna_aresta(QString nome) {
    vector<QString> lista_adj_vertice;

    for (unsigned int i = 0; i < nomes.size(); i++){
        if (nome == nomes.at(i).nome) {
            for (unsigned int j = 0; j < matriz_adj.size(); j++) {
                if (matriz_adj.at(i).at(j) != 0){
                    lista_adj_vertice.push_back(nomes.at(i).nome);
                }
           }
           break;
        }
        break;
    }
    return lista_adj_vertice;
}

vector<int> Grafo_Ponderado::obterVerticesAdj(int origem) {
    vector <int> lista;

    for(lista_adj_peso current: lista_adj[origem]) {
        lista.push_back(retornar_vertice(current.nome));
    }
    return lista;
}

int Grafo_Ponderado::retornar_vertice(QString nome) {
    for (int i = 0; i < nomes.size(); i++) {
        if(nome == nomes.at(i).nome) {
            return i;
        }
    }
}

int Grafo_Ponderado::consultarPeso(int origem, int destino) {
    for(lista_adj_peso atual: lista_adj[origem]) {
        if(retornar_vertice(atual.nome) == destino){
            return atual.peso;
        }
    }
}

bool Grafo_Ponderado::vizinhos_cores_diferente(int cor, vector<lista_adj_peso> lista_aux, vector<vertice> nomes_aux) {
    for (unsigned int i = 1; i < lista_aux.size(); i++) {
        for (unsigned int j = 0; j < nomes_aux.size(); j++) {
            if (lista_aux.at(i).nome == nomes_aux.at(j).nome) {
                if (nomes_aux.at(j).cor == cor) {
                    return false;
                }
            }
        }
    }
    return true;
}

vector<vertice> Grafo_Ponderado::welsh_Powell() {
    vector<vector<lista_adj_peso> > lista_aux = lista_adj;
    vector<vertice> nomes_aux = nomes;
    int opc_cores[6] = {1,2,3,4,5,6};
    int controle_de_cor = 0;

    //BUBBLE SORT
    for (unsigned int i = 0; i < nomes_aux.size(); i++) {
        for (unsigned int j = 0; j < nomes_aux.size()-1; j++) {
            if (nomes_aux.at(j).grau < nomes_aux.at(j+1).grau) {
                swap(nomes_aux.at(j), nomes_aux.at(j+1));
                swap (lista_aux.at(j), lista_aux.at(j+1));
            }
        }
    }

    for (unsigned int i = 0; i < nomes_aux.size(); i++) {
        controle_de_cor++;
        for (unsigned int j = 0; j < nomes_aux.size(); j++) {
            if (nomes_aux.at(j).cor == -1) {
                if (vizinhos_cores_diferente(controle_de_cor, lista_aux.at(j), nomes_aux)) {
                    nomes_aux.at(j).cor = controle_de_cor;
                }
            }
        }
    }
    return nomes_aux;
}


void Grafo_Ponderado::aumenta_saturacao_vizinhos(int cor, vector<lista_adj_peso> lista_aux, vector<vertice> &nomes_aux ) {
    for (unsigned int i = 1; i < lista_aux.size(); i++) {
        for (unsigned int j = 0; j < nomes_aux.size(); j++) {
            if (lista_aux.at(i).nome == nomes_aux.at(j).nome && nomes_aux.at(j).saturacao != -1){
                nomes_aux.at(j).saturacao++;
            }
        }
    }
}

vector<vertice> Grafo_Ponderado::dsatur() {
    vector<vector<lista_adj_peso> > lista_aux = lista_adj;
    vector<vertice> nomes_aux = nomes;
    int opc_cores[6] = {1,2,3,4,5,6};
    int controle_de_cor = 0;

    //BUBBLE SORT
    for (unsigned int i = 0; i < nomes_aux.size(); i++) {
        for (unsigned int j = 0; j < nomes_aux.size()-1; j++) {
            if (nomes_aux.at(j).grau < nomes_aux.at(j+1).grau) {
                swap(nomes_aux.at(j), nomes_aux.at(j+1));
                swap (lista_aux.at(j), lista_aux.at(j+1));
            }
        }
    }

    int _ref_maior_saturacao = 0;
    for (unsigned int i = 0; i < nomes_aux.size(); i++) {
        //FOR J É PARA ACHAR O VERTICE COM MAIOR SATURAÇÃO;
        for (unsigned int j = 0; j < nomes_aux.size(); j++) {
            while (nomes_aux.at(_ref_maior_saturacao).cor != -1){
                _ref_maior_saturacao++;
            }
            if (nomes_aux.at(j).saturacao > nomes_aux.at(_ref_maior_saturacao).saturacao &&
                nomes_aux.at(j).cor == -1)
            {
                _ref_maior_saturacao = j;
            }
        }

        do {
            if (vizinhos_cores_diferente(controle_de_cor, lista_aux.at(_ref_maior_saturacao), nomes_aux)) {
                aumenta_saturacao_vizinhos(controle_de_cor, lista_aux.at(_ref_maior_saturacao), nomes_aux);
                nomes_aux.at(_ref_maior_saturacao).cor = controle_de_cor;
            }
            else {
                controle_de_cor++;
            }
        } while (nomes_aux.at(_ref_maior_saturacao).cor == -1);
        controle_de_cor = 0;
        _ref_maior_saturacao = 0;
    }

    return nomes_aux;
}

void Grafo_Ponderado::dijkstra (QString origem) {

}

bool Grafo_Ponderado::esta_conectado (int vertice, vector<unsigned int> out_control) {
      if(out_control.empty())
          return true;

      for (int vertice_out : out_control) {
          if (vertice_out == vertice) {
              return true;
          }
      }
      return false;
  }

vector<soluction> Grafo_Ponderado::prim (QString origemString) {
    spanning_tree_prim stp;
    vector<unsigned int> out_control;
    int origem = retornar_vertice(origemString);

    for (vertice _vertice : nomes) {
        if (origem == retornar_vertice(_vertice.nome))
            out_control.push_back(retornar_vertice(_vertice.nome));
        else
            stp.control.push_back(retornar_vertice(_vertice.nome));
    }

    while (!stp.control.empty()) {
        unsigned int vertice_a, ligacao_menor_peso;
        bool primeira_iteracao = true;

        for (unsigned int vertice_atual : out_control) {
            vector<int> vertices_adjacentes_atual = obterVerticesAdj(vertice_atual);

            for (unsigned int adjacente_atual : vertices_adjacentes_atual) {
                if (!esta_conectado(adjacente_atual, out_control)) {
                    if (primeira_iteracao) {
                        vertice_a = vertice_atual;
                        ligacao_menor_peso = adjacente_atual;
                        primeira_iteracao = false;
                    }
                    else {
                        if (consultarPeso(vertice_atual, adjacente_atual) <
                            consultarPeso(vertice_a, ligacao_menor_peso))
                        {
                            vertice_a = vertice_atual;
                            ligacao_menor_peso = adjacente_atual;
                        }
                    }
                }
            }
        }

        soluction add_soluction;
        add_soluction.a = vertice_a;
        add_soluction.b = ligacao_menor_peso;
        stp.soluctions.push_back(add_soluction);

        out_control.push_back(ligacao_menor_peso);

        for (unsigned int i = 0; i < stp.control.size(); i++) {
            if (stp.control.at(i) == ligacao_menor_peso)
                stp.control.erase(stp.control.begin() + i);
        }
    }

    return trocar_indice_por_nome_vertice(stp.soluctions);
}

spanning_tree_kruskal Grafo_Ponderado::init_tree() {
    spanning_tree_kruskal stp;
    stp.control = ligacao;

    for (vertice vertice_atual : nomes){
        arvore add;
        add.index = retornar_vertice(vertice_atual.nome);
        add.floresta_pertencente = retornar_vertice(vertice_atual.nome);
        stp.floresta.push_back(add);
    }

    for (int i = 0; i < stp.control.size(); i++) {
        for (int j = 0; j < stp.control.size()-1; j++) {
            if (stp.control.at(j).peso > stp.control.at(j+1).peso) {
              swap(stp.control.at(j), stp.control.at(j+1));
            }
        }
    }
    return stp;
}

vector<soluction> Grafo_Ponderado::trocar_indice_por_nome_vertice(vector<soluction> vetor) {
    for (int i = 0; i < vetor.size(); i++) {
        vetor.at(i).vertice_a = retornar_vertice(vetor.at(i).a);
        vetor.at(i).vertice_b = retornar_vertice(vetor.at(i).b);
    }

    return vetor;
}

vector<soluction> Grafo_Ponderado::kruskal () {
    spanning_tree_kruskal stp = init_tree();

    for (int i = 0; i < stp.control.size(); i++) {
        if (stp.floresta.at(stp.control.at(i).a).floresta_pertencente != stp.floresta.at(stp.control.at(i).b).floresta_pertencente) {
            stp.soluctions.push_back(stp.control.at(i));

            int floresta_b = stp.floresta.at(stp.control.at(i).b).floresta_pertencente;
            for(arvore &arvore : stp.floresta){
                if (arvore.floresta_pertencente == floresta_b)
                    arvore.floresta_pertencente = stp.floresta.at(stp.control.at(i).a).floresta_pertencente;
            }
        }
    }
    return trocar_indice_por_nome_vertice(stp.soluctions);
}

bool Grafo_Ponderado::tem_ciclo3 () {
    for (int i = 0; i < lista_adj.size(); i++) {
        for (int j = 1; j < lista_adj.at(i).size(); j++) {
            int k = retornar_vertice(lista_adj.at(i).at(j).nome);
            for (int l = 1; l < lista_adj.at(k).size(); l++) {
                int m = retornar_vertice(lista_adj.at(k).at(l).nome);
                for (int n = 1; n < lista_adj.at(m).size(); n++) {
                    if (lista_adj.at(m).at(n).nome == lista_adj.at(i).at(0).nome)
                        return true;
                }
            }
        }
    }
    return false;
}

QString Grafo_Ponderado::verifica_planaridade() {
    if (nomes.size() <= 2) {
        return "É Planar!";
    }
    else if ((nomes.size() >= 3) &&
             (ligacao.size() <= (3 * nomes.size())-6) &&
             (tem_ciclo3())) {
        return "Pode ser Planar!";
    }
    else if ((nomes.size() >= 3) &&
             (ligacao.size() <= (2 * nomes.size())-4) &&
             (!tem_ciclo3())) {
        return "Pode ser Planar!";
    }
    return "Não é Planar!";
}
