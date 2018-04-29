using System;
using System.Collections.Generic;
using System.IO;

namespace BreastCancer
{
	class Reader
	{
		public static List<List<string>> Read(string path, char separator)
		{
			List<List<string>> vector = new List<List<string>>();
			
			using (var reader = new StreamReader(path))
			{
				while (!reader.EndOfStream)
				{
					List<string> line = new List<string>();
					string[] str = reader.ReadLine().Split(separator);

					foreach (string s in str) {
						line.Add(s);
					}

					vector.Add(line);       
				}
			}
			
			return vector;

			// for (int i = 0; i < vector.Count; i++)
			// {
			// 	for (int j = 0; j < vector[i].Count; j++)
			// 	{
			// 		Console.WriteLine(vector[i][j]);
			// 	}
			// }
			// Console.ReadKey();
		}
	}
}