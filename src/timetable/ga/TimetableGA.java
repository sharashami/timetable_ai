package timetable.ga;


import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;

import timetable.ga.app.TimetableApp;
import timetable.ga.lib.Util;

/**
 * @author shara
 * @since 18/04/2014
 */
public class TimetableGA extends FitnessFunction {

	
	public boolean SET_DETAILS = false;
	private Enviroment enviroment;
	public TimetableGA(Enviroment enviroment) {
		this.enviroment = enviroment;
		
	}
	@Override
	protected double evaluate(IChromosome arg0) {
		int nGenes = arg0.size();
		//1) disciplina/professor adjacentes (4 creditos no mesmo dia)
		

		//alleles [1...10][11...20][21...30]...
		//locus [0...9][10...19][20...29]...
		if(Util.possueRepeticao(arg0)) return 0.0;
		
		int currentAllele = 0;
		int currentLocus = 0;
		int targetAllele = 0;
		int targetLocus = 0;
		int teachersUnavailbilities = 0;
		int teachersMatches = 0;
		int adjacentSubjects = 0;
		int currentSemester = 0;
		int currentPeriod = 0;
		int currentDay = 0;
		int currentSlot = 0;
		
		boolean sameTeacher = false;
		boolean adjacentSubject = false;
		int isTeacherUnavailable = 0;
		int[][][][] teacherUnavailability = new int[enviroment.N_TEACHERS+1][enviroment.N_DAYS+1][enviroment.N_SHIFT+1][enviroment.SLOTS_PER_DAY+1];

		if(SET_DETAILS){
			debugChromossome(arg0);			
		}
		for (int i = 0; i < nGenes; i++) {
			
			currentLocus = i;
			currentAllele = Util.intAllele(arg0.getGene(i));

			if(SET_DETAILS){
				if(i == 30);
			}
			//indisponibilidade
			//FIXME E e quando for mais de um professor??
			currentDay = Math.abs(i%enviroment.N_DAYS) + 1;
			currentSemester =  Math.abs(((i)/enviroment.SLOTS_PER_SEMESTER))+1;
			
			
			currentSlot = (int) (Math.floor(((( i/(enviroment.N_DAYS*1.0) )  /  enviroment.SLOTS_PER_DAY) - ( currentSemester - 1 )) *enviroment.SLOTS_PER_DAY  +1)); 
			isTeacherUnavailable =enviroment.isUnvavailable(currentAllele,currentDay,currentSlot);
			teacherUnavailability[enviroment.getTeacherOrderN(currentAllele)][currentDay][enviroment.CURRENT_SHIFT][currentSlot] += isTeacherUnavailable; 

			if(SET_DETAILS && isTeacherUnavailable > 0){
				System.out.println("semestre "+currentSemester+" D "+ currentDay+" S "+currentSlot);
				enviroment.GA_DATA.get(currentAllele).setTeacherUnavailable(isTeacherUnavailable > 0);
			}
			//chocar professor
			//levantamentos importantes : considerar a quantidade de choques (qntos semestres chocou?)
			//verificar se no mesmo período nos próximos semestres houve choque mesmo professor
			for (int j = 1; j < nGenes/enviroment.SLOTS_PER_SEMESTER; j++) {
				targetLocus = currentLocus + (enviroment.SLOTS_PER_SEMESTER * j);
				
				if(targetLocus >= nGenes)
					break;
				
				targetAllele = Util.intAllele(arg0.getGene(targetLocus));
				
				sameTeacher =enviroment.isSameTeacher(currentAllele, targetAllele) ; 
				
				if(sameTeacher ){
					teachersMatches++;
					if(SET_DETAILS){
						System.out.println("choque semestre "+currentSemester+" D "+ currentDay+" S "+currentSlot);
					
						enviroment.GA_DATA.get(currentAllele).setTeacherMarches(sameTeacher);
						enviroment.GA_DATA.get(targetAllele).setTeacherMarches(sameTeacher);
					}
				}
				
			}
						
			//mesmo par disciplina/professor no mesmo dia
			//levantamentos importantes : isto invalida? ou é ponderado?
			//verificar se na próxima linha é o mesmo professor/disciplina
			targetLocus = currentLocus + enviroment.N_DAYS;
			if(targetLocus < nGenes){
				targetAllele = Util.intAllele(arg0.getGene(targetLocus));
				adjacentSubject = enviroment.isSamePair(currentAllele, targetAllele);
				if( adjacentSubject ){
					adjacentSubjects++;
					if(SET_DETAILS){
						System.out.println("geminada"+currentSemester+" D "+ currentDay+" S "+currentSlot);
						
						enviroment.GA_DATA.get(currentAllele).setAdjacentSubject(adjacentSubject);
						enviroment.GA_DATA.get(targetAllele).setAdjacentSubject(adjacentSubject);
					}
				}
			}
				
		}
		for (int i = 0; i <= enviroment.N_TEACHERS; i++) {
			for (int k = 1; k <= enviroment.N_DAYS; k++) {
					for (int k2 = 1; k2 <= enviroment.SLOTS_PER_DAY; k2++) {
						teachersUnavailbilities += teacherUnavailability[i][k][enviroment.CURRENT_SHIFT][k2];
					}
			}
		}
	
		//40.. group bby de professor e semestres..... /
		//before 16/09 : (nGenes - 0.7*teachersMatches - 0.3*adjacentSubjects)
		/*System.out.println("DEBUG: teacher unavailable: "+teachersUnavailbilities + " worst case "+enviroment.WORST_TEACHERS_UNAVAILABILITIES);
		System.out.println("DEBUG: teacher matches : "+teachersMatches + " adjacent subject:  "+adjacentSubjects+ " sum: "+(teachersMatches + adjacentSubjects));
		System.out.println("DEBUG: worst teacher matches : "+enviroment.WORST_TEACHERS_MATCHES + " worst adjacent subject:  "+enviroment.WORST_ADJACENT_SUBJECTS);
		System.out.println("DEBUG: sum worst case : "+((enviroment.WORST_TEACHERS_MATCHES + enviroment.WORST_ADJACENT_SUBJECTS)*1.0));
		*/
		double totalWorstCase = (enviroment.WORST_TEACHERS_MATCHES + enviroment.WORST_ADJACENT_SUBJECTS + enviroment.WORST_TEACHERS_UNAVAILABILITIES)*1.0;
		double totalCurrency = (teachersMatches + adjacentSubjects + teachersUnavailbilities)*1.0;
		double fitness = 1.0 - ( totalCurrency/totalWorstCase );
		StringBuilder debug = new StringBuilder(); 
		debug.append("DEBUG: teacher unavailable: "+teachersUnavailbilities + " worst case "+enviroment.WORST_TEACHERS_UNAVAILABILITIES);
		debug.append("\nDEBUG: teacher matches : "+teachersMatches +" worst case "+enviroment.WORST_TEACHERS_MATCHES);
		debug.append("\nDEBUG: adjacent subjects : "+adjacentSubjects+ " worst case "+enviroment.WORST_ADJACENT_SUBJECTS);
		debug.append("\nDEBUG: sum \t\tTU + TM + AS : "+teachersUnavailbilities+" + "+teachersMatches+" + "+adjacentSubjects+" = "+(teachersMatches+teachersUnavailbilities+adjacentSubjects)*1.0);
		debug.append("\nDEBUG: sum worst case TU + TM + AS : "+enviroment.WORST_TEACHERS_UNAVAILABILITIES +" + "+enviroment.WORST_TEACHERS_MATCHES +" + "+enviroment.WORST_ADJACENT_SUBJECTS+" = "+(((enviroment.WORST_TEACHERS_MATCHES + enviroment.WORST_ADJACENT_SUBJECTS + enviroment.WORST_TEACHERS_UNAVAILABILITIES)*1.0)));
		debug.append("\nDEBUG: fitness 1 - (sum / sum worst case) : "+fitness);	
		
		
		if(SET_DETAILS){
			System.out.println(debug.toString());
			TimetableApp.currentShiftCourse.getGaDetails().setAdjacentSubjects(adjacentSubjects);
			TimetableApp.currentShiftCourse.getGaDetails().setAdjacentSubjectsTotal(enviroment.WORST_ADJACENT_SUBJECTS);
			TimetableApp.currentShiftCourse.getGaDetails().setTeachersMatches(teachersMatches);
			TimetableApp.currentShiftCourse.getGaDetails().setTeachersMatchesTotal(enviroment.WORST_TEACHERS_MATCHES);
			TimetableApp.currentShiftCourse.getGaDetails().setTeachersUnavailbilities(teachersUnavailbilities);
			TimetableApp.currentShiftCourse.getGaDetails().setTeachersUnavailbilitiesTotal(enviroment.WORST_TEACHERS_UNAVAILABILITIES);
			TimetableApp.currentShiftCourse.getGaDetails().setFitness(fitness);
			SET_DETAILS=false;
		}
		if(enviroment.DEBUG){
			debugChromossome(arg0);			
		}
		
		//
		
		return fitness;
	}
	
