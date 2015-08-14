import numpy as np
import pylab as pl
import math
from scipy.optimize import minimize

'''
Fits a line trough goals-for/goals-against scatter chart for both historical and simulated results
'''

# x = array of x values from given data
# y = array of y values from given data
# function is the function attempting to fit
# function must implements evaluate(x) 
class LeastSquares:

	def __init__(self,x,y,function):
		self.x = x
		self.y = y
		self.function = function
	
	#return the sum of squares
	def evaluate(self,parameters):
		self.function.setParams(parameters)
		sum = 0;
		for i in range(0,len(self.x)): #loop 0,1,2...,length of x
			sum += math.pow(self.function.evaluate(self.x[i])-self.y[i],2)
			
		return sum
		
		
class Linear:

	def __init__(self,m,b):
		self.m = m
		self.b = b
		
	#params = array of parameter
	#in this case {m,b}
	def setParams(self,params):
		self.m = params[0]
		self.b = params[1]
		
	def evaluate(self,x):
		return self.m*x + self.b;



historical = np.loadtxt('../Calibration/historical.txt',unpack=True)
simulated = np.loadtxt('../calibration.txt',unpack=True)


'''historical data'''
hist_params = [-1.0,2.0]
hist_linear = Linear(-1.0,2.0)
hist_lsq = LeastSquares(historical[3]/38,historical[4]/38,hist_linear)

hist_result = minimize(hist_lsq.evaluate, hist_params, method='nelder-mead', options={'xtol': 1e-8, 'disp': True} )
print hist_result

'''simulated data'''
sim_params = [0.0,2.0]
sim_linear = Linear(0.0,5.0)
sim_lsq = LeastSquares(simulated[2]/38,simulated[3]/38,sim_linear)

sim_result = minimize(sim_lsq.evaluate, sim_params, method='nelder-mead', options={'xtol': 1e-8, 'disp': True} )
print sim_result



'''plot results'''
x = np.linspace(0,3,100)
hist_y = []
sim_y = []

for i in x:
	hist_y.append(hist_linear.evaluate(i))
	sim_y.append(sim_linear.evaluate(i))

pl.plot(simulated[2]/38,simulated[3]/38,'x',label='simulated',color='g')
pl.plot(historical[3]/38,historical[4]/38,'o',label='historical',color='b')

pl.plot(x,hist_y,'black',linewidth=2)
pl.plot(x,sim_y,'black',linewidth=2)

pl.title('Simulated vs. Historical Goal Distribution')
pl.xlabel('Goals For')
pl.ylabel('Goals Against')
pl.legend()
pl.show()







