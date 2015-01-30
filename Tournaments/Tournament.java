import java.util.Arrays;

/*
 * generic all-play-all tournament with Gaussian teams
 * 3 points for a win, 1 for a tie
 * ranking is points first then goal difference then goals for
 */

public class Tournament implements Runnable {

	public static void main(String[] args){
		Tournament t = new Tournament(10);
		
		t.run();
		t.sortTeams();
		t.print();
			
	}
	
	Team[] teams;
	
	
	public Tournament(int nTeams){
		this.teams = Tournament.createGaussianTeams(nTeams);
	}
	
	//play teams against each other
	public void run(){
		for(int i=0;i<teams.length;i++){
			for(int j=0;j<teams.length;j++){
				if(i != j && i<j){
					playGame(teams[i],teams[j]);
				}
			}
		}
	}
	
	public void sortTeams(){
		Arrays.sort(teams);
	}
	
	protected void playGame(Team a, Team b){
		int goalsA = a.playOpponent(b);
		int goalsB = b.playOpponent(a);
		
		//tie
		if(goalsA == goalsB){
			a.incrementStats(1, goalsA, goalsB);
			b.incrementStats(1, goalsB, goalsA);
		}
		else if(goalsA > goalsB){ //a wins
			a.incrementStats(3, goalsA, goalsB);
			b.incrementStats(0, goalsB, goalsA);
		}
		else{ //b wins
			a.incrementStats(0, goalsA, goalsB);
			b.incrementStats(3, goalsB, goalsA);
		}
	}
	
	public void print(){
		for(int i=0; i<teams.length; i++){
			System.out.println(teams[i]);
		}
	}
	
	public static Team[] createGaussianTeams(int n){
		Function gauss = new Gaussian(2,1);
		RandomGenerator gen = new RandomGenerator(gauss, 1.1,0,10);
		Team[] teams = new Team[n];
		
		for(int i=0; i<n; i++){
			teams[i] = new Team(gen.nextDouble());
		}
		
		return teams;
	}

}
