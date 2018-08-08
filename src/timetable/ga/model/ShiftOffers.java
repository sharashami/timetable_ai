package timetable.ga.model;

import java.util.List;

public class ShiftOffers {
	private String shiftName;
	private List<SubjectTeacherRelation> offers;
	private GADetails gaDetails = new GADetails();
	private String debug;
	

	public GADetails getGaDetails() {
		return gaDetails;
	}

	public String getDebug() {
		return debug;
	}
	public void setDebug(String debug) {
		this.debug = debug;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public List<SubjectTeacherRelation> getOffers() {
		return offers;
	}

	public void setOffers(List<SubjectTeacherRelation> shiftOffers) {
		this.offers = shiftOffers;
	}
}
