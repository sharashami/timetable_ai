package timetable.ga;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import timetable.ga.app.IEntryData;
import timetable.ga.app.ITimetableParameters;
import timetable.ga.lib.Util;
import timetable.ga.model.CourseOffers;
import timetable.ga.model.Subject;
import timetable.ga.model.SubjectTeacherRelation;
import timetable.ga.model.Teacher;
import timetable.ga.model.TeacherSubject;
import timetable.ga.model.Unavailability;

public class Enviroment {

	public static int N_DAYS;
	public static int N_SHIFT;
	public static int SLOTS_PER_DAY;
	
	public int N_SEMESTER;
	public int CREDITS_PER_SLOT;
	public int SLOTS_PER_SEMESTER;
	public int CURRENT_SHIFT;
	
	public int WORST_TEACHERS_MATCHES;
	public int WORST_TEACHERS_UNAVAILABILITIES;
	public int WORST_ADJACENT_SUBJECTS;
	public int N_TEACHERS;

	public final static SubjectTeacherRelation GA_EMPTY_SUBJECT_TEACHER_RELATION = getNoClassReference();
	public HashMap<Integer, SubjectTeacherRelation> GA_DATA=null;
	private HashMap<Integer, Subject> GA_AVAILABLE_SUBJECTS=null;
	private HashMap<Integer, Integer> GA_TEACHER_ORDERED=null;
	
	public HashMap<Integer, Unavailability> GA_GLOBAL_TEACHER_UNAVAILABILITY= new HashMap<Integer, Unavailability>();;
	private java.util.List<SubjectTeacherRelation> ENTRY_DATA=null;
	public boolean DEBUG = false;
	
	
	
	public HashMap<Integer, TeacherSubject> staticData = new HashMap<Integer, TeacherSubject>();
	
	@Deprecated
	public List<Subject> availablesSubjects;
	
