import java.util.Random;


abstract class IntegerRandomGenerator {

	
	private Random random;
	
	private double fmax;
	private int rangeMin, rangeMax;
	
	public IntegerRandomGenerator(double fmax, int xMin, int xMax){	
		random = new Random();
		
		this.fmax = fmax;
		this.rangeMin = xMin;
		this.rangeMax = xMax;
	}
	
	abstract double evaluate(int x);
	
	public int nextInt(){
		int result = 0;
		
		double randY1 = 0.0;
		double randY2 = 0.0;
		
		do{
			int randX = random.nextInt(rangeMax+1)+rangeMin; //random int between min and max (both inclusive)
			
			randY1 = this.evaluate(randX);
			randY2 = random.nextDouble()*fmax;
			
			result = randX;
		}while(randY2 > randY1);
		
		return result;
	}
	
}
