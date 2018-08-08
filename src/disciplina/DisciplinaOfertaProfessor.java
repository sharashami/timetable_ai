package disciplina;

import java.io.Serializable;

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

import periodoletivo.PeriodoLetivo;
import professor.Professor;
import semana.Turno;
import utils.Definicoes;

@Table(name="disciplina_oferta_professor")
@Entity
@NamedQueries({//nome da classe
	 @NamedQuery(name=Definicoes.DISCIPLINA_OFERTA_PROFESSOR_LISTAR, query="SELECT d FROM DisciplinaOfertaProfessor d ORDER BY d.disciplinaOferta.disciplina.semestre"),
	 @NamedQuery(name=Definicoes.DISCIPLINA_OFERTA_PROFESSOR_LISTAR_POR_CURSO_TURNO, query="SELECT d FROM DisciplinaOfertaProfessor d WHERE d.disciplinaOferta.disciplina.curso.id =:id_curso AND d.disciplinaOferta.turno.id=:id_turno ORDER BY d.disciplinaOferta.disciplina.semestre, d.disciplinaOferta.turno.id")
})
public class DisciplinaOfertaProfessor implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator( name = "disciplina_oferta_professor_id", sequenceName = "disciplina_oferta_professor_seq", allocationSize = 1 )  
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "disciplina_oferta_professor_id" )
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="disciplina_oferta_id", nullable = false)//FK para disciplina
	private DisciplinaOferta disciplinaOferta;

	@ManyToOne(optional=false)
	@JoinColumn(name="professor_id", nullable = false)//FK para disciplina
	private Professor professor;

	@ManyToOne(optional=false)
	@JoinColumn(name="semestre_letivo_id", nullable = false, columnDefinition="integer default 1")//FK para disciplina
	private PeriodoLetivo semestreLetivo;
	
	@Column(nullable=false, columnDefinition="boolean default false")
	private boolean paralela;
	
	@Column(nullable=false, columnDefinition="integer default 0")
	private int credito;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DisciplinaOferta getDisciplinaOferta() {
		return disciplinaOferta;
	}

	public void setDisciplinaOferta(DisciplinaOferta disciplinaOferta) {
		this.disciplinaOferta = disciplinaOferta;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public int getCredito() {
		return credito;
	}

	public void setCredito(int credito) {
		this.credito = credito;
	}

	public boolean isParalela() {
		return paralela;
	}

	public void setParalela(boolean paralela) {
		this.paralela = paralela;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + credito;
		result = prime
				* result
				+ ((disciplinaOferta == null) ? 0 : disciplinaOferta.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (paralela ? 1231 : 1237);
		result = prime * result
				+ ((professor == null) ? 0 : professor.hashCode());
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
		DisciplinaOfertaProfessor other = (DisciplinaOfertaProfessor) obj;
		if (credito != other.credito)
			return false;
		if (disciplinaOferta == null) {
			if (other.disciplinaOferta != null)
				return false;
		} else if (!disciplinaOferta.equals(other.disciplinaOferta))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (paralela != other.paralela)
			return false;
		if (professor == null) {
			if (other.professor != null)
				return false;
		} else if (!professor.equals(other.professor))
			return false;
		return true;
	}

	public PeriodoLetivo getSemestreLetivo() {
		return semestreLetivo;
	}

	public void setSemestreLetivo(PeriodoLetivo semestreLetivo) {
		this.semestreLetivo = semestreLetivo;
	}
	
}
