// ConsoleApplication2.cpp : define o ponto de entrada para o aplicativo do console.
//

#include "stdafx.h"
#include <opencv2\opencv.hpp>

using namespace cv;

Mat colorChannel(Mat image, int channel);

int main()
{

	Mat imagem = imread("C:\\Users\\5985854\\Downloads\\Imagens\\lena.jpg");
	

	for (int i = 0; i < imagem.rows; i++) {
		for (int j = 0; j < imagem.cols; j++) {

			Vec3b &pixelzao = imagem.at<Vec3b>(i, j);

			uchar cor = (pixelzao[0] * 0.07 +pixelzao[1] * 0.21 + pixelzao[2] * 0.72);

			//pixelzao[2] = cor;
			//pixelzao[1] = cor;
			//pixelzao[2] = cor;

			pixelzao[0] = cor;
			pixelzao[1] = cor;
			pixelzao[2] = cor;

			//imagem.at<Vec3b>(10, 10) = pixelzao;
		}
	}
	

	imshow("sou uma imagem daora pkct", imagem);
	imshow("1sou uma imagem daora pkct", colorChannel(imagem, 2));
	imshow("2sou uma imagem daora pkct", colorChannel(imagem, 1));
	imshow("3sou uma imagem daora pkct", colorChannel(imagem, 0));

	waitKey(0);
    return 0;
}

Mat colorChannel(Mat image, int channel)
{
	Mat newimage = image.clone();

	for (int i = 0; i < newimage.rows; i++) {
		for (int j = 0; j < newimage.cols; j++) {

			Vec3b pixelzao = newimage.at<Vec3b>(i, j);

			uchar cor = pixelzao[channel];
;
			//pixelzao[2] = cor;
			//pixelzao[1] = cor;
			//pixelzao[2] = cor;

			pixelzao[0] = cor;
			pixelzao[1] = cor;
			pixelzao[2] = cor;

			newimage.at<Vec3b>(i, j) = pixelzao;
			//imagem.at<Vec3b>(10, 10) = pixelzao;
		}
	}

	return newimage;
}
