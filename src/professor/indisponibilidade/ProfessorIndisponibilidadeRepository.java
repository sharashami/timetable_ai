package professor.indisponibilidade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import professor.Professor;
import semana.DiaDaSemana;
import utils.Definicoes;
import utils.exception.ApplicationException;

public class ProfessorIndisponibilidadeRepository {
	private EntityManager entityManager;
	
	public ProfessorIndisponibilidadeRepository(EntityManager entityManager){
		this.entityManager = entityManager;
		
	}
	@SuppressWarnings("unchecked")
    public List getIndisponibilidadeProfessor(Professor prof) {

    	return this.entityManager.createNamedQuery(Definicoes.PROFESSOR_INDISPONIBILIDADE).setParameter("idProf", prof.getId()).getResultList();
    	
    }

    public void adicionar(ProfessorIndisponibilidade p) throws ApplicationException {
        try {
			this.entityManager.persist(p);
			this.entityManager.flush();
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}

    public void remover(ProfessorIndisponibilidade p) throws ApplicationException {
    	 try{
    		 ProfessorIndisponibilidade professorTemp = new ProfessorIndisponibilidade();
    		 
    		 professorTemp = (ProfessorIndisponibilidade) this.entityManager.createNamedQuery(Definicoes.PROFESSOR_INDISPONIBILIDADE_GET)
	    		.setParameter("idProf", p.getProfessor().getId()).setParameter("idTurno", p.getTurno().getId()).setParameter("idDia", p.getDiaDaSemana().getId()).setParameter("periodo", p.getPeriodo()).getSingleResult();
    		 
    		 
 		     //= this.entityManager.find(ProfessorIndisponibilidade.class, p.getId());
 		    this.entityManager.remove(professorTemp);
 		} catch (PersistenceException e) {
 			e.printStackTrace();
 			throw new ApplicationException(e);
 		}
	}

}
