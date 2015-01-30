
class Poisson implements IntegerFunction {

	private double lambda;
	
	public Poisson(double lambda){
		this.lambda = lambda;
	}
	
	public double evaluate(int x) {
		return (Math.pow(lambda, x) * Math.exp(-1*lambda))/ (double)factorial(x);
	}
	
	private int factorial(int x){
		if(x == 0 || x == 1){
			return 1;
		}
		else{
			return x*factorial(x-1);
		}
	}

}

class Gaussian implements Function {

	private double mean;
	private double sigma;
	
	//root 2 pi is constant, recalculating is inefficient
	private double root2Pi = Math.sqrt(2*Math.PI);
	
	public Gaussian(double mean, double sigma){
		this.mean = mean;
		this.sigma = sigma;
	}
	
	
	public double evaluate(double x) {
		return Math.exp(-0.5 * ((x-mean)/sigma)*((x-mean)/sigma)) / (sigma*root2Pi);
	}
	
}
