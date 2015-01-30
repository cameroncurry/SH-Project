import java.util.Random;

//same as Function but restricted to integer x values
interface IntegerFunction {
	public double evaluate(int x);
}

public class IntegerRandomGenerator {

	private IntegerFunction f;
	private Random random;
	
	private double fmax;
	private int rangeMin, rangeMax;
	
	public IntegerRandomGenerator(IntegerFunction f, double fmax, int xMin, int xMax){
		this.f = f;
		random = new Random();
		
		this.fmax = fmax;
		this.rangeMin = xMin;
		this.rangeMax = xMax;
	}
	
	public int nextInt(){
		int result = 0;
		
		double randY1 = 0.0;
		double randY2 = 0.0;
		
		do{
			int randX = random.nextInt(rangeMax+1)+rangeMin; //random int between min and max (both inclusive)
			
			randY1 = f.evaluate(randX);
			randY2 = random.nextDouble()*fmax;
			
			result = randX;
		}while(randY2 > randY1);
		
		return result;
	}
	
}
