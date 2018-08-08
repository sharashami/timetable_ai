package curso;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import utils.Definicoes;
import eixo.Eixo;

@Table(name="curso")
@Entity
@NamedQueries({
	@NamedQuery(name=Definicoes.CURSO_LISTAR,query="SELECT c FROM Curso c"),
	@NamedQuery(name=Definicoes.CURSO_LISTAR_EIXO,query="SELECT c FROM Curso c where c.eixo.id=:eixo"),
	@NamedQuery(name=Definicoes.CURSO_COUNT,query="SELECT COUNT(c) FROM Curso c")
})
public class Curso implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;	
	
/*
	@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)//nome do campo que estara no objeto matrizcurricular
	private Collection<MatrizCurricular> matrizCurricularList;*/
	
	@Column(nullable=false, unique=true,length=Definicoes.STRING_NOME)
	private String descricao;

	@Column(nullable=false, columnDefinition="boolean default false")
	private boolean ativo = true;
	
	@Column(nullable=false, columnDefinition="int default 8")
	private int semetre;

	@ManyToOne(optional=false)
	@JoinColumn(name = "eixo_id")
	private Eixo eixo;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
/*
	public Collection<MatrizCurricular> getMatrizCurricularList() {
		return matrizCurricularList;
	}
	public void setMatrizCurricularList(
			Collection<MatrizCurricular> matrizCurricularList) {
		this.matrizCurricularList = matrizCurricularList;
	}*/

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Eixo getEixo() {
		return eixo;
	}
	public void setEixo(Eixo eixo) {
		this.eixo = eixo;
	}
	public int getSemetre() {
		return semetre;
	}
	public void setSemetre(int semetre) {
		this.semetre = semetre;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((eixo == null) ? 0 : eixo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + semetre;
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
		Curso other = (Curso) obj;
		if (ativo != other.ativo)
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (eixo == null) {
			if (other.eixo != null)
				return false;
		} else if (!eixo.equals(other.eixo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (semetre != other.semetre)
			return false;
		return true;
	}
	
	
}
