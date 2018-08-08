package professor.indisponibilidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import professor.Professor;
import semana.DiaDaSemana;
import semana.Turno;
import utils.Definicoes;


@Entity //inidicar que sera criado uma tabela no banco
@Table(name="professor_indisponibilidade")

@NamedQueries({//nome da classe
    @NamedQuery(name=Definicoes.PROFESSOR_INDISPONIBILIDADE,query="SELECT pi FROM ProfessorIndisponibilidade pi WHERE pi.professor.id = :idProf ORDER BY pi.diaDaSemana.id, pi.turno.id,pi.periodo  ASC"),
    @NamedQuery(name=Definicoes.PROFESSOR_INDISPONIBILIDADE_POR_TURNO,query="SELECT pi FROM ProfessorIndisponibilidade pi WHERE pi.professor.id = :idProf AND pi.turno.id = :idTurno ORDER BY pi.diaDaSemana.id, pi.periodo ASC"),
    @NamedQuery(name=Definicoes.PROFESSOR_INDISPONIBILIDADE_GET,query="SELECT pi  FROM ProfessorIndisponibilidade pi WHERE pi.professor.id = :idProf AND pi.turno.id = :idTurno AND pi.diaDaSemana.id = :idDia  AND pi.periodo = :periodo"),


}) 
public class ProfessorIndisponibilidade {

	@Id
	@SequenceGenerator( name = "professor_indisponibilidade_id", sequenceName = "professor_indisponibilidade_id_seq", allocationSize = 1 )  
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "professor_indisponibilidade_id" )
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="professor_id", nullable = false)//FK para professor
	private Professor professor;

	@ManyToOne
	@JoinColumn(name="dia_da_semana_id", nullable = false)//FK para professor
	private DiaDaSemana diaDaSemana;

	@ManyToOne
	@JoinColumn(name="turno_id", nullable = false)//FK para professor
	private Turno turno;

	
	@Column(name="periodo", nullable = false)
	private int periodo;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public DiaDaSemana getDiaDaSemana() {
		return diaDaSemana;
	}

	public void setDiaDaSemana(DiaDaSemana diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
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
		result = prime * result
				+ ((diaDaSemana == null) ? 0 : diaDaSemana.hashCode());
		result = prime * result + id;
		result = prime * result + periodo;
		result = prime * result
				+ ((professor == null) ? 0 : professor.hashCode());
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
		ProfessorIndisponibilidade other = (ProfessorIndisponibilidade) obj;
		if (diaDaSemana == null) {
			if (other.diaDaSemana != null)
				return false;
		} else if (!diaDaSemana.equals(other.diaDaSemana))
			return false;
		if (id != other.id)
			return false;
		if (periodo != other.periodo)
			return false;
		if (professor == null) {
			if (other.professor != null)
				return false;
		} else if (!professor.equals(other.professor))
			return false;
		if (turno == null) {
			if (other.turno != null)
				return false;
		} else if (!turno.equals(other.turno))
			return false;
		return true;
	}
}
