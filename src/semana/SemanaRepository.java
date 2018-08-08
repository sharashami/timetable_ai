package semana;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import professor.Professor;
import utils.Definicoes;
import utils.exception.ApplicationException;

public class SemanaRepository {

	private EntityManager entityManager;

	public SemanaRepository(EntityManager entityManager){
		this.entityManager = entityManager;
	}

    @SuppressWarnings("unchecked")
	public List getTurnos() {
		return this.entityManager.createNamedQuery(Definicoes.TURNO_LISTAR).getResultList();
	}

    @SuppressWarnings("unchecked")
	public List getDiasSemana() {
		return this.entityManager.createNamedQuery(Definicoes.DIA_SEMANA_LISTAR).getResultList();
	}
    @SuppressWarnings("unchecked")
	public List getDiasSemana(int numeroDias) {
		return this.entityManager.createNamedQuery(Definicoes.DIA_SEMANA_LISTAR).setMaxResults(numeroDias).getResultList();
	}
    public DiaDaSemana getDiaDaSemana(int id) {
    	DiaDaSemana diaSemana = new DiaDaSemana();
    	diaSemana = this.entityManager.find(DiaDaSemana.class, id);
    	
	    return diaSemana;
    }

    public Turno getTurno(int id) {
    	Turno turno = new Turno();
    	turno = this.entityManager.find(Turno.class, id);
    	
	    return turno;
    }

    public Long getCountPeriodos() {
        return (Long) this.entityManager.createNamedQuery(Definicoes.PERIODO_COUNT)
                .getSingleResult();
    }

    public Turno buscarTurno(Integer ID){
        return this.entityManager.find(Turno.class, ID);
    }
    
}
