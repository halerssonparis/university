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

cv::Mat PDIUtils::logaritmo(cv::Mat imagemBase) {
	cv::Mat aux = imagemBase.clone();

	for (int x = 0; x < aux.rows; x++) {
		for (int y = 0; y < aux.cols; y++) {
			PixelEC r = imagemBase.at<PixelEC>(x, y);

			double c = 255 / log(256);

			PixelEC s = c * log(1 + r);

			aux.at<PixelEC>(x, y) = s;
		}
	}

	return aux;
}

cv::Mat PDIUtils::potencia(cv::Mat imagemBase, double gama) {
	cv::Mat aux = imagemBase.clone();

	for (int x = 0; x < aux.rows; x++) {
		for (int y = 0; y < aux.cols; y++) {
			PixelEC r = imagemBase.at<PixelEC>(x, y);

			double c = 255 / pow(255, gama);

			PixelEC s = c * pow(r, gama);

			aux.at<PixelEC>(x, y) = s;
		}
	}

	return aux;
}

cv::Mat PDIUtils::suavizacao(cv::Mat imagemBase, Matriz kernel)
{
	cv::Mat aux = imagemBase.clone();

	for (int x = kernel.size() / 2; x < aux.rows - kernel.size() / 2; x++) {
		for (int y = kernel.size() / 2; y < aux.cols - kernel.size() / 2; y++) {
			
			int somatorio = 0;
			int somatorioPesos = 0;
			for (int xk = 0; xk < kernel.size(); xk++) {
				for (int yk = 0; yk < kernel.size(); yk++) {
					somatorio += imagemBase.at<PixelEC>(x - (kernel.size() / 2) + xk, y - (kernel.size() / 2) + yk) * kernel[xk][yk];
					somatorioPesos += kernel[xk][yk];
				}
			}
			
			somatorio = somatorio / somatorioPesos;

			aux.at<PixelEC>(x, y) = somatorio;
		}
	}

	return aux;
}

cv::Mat PDIUtils::suavizacaoMediana(cv::Mat imagemBase, int tamanhoKernel)
{
	cv::Mat aux = imagemBase.clone();

	for (int x = tamanhoKernel / 2; x < aux.rows - tamanhoKernel / 2; x++) {
		for (int y = tamanhoKernel / 2; y < aux.cols - tamanhoKernel / 2; y++) {

			vector<int> vizinhanca = vector<int>();
			
			for (int xk = 0; xk < tamanhoKernel; xk++) {
				for (int yk = 0; yk < tamanhoKernel; yk++) {
					
					vizinhanca.push_back(imagemBase.at<PixelEC>(x - (tamanhoKernel / 2) + xk, y - (tamanhoKernel + yk)));
				}
			}

			sort(vizinhanca.begin(), vizinhanca.end());

			aux.at<PixelEC>(x, y) = vizinhanca[vizinhanca.size() / 2];
		}
	}

	return aux;
}

cv::Mat PDIUtils::laplaciano(cv::Mat imagemBase, Matriz kernel)
{
	cv::Mat aux = imagemBase.clone();

	for (int x = kernel.size() / 2; x < aux.rows - kernel.size() / 2; x++) {
		for (int y = kernel.size() / 2; y < aux.cols - kernel.size() / 2; y++) {

			int somatorio = 0;
			int somatorioPesos = 0;
			for (int xk = 0; xk < kernel.size(); xk++) {
				for (int yk = 0; yk < kernel.size(); yk++) {
					somatorio += (imagemBase.at<PixelEC>(x - (kernel.size() / 2) + xk, y - (kernel.size() / 2) + yk) * kernel[xk][yk]);
				}
			}
			somatorio += 128;

			if (somatorio > 255)
				somatorio = 255;
			else if (somatorio < 0)
				somatorio = 0;
			//else
				//somatorio += 128;

			aux.at<PixelEC>(x, y) = somatorio;
		}
	}

	return aux;
}

cv::Mat PDIUtils::agucamento(cv::Mat imagemBase, Matriz kernel)
{
	cv::Mat aux = laplaciano(imagemBase, kernel);

	for (int x = 0; x < imagemBase.rows; x++) {
		for (int y = 0; y < imagemBase.cols; y++) {
			
			int somatorio = (aux.at<PixelEC>(x, y) + imagemBase.at<PixelEC>(x, y)) - 128;

			if (somatorio < 0)
				somatorio = 0;
			else if (somatorio > 255)
				somatorio = 255;
			

			aux.at<PixelEC>(x, y) = somatorio;
		}
	}

	return aux;
}

Matriz PDIUtils::kernelAritmetico(int tamanho)
{
	Matriz novoKernel = vector<vector<int>>();
	for (int i = 0; i < tamanho; i++) {
		novoKernel.push_back(vector<int>());
		for (int j = 0; j < tamanho; j++) {
			novoKernel[i].push_back(1);
		}
	}

	return novoKernel;
}

Matriz PDIUtils::kernelGauss5() {
	Matriz novoKernelGauss = vector<vector<int>>();
	novoKernelGauss.push_back({ -1,  -1, -1 });
	novoKernelGauss.push_back({ -1, 8, -1 });
	novoKernelGauss.push_back({ -1,  -1, -1 });

	return novoKernelGauss;
}


