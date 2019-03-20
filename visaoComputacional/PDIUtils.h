#pragma once
#include <opencv2/opencv.hpp>
#include <cstdarg>

using Matriz = std::vector<std::vector<int>>;
using Pixel = cv::Vec3b;
using PixelEC = uchar;

class PDIUtils
{
public:
	static cv::Mat escalaCinza(cv::Mat imagemColorida);
	static cv::Mat canal(cv::Mat imagemColorida, int canal);
	static cv::Mat negativo(cv::Mat imagemBase);

	static std::vector<float> PDIUtils::histograma(cv::Mat imagem);
	static cv::Mat limiarizacao(cv::Mat imagemBase, int limiar);
	static cv::Mat janelamento(cv::Mat imagemBase, int li, int ls);
	static cv::Mat janelamento2(cv::Mat imagemBase);
};


