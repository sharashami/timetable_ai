package timetable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import professor.Professor;
import semana.DiaDaSemana;
import semana.Turno;
import curso.Curso;
import disciplina.Disciplina;
import eixo.Eixo;

@Entity
@Table(name="horario_aula")
public class Aula {

	@Id
	@SequenceGenerator(name="horario_aula_id", sequenceName="horario_aula_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="horario_aula_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="horario_id")
	private Horario horario;
		
	@ManyToOne
	@JoinColumn(name="eixo_id")
	private Eixo eixo;
	
	@ManyToOne
	@JoinColumn(name="curso_id")
	private Curso curso;

	@ManyToOne
	@JoinColumn(name="turno_id")
	private Turno turno;
	
	@Column(name="semestre", nullable=false)
	private int semestre;
	
	@ManyToOne
	@JoinColumn(name="disciplina_id")
	private Disciplina disciplina;

	@ManyToOne
	@JoinColumn(name="professor_id")
	private Professor professor;
	
	@ManyToOne
	@JoinColumn(name="dia_id")
	private DiaDaSemana dia;
	
	@Column(name="periodo", nullable=false)
	private int periodo;

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public Eixo getEixo() {
		return eixo;
	}

	public void setEixo(Eixo eixo) {
		this.eixo = eixo;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public DiaDaSemana getDia() {
		return dia;
	}

	public void setDia(DiaDaSemana dia) {
		this.dia = dia;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((curso == null) ? 0 : curso.hashCode());
		result = prime * result + ((dia == null) ? 0 : dia.hashCode());
		result = prime * result
				+ ((disciplina == null) ? 0 : disciplina.hashCode());
		result = prime * result + ((eixo == null) ? 0 : eixo.hashCode());
		result = prime * result + ((horario == null) ? 0 : horario.hashCode());
		result = prime * result + periodo;
		result = prime * result
				+ ((professor == null) ? 0 : professor.hashCode());
		result = prime * result + semestre;
		result = prime * result + ((turno == null) ? 0 : turno.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aula other = (Aula) obj;
		if (curso == null) {
			if (other.curso != null)
				return false;
		} else if (!curso.equals(other.curso))
			return false;
		if (dia == null) {
			if (other.dia != null)
				return false;
		} else if (!dia.equals(other.dia))
			return false;
		if (disciplina == null) {
			if (other.disciplina != null)
				return false;
		} else if (!disciplina.equals(other.disciplina))
			return false;
		if (eixo == null) {
			if (other.eixo != null)
				return false;
		} else if (!eixo.equals(other.eixo))
			return false;
		if (horario == null) {
			if (other.horario != null)
				return false;
		} else if (!horario.equals(other.horario))
			return false;
		if (periodo != other.periodo)
			return false;
		if (professor == null) {
			if (other.professor != null)
				return false;
		} else if (!professor.equals(other.professor))
			return false;
		if (semestre != other.semestre)
			return false;
		if (turno == null) {
			if (other.turno != null)
				return false;
		} else if (!turno.equals(other.turno))
			return false;
		return true;
	}
	
}
