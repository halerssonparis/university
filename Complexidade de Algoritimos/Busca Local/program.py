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

    def __init__(self):
        print "[*] - Welcome!"

    def loadData(self, fileName):
        with open(fileName) as dataset:
            data = csv.reader(dataset, delimiter=',')

            for index, item in enumerate(data):
                if (index == 0):
                    self.inputsLength = item[0]
                    self.maxCapacity = item[1]
                elif (index == 1):
                    self.weights = item
                elif (index == 2):
                    self.values = item                    

    

    def searchSoluctions(self):
        #[0] weigth  |  [1] value
        newSoluction.append([0, 0])           
        newWeigth = 0

        for (i = 0; i < self.inputsLength; i++):
            del newSoluction[:]
            
            if (values[i] < self.maxCapacity):      

                newSoluction.append([weights[i], values[i])         
                newWeigth = values[i]

                for (j = i+1; j < self.inputsLength; j++):
                    if (newWeigth + values[j] < self.maxCapacity):
                        for (k = j; k < self.inputsLength; k++):
                            
                            

                    elif (newWeigth + values[j] == self.maxCapacity):
                        newSoluction.append([weights[j], values[i])
                        newWeigth = newWeigth + values[i]
                        self.soluctions.add(newSoluction, newWeigth)
                        newSoluction.pop()
                    
            elif (values[i] == maxCapacity):
                self.soluctions.add(weights[i], values[i])

    def printValues(self):
        print "[-] - Items:"
        print self.inputsLength
        print self.maxCapacity
        print self.weights
        print self.values

BagzaoBrabo = BagProblem()
BagzaoBrabo.loadData("instancias2/a100.lia")
BagzaoBrabo.printValues()

