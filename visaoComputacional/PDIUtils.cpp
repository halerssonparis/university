#include "PDIUtils.h"
#include <algorithm>
#include <cmath>


cv::Mat PDIUtils::escalaCinza(cv::Mat imagemColorida) {
	cv::Mat aux = imagemColorida.clone();

	cv::cvtColor(imagemColorida, aux, cv::COLOR_BGR2GRAY);

	return aux;
}

cv::Mat PDIUtils::negativo(cv::Mat imagemBase) {
	cv::Mat aux = imagemBase.clone();

	for (int x = 0; x < aux.rows; x++) {
		for (int y = 0; y < aux.cols; y++) {
			PixelEC pixel = imagemBase.at<PixelEC>(x, y);
			PixelEC negativo = 255 - pixel;
			aux.at<PixelEC>(x, y) = negativo;
		}
	}

	return aux;
}

cv::Mat PDIUtils::canal(cv::Mat imagemColorida, int canal) {
	cv::Mat aux = escalaCinza(imagemColorida);

	for (int x = 0; x < aux.rows; x++) {
		for (int y = 0; y < aux.cols; y++) {

			Pixel pixel = imagemColorida.at<Pixel>(x, y);
			PixelEC pCanal = pixel[canal];


			aux.at<PixelEC>(x, y) = pCanal;
		}
	}

	return aux;
}


std::vector<float> PDIUtils::histograma(cv::Mat imagem) {
	std::vector<float> hist = std::vector<float>(256);

	/*/PixelEC*/

	for (int i = 0; i < imagem.rows; i++) {
		for (int j = 0; j < imagem.cols; j++) {
			hist[imagem.at<uchar>(i, j)]++;
		}
	}

	for (int i = 0; i < hist.size(); i++) {
		hist[i] = hist[i] / (imagem.rows * imagem.cols);
	}

	//faz o histograma

	return hist;
}


cv::Mat PDIUtils::limiarizacao(cv::Mat imagemBase, int limiar) {
	cv::Mat aux = imagemBase.clone();

	for (int i = 0; i < aux.rows; i++) {
		for (int j = 0; j < aux.cols; j++) {

			if (imagemBase.at<uchar>(i, j) <= limiar) {
				aux.at<uchar>(i, j) = 0;
			}
			else {
				aux.at<uchar>(i, j) = 255;
			}
		}
	}

	return aux;
}

cv::Mat PDIUtils::janelamento(cv::Mat imagemBase, int li, int ls) {
	cv::Mat aux = imagemBase.clone();

	for (int i = 0; i < aux.rows; i++) {
		for (int j = 0; j < aux.cols; j++) {

			if (imagemBase.at<uchar>(i, j) <= li)
				aux.at<uchar>(i, j) = 0;
			else  if (imagemBase.at<uchar>(i, j) >= ls)
				aux.at<uchar>(i, j) = 255;
			else
				aux.at<uchar>(i, j) = (aux.at<uchar>(i, j) - li) * (255 / (ls - li) );
		}
	}

	return aux;
}

cv::Mat PDIUtils::janelamento2(cv::Mat imagemBase)
{
	int li = 0, ls = 255;
	bool err1 = true, err2 = true;

	std::vector<float> hist = PDIUtils::histograma(imagemBase);

	for (int i = 0; i < hist.size(); i++) {
		if (hist[i] == 0 && err1) {
			li++;
		}
		else {
			err1 = false;
		}
	}

	for (int i = 0; i < hist.size(); i++) {
		if (hist[hist.size() - i] == 0 && err2) {
			ls--;
		}
		else {
			err2 = false;
		}
	}

	std::cout << li << " " << ls;
	return janelamento(imagemBase, li, ls);
}

