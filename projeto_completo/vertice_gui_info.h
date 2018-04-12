#ifndef VERTICE_GUI_INFO_H
#define VERTICE_GUI_INFO_H
#include <QGraphicsEllipseItem>
#include <QString>
#include <QGraphicsTextItem>
#include "vertice.h"


class Vertice_GUI_Info //: public QObject, public QGraphicsEllipseItem
{
public:
    Vertice_GUI_Info();
    Vertice *vertice;
    QGraphicsTextItem *texto;
    QString nome;
};

#endif // VERTICE_GUI_INFO_H
