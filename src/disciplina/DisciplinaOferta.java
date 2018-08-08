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

import semana.Turno;
import utils.Definicoes;

@Table(name="disciplina_oferta")
@Entity
@NamedQueries({//nome da classe
	 @NamedQuery(name=Definicoes.DISCIPLINA_OFERTA_LISTAR, query="SELECT d FROM DisciplinaOferta d")
})
public class DisciplinaOferta implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator( name = "disciplina_oferta_id", sequenceName = "disciplina_oferta_seq", allocationSize = 1 )  
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "disciplina_oferta_id" )
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="turno_id", nullable = false)//FK para turno
	private Turno turno;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="disciplina_id", nullable = false)//FK para disciplina
	private Disciplina disciplina;
	
	@Column(nullable=false, columnDefinition="integer default 0")
	private int creditosTotais;
	
	@Column(nullable=false, columnDefinition="integer default 0")
	private int creditosRestantes;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public int getCreditosTotais() {
		return creditosTotais;
	}

	public void setCreditosTotais(int creditosTotais) {
		this.creditosTotais = creditosTotais;
	}

	public int getCreditosRestantes() {
		return creditosRestantes;
	}

	public void setCreditosRestantes(int creditosRestantes) {
		this.creditosRestantes = creditosRestantes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + creditosRestantes;
		result = prime * result + creditosTotais;
		result = prime * result
				+ ((disciplina == null) ? 0 : disciplina.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DisciplinaOferta other = (DisciplinaOferta) obj;
		if (creditosRestantes != other.creditosRestantes)
			return false;
		if (creditosTotais != other.creditosTotais)
			return false;
		if (disciplina == null) {
			if (other.disciplina != null)
				return false;
		} else if (!disciplina.equals(other.disciplina))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (turno == null) {
			if (other.turno != null)
				return false;
		} else if (!turno.equals(other.turno))
			return false;
		return true;
	}
}
