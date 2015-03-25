import java.util.Random;

/*
 * tournament that represents the major league soccer (american) league
 * 
 * 20 teams split into 2 conferences
 * each team plays everyone in the other conference once
 * each team plays everyone in their own conference twice
 * and then 6 random games between opponents in their own conference
 */


public class MLS extends Tournament {
	
	Random r;

	//I consider the two half of the team array to be the different conferences
	//must have an even number of teams
	public MLS(Team[] teams) {
		super(teams);
		r = new Random();
	}
	
	public void run(){
		//play everyone in your conference twice (top half and bottom half)
		for(int i=0; i<teams.length;i++){
			for(int j=0; j<teams.length; j++){
				if(i != j){
					
					if(i<teams.length/2 && j<teams.length/2){
						playGame(teams[i],teams[j]);
					}
					else if(i>=teams.length/2 && j>=teams.length/2){
						playGame(teams[i],teams[j]);
					}
					
				}
			}
		}
		
		//play everyone in other conference once
		for(int i=0;i<teams.length;i++){
			for(int j=0;j<teams.length;j++){
				if(i<teams.length/2 && j>=teams.length/2){
					playGame(teams[i],teams[j]);
				}
			}
		}
		
		//TODO
		//play 6 games in own conference against random teams
		for(int i=0; i<teams.length; i++){
			for(int j=0; j<6;j++){
				//first conference
				if(i < teams.length/2){
					int opponent = r.nextInt(teams.length/2);
					//playGame(teams[i],);
				}
				else { //second conference
					
				}
				
			}
		}
	}

}
