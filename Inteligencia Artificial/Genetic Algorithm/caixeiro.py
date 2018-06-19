## TO DO: to implement roullet,   change the bubbleSort
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
        #to implement roullet
        for lucky in range(len(self.population)/2):
            self.selectedUsers.append(self.population[np.random.randint(low=0, high=len(self.population)/2, size=1)[0]])


    def sortData(self):
        for way in self.population:
            self.redundancyPopulation.append(self.fitness(way))

        #This is bubbleSort, *temporary" CHANGE THIS kk!!
        for i in range(len(self.redundancyPopulation) - 1):
            for j in range(i+1, len(self.redundancyPopulation)):
                if (self.redundancyPopulation[i] > self.redundancyPopulation[j]):
                    self.redundancyPopulation[i], self.redundancyPopulation[j] = self.redundancyPopulation[j], self.redundancyPopulation[i]
                    self.population[i], self.population[j] = self.population[j], self.population[i]
        
        del self.redundancyPopulation[:]
   
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

        print "Melhor Rota {} : {}   :   {}".format(msg, w, route) 
        print

    def printa(self, msg):
        for s in self.population:
            print "Rota: {}   :    Valor da Rota: {}".format(s, self.fitness(s))
            
        print
        self.bestRoute(msg)



#pop = 32 16 100 500 1000 5000 10000
print
print "###### ALGORITIMO GENETICO PARA SOLUCAO DO PROBLEMA DO CAIXEIRO VIAJANTE ######"
print "--- Se o seu computador for fraco (hardware), escolha valores baixos, pois o programa vai mostrar todas as rotas.\n"
print "[*] Escolha uma populacao:\n-16\n-32\n-100\n-500\n-1000\n-5000\n-10000\nDigiteExemplo: 500\n"
resultPopulation = input()
print

print "[*] Digite a quantidade de geracoes: \nDigiteExemplo: 50"
resultG = input()
print

print "[*]Escolha a quantidade de cidades:\n-20\n-30\n-40\nDigiteExemplo: 20\n"
resultC = input()

problem = Caixeiro(resultPopulation, resultG, resultC)
problem.printa("| Rota apos a aplicacao do Algoritimo")