	public void init(){
		GA_DATA = new HashMap<Integer, SubjectTeacherRelation>();
		CURRENT_SHIFT = ENTRY_DATA.get(0).getShift();
		GA_AVAILABLE_SUBJECTS=null;
		GA_TEACHER_ORDERED=null;
		List<Integer> semesters = new ArrayList<Integer>();
		for (int i = 0; i < ENTRY_DATA.size(); i++) {
			if(semesters.indexOf(ENTRY_DATA.get(i).getSemester()) == -1)
				semesters.add(ENTRY_DATA.get(i).getSemester());
		}
		N_SEMESTER = semesters.size();
		
		WORST_TEACHERS_MATCHES =0;
		WORST_TEACHERS_UNAVAILABILITIES=0;
		WORST_ADJACENT_SUBJECTS =0;
		N_TEACHERS =0;
			
	}
	/**Creates the data related to teacher/subject/credits
	 */
	public void simulateData(){
		setAvailablesSubjects();
		int i = 1;
		
		//s5
		staticData.put(i, new TeacherSubject("Ajalmar",1, "BD",1));i++;
		staticData.put(i, new TeacherSubject("Ajalmar",1, "BD",1));i++;
		staticData.put(i, new TeacherSubject("Wellington",2, "REDES 1",2));i++;
		staticData.put(i, new TeacherSubject("Wellington",2, "REDES 1",2));i++;
		staticData.put(i, new TeacherSubject("Igor",3, "ES",3));i++;
		staticData.put(i, new TeacherSubject("Igor",3, "ES",3));i++;
		staticData.put(i, new TeacherSubject("Sandro",4, "MICRO",4));i++;
		staticData.put(i, new TeacherSubject("Sandro",4, "MICRO",4));i++;
		staticData.put(i, new TeacherSubject("Amauri",5, "AA",5));i++;
		staticData.put(i, new TeacherSubject("Amauri",5, "AA",5));i++;
		
		//s6
		staticData.put(i, new TeacherSubject("Adriano",6, "TC",6));i++;
		staticData.put(i, new TeacherSubject("Adriano",6, "TC",6));i++;
		staticData.put(i, new TeacherSubject("Rodolfo",7, "SE",7));i++;
		staticData.put(i, new TeacherSubject("Rodolfo",7, "SE",7));i++;
		staticData.put(i, new TeacherSubject("Corneli",8, "REDES 2",8));i++;
		staticData.put(i, new TeacherSubject("Corneli",8, "REDES 2",8));i++;
		staticData.put(i, new TeacherSubject("Nivando",9, "LP",9));i++;
		staticData.put(i, new TeacherSubject("Nivando",9, "LP",9));i++;
		staticData.put(i, new TeacherSubject("Socorro",10, "MTC",10));i++;
		staticData.put(i, new TeacherSubject("-",0, "-",0));i++;
		
		//s7
		staticData.put(i, new TeacherSubject("Nivando",9, "COMP",11));i++;
		staticData.put(i, new TeacherSubject("Nivando",9, "COMP",11));i++;
		staticData.put(i, new TeacherSubject("Adriano",6, "APSOO",12));i++;
		staticData.put(i, new TeacherSubject("Adriano",6, "APSOO",12));i++;
		staticData.put(i, new TeacherSubject("Eugenio",11, "ACC",13));i++;
		staticData.put(i, new TeacherSubject("Eugenio",11, "ACC",13));i++;
		staticData.put(i, new TeacherSubject("Corneli",8, "SD",14));i++;
		staticData.put(i, new TeacherSubject("Corneli",8, "SD",14));i++;
		staticData.put(i, new TeacherSubject("Ajalmar",1, "IA",15));i++;
		staticData.put(i, new TeacherSubject("Ajalmar",1, "IA",15));i++;
		
	}
	public void setParameters(ITimetableParameters parameters){
		N_DAYS = parameters.getNumberOfDays();
		CREDITS_PER_SLOT = parameters.getCreditsPerSlot();
		SLOTS_PER_SEMESTER= parameters.getNumberOfSlotsPerSemesters();
		SLOTS_PER_DAY = SLOTS_PER_SEMESTER/N_DAYS;
		N_SHIFT = 3;
	}
	
	/**It's a fix for cases we do not have any class, instance of a empty subject teacher relation to fill slots when needed
	 * All slots must be filled, so when does not have a subject teacher relation, fill with this instance
	 * @return 
	 */
	private static SubjectTeacherRelation getNoClassReference(){
		SubjectTeacherRelation emptyRelation = new SubjectTeacherRelation();
		 java.util.List<Teacher> emptyTeacher = new ArrayList<Teacher>();
		 emptyTeacher.add(new Teacher("-", 0,0));
		 emptyRelation.setSubject(new Subject("-", 0, 0, 1, emptyTeacher));
		return emptyRelation;
	}

