#include <iostream>
#include "PDIUtils.h"
#include "opencv2/opencv.hpp"

using namespace cv;

int main()
{
	Mat imagem;
	Mat imagemResultado;
	imagem = imread("Imagens/testeLimiarizacao1.jpg");
	imagem = PDIUtils::escalaCinza(imagem);

	//imagemResultado = PDIUtils::limiarizacao(imagem, 50);
	imagemResultado = PDIUtils::otsu(imagem);

	imshow("Original", imagem);
	imshow("Resultado", imagemResultado);

	waitKey(0);


	return 0;
}