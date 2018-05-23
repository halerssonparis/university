using System;
using System.Collections.Generic;

namespace BreastCancer
{
  class TumourFeatures
    {
        public static int FeatureCount = 10;

        public float Radius { get; set; }
        public float Texture { get; set; }
        public float Perimeter { get; set; }
        public float Area { get; set; }
        public float Smoothness { get; set; }
        public float Compactness { get; set; }
        public float Concavity { get; set; }
        public float ConcavePoints { get; set; }
        public float Symmetry { get; set; }
        public float FractalDimension { get; set; }

        public TumourFeatures(float radius, float texture, float perimeter, float area, float smoothness,
            float compactness, float concavity, float concavePoints, float symmetry, float fractalDimension)
        {
            Radius = radius;
            Texture = texture;
            Perimeter = perimeter;
            Area = area;
            Smoothness = smoothness;
            Compactness = compactness;
            Concavity = concavity;
            ConcavePoints = concavePoints;
            Symmetry = symmetry;
            FractalDimension = fractalDimension;
        }

        public TumourFeatures(List<float> values)
        : this(values[0], values[1], values[2], values[3], values[4],
            values[5],values[6], values[7], values[8], values[9])
        {

        }

        public void Print()
        {
            Console.WriteLine("Radius: " + Radius);
            Console.WriteLine("Texture: " + Texture);
            Console.WriteLine("Perimeter: " + Perimeter);
            Console.WriteLine("Area: " + Area);
            Console.WriteLine("Smoothness: " + Smoothness);
            Console.WriteLine("Compactness: " + Compactness);
            Console.WriteLine("Concavity: " + Concavity);
            Console.WriteLine("Concave Points: " + ConcavePoints);
            Console.WriteLine("Symmetry: " + Symmetry);
            Console.WriteLine("Fractal Dimension: " + FractalDimension);
        }

        public float CalculateSimilarity(TumourFeatures other, List<float> weights)
        {
            //Definir o min e max dos atributos
            double maxRadius;
            double maxRadius; 
            double maxPerimeter;
            double maxArea;
            double maxConcavity;
            double maxconcavePoints;
            double maxSymmetry;
            double maxFractalDimension;
            
            double minRadius;
            double minRadius;
            double minPerimeter;
            double minArea;
            double minConcavity;
            double minconcavePoints;
            double minSymmetry;
            double minFractalDimension;

            List<double> similarities = new List<double>() {
                1 - ((other.radius - this.radius) / maxRadius - minRadius),
                1 - ((other.texture - this.texture) / maxTexture - minTexture),
                1 - ((other.perimeter - this.perimeter) / maxPerimeter - minPerimeter),
                1 - ((other.area - this.area) / maxArea - minArea),
                1 - ((other.concavity - this.concavity) / maxConcavity - minConcavity),
                1 - ((other.concavePoints - this.concavePoints) / maxConcavePoints - minConcavePoints),
                1 - ((other.symmetry - this.symmetry) / maxSymmetry - minSymmetry),
                1 - ((other.fractalDimension - this.fractalDimension) / maxFractalDimension - minFractalDimension)
            };

            /*double similarityRadius = 1 - ((other.radius - this.radius) / maxRadius - minRadius); 
            double similarityTexture = 1 - ((other.texture - this.texture) / maxTexture - minTexture);
            double similarityPerimeter = 1 - ((other.perimeter - this.perimeter) / maxPerimeter - minPerimeter);
            double similarityArea = 1 - ((other.area - this.area) / maxArea - minArea);
            double similaritySmoothness = 1 - ((other.area - this.area) / maxArea - minArea);
            double similarityConcavity = 1 - ((other.concavity - this.concavity) / maxConcavity - minConcavity);
            double similarityConcavePoints = 1 - ((other.concavePoints - this.concavePoints) / maxConcavePoints - minConcavePoints);
            double similaritySymmetry = 1 - ((other.symmetry - this.symmetry) / maxSymmetry - minSymmetry);
            double similarityFractalDimension = 1- ((other.fractalDimension - this.fractalDimension) / maxFractalDimension - minFractalDimension);*/
            
            double similarityTotal = 0.0;
            double weightSum = 0.0;

            for (int index = 0; index < similarities.Count; index++) {
                similarityTotal += weights[index] * similarities[index];
                weightSum += weights[index];
            }
            
            similarityTotal = similarityTotal / weightSum;
            
            return similarityTotal;
        }
    }
}
