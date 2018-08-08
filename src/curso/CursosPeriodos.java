package curso;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import semana.Turno;
import utils.Definicoes;


@Table(name="cursos_periodos")
@IdClass(CursosPeriodosPk.class)
@Entity
@NamedQueries({
	@NamedQuery(name=Definicoes.PERIODO_DO_CURSO,query="SELECT c FROM CursosPeriodos c WHERE c.curso.id = :idCurso"),
	@NamedQuery(name=Definicoes.PERIODO_GET,query="SELECT c FROM CursosPeriodos c WHERE c.curso.id = :idCurso AND c.turno.id = :idTurno")
})
public class CursosPeriodos  implements Serializable {

	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "curso_id", nullable = false)
	//@ManyToOne(optional=false)
	@Id
	private Curso curso;
	
	@JoinColumn(name = "turno_id", nullable = false)
	//@ManyToOne(optional=false)
	@Id
	private Turno turno;

	private int qtdePeriodos;
	


	public Turno getTurno() {
		return turno;
	}


	public void setTurno(Turno turno) {
		this.turno = turno;
	}


	public int getQtdePeriodos() {
		return qtdePeriodos;
	}


	public void setQtdePeriodos(int qtdePeriodos) {
		this.qtdePeriodos = qtdePeriodos;
	}


	public Curso getCurso() {
		return curso;
	}


	public void setCurso(Curso curso) {
		this.curso = curso;
	}

}
