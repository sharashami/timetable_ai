package professor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import periodoletivo.PeriodoLetivo;
import disciplina.DisciplinaOferta;


public class ProfessorOfertaDisciplina {

	@Id
	@SequenceGenerator( name = "professor_oferta_disciplina_id", sequenceName = "oferta_disciplina_professor_seq", allocationSize = 1 )  
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "oferta_disciplina_professor_id" )
	@Column(name = "id", nullable = false)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="professor_id", nullable = false)//FK para professor
	private Professor professor;
	
	@ManyToOne
	@JoinColumn(name="oferta_disciplina_id", nullable = false)//FK para ofeta dsicaplina
	private DisciplinaOferta ofertaDisciplina;
	
	
	@ManyToOne
	@JoinColumn(name="semestre_letivo_id", nullable = false)//FK para semestre
	private PeriodoLetivo semestreLetivo;

	
	@Column(nullable=false, columnDefinition="boolean default true")
	private boolean paralela;
	
	@Column(nullable=false, columnDefinition="integer default 0")
	private int credito;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public DisciplinaOferta getOfertaDisciplina() {
		return ofertaDisciplina;
	}

	public void setOfertaDisciplina(DisciplinaOferta ofertaDisciplina) {
		this.ofertaDisciplina = ofertaDisciplina;
	}

	public PeriodoLetivo getSemestreLetivo() {
		return semestreLetivo;
	}

	public void setSemestreLetivo(PeriodoLetivo semestreLetivo) {
		this.semestreLetivo = semestreLetivo;
	}

	public boolean isParalela() {
		return paralela;
	}

	public void setParalela(boolean paralela) {
		this.paralela = paralela;
	}

	public int getCredito() {
		return credito;
	}

	public void setCredito(int credito) {
		this.credito = credito;
	}
}
