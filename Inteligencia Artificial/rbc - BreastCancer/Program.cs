using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;

namespace BreastCancer
{
    class ExperimentSetup
    {
        public List<BreastCancerCase> CaseBase { get; set; }
        public List<BreastCancerCase> TestSet { get; set; }
        public List<float> MeanWeights { get; set; }
        public List<float> ErrorWeights { get; set; }
        public List<float> ExtremeWeights { get; set; }


        // Creates a new randomized experiment setup, splitting the data in two parts
        public ExperimentSetup(List<BreastCancerCase> dataset, double testRatio)
        {
            int testCount = (int) Math.Round(dataset.Count * testRatio);

            Random rng = new Random();
            int n = dataset.Count;
            while (n > 1) {
                n--;
                int k = rng.Next(n + 1);
                BreastCancerCase value = dataset[k];
                dataset[k] = dataset[n];
                dataset[n] = value;
            }

            CaseBase = dataset.GetRange(0, dataset.Count - testCount);
            TestSet = dataset.GetRange(dataset.Count - testCount, testCount);
        }
    }

    class Program
    {
		static List<BreastCancerCase> Parse(List<List<string>> input)
		{
			return input.Select(row => new BreastCancerCase(row)).ToList();
		}

        static void RunExperiment(ExperimentSetup setup)
        {
            int n = setup.TestSet.Count;
            int[,] confusionMatrix = new int[n, n]; // Implicitly filled with zeros

            foreach (BreastCancerCase testCase in setup.TestSet)
            {
                List<BreastCancerCase> ordered = setup.CaseBase
                    .OrderByDescending(value => value.CompareTo(
                        testCase, setup.MeanWeights, setup.ErrorWeights, setup.ExtremeWeights
                    )).ToList();

                Console.WriteLine("Case under evaluation: ");
                testCase.Print();
                Console.WriteLine("\nMost similar cases in the database:\n");

                bool actual = testCase.Malignant;
                bool predicted = ordered[0].Malignant;

                int row = predicted ? 0 : 1;
                int column = actual ? 0 : 1;
                confusionMatrix[row, column]++;

                for (int i = 0; i < 5; i++)
                {
                    BreastCancerCase match = ordered[i];

                    Console.WriteLine("Match n. #" + i);
                    match.Print();

                    bool valid = match.Malignant == actual;

                    float y = match.CompareTo(testCase, setup.MeanWeights, setup.ErrorWeights, setup.ExtremeWeights);
                    Console.WriteLine("\nSimilarity: " + y);
                    Console.WriteLine("Valid diagnosis: " + (valid ? "YES" : "NO"));
                    Console.WriteLine();
                }
            }

            Console.WriteLine("Outcome:");
            Console.WriteLine("\tB\tM");
            Console.WriteLine("B\t" + confusionMatrix[0, 0] + "\t" + confusionMatrix[0, 1]);
            Console.WriteLine("M\t" + confusionMatrix[1, 0] + "\t" + confusionMatrix[1, 1]);

            // True Negatives + True Positives / Negatives + Positives
            double accuracy = (double) (confusionMatrix[0, 0] + confusionMatrix[1, 1]) / (double) n;
            Console.WriteLine("Accuracy: " + accuracy);
        }

        static void Main(string[] args)
        {
            List<BreastCancerCase> data = Parse(Reader.Read("./data.csv", ','));

            List<float> weights = Enumerable.Repeat(1f, TumourFeatures.FeatureCount).ToList();

            ExperimentSetup setup = new ExperimentSetup(data, 0.3);
            setup.MeanWeights = weights;
            setup.ErrorWeights = weights;
            setup.ExtremeWeights = weights;

            RunExperiment(setup);
        }
    }
}
