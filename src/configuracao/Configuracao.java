package configuracao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import utils.Definicoes;

import com.sun.istack.internal.NotNull;

@Entity //inidicar que sera criado uma tabela no banco
@Table(name="configuracao")
@NamedQueries({//nome da classe
    @NamedQuery(name=Definicoes.CONFIGURACAO_GET,query="SELECT p FROM Configuracao p WHERE p.nome = :nome")
}) 

public class Configuracao  implements Serializable {
	@Id
	@SequenceGenerator( name = "configuracao_id", sequenceName = "configuracao_seq", allocationSize = 1 )  
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "configuracao_id" )
	@Column(name = "id", nullable = false)
	private Long id;
	

	@Column(nullable=false, unique=true,length=200)
	@NotNull
	private String nome;
	

	@Column(nullable=false,length=200)
	@NotNull
	private String valor;

	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		Configuracao other = (Configuracao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
}
