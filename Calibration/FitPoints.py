import math
import numpy as np
import pylab as plt
from scipy.optimize import minimize

'''
Fits a skew normal distribution to the points histogram
so teams can be randomly sampled in simulation
'''

#skew normal distribution
class Skew:

	#calculate once 
	rt2pi = math.sqrt(2*math.pi)
	rt2 = math.sqrt(2)
	
	#alpha is the amount skewed
	def __init__(self,mean,sigma,alpha):
		self.mean = mean
		self.sigma = sigma
		self.alpha = alpha
		
	def setParams(self,params):
		self.mean = params[0]
		self.sigma = params[1]
		self.alpha = params[2]

	def evaluate(self,x):
		return (2/self.sigma) * self.gauss((x-self.mean)/self.sigma) * self.Phi(self.alpha*((x-self.mean)/self.sigma))
		
	#Gaussian component
	def gauss(self,x):
		return math.exp(-(x**2)) / self.rt2pi
		
	#Phi term
	def Phi(self,x):
		return 0.5*(1 + math.erf(x/self.rt2))


#negative log likelihood
class NLL:
	
	#data is an array of values to be evaluated by the function
	def __init__(self,function,data):
		self.f = function
		self.data = data
	
	#array of parameters given by minimiser	
	def evaluate(self,params):
		self.f.setParams(params)
		
		sum = 0
		for i in self.data:
			sum += math.log(self.f.evaluate(i))
			
		return -sum
		

'''data from file'''
data = np.loadtxt('historical.txt',unpack=True)
points = data[6]/38 #normalise to number of games
hist = plt.hist(points,normed=1,bins=40,color='blue')


'''function'''
params = [0.9,0.7,5]
skew = Skew(0.9,0.7,5)


'''log likelihood'''
nll = NLL(skew,points)
result = minimize(nll.evaluate, params, method='nelder-mead', options={'xtol': 1e-8, 'disp': True})
print result


'''plot function over histogram'''
x = np.linspace(0,3,100)
y = []
for i in x:
	y.append(1.788*skew.evaluate(i))

plt.plot(x,y,'red',linewidth=2)

plt.title('EPL Point Distribution 1995-2014')
plt.xlabel('Points per Game')
plt.ylabel('Frequency')
plt.show()









