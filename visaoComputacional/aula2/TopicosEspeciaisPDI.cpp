#include <iostream>
#include "PDIUtils.h"
#include "opencv2/opencv.hpp"

using namespace cv;

int main()
{
	Mat imagem;
	Mat imagemResultado;
	imagem = imread("Imagens/pow.tif");
	imagem = PDIUtils::escalaCinza(imagem);
	
	

	//imagemResultado = PDIUtils::janelamento(imagem);
	//imagemResultado = PDIUtils::logaritmo(imagem);
	//imagemResultado = PDIUtils::potencia(imagem, 1/2.0);
	imagemResultado = PDIUtils::linearParticionada(imagem, 50, 30, 120, 10);


	imshow("Original", imagem);
	imshow("Resultado", imagemResultado);

	waitKey(0);

    
	return 0;
}