	/**Fill the hash map <GA_DATA> with the right quantity of each subject teacher relation.
	 * Each entry data relation fill <x> entries in <GA_DATA> based on the proportion : xCredits -> xSlots   
	 * Observe that sometimes there are no entries to all slots of the semester, it means that has no class in that slot, those slots must be filled with "-"   
	 * @param entryData
	 */
	public void setEntryData(List<SubjectTeacherRelation> subjectOffers){
		 ENTRY_DATA = subjectOffers;
		 init();
		 int currentSemester = 0;
		 int currentSlot = 0;
		 int entryDataSize = ENTRY_DATA.size();
		 int index =1;
		 for(int i = 0; i < entryDataSize; i++){
			 if(currentSemester == 0)currentSemester = ENTRY_DATA.get(i).getSemester();

			 //verify if all slots were filled, no class case
			 if(currentSemester != ENTRY_DATA.get(i).getSemester() && currentSlot < SLOTS_PER_SEMESTER){
				 while(currentSlot < SLOTS_PER_SEMESTER){
					
					 GA_DATA.put(index++, GA_EMPTY_SUBJECT_TEACHER_RELATION);
					 currentSlot++;
				 }
				 currentSemester =  ENTRY_DATA.get(i).getSemester();
				 currentSlot = 0;
			 } else if(currentSlot == SLOTS_PER_SEMESTER){ 
				 currentSemester =  ENTRY_DATA.get(i).getSemester();
				 currentSlot = 0;
			 }
			 
			 int nOcurrency = numberOfSlots(ENTRY_DATA.get(i));
			 while(nOcurrency > 0){
				 GA_DATA.put(index++, ENTRY_DATA.get(i).doClone());
				 nOcurrency--;
				 currentSlot++;
			 }
			 addAvailableSubject(ENTRY_DATA.get(i).getSubject());
			 addAvailableTeacher(ENTRY_DATA.get(i).getSubject().getTeachers());
			 
		 }

		 //fix, the for limit is the number of offers, so on the last semester may miss some empty slots 
		 while(currentSlot < SLOTS_PER_SEMESTER){
		
			 GA_DATA.put(index++, GA_EMPTY_SUBJECT_TEACHER_RELATION);
			 currentSlot++;
			 
				
		 }

		 N_TEACHERS = GA_TEACHER_ORDERED.size();
	
		 setWorstAdjacentSubjects();
		 setWorstTeachersMatches();
		 setWorstTeachersUnavailbilities();

	}
	/**Fill a hash map list with the subjects offered if it does not already exist, it is used for calculating the worst case of adjacent subjects
	 * 
	 * @param s
	 */
	private void addAvailableSubject(Subject s){
		if(GA_AVAILABLE_SUBJECTS == null){
			GA_AVAILABLE_SUBJECTS = new HashMap<Integer, Subject>();
		}
		if(!GA_AVAILABLE_SUBJECTS.containsKey(s.getCode()))
				GA_AVAILABLE_SUBJECTS.put(s.getCode(), s);
	}
	/**Fill a hash map list with the teachers if it does not already exist, it is used for calculating the worst case of teachers matches
	 * 
	 * @param s
	 */
	private void addAvailableTeacher(List<Teacher> s){
		if(GA_TEACHER_ORDERED == null){
			GA_TEACHER_ORDERED = new HashMap<Integer, Integer>();
		}
		int size = s.size();
		for(int i = 0 ; i < size; i++){
			
			if(!GA_TEACHER_ORDERED.containsKey(	s.get(i).getCode())){
				
				GA_TEACHER_ORDERED.put(s.get(i).getCode(), GA_TEACHER_ORDERED.values().size()+1);

				//fill random
				//max 3 days unavailable
				/*Unavailability unavailability = new Unavailability();
				
				Random q = new Random();
				int q2=q.nextInt(3);
				while(q2 > 0){
					int d = q.nextInt(Enviroment.N_DAYS+1);
					if(d > 0){
						unavailability.setUnavailable(d);
					}
					q2--;
				}*/
				if(!GA_GLOBAL_TEACHER_UNAVAILABILITY.containsKey(s.get(i).getCode())){
					GA_GLOBAL_TEACHER_UNAVAILABILITY.put(s.get(i).getCode(), s.get(i).getUnavailability());
					//System.out.println(s.get(i).getName());
					//System.out.println(s.get(i).getUnavailability()+"\n\n");
				}

			}
		}
		
	}
	/**Returns the proportion xCredits -> xSlots of the subject teacher relation <s>   
	 * @param s - SubjectTeacherRelation
	 * @return
	 */
	public int numberOfSlots(SubjectTeacherRelation s){
		return numberOfSlots(s.getSubject().getCredits());
	}

	public int numberOfSlots(int credits){
		return (int)Math.ceil(credits/(CREDITS_PER_SLOT*1.0));
	}
	
