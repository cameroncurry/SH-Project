
class PoissonGenerator extends IntegerRandomGenerator {

	private double lambda;
	
	public PoissonGenerator(double fmax, int xMin, int xMax,double lambda){
		super(fmax,xMin,xMax);
		this.lambda = lambda;
	}
	
	public void setLambda(double l){
		this.lambda = l;
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

class SkewNormal implements Function {
	
	private double mean, sigma, alpha;
	
	//normalisation constant for Gaussian
	private double norm = Math.sqrt(2*Math.PI);

	public SkewNormal(double mean, double sigma, double alpha){
		this.mean = mean;
		this.sigma = sigma;
		this.alpha = alpha;
	}
	
	public double evaluate(double x) {
		return 2*gauss(x)*ErrorFunction.Phi(alpha * (x-mean)/sigma);
	}
	
	//Gaussian term
	private double gauss(double x){
		return Math.exp(-1*Math.pow((x-mean)/sigma,2)) / (sigma*norm);
	}
	
}

/*
 * Error function class created by Robert Sedgewick and Kevin Wayne
 * http://introcs.cs.princeton.edu/java/21function/ErrorFunction.java.html
 */
class ErrorFunction {


    // fractional error in math formula less than 1.2 * 10 ^ -7.
    // although subject to catastrophic cancellation when z in very close to 0
    // from Chebyshev fitting formula for erf(z) from Numerical Recipes, 6.2
    public static double erf(double z) {
        double t = 1.0 / (1.0 + 0.5 * Math.abs(z));

        // use Horner's method
        double ans = 1 - t * Math.exp( -z*z   -   1.26551223 +
                                            t * ( 1.00002368 +
                                            t * ( 0.37409196 + 
                                            t * ( 0.09678418 + 
                                            t * (-0.18628806 + 
                                            t * ( 0.27886807 + 
                                            t * (-1.13520398 + 
                                            t * ( 1.48851587 + 
                                            t * (-0.82215223 + 
                                            t * ( 0.17087277))))))))));
        if (z >= 0) return  ans;
        else        return -ans;
    }

    // fractional error less than x.xx * 10 ^ -4.
    // Algorithm 26.2.17 in Abromowitz and Stegun, Handbook of Mathematical.
    public static double erf2(double z) {
        double t = 1.0 / (1.0 + 0.47047 * Math.abs(z));
        double poly = t * (0.3480242 + t * (-0.0958798 + t * (0.7478556)));
        double ans = 1.0 - poly * Math.exp(-z*z);
        if (z >= 0) return  ans;
        else        return -ans;
    }

    // cumulative normal distribution
    // See Gaussia.java for a better way to compute Phi(z)
    public static double Phi(double z) {
        return 0.5 * (1.0 + erf(z / (Math.sqrt(2.0))));
    }

}
