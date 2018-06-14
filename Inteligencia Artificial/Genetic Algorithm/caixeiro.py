import numpy as np

class Caixeiro:
    
    totalCities = 0
    cities = []
    population = []

    def __init__(self, citiesLen):
        self.totalCities = citiesLen
        self.cities = np.zeros([citiesLen, citiesLen])
        self.inicializeCities(citiesLen)

    def inicializeCities(self, citiesLen):
        
        for i in range(citiesLen):
            for j in range(citiesLen):
                self.cities[i][j] = input()

    
            
    def initRandomPopulation(self):
        for p in range(0, 5):
            self.population.append(np.random.choice(range(self.totalCities), self.totalCities, replace=False))

    def printa(self):
        print self.cities
        print self.population
        print self.fitness([1,2,5,2,5])

    def fitness(self, way):
        return np.sum(way)        

problem = Caixeiro(3)
problem.initRandomPopulation()
problem.printa()
