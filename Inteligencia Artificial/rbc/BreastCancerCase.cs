using System;
using System.Collections.Generic;
using System.Linq;

namespace BreastCancer
{
  class BreastCancerCase
    {
        public int Id { get; set; }
        public bool Malignant { get; set; }
        public TumourFeatures Mean { get; set; }
        public TumourFeatures StandardError { get; set; }
        public TumourFeatures Extreme { get; set; }

        private void Initialize(int id, bool malignant, TumourFeatures mean,
            TumourFeatures standardError, TumourFeatures extreme)
        {
            Id = id;
            Malignant = malignant;
            Mean = mean;
            StandardError = standardError;
            Extreme = extreme;
        }

        static List<float> ProcessValues(List<string> input, int startIndex)
        {
            return input
                .GetRange(startIndex, TumourFeatures.FeatureCount)
                .Select(val => float.Parse(val))
                .ToList();
        }

        public BreastCancerCase(List<string> input)
        {
            List<float> mean = ProcessValues(input, 2);
            List<float> standardError = ProcessValues(input, 2 + TumourFeatures.FeatureCount);
            List<float> extreme = ProcessValues(input, 2 + (TumourFeatures.FeatureCount * 2));

            Initialize(
                int.Parse(input[0]),
                input[1].Equals("M"), 
                new TumourFeatures(mean),
                new TumourFeatures(standardError),
                new TumourFeatures(extreme)
            );
        }

        // Similarity comparison
        public float CompareTo(BreastCancerCase other, List<float> meanWeights,
            List<float> errorWeights, List<float> extremeWeights)
        {
            List<float> values = new List<float>();
            values.Add(this.Mean.CalculateSimilarity(other.Mean, meanWeights));
            values.Add(this.StandardError.CalculateSimilarity(other.StandardError, errorWeights));
            values.Add(this.Extreme.CalculateSimilarity(other.Extreme, extremeWeights));
            return values.Average();
        }

        public void Print()
        {
            Console.WriteLine("Breast Cancer Case #" + Id);
            Console.WriteLine("Mean of the features:");
            Mean.Print();
            Console.WriteLine("Standard Error of the features:");
            StandardError.Print();
            Console.WriteLine("Extreme values of the features:");
            Extreme.Print();

            Console.WriteLine("Diagnosis: " + (Malignant ? "Malignant" : "Benign"));
        }
    }
}