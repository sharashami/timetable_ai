package timetable.ga.model;

import java.util.ArrayList;
import java.util.List;

public class CourseOffers {
	private String name;
	private List<ShiftOffers> shiftOffers;
	
	public CourseOffers(){
		shiftOffers = new ArrayList<ShiftOffers>();
	}
	public void addShiftOffers(ShiftOffers shiftOffer){
				
		this.shiftOffers.add(shiftOffer);
	}	
	/**Order the list of offers, ordering the major number of offers by shift descending.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void orderShiftOffersByAmountDesc(){
		
		ShiftOffers major;
		for (int i = 0; i < shiftOffers.size()-1; i++) {
			for (int j = i+1; j < shiftOffers.size(); j++) {
				if(shiftOffers.get(i).getOffers().size() < shiftOffers.get(j).getOffers().size()){
					major = shiftOffers.get(j);
					shiftOffers.remove(j);
					shiftOffers.add(i, major);		
				}
			}
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ShiftOffers> getShiftOffers() {
		return shiftOffers;
	}

}
