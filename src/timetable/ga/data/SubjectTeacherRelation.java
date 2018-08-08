package timetable.ga.data;

import timetable.ga.Enviroment;

public class SubjectTeacherRelation {
	private long code;
	private Subject subject;
	private int semester;
	private String view;
	
	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	
	public String getView() {
		if(this.equals(Enviroment.GA_EMPTY_SUBJECT_TEACHER_RELATION))
			return " - ";
		//FIXME e quando for mais de um professor??
		else return subject.getTeachers().get(0).getName() + " - "+ subject.getName();
		
	}
}
