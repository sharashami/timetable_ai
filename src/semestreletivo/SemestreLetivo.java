package semestreletivo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import utils.Definicoes;

/*@Table(name="semestre_letivo")
@Entity
@NamedQueries({//nome da classe
    @NamedQuery(name=Definicoes.SEMESTRE_LETIVO_LISTAR,query="SELECT sl FROM SemestreLetivo sl ORDER BY sl.semestre DESC"),
    @NamedQuery(name=Definicoes.SEMESTRE_LETIVO_INICIAR,query="UPDATE SemestreLetivo sl SET sl.ativo = false WHERE sl.id <> :id"),
    @NamedQuery(name=Definicoes.SEMESTRE_LETIVO_ATIVAR,query="UPDATE SemestreLetivo sl SET sl.ativo = true WHERE sl.id = :id"),
    @NamedQuery(name = Definicoes.SEMESTRE_LETIVO_COUNT, query="SELECT COUNT(sl) FROM SemestreLetivo sl")
}) */
public class SemestreLetivo {

	@Id
	@SequenceGenerator( name = "semestre_letivo_id", sequenceName = "semestre_letivo_seq", allocationSize = 1 )  
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "semestre_letivo_id" )
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(nullable=false, unique=true,length=6)
	private String semestre;
	
	@Column(nullable=false, columnDefinition="boolean default false")
	private boolean ativo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
}
