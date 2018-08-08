package curso;

import java.io.Serializable;

import semana.Turno;

public class CursosPeriodosPk implements Serializable{

	private static final long serialVersionUID = 1L;

	private Turno turno;

	private Curso curso;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CursosPeriodosPk){
        	CursosPeriodosPk carPk = (CursosPeriodosPk) obj;
 
            if(!carPk.getCurso().equals(curso)){
                return false;
            }
 
            if(!carPk.getTurno().equals(turno)){
                return false;
            }
 
            return true;
        }
 
        return false;
    }
 
    @Override
    public int hashCode() {
        return curso.hashCode() + turno.hashCode();
    }

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
    

}
