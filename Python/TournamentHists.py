import sys
import numpy as np
import pylab as plt


file = open('deviations.txt','r')
line = file.readline()

'''names of tournaments'''
names = line.split()
file.close()

data = np.loadtxt('deviations.txt',skiprows=1,unpack=True)

for i in range(0,len(data)):
    plt.hist(data[i], bins=50,normed=1,label=names[i])

plt.legend()
plt.ylabel('Frequency')
plt.xlabel('Tournament Deviation')
plt.show()