	public void debugChromossome (IChromosome arg0){
		if(1==1)
		return ;
		
		int nGenes = arg0.size();
		String vDefault = "";
		vDefault = vDefault.concat("[");
		StringBuffer str = new StringBuffer(30);
		

		int currentAllele = 0;
		int currentLocus = 0;
		int targetAllele = 0;
		int targetLocus = 0;
		int teachersUnavailbilities = 0;
		int teachersMatches = 0;
		int adjacentSubjects = 0;
		int currentSemester = 0;
		int currentPeriod = 0;
		int currentDay = 0;
		int currentSlot = 0;
		
		boolean sameTeacher = false;
		boolean adjacentSubject = false;
		int isTeacherUnavailable = 0;
		int[][][][] teacherUnavailability = new int[enviroment.N_TEACHERS+1][enviroment.N_DAYS+1][enviroment.N_SHIFT+1][enviroment.SLOTS_PER_DAY+1];
		
		for(int i = 0; i < arg0.size(); i++){
			/*

			currentLocus = i;
			currentAllele = Util.intAllele(arg0.getGene(i));
			
			//indisponibilidade
			//FIXME E e quando for mais de um professor??
			currentDay = Math.abs(i%enviroment.N_DAYS) + 1;
			currentSemester =  Math.abs(((i)/enviroment.SLOTS_PER_SEMESTER))+1;
			
			
			currentSlot = (int) (Math.floor(
					
					((( i/(enviroment.N_DAYS*1.0) )  /  enviroment.SLOTS_PER_DAY) - ( currentSemester - 1 )) *enviroment.SLOTS_PER_DAY  +1)); 
			isTeacherUnavailable =enviroment.isUnvavailable(currentAllele,currentDay,currentSlot);
			teacherUnavailability[enviroment.getTeacherOrderN(currentAllele)][currentDay][enviroment.CURRENT_SHIFT][currentSlot] += isTeacherUnavailable; 

			//chocar professor
			//levantamentos importantes : considerar a quantidade de choques (qntos semestres chocou?)
			//verificar se no mesmo período nos próximos semestres houve choque mesmo professor
			for (int j = 1; j < nGenes/enviroment.SLOTS_PER_SEMESTER; j++) {
				targetLocus = currentLocus + (enviroment.SLOTS_PER_SEMESTER * j);
				
				if(targetLocus >= nGenes)
					break;
				
				targetAllele = Util.intAllele(arg0.getGene(targetLocus));
				
				sameTeacher =enviroment.isSameTeacher(currentAllele, targetAllele) ; 
				
				if(sameTeacher ){
					teachersMatches++;
				}
				enviroment.GA_DATA.get(currentAllele).setTeacherMarches(sameTeacher);
				enviroment.GA_DATA.get(targetAllele).setTeacherMarches(sameTeacher);
				
			}
						
			//mesmo par disciplina/professor no mesmo dia
			//levantamentos importantes : isto invalida? ou é ponderado?
			//verificar se na próxima linha é o mesmo professor/disciplina
			targetLocus = currentLocus + enviroment.N_DAYS;
			if(targetLocus < nGenes){
				targetAllele = Util.intAllele(arg0.getGene(targetLocus));
				adjacentSubject = enviroment.isSamePair(currentAllele, targetAllele);
				if( adjacentSubject ){
					adjacentSubjects++;
				}
				enviroment.GA_DATA.get(currentAllele).setAdjacentSubject(adjacentSubject);
				enviroment.GA_DATA.get(targetAllele).setAdjacentSubject(adjacentSubject);
			}
			
			*/
			
			//imprimir numero
			//vDefault= vDefault.concat(((IntegerGene)c.getGene(j)).intValue()+" ");	
			
			
			//imprimir string 
			str = new StringBuffer(30);
			Util.replaceCaracteres(30, 
					enviroment.getTeacher(((IntegerGene)arg0.getGene(i)).intValue()) +" // " +enviroment.getSubject(((IntegerGene)arg0.getGene(i)).intValue()), 
					str);
			
			vDefault= vDefault.concat(str.toString() +"\t");
			

			if(i+1 < arg0.size() && ((i+1)%enviroment.N_DAYS) == 0)
				vDefault = vDefault.concat("\n ");	
			
			
			if(i+1 < arg0.size() && ((i+1)%enviroment.SLOTS_PER_SEMESTER) == 0)
				vDefault = vDefault.concat("\n ");	
			
		}/*
		for (int i = 0; i <= enviroment.N_TEACHERS; i++) {
			for (int k = 1; k <= enviroment.N_DAYS; k++) {
					for (int k2 = 1; k2 <= enviroment.SLOTS_PER_DAY; k2++) {
						teachersUnavailbilities += teacherUnavailability[i][k][enviroment.CURRENT_SHIFT][k2];
					}
			}
		}*/
	
		
		vDefault = vDefault.concat(" ]\n\n");
		
	
		System.out.println("\n"+vDefault);
		/*
		StringBuilder debug = new StringBuilder(); 
		debug.append("DEBUG: teacher unavailable: "+teachersUnavailbilities + " worst case "+enviroment.WORST_TEACHERS_UNAVAILABILITIES);
		debug.append("\nDEBUG: teacher matches : "+teachersMatches +" worst case "+enviroment.WORST_TEACHERS_MATCHES);
		debug.append("\nDEBUG: adjacent subjects : "+adjacentSubjects+ " worst case "+enviroment.WORST_ADJACENT_SUBJECTS);
		debug.append("\nDEBUG: sum \t\tTU + TM + AS : "+teachersUnavailbilities+" + "+teachersMatches+" + "+adjacentSubjects+" = "+(teachersMatches+teachersUnavailbilities+adjacentSubjects)*1.0);
		debug.append("\nDEBUG: sum worst case TU + TM + AS : "+enviroment.WORST_TEACHERS_UNAVAILABILITIES +" + "+enviroment.WORST_TEACHERS_MATCHES +" + "+enviroment.WORST_ADJACENT_SUBJECTS+" = "+(((enviroment.WORST_TEACHERS_MATCHES + enviroment.WORST_ADJACENT_SUBJECTS + enviroment.WORST_TEACHERS_UNAVAILABILITIES)*1.0)));
		debug.append("\nDEBUG: fitness 1 - (sum / sum worst case) : "+arg0.getFitnessValueDirectly());
		//FIXME TESTE 01	
		System.out.println(debug.toString());
		TimetableApp.currentShiftCourse.setDebug(debug.toString());*/
	}
}
