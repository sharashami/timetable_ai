package semana;

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

import utils.Definicoes;


@Table(name="periodo")
@Entity
@NamedQueries({
	@NamedQuery(name=Definicoes.PERIODO_COUNT,query="SELECT COUNT(c) FROM Periodo c")
})
public class Periodo  implements Serializable{


	@Id
	@SequenceGenerator( name = "periodo_id", sequenceName = "periodo_seq", allocationSize = 1 )  
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "periodo_id" )
	@Column(name = "id", nullable = false)
	private Integer id;

	private String descricao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
