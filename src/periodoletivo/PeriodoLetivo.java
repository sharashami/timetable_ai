package periodoletivo;

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

@Table(name="periodo_letivo")
@Entity
@NamedQueries({//nome da classe
    @NamedQuery(name=Definicoes.PERIODO_LETIVO_GET_ATIVO,query="SELECT sl FROM PeriodoLetivo sl WHERE sl.ativo = true"),
    @NamedQuery(name=Definicoes.PERIODO_LETIVO_LISTAR,query="SELECT sl FROM PeriodoLetivo sl ORDER BY sl.descricao DESC"),
    @NamedQuery(name=Definicoes.PERIODO_LETIVO_INICIAR,query="UPDATE PeriodoLetivo sl SET sl.ativo = false WHERE sl.id <> :id"),
    @NamedQuery(name=Definicoes.PERIODO_LETIVO_ATIVAR,query="UPDATE PeriodoLetivo sl SET sl.ativo = true WHERE sl.id = :id"),
    @NamedQuery(name = Definicoes.PERIODO_LETIVO_COUNT, query="SELECT COUNT(sl) FROM PeriodoLetivo sl")
}) 
public class PeriodoLetivo {

	@Id
	@SequenceGenerator( name = "periodo_letivo_id", sequenceName = "periodo_letivo_seq", allocationSize = 1 )  
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "periodo_letivo_id" )
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(nullable=false, unique=true,length=6)
	private String descricao;
	
	@Column(nullable=false, columnDefinition="boolean default false")
	private boolean ativo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + id;
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
		PeriodoLetivo other = (PeriodoLetivo) obj;
		if (ativo != other.ativo)
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
}
