
public class Team implements Comparable<Team> {

	private double skill;
	
	private int points;
	private int goalsFor;
	private int goalsAgainst;
	
	public Team(double skill) {
		this.skill = skill;
	}
	
	public double skill(){return skill;}
	public int points(){return points;}
	public int goalsFor(){return goalsFor;}
	public int goaslAgainst(){return goalsAgainst;}
	
	public int goalDifference(){
		return goalsFor - goalsAgainst;
	}
	
	public String toString(){
		return String.format("%.3f", skill)+" "+points+" "+goalsFor+" "+goalsAgainst;
	}
	
	//returns number of goals scored against opponent
	public int playOpponent(PoissonGenerator poisson, Team opponent){
		poisson.setLambda(this.skill);
		return poisson.nextInt();
	}
	
	public void incrementStats(int pointsGained, int goalsScored, int goalsConceded){
		this.points += pointsGained;
		this.goalsFor += goalsScored;
		this.goalsAgainst += goalsConceded;
	}
	
	public void resetStats(){
		this.points = 0;
		this.goalsFor = 0;
		this.goalsAgainst = 0;
	}
	

	//compare teams against their intrinsic skill
	public int compareTo(Team o) {
		if(this.skill > o.skill()){
			return -1;
		}
		else if(this.skill < o.skill()){
			return 1;
		}
		else{
			return 0;
		}
	}

}
