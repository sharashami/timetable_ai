package timetable.ga.data;

import timetable.ga.Enviroment;
import timetable.ga.lib.Util;



public class Teacher {
	private String name;//name
	private int code;
	private int creditsUsed;
	private Unavailability unavailability;
	/**
	 * 
	 * @param n - name
	 * @param c - code
	 */
	public Teacher(String n, int c, int cUsed) {
		this.name = n;
		this.code = c;
		this.creditsUsed = cUsed;

		
		
	}
	public int isUnavailable(int day){
		return unavailability.unavailable(day);
	}

	public void setUnavailability(Unavailability unavailability){
		this.unavailability = unavailability;
	}
	
	/**Count how many days the teacher is unavailable
	 * 
	 * @return
	 */
	public int getUnavailabilities(){
		StringBuffer str = new StringBuffer(20);
		Util.replaceCaracteres(20, name, str);
		//Unavailability unavailability =  Enviroment.GA_TEACHER_UNAVAILABILITY.get(code);
		System.out.print("\n"+str+"\t\t");
		int sum = 0;
		for(int i =1; i<= Enviroment.N_DAYS; i++){
			System.out.print(unavailability.unavailable(i)+ "  ");
			sum += unavailability.unavailable(i);
		}
		return sum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getCreditsUsed() {
		return creditsUsed;
	}
	public void setCreditsUsed(int creditsUsed) {
		this.creditsUsed = creditsUsed;
	}
	public Unavailability getUnavailability() {
		return unavailability;
	}
}
