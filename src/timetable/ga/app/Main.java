package timetable.ga.app;


import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import semana.SemanaRepository;
import timetable.ga.Enviroment;
import timetable.ga.model.CourseOffers;
import disciplina.DisciplinaOfertaProfessorRepository;


public class Main {


	static EntityManagerFactory entityManagerFactory=Persistence.createEntityManagerFactory("horario");
	public static void main(String[] args) throws Exception {

		executeGAParameters(0, 0, 75);
		
		
		/*
		System.out.println("\nCONFIGURA플O M 12, C 35%, P 75");
		executeGAParameters(0, 0, 75);

		System.out.println("\nCONFIGURA플O default M 12, C 35%, P 150");
		executeGAParameters(0, 0, 150);

		System.out.println("\nCONFIGURA플O default M 12, C 35%, P 300");
		executeGAParameters(0, 0, 300);

		System.out.println("\nCONFIGURA플O default M 12, C 35%, P 600");
		executeGAParameters(0, 0, 600);
		
		System.out.println("\nCONFIGURA플O M 12, C 50%, P 75");
		executeGAParameters(12, 0.5, 75);
		
		System.out.println("\nCONFIGURA플O M 12, C 50%, P 150");
		executeGAParameters(12, 0.5, 150);

		System.out.println("\nCONFIGURA플O M 12, C 50%, P 300");
		executeGAParameters(12, 0.5, 300);

		System.out.println("\nCONFIGURA플O M 12, C 50%, P 600");
		executeGAParameters(12, 0.5, 600);

		System.out.println("\nCONFIGURA플O M 40, C 50%, P 75");
		executeGAParameters(40, 0.5, 75);
		
		System.out.println("\nCONFIGURA플O M 40, C 50%, P 150");
		executeGAParameters(40, 0.5, 150);
		
		System.out.println("\nCONFIGURA플O M 25, C 50%, P 300");
		executeGAParameters(40, 0.5, 300);
		
		System.out.println("\nCONFIGURA플O M 25, C 50%, P 600");
		executeGAParameters(40, 0.5, 600);


		System.out.println("\nCONFIGURA플O M 25, C 50%, P 75");
		executeGAParameters(25, 0.5, 75);
		
		System.out.println("\nCONFIGURA플O M 25, C 50%, P 150");
		executeGAParameters(25, 0.5, 150);
		
		System.out.println("\nCONFIGURA플O M 25, C 50%, P 300");
		executeGAParameters(25, 0.5, 300);
		
		System.out.println("\nCONFIGURA플O M 25, C 50%, P 600");
		executeGAParameters(25, 0.5, 600);

		System.out.println("\nCONFIGURA플O M 25, C 60%, P 75");
		executeGAParameters(25, 0.6, 75);
		
		System.out.println("\nCONFIGURA플O M 25, C 60%, P 150");
		executeGAParameters(25, 0.6, 150);
		
		System.out.println("\nCONFIGURA플O M 25, C 60%, P 300");
		executeGAParameters(25, 0.6, 300);
		
		System.out.println("\nCONFIGURA플O M 25, C 60%, P 600");
		executeGAParameters(25, 0.6, 600);
		*/
		/*
	    
		ITimetableParameters parameters = new ITimetableParameters() {

			EntityManagerFactory entityManagerFactory=Persistence.createEntityManagerFactory("horario");
		    EntityManager entityManager = entityManagerFactory.createEntityManager();
		    
			@Override
			public int getPopulationSize() {
				// TODO Auto-generated method stub
				return 150;
			}
			
			@Override
			public int getNumberOfSlotsPerSemesters() {
				// TODO Auto-generated method stub
				return 10;
			}
			
			@Override
			public int getNumberOfSemesters() {
				// TODO Auto-generated method stub
				return 8;
			}
			
			@Override
			public int getNumberOfDays() {
				// TODO Auto-generated method stub
				return 5;
			}
			
			@Override
			public int getCreditsPerSlot() {
				// TODO Auto-generated method stub
				return 2;
			}

			@Override
			public int getNumberOfTeachers() {
				// TODO Auto-generated method stub
				DisciplinaOfertaProfessorRepository dados = new DisciplinaOfertaProfessorRepository(entityManager);
				return dados.getTimetableNumberOfTeachers();
			}
		};

	    IEntryData entryData = new IEntryData() {

			EntityManagerFactory entityManagerFactory=Persistence.createEntityManagerFactory("horario");
		    EntityManager entityManager = entityManagerFactory.createEntityManager();
		    
			@Override
			public List<CourseOffers> getCourseOffers() {
				DisciplinaOfertaProfessorRepository dados = new DisciplinaOfertaProfessorRepository(entityManager);
				return dados.getTimetableEntryData();
			}
		};
	    new TimetableApp(entryData, parameters).doTimetable();
	    
	    if (1 ==1 ) return ;
		//define number of semesters and slots per semester
		Enviroment.N_SEMESTER = 3;
		Enviroment.SLOTS_PER_SEMESTER = 10;
		Enviroment.POPULATION_SIZE = 30;
		Enviroment.simulateData();*/
		
		
		
	}
	public static void executeGAParameters(final int mutationRate, final double crossoverRate, final int population){
		 final EntityManager entityManager = entityManagerFactory.createEntityManager();

			for (int i = 0; i < 10; i++) {
		
		//pegar de tabela configura寤es
				ITimetableParameters parameters = new ITimetableParameters() {

					@Override
					public int getPopulationSize() {
						// TODO Auto-generated method stub
						return population;
					}
					
					@Override
					public int getNumberOfSlotsPerSemesters() {
						// TODO Auto-generated method stub
						return 10;
					}
					
					@Override
					public int getNumberOfDays() {
						// TODO Auto-generated method stub
						return 5;
					}
					
					@Override
					public int getCreditsPerSlot() {
						// TODO Auto-generated method stub
						return 2;
					}

					@Override
					public int getMutatioRate() {
						// TODO Auto-generated method stub
						return mutationRate;
					}

					@Override
					public double getCrossoverRate() {
						// TODO Auto-generated method stub
						return crossoverRate;
					}
				};
			    IEntryData entryData = new IEntryData() {
					@Override
					public List<CourseOffers> getCourseOffers() {
						DisciplinaOfertaProfessorRepository dados = new DisciplinaOfertaProfessorRepository(entityManager);
						return dados.getTimetableEntryData();
					}
				};

				TimetableApp tApp = new TimetableApp(parameters);
				entryData = new IEntryData() {		    
					@Override
					public List<CourseOffers> getCourseOffers() {
						DisciplinaOfertaProfessorRepository dados = new DisciplinaOfertaProfessorRepository(entityManager);
						return dados.getTimetableEntryData();
					}
				};
					System.out.println("EXECU플O "+i+"\n\n");
					tApp.setEntryData(entryData);
					tApp.doTimetable();
				}
				//FIXME TESTE 01System.out.print(tApp.GENERATIONS_EXECUTION+" / "+tApp.FITNESS_EXECUTION+" / ");
				//FIXME TESTE 01System.out.print(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(tApp.TIME_EXECUTION),   TimeUnit.MILLISECONDS.toSeconds(tApp.TIME_EXECUTION) -   TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tApp.TIME_EXECUTION))));
				//FIXME TESTE 01System.out.print(" | ");

				//FIXME TESTE 01System.out.println("\n");
	}
	
}
