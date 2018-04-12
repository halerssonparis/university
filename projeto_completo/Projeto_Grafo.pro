#-------------------------------------------------
#
# Project created by QtCreator 2017-09-23T15:26:25
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = Projeto_Grafo
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    backend.cpp \
    grafico_v.cpp \
    vertice_gui_info.cpp \
    grafo_ponderado.cpp \
    ligacao.cpp \
    grafo.cpp \
    vertice.cpp

HEADERS  += mainwindow.h \
    backend.h \
    grafico_v.h \
    vertice_gui_info.h \
    grafo_ponderado.h \
    ligacao.h \
    grafo.h \
    vertice.h

FORMS    += mainwindow.ui
