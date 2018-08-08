package timetable.ga.model;

public class GADetails {
	private int teachersMatches;
	private int adjacentSubjects;
	private int teachersUnavailbilities;
	
	private int teacherMachesTotal;
	private int adjacentSubjectsTotal;
	private int teachersUnavailbilitiesTotal;
	
	private double fitness;

	public int getTeachersMatches() {
		return teachersMatches;
	}

	public void setTeachersMatches(int teacherMatches) {
		this.teachersMatches = teacherMatches;
	}

	public int getAdjacentSubjects() {
		return adjacentSubjects;
	}

	public void setAdjacentSubjects(int adjacentSubjects) {
		this.adjacentSubjects = adjacentSubjects;
	}

	public int getTeacherMachesTotal() {
		return teacherMachesTotal;
	}

	public void setTeachersMatchesTotal(int teachersMachesTotal) {
		this.teacherMachesTotal = teachersMachesTotal;
	}

	public int getAdjacentSubjectsTotal() {
		return adjacentSubjectsTotal;
	}

	public void setAdjacentSubjectsTotal(int adjacentSubjectsTotal) {
		this.adjacentSubjectsTotal = adjacentSubjectsTotal;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public int getTeachersUnavailbilities() {
		return teachersUnavailbilities;
	}

	public void setTeachersUnavailbilities(int teachersUnavailbilities) {
		this.teachersUnavailbilities = teachersUnavailbilities;
	}

	public int getTeachersUnavailbilitiesTotal() {
		return teachersUnavailbilitiesTotal;
	}

	public void setTeachersUnavailbilitiesTotal(int teachersUnavailbilitiesTotal) {
		this.teachersUnavailbilitiesTotal = teachersUnavailbilitiesTotal;
	}
}
