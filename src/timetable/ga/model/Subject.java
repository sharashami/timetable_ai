package timetable.ga.model;

import java.util.List;


public class Subject {
	
	private String name;//name
	private int code;
	private int semester;
	private int credits;
	private int qSlots;//quantity of slots
	private List<Teacher> teachers;//quantity of teachers responsible for
	
	/**
	 * 
	 * @param n - name
	 * @param s - semester
	 * @param qSlots - quantity of slots
	 */
	@Deprecated
	public Subject(String n, int s, int qSlots) {
		this.name = n;
		this.semester = s;
		this.qSlots = qSlots;
	}
	
	public Subject(String n,int code, int s, int c, java.util.List<Teacher> t) {
		this.name = n;
		this.semester = s;
		this.code = code;
		this.credits = c;
		this.teachers = t;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	@Deprecated
	public int getqSlots() {
		return qSlots;
	}
	@Deprecated
	public void setqSlots(int qSlots) {
		this.qSlots = qSlots;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	

}
