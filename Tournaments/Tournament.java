import java.util.Arrays;
import java.util.Comparator;

/*
 * generic all-play-all tournament with Gaussian teams
 * 3 points for a win, 1 for a tie
 * ranking is points first then goal difference then goals for
 */

public class Tournament implements Runnable {
	
	
	protected Team[] teams;
	//only have 1 instance of a random number generator to avoid random number bias between multiple instances of Random
	protected PoissonGenerator poisson;
	
	
	//parameters from calibration
	double meanX = 1.248;
	double meanY = 1.36-1.25;
	//double meanY = 1.36;
	//double theta = -27.66 * Math.PI/180.;
	double theta = -60.0 * Math.PI/180.;
	double sinTheta = Math.sin(theta);
	double cosTheta = Math.cos(theta);
	
	public Tournament(Team[] teams){
		this.teams = teams;
		this.poisson = new PoissonGenerator(1,0,10,1);
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
		Arrays.sort(teams,tournamentRanking);
	}
	
	public void reset(){ //resets all stats of tournament so it can be run again with same teams
		for(int i=0; i<teams.length;i++){
			teams[i].resetStats();
		}
	}
	
	
	public void playGame(Team a, Team b){
		
		int goalsA = 0;
		int goalsB = 0;
		
		if(a.skill() > b.skill()){
			//calculate relative skill vs. opponent
			double relativeSkillA = (a.skill()-meanX)*cosTheta - (b.skill()-meanY)*sinTheta;
			double relativeSkillB = (a.skill()-meanX)*sinTheta + (b.skill()-meanY)*cosTheta;
		
			poisson.setLambda(relativeSkillA+meanX);
			goalsA = poisson.nextInt();
			poisson.setLambda(relativeSkillB+meanY);
			goalsB = poisson.nextInt();
		}
		else {
			double relativeSkillB = (b.skill()-meanX)*cosTheta - (a.skill()-meanY)*sinTheta;
			double relativeSkillA = (b.skill()-meanX)*sinTheta + (a.skill()-meanY)*cosTheta;
		
			poisson.setLambda(relativeSkillA+meanX);
			goalsA = poisson.nextInt();
			poisson.setLambda(relativeSkillB+meanY);
			goalsB = poisson.nextInt();
		}
		
		//int goalsA = a.playOpponent(poisson, b);
		//int goalsB = b.playOpponent(poisson, a);
		
		
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
	
	public double deviationOfTeams(){
		//make copy of team array
		Team[] teams = Arrays.copyOf(this.teams,this.teams.length);
		Arrays.sort(teams); // sort teams in order of skill
		this.sortTeams(); // sort teams in tournament rankings
		
		double sum = 0;
		
		for(int i=0; i<teams.length; i++){
			for(int j=0; j<teams.length; j++){
				if(i<=j){
					//find the same team in both arrays and compare rankings i & j
					if(teams[i].compareTo(this.teams[j]) == 0){ 
						sum += (i-j)*(i-j); //difference in ranking squared
					}
				}
			}
		}
		
		return Math.sqrt(sum / (double)teams.length);
		
	}
	
	
	public void print(){
		for(int i=0; i<teams.length; i++){
			System.out.println(teams[i]);
		}
	}
	
	public static Team[] createGaussianTeams(int n, double mean, double sigma, double xmin, double xmax){
		Function gauss = new Gaussian(mean,sigma);
		RandomGenerator gen = new RandomGenerator(gauss, 1.1,xmin,xmax);
		Team[] teams = new Team[n];
		
		for(int i=0; i<n; i++){
			teams[i] = new Team(gen.nextDouble());
		}
		
		return teams;
	}
	
	public static Team[] createSkewTeams(int n, double mean, double sigma, double alpha, double xMin, double xMax){
		Function skew = new SkewNormal(mean,sigma,alpha);
		RandomGenerator gen = new RandomGenerator(skew,1.4,xMin,xMax);
		Team[] teams = new Team[n];
		
		for(int i=0;i<n;i++){
			teams[i] = new Team(gen.nextDouble());
		}
		return teams;
	}
	
	//compares teams based on performance in tournament
	public static Comparator<Team> tournamentRanking = new Comparator<Team>(){

		public int compare(Team o1, Team o2) {
			if(o1.points() > o2.points()){
				return -1;
			}
			else if(o1.points() < o2.points()){
				return +1;
			}
			else { // points are equal
				
				if(o1.goalDifference() > o2.goalDifference()){
					return -1;
				}
				else if(o1.goalDifference() < o2.goalDifference()){
					return +1;
				}
				else{ // goals difference is equal
					
					if(o1.goalsFor() > o2.goalsFor()){
						return -1;
					}
					else if(o1.goalsFor() < o2.goalsFor()){
						return +1;
					}
					else{
						return 0; // teams are equal
					}
				}
			}
		}
		
	};

}


