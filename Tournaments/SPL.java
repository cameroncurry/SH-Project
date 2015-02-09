
public class SPL extends Tournament {

	public SPL(Team[] teams) {
		super(teams);
	}
	
	//each team plays every other team 3 times
	//the league is then split into top 6 and bottom 6
	//then each team plays other teams once in their section
	
	//I am ignoring that the SPL predicts where a club will place
	//so that games can be scheduled for the whole season at once
	//and that an equal number of home and away games are played
	
	public void run(){
		
		for(int k=0;k<3;k++){ //repeat 3 times
			//play everyone once
			for(int i=0;i<teams.length;i++){
				for(int j=0;j<teams.length;j++){
					if(i != j && i<j){
						playGame(teams[i],teams[j]);
					}
				}
			}
		}
	
		//sort teams in tournament ranking
		super.sortTeams();
		
		//play everyone once in top and bottom half
		for(int i=0;i<teams.length;i++){
			for(int j=0;j<teams.length;j++){
				if(i != j && i<j){
					if(i<teams.length/2 && j<teams.length/2){
						playGame(teams[i],teams[j]);
					}
					else if(i>=teams.length/2 && j>=teams.length/2){
						playGame(teams[i],teams[j]);
					}
				}
			}
		}
		
		
	}

}
