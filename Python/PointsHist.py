import numpy as np
import pylab as pl

'''plots the point distribution of simulated data and historical data'''


'''read data from files'''
historical = np.loadtxt('Calibration/historical.txt',unpack=True)
simulated = np.loadtxt('calibration.txt',unpack=True)

'''plot point distribution'''
array = []
array.append(historical[6]/38)
array.append(simulated[1]/38)
	
pl.hist(array,bins=40,normed=1,color=['black','grey'],label=['historical','simulated'])
pl.legend()
pl.title('Simulated vs. Historical Point Distributions')
pl.xlabel('Points per Game')
pl.ylabel('Frequency')
pl.show()



	
	







