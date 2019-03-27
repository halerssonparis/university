#include "PDIUtils.h"
#include <algorithm>
#include <cmath>


cv::Mat PDIUtils::escalaCinza(cv::Mat imagemColorida) {
	cv::Mat aux = imagemColorida.clone();

	//cv::cvtColor(imagemColorida, aux, CV_BGR2GRAY);
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

	for (int x = 0; x < imagem.rows; x++) {
		for (int y = 0; y < imagem.cols; y++) {
			PixelEC pixel = imagem.at<PixelEC>(x, y);
			hist[pixel]++;
		}
	}

	float numPixels = imagem.rows * imagem.cols;
	
	for (int i = 0; i < 256; i++) {
		hist[i] /= numPixels;
	}

	return hist;
}


cv::Mat PDIUtils::limiarizacao(cv::Mat imagemBase, int limiar) {
	cv::Mat aux = imagemBase.clone();

	for (int x = 0; x < imagemBase.rows; x++) {
		for (int y = 0; y < imagemBase.cols; y++) {
			PixelEC pixel = imagemBase.at<PixelEC>(x, y);
			if (pixel <= limiar)
				aux.at<PixelEC>(x, y) = 0;
			else	
				aux.at<PixelEC>(x, y) = 255;
		}
	}

	return aux;
}

cv::Mat PDIUtils::janelamento(cv::Mat imagemBase, int li, int ls) {
	cv::Mat aux = imagemBase.clone();

	for (int x = 0; x < imagemBase.rows; x++) {
		for (int y = 0; y < imagemBase.cols; y++) {
			PixelEC pixel = imagemBase.at<PixelEC>(x, y);
			if (pixel < li)
				aux.at<PixelEC>(x, y) = 0;
			else if(pixel > ls)
				aux.at<PixelEC>(x, y) = 255;
			else
				aux.at<PixelEC>(x, y) = (pixel - li) * (255 / (ls - li));
		}
	}

	return aux;

}

cv::Mat PDIUtils::janelamento(cv::Mat imagemBase)
{
	std::vector<float> hist = histograma(imagemBase);

	int li = 0, ls = 255;

	for (int i = 0; i < 255; i++) {
		if (hist[i] > 0) {
			li = i;
			break;
		}
	}

	for (int i = 255; i >= 0; i--) {
		if (hist[i] > 0) {
			ls = i;
			break;
		}
	}

	return janelamento(imagemBase, li, ls);
}

cv::Mat PDIUtils::logaritmo(cv::Mat imagemBase)
{
	cv::Mat image2 = imagemBase.clone();

	double c = 255 / log(256);

	for (int x = 0; x < imagemBase.rows; x++) {
		for (int y = 0; y < imagemBase.cols; y++) {
			image2.at<uchar>(x, y) = c * log(image2.at<uchar>(x, y) + 1);
		}
	}

	return image2;
}

cv::Mat PDIUtils::potencia(cv::Mat imagemBase, double gama)
{
	cv::Mat image2 = imagemBase.clone();

	double c = 255 / pow(255, gama);

	for (int x = 0; x < imagemBase.rows; x++) {
		for (int y = 0; y < imagemBase.cols; y++) {
			image2.at<uchar>(x, y) = c * pow(image2.at<uchar>(x, y) + 1, gama);
		}
	}

	return image2;
}

cv::Mat PDIUtils::linearParticionada(cv::Mat imagemBase, int r1, int s1, int r2, int s2)
{

	cv::Mat image2 = imagemBase.clone();

	for (int x = 0; x < image2.rows; x++) {
		for (int y = 0; y < image2.cols; y++) {
			PixelEC pixel = image2.at<PixelEC>(x, y);
			if (pixel <= r1)
				image2.at<PixelEC>(x, y) = pixel * (s1 / r1);
			else if (pixel >= r2)
				image2.at<PixelEC>(x, y) = s2 + (pixel - r2) * ((255 - s2) / (255 - r2));
			else
				image2.at<PixelEC>(x, y) = s1 + (pixel - r1) * ((s2 - s1) / (r2 - r1));
		}
	}

	return image2;
	
}

