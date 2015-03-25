import numpy as np
import random as r
import Permutations

'''returns average of a random sample from array'''
def sampleAverage(array):
    sampleSum = 0
    for i in array:
        sampleSum += array[r.randint(0,len(array)-1)]
        
    return sampleSum / float(len(array))


def bootstrap(array,iterations=100):
    sum = 0
    sumSq = 0
    for i in range(0,iterations):
        sample = sampleAverage(array)
        sum += sample
        sumSq += sample*sample
    
    average = sum / float(iterations)    
    sqAverage = sumSq / float(iterations)
    
    return np.sqrt(sqAverage - average*average)


def printStats(name,array,nteams):
    average = np.average(array)
    error = bootstrap(array)
    
    percentError = error / average
    
    adjustedAv = average / Permutations.maxDev(nteams)
    adjsutedErr = adjustedAv * percentError

    print "%s unadjusted: %.4f +/- %.4f"%(name,average,error)
    print "%s adjusted: %.4f +/- %.4f"%(name, adjustedAv,adjsutedErr)


data = np.loadtxt('../bootstrap.txt',unpack=True,skiprows=1)

printStats('EPL', data[0], 20)
print ''
printStats('SPL', data[2], 12)







