import java.util.Random;

public class RandomTournament extends Tournament {
	
	Random r;
	int n;
	
	public RandomTournament(Team[] teams, int iterations){
		super(teams);
		this.n = iterations;
		r = new Random();
	}

	public void run(){
		for(int k=0;k<n;k++){
			//play enough games for everyone plays everyone once
			//though some teams might play each other more than once
			for(int i=0; i<teams.length-1;i++){
				int x = r.nextInt(teams.length);
				int y = x;//teams must be different
				do{
					y = r.nextInt(teams.length);
				}while(y == x);
				
				playGame(teams[x],teams[y]);
			}
		}
	}

}
