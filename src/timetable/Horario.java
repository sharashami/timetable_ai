package timetable;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import periodoletivo.PeriodoLetivo;

@Entity
@Table(name="horario")
public class Horario {
	
	@Id
	@SequenceGenerator(name="horario_id", sequenceName="horario_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="horario_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="periodo_letivo_id")
	private PeriodoLetivo periodoLetivo;
	
	@OneToMany(mappedBy="horario",cascade=CascadeType.ALL)
	private List<Aula> aulas;
	
	
	public List<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(List<Aula> aulas) {
		this.aulas = aulas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PeriodoLetivo getPeriodoLetivo() {
		return periodoLetivo;
	}

	public void setPeriodoLetivo(PeriodoLetivo periodoLetivo) {
		this.periodoLetivo = periodoLetivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aulas == null) ? 0 : aulas.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((periodoLetivo == null) ? 0 : periodoLetivo.hashCode());
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
		Horario other = (Horario) obj;
		if (aulas == null) {
			if (other.aulas != null)
				return false;
		} else if (!aulas.equals(other.aulas))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (periodoLetivo == null) {
			if (other.periodoLetivo != null)
				return false;
		} else if (!periodoLetivo.equals(other.periodoLetivo))
			return false;
		return true;
	}

	
	
	
}
