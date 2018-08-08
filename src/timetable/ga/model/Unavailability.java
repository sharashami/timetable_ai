package timetable.ga.model;

import timetable.ga.Enviroment;

public class Unavailability {
	private int[][][] daysShift;
	
	public Unavailability() {
		daysShift = new int[Enviroment.N_DAYS+1][Enviroment.N_SHIFT+1][Enviroment.SLOTS_PER_DAY+1];
	}
	public int isUnavailable(int day, int shift, int slot){
		return daysShift[day][shift][slot];
	}

	public void setUnavailable(int day){
		for (int i = 1; i <= Enviroment.N_SHIFT; i++) {
			for (int j = 1; j <= Enviroment.SLOTS_PER_DAY; j++) {
				daysShift[day][i][j] = 1;	
			}
			
		}
		
	}
	public void setUnavailable(int day,int shift,int slot){
		daysShift[day][shift][slot] = 1;
	}
	
	/**Set unavailable in  all slost(periods) of the shift
	 * 
	 * @param day
	 * @param shift
	 */
	public void setUnavailable(int day,int shift){
		for (int i = 1; i <= Enviroment.SLOTS_PER_DAY; i++) {
			daysShift[day][shift][i] = 1;
		}
		
	}
	@Override
	public String toString() {
		
		for(int i =1; i < Enviroment.N_DAYS+1; i++){
			
			for(int k =1; k < Enviroment.N_SHIFT+1; k++){
				
				for(int l =1; l < Enviroment.SLOTS_PER_DAY+1; l++){
				//	System.out.println(i+" "+k+" P "+l+" = "+daysShift[i][k][l]+" ");
				}	
			}
			
		}
			
		return "";
	}
}