	public int getTeacherOrderN(int k){
		if(GA_DATA.get(k).equals(GA_EMPTY_SUBJECT_TEACHER_RELATION)) return 0;
		return GA_TEACHER_ORDERED.get(GA_DATA.get(k).getSubject().getTeachers().get(0).getCode()).intValue();
	}

	public int isUnvavailable(int k, int day, int currentSlot){
		if(GA_DATA.get(k).equals(GA_EMPTY_SUBJECT_TEACHER_RELATION)) return 0;
		
		SubjectTeacherRelation str = GA_DATA.get(k);
		//if(str.getSubject().getTeachers().get(0).getName().equals("EUGENIO"))
		
		int v =GA_GLOBAL_TEACHER_UNAVAILABILITY.get(str.getSubject().getTeachers().get(0).getCode()).isUnavailable(day,str.getShift(),currentSlot); 
		//System.out.println(str.getSubject().getTeachers().get(0).getName() + " indisp "+day+ " "+str.getShift()+" "+currentSlot+ " = "+v);
		//FIXME e quando for mais de um professor??
		return v;
	}
	public String getTeacher(int k){
		//FIXME e quando for mais de um professor??
		
		return GA_DATA.get(k).getSubject().getTeachers().get(0).getName();
	}
	public String getSubject(int k){
		return GA_DATA.get(k).getSubject().getName();
	}
	public boolean isSameTeacher(int k1, int k2){
		//FIXME e quando for mais de um professor??
		if(GA_DATA.get(k1).equals(GA_EMPTY_SUBJECT_TEACHER_RELATION) || GA_DATA.get(k2).equals(GA_EMPTY_SUBJECT_TEACHER_RELATION))
			return false;
		return GA_DATA.get(k1).getSubject().getTeachers().get(0).getName().equals(GA_DATA.get(k2).getSubject().getTeachers().get(0).getName());
	}
	public boolean isSameSubject(int k1, int k2){
		return GA_DATA.get(k1).getSubject().getName().equals(GA_DATA.get(k2).getSubject().getName());
	}
	public boolean isSamePair(int k1, int k2){
		SubjectTeacherRelation ts1  = GA_DATA.get(k1);
		SubjectTeacherRelation ts2  = GA_DATA.get(k2);
		if(ts1.equals(GA_EMPTY_SUBJECT_TEACHER_RELATION) || ts2.equals(GA_EMPTY_SUBJECT_TEACHER_RELATION))
			return false;
		//FIXME e quando for mais de um professor??
		return ts1.getSubject().getTeachers().get(0).equals(ts2.getSubject().getTeachers().get(0)) 
				&& ts1.getSubject().equals(ts2.getSubject());
	}
	
