import sys
import numpy as np
import pylab as plt


file = open('deviations.txt','r')
line = file.readline()

'''names of tournaments'''
names = line.split()
file.close()



data = np.loadtxt('deviations.txt',skiprows=1,unpack=True)

#plt.hist(data[0], bins=50,normed=1,color='black',label='EPL')
#plt.hist(data[1], bins=50,normed=1,color='m',label='SPL')
#plt.hist(data[3], bins=50,normed=1,color='c',label='Swiss')

for i in range(0,len(data)):
    plt.hist(data[i], bins=50,normed=1,label=names[i])

#plt.hist(data[0],bins=50, normed=1,color=['black'],label='EPL')
#plt.hist(data[1],bins=50, normed=1,color=['grey'],label='SPL')
plt.legend()
plt.ylabel('Frequency')
plt.xlabel('Tournament Deviation')
plt.show()
