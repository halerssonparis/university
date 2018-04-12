#ifndef VERTICE_H
#define VERTICE_H
#include <QGraphicsEllipseItem>


class Vertice : public QGraphicsEllipseItem
{
public:
    Vertice();
    bool pressionada;

    // QGraphicsItem interface
protected:
    //void mousePressEvent(QGraphicsSceneMouseEvent *event) override;
    void mouseMoveEvent(QGraphicsSceneMouseEvent *event) override;
    void mouseReleaseEvent(QGraphicsSceneMouseEvent *event) override;

    void contextMenuEvent(QGraphicsSceneContextMenuEvent *event) override;
};

#endif // VERTICE_H