	@Deprecated
	public void setAvailablesSubjects(){
		availablesSubjects = new ArrayList<Subject>();
		int i = 1;
		availablesSubjects.add(new Subject("BD", i, 2));
		availablesSubjects.add(new Subject("REDES", i, 2));
		availablesSubjects.add(new Subject("ES", i, 2));
		availablesSubjects.add(new Subject("MICRO", i, 2));
		availablesSubjects.add(new Subject("AA", i, 2));

		i++;
		availablesSubjects.add(new Subject("TC", i, 2));
		availablesSubjects.add(new Subject("SE", i, 2));
		availablesSubjects.add(new Subject("REDES 2", i, 2));
		availablesSubjects.add(new Subject("LP", i, 2));
		availablesSubjects.add(new Subject("MTC", i, 1));
		availablesSubjects.add(new Subject("-", i, 1));

		i++;
		availablesSubjects.add(new Subject("COMP", i, 2));
		availablesSubjects.add(new Subject("APSOO", i, 2));
		availablesSubjects.add(new Subject("ACC", i, 2));
		availablesSubjects.add(new Subject("SD", i, 2));
		availablesSubjects.add(new Subject("IA", i, 2));
	
		
	}
	/*public void main(String args[]) {
		Enviroment.N_SEMESTER = 3;
		Enviroment.SLOTS_PER_SEMESTER = 10;
		N_TEACHERS=11;
		simulateData();
		setWorstTeachersMatches();
		setWorstAdjacentSubjects();
	}*/
	public void setWorstTeachersMatches(){
		int relation[][] = new int[N_TEACHERS+1][N_SEMESTER+1];
		int currentSemester = 1;
		
		//count in which semesters the teachers are. do not consider the empty subject teacher relation
		//iterate all the slots summing the teacher X occurrences  
		for (int i = 1; i <= N_SEMESTER*SLOTS_PER_SEMESTER; i++) {
			currentSemester = Math.abs(((i-1)/SLOTS_PER_SEMESTER))+1;
			//FIXME e quando for mais de um professor??
			
			if(!GA_DATA.get(i).equals(GA_EMPTY_SUBJECT_TEACHER_RELATION)){
				Teacher t = GA_DATA.get(i).getSubject().getTeachers().get(0);
				
				relation[GA_TEACHER_ORDERED.get(t.getCode()).intValue()][currentSemester]++;
			}
		}
		int major = 0;
		int minorSum = 0;
		int majorIndex = 0;
		//count the worst case of each teacher
		for (int i = 1; i <= N_TEACHERS; i++) {
			major = 0;
			minorSum = 0;
			majorIndex = 0;
			
			//find out the major value per semester
			for (int j= 1; j <= N_SEMESTER; j++) {
				if(relation[i][j]>major){
					major = relation[i][j];
					majorIndex = j;
				}
			}
			//sum the other values to find out the worst case
			for (int j= 1; j <= N_SEMESTER; j++) {
				if(j != majorIndex)minorSum = minorSum +relation[i][j]; 
			}

			if(relation[i][majorIndex] < minorSum) WORST_TEACHERS_MATCHES = WORST_TEACHERS_MATCHES + relation[i][majorIndex];
			else WORST_TEACHERS_MATCHES = WORST_TEACHERS_MATCHES + minorSum;
			
		}
		
	
	}

