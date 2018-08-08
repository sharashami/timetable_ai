package timetable.ga.model;

import timetable.ga.Enviroment;

public class SubjectTeacherRelation implements Cloneable{
	private long code;
	private Subject subject;
	private int semester;
	private int shift;
	private String shiftName;
	private String view;
	private boolean adjacentSubject;
	private boolean teacherMarches;
	private boolean teacherUnavailable;
	
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
	public int getShift() {
		return shift;
	}
	public void setShift(int shift) {
		this.shift = shift;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	public boolean isAdjacentSubject() {
		return adjacentSubject;
	}
	public void setAdjacentSubject(boolean adjacentSubject) {
		this.adjacentSubject = adjacentSubject;
		System.out.println(subject.getTeachers().get(0).getName() +" em "+subject.getName()+"\n");
	}
	public boolean isTeacherMarches() {
		return teacherMarches;
	}
	public void setTeacherMarches(boolean teacherMarches) {
		this.teacherMarches = teacherMarches;
		System.out.println(subject.getTeachers().get(0).getName() +" em "+subject.getName()+"\n");
	}
	public boolean isTeacherUnavailable() {
		return teacherUnavailable;
	}
	public void setTeacherUnavailable(boolean teacherUnavailable) {
		System.out.println(subject.getTeachers().get(0).getName() +" em "+subject.getName()+"\n");
		this.teacherUnavailable = teacherUnavailable;
	}
	public SubjectTeacherRelation doClone(){
		try {
			return (SubjectTeacherRelation) clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		SubjectTeacherRelation a = new SubjectTeacherRelation();
		a.setCode(code);
		a.setSemester(semester);
		a.setShift(shift);
		a.setShiftName(shiftName);
		a.setSubject(subject);
		return a;
	}
}
