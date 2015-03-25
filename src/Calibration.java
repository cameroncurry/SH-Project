import java.io.IOException;
import java.io.PrintWriter;


public class Calibration {

	public static void main(String[] args) throws IOException, InterruptedException{
		
		
		PrintWriter p = new PrintWriter("calibration.txt");
		
		for(int j=0; j<100; j++){
			Team[] teams = Tournament.createSkewTeams(20,0.88658947,0.90241807,4.25338912, 0,3);
			//Team[] teams = Tournament.createGaussianTeams(20, 2, 0.5, 0, 10);
			Tournament epl = new EPL(teams);
		
			epl.run();
		
		
			for(int i=0;i<teams.length;i++){
				p.println(teams[i]);
			}
		
		}
		
		p.close();
	}

}
