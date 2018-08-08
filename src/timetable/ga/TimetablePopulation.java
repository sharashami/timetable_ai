package timetable.ga;



import java.util.Random;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.IntegerGene;

import timetable.ga.lib.Util;

public class TimetablePopulation{
	
	private static Random random;
	private static java.util.List<Integer> numbersAlreadyUsed;
	private Enviroment enviroment ;
	public TimetablePopulation(Enviroment enviroment) {
		random = new Random();
		numbersAlreadyUsed = new java.util.ArrayList<Integer>();
		this.enviroment = enviroment;
	}

	/**Creates a individual of number of genes size and gene's values between (rInitial, rFinal)
	 * Obeys the timetable structure
	 * 
	 * @param nGenes - number of genes
	 * @return IChromossome
	 * @throws InvalidConfigurationException 
	 */
	public IChromosome createIndividual(Configuration conf, int nGenes) throws InvalidConfigurationException{
		Gene[] genes = new IntegerGene[nGenes];
		int j = 0;
		int localInitialRange = 0;
		int localFinalRange = 0;
		
		
		//0 a 1
		for(int i = 0; i < enviroment.N_SEMESTER; i++){
			
			//j = 0x2 = 0
			//j = 1x2 = 2
			j = i*enviroment.SLOTS_PER_SEMESTER;
			localInitialRange = j+1;
			localFinalRange = (localInitialRange + enviroment.SLOTS_PER_SEMESTER) - 1;
			
			
			//alleles [1...10][11...20][21...30]...
			//locus [0...9][10...19][20...29]...
			
			//0 a 9
			//0 a 9
			numbersAlreadyUsed.clear();
			for( ; j < i*enviroment.SLOTS_PER_SEMESTER +  enviroment.SLOTS_PER_SEMESTER; j ++){
				genes[j] = new IntegerGene(conf, localInitialRange, localFinalRange);
				genes[j].setAllele(getNextValidRandom(localInitialRange, localFinalRange));
				
				
			}
		}

		Chromosome individual = new Chromosome(conf, genes);
		return individual;
		
	}
	
	/**Returns a valid random number that is in (localInitialRange,localFinalRange) AND was not used before.  
	 * @param localInitialRange
	 * @param localFinalRange
	 * @return
	 */
	private int getNextValidRandom(int localInitialRange, int localFinalRange){
		int v = random.nextInt(localFinalRange+1);
		while(    ( v >= localInitialRange && v <= localFinalRange) == false || numbersAlreadyUsed.indexOf(v) > -1){
			v = random.nextInt(localFinalRange+1);
		}
		
		numbersAlreadyUsed.add(v);
		return v;
	}
	/**Initializes the population considering the timetable structure 
	 * alleles [1...8][9...16][17...24]
	 * @param popSize population size
	 * @return
	 * @throws InvalidConfigurationException 
	 */
	public Population getInitialPopulation(Configuration conf, int popSize, int nGenes) throws InvalidConfigurationException{
		
		
		IChromosome[] individuals = new Chromosome[popSize];
		for (int i = 0; i < popSize; i++) {
			individuals[i]= createIndividual(conf, nGenes);
		}
		
		return new Population(conf, individuals)
		;
	}

}
