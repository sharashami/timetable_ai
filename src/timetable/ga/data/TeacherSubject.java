package timetable.ga.data;


public class TeacherSubject {

	private String teacher;
	private int teacherID;
	private String subject;
	private int subjectID;
	
	public TeacherSubject(String t,int tID, String s, int sID) {
		teacher = t;
		subject = s;
		teacherID = tID;
		subjectID = sID;
		
	}
	public int getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(int teacherID) {
		this.teacherID = teacherID;
	}
	public int getSubjectID() {
		return subjectID;
	}
	public void setSubjectID(int subjectID) {
		this.subjectID = subjectID;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

}
