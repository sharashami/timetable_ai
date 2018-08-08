package timetable.ga.data;

import timetable.ga.Enviroment;

public class Unavailability {
	private int[] days;
	
	public Unavailability() {
		days = new int[Enviroment.N_DAYS+1];
	}
	public int unavailable(int day){
		return days[day];
	}

	public void setUnavailable(int day){
		days[day] = 1;
	}
}
