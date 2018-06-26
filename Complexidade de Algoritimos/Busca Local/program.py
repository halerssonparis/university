import csv
import collections

class Soluctions:
    
    inputsSoluctions = []
    
    compare = lambda list1, list2: collenctions.Counter(list1) == collenctions.Counter(list2)    

    #def __init__(self):

    def add(self, inputs, totalValue):
        if (verifySoluctions(inputs)):
            inputsSoluctions.append([inputs, totalValue])

    def verifySoluctions(self, newSoluction):
        for soluction in self.inputsSoluctions:
            if (compare(soluction[0], newSoluction)):
                return False
                 
        return True

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
                    self.weights = item
                elif (index == 2):
                    self.values = item                    

    

    def searchSoluctions(self):
        #[0] weigth  |  [1] value
        newWeigth = 0

        for i in range(self.inputsLength):
            del self.newSoluction[:]
            
            if (self.values[i] < self.maxCapacity):      

                self.newSoluction.append([self.weights[i], self.values[i]])         
                newWeigth = self.values[i]

                for j in range(i+1, self.inputsLength):
                    if (newWeigth + self.values[j] < self.maxCapacity):
                        self.newSoluction.append([self.weights[j], self.values[j]])
                        newWeigth = newWeigth + self.values[i]

                        for k in range(j + 1, inputsLength):
                            if (newWeigth + self.values[k] < self.maxCapacity):
                                self.newSoluction.append([self.weights[j], self.values[j]])
                                newWeigth = newWeigth + self.values[j]

                            elif (newWeigth + self.values[k] == self.maxCapacity):
                                self.newSoluction.append([self.weights[k], self.values[k]])
                                self.soluctions.add(self.newSoluction, newWeigth + self.values[k])
                                self.newSoluction.pop()

                    elif (newWeigth + self.values[j] == self.maxCapacity):
                        self.newSoluction.append([self.weights[j], self.values[i]])
                        self.soluctions.add(self.newSoluction, newWeigth + self.values[i])
                        self.newSoluction.pop()
                    
            elif (self.values[i] == self.maxCapacity):
                self.soluctions.add(self.weights[i], self.values[i])

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
BagzaoBrabo.printValues()



