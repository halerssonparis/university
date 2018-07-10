import csv
import collections
import copy
import numpy as np
import matplotlib.pyplot as plt

class Soluctions:
    
    inputsSoluctions = []
    
    compare = lambda list1, list2: collections.Counter(list1) == collections.Counter(list2)    

    #def __init__(self):

    def add(self, inputs, totalValue):
        if (self.verifySoluctions(inputs)):
            self.inputsSoluctions.append([inputs, totalValue])

    def verifySoluctions(self, newSoluction):
        for soluction in self.inputsSoluctions:
            #Coloquei soluction[0], pq na posicao [1] esta a soma total dos pesos da solucao
            if (self.compare(soluction[0], newSoluction)):
                return False
                 
        return True

    #def compare(self, list1, list2):
        #for index, item in enumerate(list1):
            
    

class BagProblem:
    
    inputsLength = 0
    maxCapacity = 0
    weights = []
    values = []
    soluctions = Soluctions()
    newSoluction = []


    def __init__(self):
        print "[*] - Welcome!"

    def loadData(self, fileName):
        with open(fileName) as dataset:
            data = csv.reader(dataset, delimiter=',')

            for index, item in enumerate(data):
                if (index == 0):
                    self.inputsLength = int(item[0])
                    self.maxCapacity = int(item[1])
                elif (index == 1):
                    for i in item:
                        self.weights.append(int(i))
                elif (index == 2):
                    for i in item:
                        self.values.append(int(i))    

    

    def searchSoluctions(self):

        #plt.axis([0, 10000, 0, 100])
        fig = plt.figure()
        ax = fig.add_subplot(111)

        #[0] weigth  |  [1] value
        soluctions_t = []
        trys = []
        newWeigth = 0
        scheduler = 0
        counter = 0        
        maxLocal = [0, []]
        t = 0

        for i in range(self.inputsLength):
            if (self.weights[i] < self.maxCapacity):
                newWeigth = self.weights[i]
                trys.append(i)

                while trys:
                    counter = counter + 1
                    #se a somas dos pesos nao passarem e nao tiver aquele item na soolucao, ele adiciona.
                    if (newWeigth + self.weights[scheduler] < self.maxCapacity and (scheduler not in trys)):
                        newWeigth = newWeigth + self.weights[scheduler]
                        trys.append(scheduler)
                        scheduler = 0

                    elif (scheduler == (self.inputsLength - 1)):
                        soluctions_t.append([copy.deepcopy(trys), newWeigth])
                        if (t == 0):
                            maxLocal[0] = newWeigth
                            maxLocal[1] = trys
                            print maxLocal
                            t = 1

                        if (newWeigth < maxLocal[0] and len(trys) > len(maxLocal[1])):
                            maxLocal[0] = newWeigth
                            maxLocal[1] = trys
                            print maxLocal

                        plt.scatter(counter, len(trys))                 
                        plt.pause(0.001)

                        #print "New S: {}".format(trys)

                        while (scheduler == (self.inputsLength - 1) and trys):
                            scheduler = trys[-1]
                            newWeigth = newWeigth - self.weights[scheduler]
                            trys.pop()

                        if (scheduler + 1 <= (self.inputsLength - 1) and trys):
                            scheduler = scheduler + 1
                    else:
                        scheduler = scheduler + 1
            
        plt.show()

    def printValues(self):
        print "[-] - Items:"
        print self.soluctions.inputsSoluctions
        '''print self.inputsLength
        print self.maxCapacity
        print self.weights
        print self.values'''

BagzaoBrabo = BagProblem()
BagzaoBrabo.loadData("instancias2/a100.lia")
BagzaoBrabo.searchSoluctions()



