package timetable.ga.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.MutationOperator;

import timetable.ga.Enviroment;
import timetable.ga.TimetableConfiguration;
import timetable.ga.TimetableGA;
import timetable.ga.TimetableGenotype;
import timetable.ga.TimetableMutationOperator;
import timetable.ga.TimetableOXGeneticOperator;
import timetable.ga.TimetablePopulation;
import timetable.ga.lib.Util;
import timetable.ga.model.CourseOffers;
import timetable.ga.model.ShiftOffers;
import timetable.ga.model.SubjectTeacherRelation;

public class TimetableApp implements Serializable{
	public int MUTATION_RATE;
	public double CROSSOVER_RATE;
	public int POPULATION;
	public long TIME_EXECUTION = 0;
	public long GENERATIONS_EXECUTION = 0;
	public double FITNESS_EXECUTION = 0;
	//public double FITNESS_EXECUTION = 0;
	
	private List<CourseOffers> courses;
	private IEntryData entryData;
	public static ShiftOffers currentShiftCourse;
	private Enviroment enviroment;
	public TimetableApp(ITimetableParameters parameters){
		POPULATION= parameters.getPopulationSize();
		CROSSOVER_RATE = parameters.getCrossoverRate();
		MUTATION_RATE = parameters.getMutatioRate();
		enviroment = new Enviroment();
		enviroment.setParameters(parameters);
	}
	public void setEntryData(IEntryData entryData){
		courses = entryData.getCourseOffers();
		this.entryData =entryData;
		
	}
	
