package timetable.ga;

import java.util.List;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.RandomGenerator;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.SwappingMutationOperator;

public class TimetableMutationOperator extends SwappingMutationOperator {


	private Enviroment enviroment;
	public TimetableMutationOperator(Configuration a_config) throws InvalidConfigurationException {
		super(a_config);

		enviroment = ((timetable.ga.TimetableConfiguration) a_config).getEnviroment();
	}

	public TimetableMutationOperator(Configuration a_config,
			int a_desiredMutationRate) throws InvalidConfigurationException {
		super(a_config, a_desiredMutationRate);
		enviroment = ((timetable.ga.TimetableConfiguration) a_config).getEnviroment();
	}
	
	@Override
	protected Gene[] operate(RandomGenerator a_generator, int a_target_gene,
			Gene[] a_genes) {
		  // swap this gene with the other one now:
		   	    //  mutateGene(genes[j], generator);
		 	  	    // -------------------------------------
					int currentSemester = Math.abs(((a_target_gene)/enviroment.SLOTS_PER_SEMESTER))+1;
					int semesterSlotLimit = (currentSemester + enviroment.SLOTS_PER_SEMESTER) -1 -1;
		 	 	    
					//int other = getStartOffset()        + a_generator.nextInt(a_genes.length - getStartOffset());
					
					int other = (currentSemester-1)*enviroment.SLOTS_PER_SEMESTER + a_generator.nextInt(enviroment.SLOTS_PER_SEMESTER - 1);
		 	 	    Gene t = a_genes[a_target_gene];
		 	 	    a_genes[a_target_gene] = a_genes[other];
		 	 	    a_genes[other] = t;
		 	 	    return a_genes;
	}
}
