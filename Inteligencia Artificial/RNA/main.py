import numpy as np
import csv 


class NeuralNetwork:

    weights = []
    z = []
    zs = []
    a = []
    yHat = []

    delta = []
    total = []

    def __init__(self, inputs, hidden_layers, out):
        self.inputs = inputs
        self.hidden_layers = hidden_layers
        self.out = out

        self.weights.append(np.random.rand(self.inputs, self.hidden_layers))
       
        for w in range(1, hidden_layers):
            self.weights.append(np.random.rand(self.hidden_layers, self.hidden_layers))
    
        self.weights.append(np.random.rand(self.hidden_layers, self.out))
        #print(self.weights)

    def feedFoward(self, x):
        #z = final value (somatorio) |  a = simgoid dos valores

        self.z = np.dot(x, self.weights[0])
        for i in range(1, len(self.weights) - 1):
            self.a.append(self.sigmoid(self.z))
            self.z.append(np.dot(self.a[i-1]), self.self.weights[i])

                
        yHat = self.sigmoid(self.z[-1])
        return yHat        

    def sigmoid(self, z):
        return 1 / (1 + np.exp(-z))

    def costFunctionPrime(self, x, y):
        self.yHat = self.feedFoward(x)
        
        delta  = np.multiply(-(y - self.yHat), self.sigmoidPrime(self.self.z[-1]))
        
    
class Database:
        
    data = [];
    
    def getData(self):
        with open('dataset.csv') as dataset:
            self.data = csv.reader(dataset, delimiter=',')
            #for row in data:
                #print(row)
                #print()

    def randomData(self):
        print()        
        #self.data = numpy.random.shuffle(self.data)
        

db = Database()
db.getData()
db.randomData()


rn = NeuralNetwork(10, 3, 1)
#rn.feedFoward(np.random.rand(10, 3))
'''
matriz = []
matriz.append(np.zeros((4,4), dtype=np.int))
matriz.append(np.zeros((4,4), dtype=np.int))

print(matriz[1][0][0])'''

#w1 = np.random.rand(2, 3)
#print(len(w1))
#print(w1.shape[0])
#print(w1)


import os

var = os.system("ls")
print(var)
