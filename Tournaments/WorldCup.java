/*
 * tournament that replicates the World Cup format
 * 32 teams divided into 8 groups
 * 
 * each team plays every team in their group
 * then moves onto knock out
 */
public class WorldCup extends Tournament {
	
	int groups = 8;
	int teamsPerGroup;

	public WorldCup(Team[] teams) {
		super(teams);
		teamsPerGroup = teams.length / groups;
	}
	
	public void run(){
		//round robin
		for(int k=0; k<groups; k++){
			for(int i=0;i<teamsPerGroup;i++){
				for(int j=0;j<teamsPerGroup;j++){
					int a = i+ (k*teamsPerGroup);
					int b = j+ (k*teamsPerGroup);
					if(a != b && i<j){
						playGame(teams[a],teams[b]);
					}
				}
			}
		}
		
		//knock out stage
		
		
		//rank teams in each group
		Tournament[] tournaments = new Tournament[groups];
		for(int i=0; i<groups; i++){
			Team[] group = new Team[teamsPerGroup];
			for(int j=0;j<teamsPerGroup;j++){
				group[j] = this.teams[i*teamsPerGroup+j];
			}
			tournaments[i] = new Tournament(group);
			tournaments[i].sortTeams();
		}
		
		//now each mini tournament is ranked, put teams back in sorted order
		Team[] rankedTeams = new Team[teams.length];
		for(int i=0;i<groups;i++){
			Team[] miniTournament = tournaments[i].getTeams();
			for(int j=0;j<teamsPerGroup;j++){
				rankedTeams[i*teamsPerGroup+j] = miniTournament[j];
			}
		}
		
		//team array in this tournament is now ranked by group
		super.teams = rankedTeams;
		
		
		//now play the knock out games
		Team[] roundof16 = new Team[teams.length/2]; //take top two from each group
		for(int i=0;i<teams.length/2;i+=2){
			roundof16[i] = teams[i*teamsPerGroup/2];
			roundof16[i+1] = teams[i*teamsPerGroup/2 +1];
		}
		
		//play through tournament
		knockoutTeams(roundof16);
		
		
	}
	
	//Team... must be pairs of teams to play against each other
	private void knockoutTeams(Team[] teams){
		if(teams.length == 1){
			return;
		}
		else{//play teams next to each other in array
			Team[] winners = new Team[teams.length/2];
			for(int i=0;i<teams.length/2;i++){
				int n = playWithoutTie(teams[2*i],teams[2*i+1]);
				if(n == 0){
					winners[i] = teams[2*i];
				}
				else{
					winners[i] = teams[2*i +1];
				}
			}
			
			knockoutTeams(winners);
		}
		
	}
	
	

}
