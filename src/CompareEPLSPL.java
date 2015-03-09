import java.io.IOException;
import java.io.PrintWriter;


public class CompareEPLSPL {
	
	public static void main(String[] args) throws IOException, InterruptedException{
		
		Team[] eplTeams = Tournament.createSkewTeams(20,0.88658947,0.90241807,4.25338912, 0,3);
		Team[] splTeams = Tournament.createSkewTeams(20,0.88658947,0.90241807,4.25338912, 0,3);
		
		Tournament epl = new EPL(eplTeams);
		Tournament spl = new SPL(splTeams);
		
		
		PrintWriter p = new PrintWriter("deviations.txt");
		
		for(int i=0;i<5000;i++){
			//p.printf("%.5f\n", averageTournamentDeviation(epl,1));
			p.printf("%.5f %.5f\n",averageTournamentDeviation(epl,1),averageTournamentDeviation(spl,1));
		}
		
		p.close();
		
		Runtime r = Runtime.getRuntime();
		Process pr = r.exec("python DeviationHist.py");
		pr.waitFor();
	}
	
	public static double averageTournamentDeviation(Tournament t, int iterations){
		double varSum = 0;
		for(int i=0;i<iterations;i++){
			t.run();
			varSum += t.deviationOfTeams();
			t.reset();
		}
		return varSum / (double)iterations;
	}

}
