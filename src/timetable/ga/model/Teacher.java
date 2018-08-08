package timetable.ga.model;

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
	public int isUnavailable(int day,int shift,int slot){
		return unavailability.isUnavailable(day,shift,slot);
	}

	public void setUnavailability(Unavailability unavailability){
		this.unavailability = unavailability;
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
