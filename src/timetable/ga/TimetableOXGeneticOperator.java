package timetable.ga;


import java.util.ArrayList;

import javax.persistence.criteria.CriteriaBuilder.In;

import timetable.ga.lib.Util;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.impl.CrossoverOperator;

public class TimetableOXGeneticOperator extends CrossoverOperator {

	private Enviroment enviroment;
	
	public TimetableOXGeneticOperator(Configuration a_configuration)
			throws InvalidConfigurationException {
		super(a_configuration);
		enviroment = ((timetable.ga.TimetableConfiguration) a_configuration).getEnviroment();
	}
	public TimetableOXGeneticOperator(Configuration a_configuration,int a_desiredCrossoverRate)
			throws InvalidConfigurationException {
		super(a_configuration,a_desiredCrossoverRate);
		enviroment = ((timetable.ga.TimetableConfiguration) a_configuration).getEnviroment();
	}

	public TimetableOXGeneticOperator(Configuration a_configuration,double a_desiredCrossoverRate)
			throws InvalidConfigurationException {
		super(a_configuration,a_desiredCrossoverRate);
		enviroment = ((timetable.ga.TimetableConfiguration) a_configuration).getEnviroment();
	}
	@Override
	protected void doCrossover(IChromosome c1, IChromosome c2, java.util.List arg2,
			RandomGenerator random) {
		
		int nGenes = c1.size();
		Gene[] c1Genes = c1.getGenes();
		Gene[] c2Genes = c2.getGenes();
				
		java.util.List<Integer> firstGenes = new java.util.ArrayList<Integer>();
		java.util.List<Integer> secondGenes = new java.util.ArrayList<Integer>();
		
		int initialLocus, finalLocus, point1, point2, currGene,aux;
		
		for (int i = 0; i < enviroment.N_SEMESTER; i++) {
			
			initialLocus = i * enviroment.SLOTS_PER_SEMESTER;
			finalLocus = initialLocus + (enviroment.SLOTS_PER_SEMESTER - 1);
		
			point1 = getRandom(random, initialLocus+1, finalLocus-1);
			point2 = getRandom(random,initialLocus+1, finalLocus-1);
			while(point2 == point1) point2 = getRandom(random,initialLocus+1, finalLocus-1);
			if(point1 > point2){aux = point1; point1 = point2; point2 = aux;}	
			
			//makes a copy of the c1, c2 genes into a list.
			currGene = initialLocus;
			while(currGene <= finalLocus){
				firstGenes.add(Util.intAllele(c1Genes[currGene]));
				secondGenes.add(Util.intAllele(c2Genes[currGene]));
				currGene++;
			}
			
			//removes from c1 genes those ones that are in [initialLocus,finalLocus] (c2 genes) --vice-verse
			//adds in c1 right locus the allele c2 at locus currGene.
			currGene = point1;
			while (currGene <= point2){
				firstGenes.remove(Integer.valueOf(Util.intAllele(c2Genes[currGene])));
				firstGenes.add(currGene, Util.intAllele(c2Genes[currGene]));
				
				secondGenes.remove(Integer.valueOf(Util.intAllele(c1Genes[currGene])));
				secondGenes.add(currGene, Util.intAllele(c1Genes[currGene]));
				currGene++;
			}
			
			//update the original c1 and c2 with the new sequence of genes alleles
			currGene = initialLocus;
			while(currGene <= finalLocus){
				c1Genes[currGene].setAllele(firstGenes.get(currGene));
				c2Genes[currGene].setAllele(secondGenes.get(currGene));
				currGene++;
			}
		
		}
		arg2.add(c1);
		arg2.add(c2);
	}


	private int getRandom(RandomGenerator random, int localInitialRange, int localFinalRange){
		int v = random.nextInt(localFinalRange+1);
		while( ( v >= localInitialRange && v <= localFinalRange) == false){
			v = random.nextInt(localFinalRange+1);
		}
		
		return v;
	}
}
