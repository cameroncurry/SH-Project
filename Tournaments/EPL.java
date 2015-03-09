
public class EPL extends Tournament {

	public EPL(Team[] teams) {
		super(teams); //20 teams in the EPL
	}
	
	//each team plays every other team twice
	public void run(){
		
		for(int i=0; i<teams.length;i++){
			for(int j=0;j<teams.length;j++){
				if(i != j){//team doesn't play itself but plays others twice
					playGame(teams[i],teams[j]);
				}
			}
		}
		
	}

}
