#ifndef LIGACAO_H
#define LIGACAO_H
#include <QGraphicsLineItem>
#include <QGraphicsTextItem>

class Ligacao
{
public:
    Ligacao();
    QString origem;
    QString destino;
    QGraphicsLineItem *line;
    QGraphicsTextItem *texto;
};

#endif // LIGACAO_H