	public List<CourseOffers> doTimetable(){
		TIME_EXECUTION = 0;
		GENERATIONS_EXECUTION = 0;
		FITNESS_EXECUTION = 0;
		
		List<SubjectTeacherRelation> result;
		int shiftsTotalNumber = 0;

		for (int i = 0; i < courses.size(); i++) {
			long initialTime = System.currentTimeMillis();
			
			courses.get(i).orderShiftOffersByAmountDesc();
			
			for (int j = 0; j < courses.get(i).getShiftOffers().size(); j++) {
				
				currentShiftCourse = courses.get(i).getShiftOffers().get(j);
				enviroment.setEntryData(currentShiftCourse.getOffers());

				//FIXME TESTE 01System.out.println("\n\nGA course "+i+" shift "+Enviroment.CURRENT_SHIFT);
				System.out.println("#"+courses.get(i).getName());
				result = executeGA();
				currentShiftCourse.setOffers(result);
				shiftsTotalNumber++;
				
			}
			
			long finalTime = System.currentTimeMillis() - initialTime;
			
			TIME_EXECUTION += finalTime;
		}
		FITNESS_EXECUTION = FITNESS_EXECUTION/shiftsTotalNumber;
		return courses;
	}
	private List<SubjectTeacherRelation> executeGA(){

		try {
			TimetablePopulation populatioModel = new TimetablePopulation(enviroment);
			Configuration.reset();
			Configuration conf = new TimetableConfiguration(enviroment);
			conf.setPreservFittestIndividual(true);
			conf.setAlwaysCaculateFitness(true);
			System.out.println("antes "+conf.getNaturalSelectors(true).size());
			System.out.println("depois "+conf.getNaturalSelectors(false).size());
			if(MUTATION_RATE != 0 && CROSSOVER_RATE != 0){
				conf.getGeneticOperators().clear();//removing defaults genetic operators
				conf.addGeneticOperator(new TimetableOXGeneticOperator(conf,CROSSOVER_RATE));
				conf.addGeneticOperator(new TimetableMutationOperator(conf,MUTATION_RATE));
			}else{ //default
				conf.getGeneticOperators().clear();//removing defaults genetic operators
				conf.addGeneticOperator(new TimetableOXGeneticOperator(conf,0.50));
				conf.addGeneticOperator(new TimetableMutationOperator(conf,25));
			}
			conf.setSampleChromosome(populatioModel.createIndividual(conf, enviroment.N_SEMESTER*enviroment.SLOTS_PER_SEMESTER));		
			
			FitnessFunction myFunc = new TimetableGA(enviroment);
			conf.setFitnessFunction(myFunc);
			conf.setPopulationSize(POPULATION);
			
			
			Genotype population = new TimetableGenotype(conf,populatioModel.getInitialPopulation(conf, POPULATION,enviroment.N_SEMESTER*enviroment.SLOTS_PER_SEMESTER));
			
			int limitOfGenerations = 0;
			IChromosome bestSolution = null;
			double bestFitness = 0;
			
			long initialTime = System.currentTimeMillis();
			long currentTime = 0;
			
			//população padrão : 75 1/2 e 2/3 e 3/3 e o melhor
			//populacao ZERO
			String resultString = "";

			graphTest1(limitOfGenerations, population, resultString,false);
			
			while (bestFitness < 1.0) {			
				limitOfGenerations++;
				population.evolve();	
				System.out.println("evoluindo");
				bestSolution =population.getFittestChromosome();
			

				currentTime = System.currentTimeMillis() - initialTime;
				bestFitness= bestSolution.getFitnessValue();
				if(bestFitness == 1.0){	
					graphTest1(limitOfGenerations, population, resultString,true);
					break;
				}
				
				if(TimeUnit.MILLISECONDS.toMinutes(currentTime) > 5){
					graphTest1(limitOfGenerations, population, resultString,true);
					break;
				}
				
				graphTest1(limitOfGenerations, population, resultString,false);
			}
				
			graphTest1LasPop(population);
			System.out.println("tempo "+TimeUnit.MILLISECONDS.toMinutes(currentTime));
			GENERATIONS_EXECUTION +=limitOfGenerations;
			FITNESS_EXECUTION += bestFitness;
			
			//FIXME TESTE 01System.out.println("fitness"+bestFitness);
			((TimetableGA)myFunc).SET_DETAILS=true;
			bestFitness= bestSolution.getFitnessValue();
			((TimetableGA) myFunc).debugChromossome(bestSolution);
			
			List<SubjectTeacherRelation> result = new ArrayList<SubjectTeacherRelation>();
			
			int currentAllele = 0;
			int currentSemester = 0;
			int currentDay = 0;
			int currentSlot = 0;
			for(int i = 0; i < bestSolution.size(); i++){
				
				currentAllele = Util.intAllele(bestSolution.getGene(i));
				
				//add new unavailability
				currentDay = Math.abs(i%enviroment.N_DAYS) + 1;
				currentSemester = Math.abs(((i)/enviroment.SLOTS_PER_SEMESTER))+1;
				currentSlot = (int) (Math.floor(
						
						((( i/(enviroment.N_DAYS*1.0) )  /  enviroment.SLOTS_PER_DAY) - ( currentSemester - 1 )) *enviroment.SLOTS_PER_DAY  +1)); 
				
				enviroment.setUnavailable(currentAllele,currentDay,currentSlot);
				
				result.add(enviroment.GA_DATA.get(currentAllele));
			}
			return result;
			
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void graphTest1(int popIndex, Genotype currentPop, String result, boolean lastPop){
//	System.out.println(popIndex + "  "+popIndex%10.0 + "  "+(popIndex == POPULATION));
		if(lastPop || popIndex%10.0 == 0){
			List<Chromosome> listBestOnes = currentPop.getFittestChromosomes( (int)((1/3.0)*POPULATION));
			double fitnessAverage = 0;			
			for (int i = 0; i < listBestOnes.size(); i++) {
				fitnessAverage += listBestOnes.get(i).getFitnessValueDirectly();
			}
			fitnessAverage = fitnessAverage/((1/3.0)*POPULATION);
			System.out.print(popIndex+","+fitnessAverage);
			
			listBestOnes = currentPop.getFittestChromosomes( (int)((2/3.0)*POPULATION));
			fitnessAverage = 0.0;
			for (int i = 0; i < listBestOnes.size(); i++) {
				fitnessAverage += listBestOnes.get(i).getFitnessValueDirectly();
			}
			fitnessAverage = fitnessAverage/((2/3.0)*POPULATION);
			System.out.print(","+fitnessAverage);
			
			listBestOnes = currentPop.getFittestChromosomes( (int)((3/3.0)*POPULATION));
			fitnessAverage = 0.0;
			for (int i = 0; i < listBestOnes.size(); i++) {
				fitnessAverage += listBestOnes.get(i).getFitnessValueDirectly();
			}
			fitnessAverage = fitnessAverage/((3/3.0)*POPULATION);
			System.out.print(","+fitnessAverage);
		
			System.out.print(","+currentPop.getFittestChromosome().getFitnessValueDirectly());
			if(!lastPop)
				System.out.println();
		}
	}
	private void graphTest1LasPop(Genotype currentPop){
//		System.out.println(popIndex + "  "+popIndex%10.0 + "  "+(popIndex == POPULATION));
			//System.out.println();
				List<Chromosome> listBestOnes = currentPop.getFittestChromosomes(POPULATION);
				double bestFitness = 0;
				double bestFitnessCount = 1;
				double fitnessAverage = 0;
				int currentIndex = 0;
				String averageS = "";
				
				bestFitness = listBestOnes.get(currentIndex).getFitnessValueDirectly();
				
				while(currentIndex < 75){
					
					
					
					//System.out.print(listBestOnes.get(currentIndex).getFitnessValueDirectly()+((currentIndex != 75)?",":""));
					
				
					fitnessAverage += listBestOnes.get(currentIndex).getFitnessValueDirectly();
					currentIndex++;
					if(currentIndex < 75 && bestFitness == listBestOnes.get(currentIndex).getFitnessValueDirectly())
						bestFitnessCount++;
					if(currentIndex > 0 && currentIndex%5 == 0){
						//System.out.print((fitnessAverage/5.0)+((currentIndex != 75)?",":""));
						averageS += (fitnessAverage/5.0)+((currentIndex != 75)?",":"");
						fitnessAverage = 0;
					}
				}
				System.out.println(","+bestFitnessCount);
				System.out.println("*"+averageS);
		}
}
