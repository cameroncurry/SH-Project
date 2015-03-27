import numpy as np
import itertools
import pylab as plt
import math

'''deviation of tournament'''
def tournamentDev(expected,result):
    sum = 0
    for i in range(0,len(expected)):
        sum += (expected[i] - result[i])**2
        
    return math.sqrt(sum / float(len(expected)))


def possibleDeviations(N):
    possibilites = []
    iterable = np.arange(1,N+1)

    for iteration in itertools.permutations(iterable):
        possibilites.append(tournamentDev(iterable, iteration))
    
    return possibilites



def plotPermutation():
    for i in range(4,8):
        plt.plot(possibleDeviations(i))

        plt.show()
        
  
def reverse(array):
    x = []
    N = len(array)
    for i in range(0,N):
        x.append(array[N-1-i])
    return x      


def maxDev(N):
    expected = np.arange(0,N)
    compliment = reverse(expected)
    return tournamentDev(expected, compliment)

print maxDev(32)

    
