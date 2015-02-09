
public class RunTournaments {
	
	public static void main(String[] args){
		
		Team[] eplTeams = Tournament.createGaussianTeams(20);
		Team[] splTeams = Tournament.createGaussianTeams(20);
		
		Tournament epl = new EPL(eplTeams);
		Tournament spl = new SPL(splTeams);
		
		for(int i=0;i<10000;i++){
			System.out.println(tournamentVariance(epl,1)+" "+tournamentVariance(spl,1));
		//System.out.println(tournamentVariance(spl,10));
		}
		
		
	}
	
	public static double tournamentVariance(Tournament t, int iterations){
		double varSum = 0;
		for(int i=0;i<iterations;i++){
			t.run();
			varSum += t.deviationOfTeams();
			t.reset();
		}
		return varSum / (double)iterations;
	}

}
