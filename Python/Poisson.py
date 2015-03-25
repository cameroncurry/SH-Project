from scipy.stats import poisson
import numpy as np
import math
import pylab as plt


x = np.arange(0,21)

plt.plot(x,poisson.pmf(x,1),'black')
plt.plot(x,poisson.pmf(x,1),'o',label='$\lambda = 1$')

plt.plot(x,poisson.pmf(x,5),'black')
plt.plot(x,poisson.pmf(x,5),'s',label='$\lambda = 5$')

plt.plot(x,poisson.pmf(x,10),'black')
plt.plot(x,poisson.pmf(x,10),'d',label='$\lambda = 10$')

plt.title('$Poisson$ $Distribution$');
plt.legend()
plt.xlabel('$x$')
plt.ylabel('$P_{\lambda}(x)$')
plt.show()