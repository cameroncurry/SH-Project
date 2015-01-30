
public class Team implements Comparable<Team> {

	private double skill;
	private IntegerRandomGenerator poisson;
	
	private int points;
	private int goalsFor;
	private int goalsAgainst;
	
	public Team(double skill) {
		this.skill = skill;
		this.poisson = new IntegerRandomGenerator(new Poisson(skill), 1, 0, (int)skill*2);
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
	public int playOpponent(Team opponent){
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
	

	//comparable
	public int compareTo(Team o) {
		if(points > o.points()){
			return -1;
		}
		else if(points < o.points()){
			return +1;
		}
		else { // points are equal
			
			if(goalDifference() > o.goalDifference()){
				return -1;
			}
			else if(goalDifference() < o.goalDifference()){
				return +1;
			}
			else{ // goals difference is equal
				
				if(goalsFor > o.goalsFor()){
					return -1;
				}
				else if(goalsFor < o.goalsFor()){
					return +1;
				}
				else{
					return 0; // teams are equal
				}
			}
		}
	}

}
