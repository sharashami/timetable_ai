package matriz;



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

import utils.Definicoes;
import curso.Curso;
import disciplina.Disciplina;

@Table(name="matriz_curricular")
@Entity
@NamedQueries({//nome da classe
	@NamedQuery(name=Definicoes.MATRIZ_CURRICULAR_CURSO,query="SELECT m FROM MatrizCurricular m WHERE m.curso.id = :cursoParam"),
	@NamedQuery(name=Definicoes.MATRIZ_COUNT, query="SELECT COUNT(m) FROM MatrizCurricular m")
}) 

public class MatrizCurricular implements Serializable{

	@Id
	@SequenceGenerator( name = "matrizCurricular_id", sequenceName = "matrizCurricular_seq", allocationSize = 1 )  
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "matrizCurricular_id" )
	@Column(name = "id", nullable = false)
	private int id;

	@ManyToOne(optional=false)
	@JoinColumn(name = "disciplina_id", nullable=false)
	private Disciplina disciplina;

	@ManyToOne(optional=false)
	@JoinColumn(name = "curso_id",  nullable=false)
	private Curso curso;
	  
	@Column(nullable=false, columnDefinition="boolean default false")
	private boolean ativo;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
