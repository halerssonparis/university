import numpy as np
import csv

class Caixeiro:
    
    choices = [16,32,100,500,1000,5000,10000]
    lenPopulation = 0
    fileCities = ""
    

    totalCities = 0
    cities = []
    population = []
    redundancyPopulation = [] # this is the fitness result population
    
    selectedUsers = [] # this is the selected user's (by roullet)
    newPopulation = [] # this is the son's 

    def __init__(self, lenP, generation, fileC):
        
        self.initVariables(lenP, fileC)
        self.totalCities = fileC
        self.inicializeCities()
        self.initRandomPopulation()
        self.sortData()
        self.printa("| Rota sem aplicacao do Algoritimo ")
    
        print "\n[-] Aplicando Algoritimo Genetico...        OBS: Os resultados serao exibidos da menor rota para a maior rota."

        for i in range(generation):
            self.sortData()
            self.userSelector()
            self.crossing()
            self.mutation()
            self.addNewPopulation()
    
        self.sortData()

    def inicializeCities(self):
        with open(self.fileCities) as dataset:
            data = csv.reader(dataset, delimiter=' ')
            for distanceTable in data:
                self.cities.append(map(int, distanceTable))


    def initVariables(self, lenP, fileC):
        if (lenP in self.choices):
            self.lenPopulation = lenP
        else:
            print "Tamanho da Populacao nao permitido!"
            exit()
    
        if (fileC == 20):
            self.fileCities = "20cities.txt"
        elif (fileC == 30):
            self.fileCities = "30cities.txt"
        elif (fileC == 40):
            self.fileCities = "40cities.txt"
        else:
            exit()

    def addNewPopulation(self):
        count = 0
        for i in range(len(self.population)/2, len(self.population)):
            self.population[i] = self.newPopulation[count]
            count = count + 1 
            if (count == len(self.newPopulation)):
                break

    def mutation(self):
        for individual in range(len(self.newPopulation)):
            swp1 = np.random.randint(low=0, high=self.totalCities, size=1)[0]
            swp2 = np.random.randint(low=0, high=self.totalCities-1, size=1)[0]
            self.newPopulation[individual][swp1], self.newPopulation[individual][swp1] = self.newPopulation[individual][swp2], self.newPopulation[individual][swp1]

    def crossing(self):
        del self.newPopulation[:]

        for parentsIndex in range(0, len(self.selectedUsers), 2):
            first = []
            second = []

            for geneIndex in range(self.totalCities/2):
                first.append(self.selectedUsers[parentsIndex][np.random.randint(low=0, high=self.totalCities-1, size=1)[0]])
                second.append(self.selectedUsers[parentsIndex+1][np.random.randint(low=0, high=self.totalCities-1, size=1)[0]])

            for geneIndex in range(self.totalCities):
                               
                if ((self.selectedUsers[parentsIndex+1][geneIndex] not in first) and len(first) != self.totalCities):
                    first.append(self.selectedUsers[parentsIndex+1][geneIndex])
                    if (len(first) != len(set(first))):
                        first = list(set(first))

                if ((self.selectedUsers[parentsIndex][geneIndex] not in second) and len(second) != self.totalCities):
                    second.append(self.selectedUsers[parentsIndex][geneIndex])
                    if (len(second) != len(set(second))):
                        second = list(set(second))

            self.newPopulation.append(first)
            self.newPopulation.append(second)

    def userSelector(self):
        for index in range(len(self.population)/2):
            parent1 = self.population[np.random.randint(low=0, high=len(self.population)/2, size=1)[0]]
            parent2 = self.population[np.random.randint(low=0, high=len(self.population)/2, size=1)[0]]
            parent3 = self.population[np.random.randint(low=0, high=len(self.population)/2, size=1)[0]]
            parent4 = self.population[np.random.randint(low=0, high=len(self.population)/2, size=1)[0]]
            parent5 = self.population[np.random.randint(low=0, high=len(self.population)/2, size=1)[0]]
            parent6 = self.population[np.random.randint(low=0, high=len(self.population)/2, size=1)[0]]

            f1, f2, f3, f4, f5, f6 = self.fitness(parent1), self.fitness(parent2), self.fitness(parent3), self.fitness(parent4), self.fitness(parent5), self.fitness(parent6)
    
            p1, p2, p3, p4, p5, p6 = f1, f1 + f2, f1 + f2 + f3, f1 + f2 + f3 + f4, f1 + f2 + f3 + f4 + f5, f1 + f2 + f3 + f4 + f5 + f6

            totalFitness = f1 + f2 + f3 + f4 + f5 + f6
            result = np.random.randint(low=0, high=totalFitness, size=1)[0]
            
            if (result <= p1):
                self.selectedUsers.append(parent1)
            elif (result > p1 and result <= p2):
                self.selectedUsers.append(parent2)
            elif (result > p2 and result <= p3):
                self.selectedUsers.append(parent3)
            elif (result > p3 and result <= p4):
                self.selectedUsers.append(parent4)
            elif (result > p4 and result <= p5):
                self.selectedUsers.append(parent4)
            elif (result > p5 and result <= p6):
                self.selectedUsers.append(parent4)


    def sortData(self):
        del self.redundancyPopulation[:]

        for way in self.population:
            self.redundancyPopulation.append(self.fitness(way))

        #This is bubbleSort, *temporary" CHANGE THIS kk!!
        for i in range(len(self.redundancyPopulation) - 1):
            for j in range(i+1, len(self.redundancyPopulation)):
                if (self.redundancyPopulation[i] > self.redundancyPopulation[j]):
                    self.redundancyPopulation[i], self.redundancyPopulation[j] = self.redundancyPopulation[j], self.redundancyPopulation[i]
                    self.population[i], self.population[j] = self.population[j], self.population[i]
        
        
   
    def initRandomPopulation(self):
        for p in range(0, self.lenPopulation):
            self.population.append(np.random.choice(range(self.totalCities), self.totalCities, replace=False))

    def fitness(self, way):
        i = 0
        for city in range(1, len(way)):
            i = i + self.cities[way[city-1]][way[city]]
        return i    


    def bestRoute(self, msg):
        #gamb
        route = 9999999
        w = []
        for way in self.population:
            if (self.fitness(way) < route):
                route = self.fitness(way)
                w = way

        print "Melhor Rota {} : {}   :  Valor da Rota: {}".format(msg, w, route) 
        print

    def printa(self, msg):
        for s in self.population:
            print "Rota: {}   :    Valor da Rota: {}".format(s, self.fitness(s))
            
        print
        self.bestRoute(msg)



print
print "###### ALGORITIMO GENETICO PARA O PROBLEMA DO CAIXEIRO VIAJANTE ######"
print "--- Se o seu computador for fraco (hardware), escolha valores baixos, pois o programa vai mostrar todas as populacoes.\n"

print "[*] Escolha uma populacao:\n-16\n-32\n-100\n-500\n-1000\n-5000\n-10000\nDigiteExemplo: 500\n"
resultPopulation = input(">")
print

print "[*] Digite a quantidade de geracoes: \nDigiteExemplo: 50"
resultG = input(">")
print

print "[*]Escolha a quantidade de cidades:\n-20\n-30\n-40\nDigiteExemplo: 20\n"
resultC = input(">")

problem = Caixeiro(resultPopulation, resultG, resultC)
problem.printa("| Rota apos a aplicacao do Algoritimo")
