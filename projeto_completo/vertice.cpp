#include "vertice.h"
#include <QGraphicsSceneMouseEvent>
#include <QDebug>

//ei, vc sabe se tem um funcao pra controlar só o botao direito?

Vertice::Vertice() : QGraphicsEllipseItem()
{
    pressionada = false;
}


//void Vertice::mousePressEvent(QGraphicsSceneMouseEvent *event)
//{
//    qDebug()<<"mousePress";
//    if (event->button() == Qt::RightButton) {
//         pressionada = true;
//    }
//}

void Vertice::mouseMoveEvent(QGraphicsSceneMouseEvent *event)
{
    qDebug()<<"ola";
    if(pressionada) { setPos(event->pos()); }
}

void Vertice::mouseReleaseEvent(QGraphicsSceneMouseEvent *event)
{
    pressionada = false;
}

void Vertice::contextMenuEvent(QGraphicsSceneContextMenuEvent *event)
{
    qDebug()<<"Context"<<event->pos();
    pressionada = true;
}
