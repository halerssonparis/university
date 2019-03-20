#include <iostream>
#include "PDIUtils.h"
#include "opencv2/opencv.hpp"

using namespace cv;

int main()
{
	Mat imagem;
	Mat imagemResultado;
	imagem = imread("Imagens/polen.tif");
	imagem = PDIUtils::escalaCinza(imagem);
	std::vector<float> hist = PDIUtils::histograma(imagem);

	/*
	for (int i = 0; i < hist.size(); i++) {
		/*for (int j = 0; j < hist[i]; j++) {
			std::cout << "0";
		}
		std::cout << "\n";
		std::cout << i << " : " << hist[i] << "\n";
	}
	*/
	//imagem = PDIUtils::limiarizacao(imagem, 105);
	//imagem = PDIUtils::janelamento(imagem, 90, 120);
	imagem = PDIUtils::janelamento2(imagem);
	//Local do processamento

	imshow("Original", imagem);
	//imshow("Resultado", imagemResultado);
	waitKey(0);

    
	return 0;
}