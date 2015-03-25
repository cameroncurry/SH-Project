import java.util.Arrays;


public class Swiss extends Tournament {

	private int nGames;
	
	public Swiss(Team[] teams, int nGames) {
		super(teams);
		this.nGames = nGames;
	}
	
	/*
	 * first sort teams based on skill level (simulate seeding)
	 * first game: 1st plays last, 2nd plays next to last, ...
	 * sort teams based on performance
	 * second game: 1st plays last, 2nd plays ... (1st place may or may not be the same team)
	 */
	public void run(){
		
		Arrays.sort(teams);
		for(int j=0;j<nGames;j++){
			for(int i=0;i<teams.length/2;i+=2){
				playGame(teams[i],teams[i+1]);
			}
			sortTeams();
		}
	}

}
