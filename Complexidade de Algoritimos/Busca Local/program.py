import csv
import collections


class bagProblem:
    
    inputsLength = 0
    maxCapacity = 0
    weights = []
    values = []
    soluctions = []    

    compare = lambda list1, list2: collenctions.Counter(list1) == collenctions.Counter(list2)

    def __init__(self):
        print "[*] - Welcome!"

    def loadData(self, fileName):
        with open(fileName) as dataset:
            data = csv.reader(dataset, delimiter=',')

            for index, item in enumerate(data):
                if (index == 0):
                    self.inputsLength = item[0]
                    self.maxCapacity = item[1]
                else:
                    
                    
                    print item          
        
    def verifySoluctions(self, newSoluction):
        for soluction in self.soluctions:
            if (compare(soluction, newSoluction)):
                return False
                 
        return True


newBag = bagProblem()
newBag.loadData("instancias2/a100.lia")

