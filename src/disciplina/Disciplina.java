package disciplina;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import curso.Curso;
import utils.Definicoes;

@Entity //inidicar que sera criado uma tabela no banco
@Table(name="disciplina")
@NamedQueries({//nome da classe
	 @NamedQuery(name=Definicoes.DISCIPLINAS_LISTAR, query="SELECT d FROM Disciplina d"),
	 @NamedQuery(name=Definicoes.DISCIPLINAS_BUSCAR, query="SELECT d FROM Disciplina d WHERE d.curso.id = :curso AND d.semestre = :semestre"),
	 //@NamedQuery(name=Definicoes.DISCIPLINAS_OFERTADAS, query="SELECT d FROM Disciplina d WHERE d.ofertada = true"),
	 @NamedQuery(name=Definicoes.DISCIPLINA_COUNT, query="SELECT COUNT(d) FROM Disciplina d")
})

public class Disciplina implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator( name = "disciplina_id", sequenceName = "disciplina_seq", allocationSize = 1 )  
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "disciplina_id" )
	@Column(name = "id", nullable = false)
	private Long id;

//	@OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL)//nome do campo que estara no objeto matrizcurricular
//	private Collection<MatrizCurricular> matrizCurricularList;
	 
	@Column(nullable=false, unique=false,length=100)
	private String descricao;

	@Column(nullable=false, unique=true,length=30)
	private String codigo;
	
	@Column(name="semestre", nullable=false, columnDefinition="integer default 0")
	private int semestre;

	@Column(nullable=false, columnDefinition="integer default 0")
	private int credito;


	@Column(nullable=false, columnDefinition="boolean default true")
	private boolean ativo = true;
	
	@Transient
	private boolean ofertada = false;
	
	@Transient
	private String turno;

	@OneToOne
	@JoinColumn(name="curso_id")
	private Curso curso;
		
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

//	public Collection<MatrizCurricular> getMatrizCurricularList() {
//		return matrizCurricularList;
//	}
//	
//	public void setMatrizCurricularList(
//			Collection<MatrizCurricular> matrizCurricularList) {
//		this.matrizCurricularList = matrizCurricularList;
//	}

	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getSemestre() {
		return semestre;
	}
	
	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	public int getCredito() {
		return credito;
	}
	
	public void setCredito(int credito) {
		this.credito = credito;
	}

	public boolean isAtivo() {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Curso getCurso() {
		if(curso == null) curso = new Curso();
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public boolean isOfertada() {
		return ofertada;
	}

	public void setOfertada(boolean ofertada) {
		this.ofertada = ofertada;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + credito;
		result = prime * result + ((curso == null) ? 0 : curso.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (ofertada ? 1231 : 1237);
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
		Disciplina other = (Disciplina) obj;
		if (ativo != other.ativo)
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (credito != other.credito)
			return false;
		if (curso == null) {
			if (other.curso != null)
				return false;
		} else if (!curso.equals(other.curso))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ofertada != other.ofertada)
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
