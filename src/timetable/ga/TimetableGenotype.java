package timetable.ga;

import timetable.ga.lib.Util;


import org.jgap.Configuration;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.IntegerGene;

public class TimetableGenotype extends Genotype {
	
	
	private Enviroment enviroment;
	
	public TimetableGenotype(Configuration arg0, Population arg1)
			throws InvalidConfigurationException {
		super(arg0, arg1);

		enviroment = ((timetable.ga.TimetableConfiguration) arg0).getEnviroment();
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		//String vDefault = super.toString();
		String vDefault = "";
		StringBuffer str = new StringBuffer(30);
		
		if(getPopulation() != null){
			int size = getPopulation().size();
			
			for (int i = 0; i < size; i++) {
				IChromosome c = getPopulation().getChromosome(i);
				vDefault = vDefault.concat("[");
				for(int j = 0; j < c.size(); j++){
					//imprimir numero
					//vDefault= vDefault.concat(((IntegerGene)c.getGene(j)).intValue()+" ");	
					
					
					//imprimir string 
					str = new StringBuffer(30);
					Util.replaceCaracteres(30, 
							enviroment.getTeacher(((IntegerGene)c.getGene(j)).intValue()) +" // " +enviroment.getSubject(((IntegerGene)c.getGene(j)).intValue()), 
							str);
					
					vDefault= vDefault.concat(str.toString() +"\t");
					

					if(j+1 < c.size() && ((j+1)%enviroment.N_DAYS) == 0)
						vDefault = vDefault.concat("\n ");	
					
					
					if(j+1 < c.size() && ((j+1)%enviroment.SLOTS_PER_SEMESTER) == 0)
						vDefault = vDefault.concat("\n ");	
					
				}
				
				vDefault = vDefault.concat(" ]\nFITNESS cromossomo "+i+ " " + c.getFitnessValue()+"\n\n");

			}
		}
		return vDefault;
		
	}
	
}
