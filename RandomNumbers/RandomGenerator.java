import java.util.Random;

//mathematical function that can be evaluated at any point x
interface Function {
	public double evaluate(double x);
}


public class RandomGenerator {

	private Function f;
	private Random random;
	
	private double fmax, rangeMin, rangeMax;
	
	public RandomGenerator(Function f, double fmax, double xMin, double xMax){
		this.f = f;
		random = new Random();
		
		this.fmax = fmax;
		this.rangeMin = xMin;
		this.rangeMax = xMax;
	}
	
	//returns a random variable from the custom distribution
    public double nextDouble(){
		//variable for the next random number
		//returns NaN if process did not work correctly
		double result = Double.NaN;
	

		//dumb variables to hold y coordinates
		double randY1 = 0.0;
		double randY2 = 0.0;

		do {
			//create random x position by finding a random number between 0-1
			//and then squeeze/strech it into the range defiend above
			double randX = random.nextDouble()*(rangeMax-rangeMin) + rangeMin;

			//calculate y at the random x position
			randY1 = f.evaluate(randX);
		
			//find another random y value between 0 and fMax by generating
			//a random number between 0-1 and squeeze/stretch again
			randY2 = random.nextDouble()*fmax;

			//set result
			result = randX;
		}while(randY2 > randY1);

		return result;
    }
	
}
