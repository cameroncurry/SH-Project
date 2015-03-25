import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.*;


public class RunTournaments {
	
	/*
	 * Callable task that calculates the average and stdev
	 * of tournament deviation for n tournaments
	 */
	public static class TournamentCallable implements Callable<Double[]> {

		public String name;
		
		Tournament t;
		int n;
		double[] results;
		
		
		public TournamentCallable(String name, Tournament t, int n){
			this.name = name;
			this.t = t;
			this.n = n;
			results = new double[n];
		}
		
		public double[] getResults(){
			return results;
		}
		
		@Override
		public Double[] call() throws Exception {
			
			double sum = 0;
			for(int i=0;i<n;i++){
				t.run();
				double dev = t.deviationOfTeams();
				results[i] = dev;
				sum += dev;
				t.reset();
			}
			double average = sum / (double)n;
			
			
			double varSum = 0;
			for(int i=0;i<n;i++){
				varSum += Math.pow(results[i]-average,2);
			}
			
			double stdev = Math.sqrt(varSum/(double)(n-1));
			return new Double[] {average,stdev};
		}
		
	}

	
	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException{	
		//create teams and tournaments
		
		Team[] eplTeams = Tournament.createSkewTeams(20,0.88658947,0.90241807,4.25338912, 0,3);
		Team[] splTeams = Tournament.createSkewTeams(12,0.88658947,0.90241807,4.25338912, 0,3);
		//Team[] swissTeams = Tournament.createSkewTeams(12,0.88658947,0.90241807,4.25338912, 0,3);
		//Team[] wcTeams = Tournament.createSkewTeams(32,0.88658947,0.90241807,4.25338912, 0,3);
		
		Tournament epl = new EPL(eplTeams);
		Tournament spl = new SPL(splTeams);
		//Tournament swiss = new Swiss(swissTeams,38);
		//Tournament worldCup = new WorldCup(wcTeams);
		
		
		//thread pool to run tournaments concurrently 
		ExecutorService pool = Executors.newFixedThreadPool(4);
		
		//add tournaments to pool
		TournamentCallable a = new TournamentCallable("EPL",epl,10000);
		Future<Double[]> eplFuture = pool.submit(a);
		
		TournamentCallable b = new TournamentCallable("SPL",spl,10000);
		Future<Double[]> splFuture = pool.submit(b);
		/*
		TournamentCallable c = new TournamentCallable("Swiss",swiss,10000);
		Future<Double[]> swissFuture = pool.submit(c);
		
		TournamentCallable d = new TournamentCallable("World-Cup",worldCup,10000);
		Future<Double[]> wcFuture = pool.submit(d);
		*/
		
		
		//print results to screen
		//System.out.printf("%.4f\t%.4f\t%.4f\t%.4f\n",eplFuture.get()[0], eplFuture.get()[1], splFuture.get()[0], splFuture.get()[1]);
		
		printResult("EPL",eplFuture,epl);
		printResult("SPL",splFuture,spl);
		//printResult("Swiss",swissFuture,swiss);
		//printResult("World Cup",wcFuture,worldCup);
		
		
		
		pool.shutdown();
		
		//plotHistogram(a,b,d,c);
		
	}
	
	public static void printResult(String name, Future<Double[]> result, Tournament t) throws InterruptedException, ExecutionException{
		double average = result.get()[0];
		double stdev = result.get()[1];
		double max = Tournament.maxDeviation(t.getTeams().length);
		double percentError = stdev/average;
		average /= max;
		stdev = average*percentError;
		System.out.printf("%s: %.4f +/- %.4f\n",name,average,stdev);
	}
	
	public static void plotHistogram(TournamentCallable... z) throws IOException, InterruptedException{
		PrintWriter p = new PrintWriter("deviations.txt");
		
		double[][] results = new double[z.length][];
		//print tournament names and setup results matrix
		for(int i=0;i<z.length;i++){
			p.printf("%s \t",z[i].name);
			results[i] = z[i].getResults();
		}
		p.println();
		
		for(int i=0;i<results[0].length;i++){
			
			for(int j=0;j<z.length;j++){
				p.printf("%.3f \t",results[j][i]);
			}
			p.println();
		}
		
		p.close();
		
		
		Runtime r = Runtime.getRuntime();
		
		Process pr = r.exec("python Python/TournamentHists.py");
		pr.waitFor();
	}
}