	public void setWorstAdjacentSubjects(){
		HashMap<Integer, Integer> relationSubject = new HashMap<Integer,Integer>();
		HashMap<Integer, Integer> relationSemester = new HashMap<Integer,Integer>();
		//int relationSubject[] = new int[N_SEMESTER+1];//the real value
		//int relationSemester[] = new int[N_SEMESTER+1];//the maximum value
		int subjectsTotalOcurrence = 0;
		int semestersTotalOcurrence = 0;
		Collection<Subject> subjects = GA_AVAILABLE_SUBJECTS.values();
		
		//count how many possibilities of adjacent subjects per semester
		//adjacent means two slots filled with the same subject, to count this we do <nSlotsSubject>/2
		//s1 occupies 2 slots, so 2/2 = 1 case
		//s1 occupies 3 slots, so floor(3/2) = 1 case
		int aux = 0;
		for (Iterator iterator = subjects.iterator(); iterator.hasNext();) {
			Subject item = (Subject) iterator.next();
			if(!item.getName().equals(GA_EMPTY_SUBJECT_TEACHER_RELATION.getSubject().getName())){
				
				
				if(!relationSubject.containsKey(item.getSemester())){
					relationSubject.put(item.getSemester(), 0);
					
					aux = N_DAYS*(int)Math.floor(SLOTS_PER_DAY/2.0);
					relationSemester.put(item.getSemester(), aux);
				}
				aux = relationSubject.get(item.getSemester()) + (int)Math.floor(numberOfSlots(item.getCredits())/2.0);
				relationSubject.put(item.getSemester(), aux);
				
				//relationSubject[item.getSemester()] = relationSubject[item.getSemester()] + (int)Math.floor(numberOfSlots(item.getCredits())/2.0);
				
			}
		}
		
		for (Iterator iterator = relationSubject.values().iterator(); iterator.hasNext();) {
			subjectsTotalOcurrence += (Integer) iterator.next();
		}

		for (Iterator iterator = relationSemester.values().iterator(); iterator.hasNext();) {
			
			semestersTotalOcurrence += (Integer) iterator.next();
		}/*
		for (int i = 1; i <= N_SEMESTER; i++) {
			relationSemester[i] = N_DAYS*(int)Math.floor(SLOTS_PER_DAY/2.0);
			semestersTotalOcurrence += relationSemester[i];
			subjectsTotalOcurrence +=relationSubject[i]; 
		}*/
		WORST_ADJACENT_SUBJECTS = (subjectsTotalOcurrence < semestersTotalOcurrence) ?subjectsTotalOcurrence:semestersTotalOcurrence;
		
	}
	/**Count how many days the teacher K is unavailable
	 * 
	 * @return
	 */
	public int getUnavailabilities(Teacher t){
		StringBuffer str = new StringBuffer(20);
		Util.replaceCaracteres(20, t.getName(), str);
		Unavailability unavailability =  GA_GLOBAL_TEACHER_UNAVAILABILITY.get(t.getCode());
	
		int sum = 0;
		for(int i =1; i<= N_DAYS; i++){
	
			for (int j = 1; j <= SLOTS_PER_DAY; j++) {//periods
	
				
				sum += unavailability.isUnavailable(i,CURRENT_SHIFT,j);	
			}
			
		}
		return sum;
	}
	/**A: Transforma a quantidade de dias indisponiveis em slots
	 * B: transforma a quantidade de creditos usados em slots
	 * se A > B, entao o pior caso será B
	 * 
	 */
	public void setWorstTeachersUnavailbilities(){
		int teacherUnavailbilities =0;
		Collection<SubjectTeacherRelation> c =GA_DATA.values();
		java.util.List<Integer> teachersVisited = new ArrayList<Integer>();
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			SubjectTeacherRelation type = (SubjectTeacherRelation) iterator.next();
			Teacher t = type.getSubject().getTeachers().get(0);

			if(!type.equals(GA_EMPTY_SUBJECT_TEACHER_RELATION) && !teachersVisited.contains(t.getCode())){
				int totalUnavailable = getUnavailabilities(t);
				int requiredSlots = countRequiredSlots(t);
				
				teacherUnavailbilities += (totalUnavailable > requiredSlots)?requiredSlots:totalUnavailable;
				teachersVisited.add(t.getCode());
			}
		}
		WORST_TEACHERS_UNAVAILABILITIES = teacherUnavailbilities;
		//FIXME TESTE 01System.out.println(WORST_TEACHERS_UNAVAILABILITIES);
	}
	
	/**Count how many slots is need to cover all the offers the teacher is involved
	 * 
	 * @param t
	 * @return
	 */
	private int countRequiredSlots(Teacher t) {
		int requiredSlots = 0;
		Collection<SubjectTeacherRelation> c =GA_DATA.values();
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			SubjectTeacherRelation type = (SubjectTeacherRelation) iterator.next();
			if(!type.equals(GA_EMPTY_SUBJECT_TEACHER_RELATION))
				for (int j = 0; j < type.getSubject().getTeachers().size(); j++) {
					if(type.getSubject().getTeachers().get(j).getCode() == t.getCode()){
						requiredSlots += numberOfSlots(type.getSubject().getCredits());
						break;
					}
				}
		}
		return requiredSlots;
	}
	public void setUnavailable(int currentAllele, int currentDay, int currentSlot) {
		SubjectTeacherRelation str = GA_DATA.get(currentAllele);
		if(!str.equals(GA_EMPTY_SUBJECT_TEACHER_RELATION)){
			GA_GLOBAL_TEACHER_UNAVAILABILITY.get(str.getSubject().getTeachers().get(0).getCode()).setUnavailable(currentDay,CURRENT_SHIFT,currentSlot);
		}
	}
}
