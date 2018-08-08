package professor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import professor.indisponibilidade.ProfessorIndisponibilidade;
import utils.Definicoes;
import utils.exception.ApplicationException;

public class ProfessorRepository {
	private EntityManager entityManager;
	
	public ProfessorRepository(EntityManager entityManager){
		this.entityManager = entityManager;
		
	}
	public void adicionar(Professor p) throws ApplicationException {
        try {
			this.entityManager.persist(p);
			this.entityManager.flush();
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}

    public void adicionarIndisponibilidade(ProfessorIndisponibilidade p) throws ApplicationException {
        try {
			this.entityManager.persist(p);
			this.entityManager.flush();
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}
    public Long getCountProfessores() {
        return (Long) this.entityManager.createNamedQuery(Definicoes.PROFESSOR_COUNT)
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List getProfessores() {

    	return this.entityManager.createNamedQuery(Definicoes.PROFESSOR_LISTAR).getResultList();
    	
    }

    @SuppressWarnings("unchecked")
    public List getProfessoresByNome(String token) {
        return this.entityManager.createNamedQuery(Definicoes.PROFESSOR_LISTAR_POR_NOME).setParameter("nome", "%"+token+"%")
                .getResultList();
    }
    
    public Professor getProfessor(Professor professor) {
    	Professor professorTemp = null;
    	
    	//buscar por email, esta sendo implementado inicialmente por email, deve ser geral posteriormente
    	if(professor.getLogin() != null)
    	{
    		
    		try {  

    			professorTemp =  (Professor) this.entityManager.createNamedQuery(Definicoes.PROFESSOR_GET_LOGIN)
    		    		.setParameter("login", professor.getLogin()).getSingleResult();   
    		    
    		}
    		catch(NoResultException e){
    			
    			professorTemp = null;  
    		}
    		
    		
    	}else if(professor.getId() != null && professor.getId() != 0){
    		professorTemp = new Professor();
    	    professorTemp = this.entityManager.find(Professor.class, professor.getId());
    	}
	    
	    return professorTemp;
    }

    public void remover(Professor professor)  throws ApplicationException {
	    try{
    		Professor professorTemp = new Professor();
		    professorTemp = this.entityManager.find(Professor.class, professor.getId());
		    this.entityManager.remove(professorTemp);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
    }

	public void editar(Professor professor) throws ApplicationException {
		try{
			Professor professorTemp = new Professor();
    	    professorTemp = this.entityManager.find(Professor.class, professor.getId());
    	    professorTemp.setId(professor.getId());
    	    professorTemp.setAdministrador(professor.isAdministrador());
    	    professorTemp.setAtivo(professor.isAtivo());
    	    professorTemp.setCpf(professor.getCpf());
    	    professorTemp.setCreditosTotais(professor.getCreditosTotais());
    	    professorTemp.setNome(professor.getNome());
    	    professorTemp.setLogin(professor.getLogin());
    	    professorTemp.setChaveEmail(professor.getChaveEmail());
    	    professorTemp.setAtivo(professor.isAtivo());
    	    this.entityManager.flush();

		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}


	public void resetarCreditoLivre() {
		this.entityManager.createNamedQuery(Definicoes.PROFESSOR_RESETAR_CRED_LIVRE).executeUpdate();
	      this.entityManager.flush();
	}
	/*
	public Professor editar(Professor p) {
		entityManager.getTransaction().begin();
		entityManager.merge(p);
		entityManager.getTransaction().commit();
		return p;
	}


	public Professor remover(Professor p) {	
		p = ler(p.getId());
		entityManager.getTransaction().begin();
		entityManager.remove(p);
		entityManager.getTransaction().commit();
		return p;
	}

	public Professor ler(long codigo) {
		return entityManager.find( Professor.class , codigo);
	}
	
	public java.util.List<Professor> lerLista(){
		return this.entityManager.createNamedQuery(Definicoes.PROFESSOR_LISTAR, Professor.class).getResultList();
	}
	
	public void resetarCreditoLivre() {
		  this.entityManager.createNamedQuery(Definicoes.PROFESSOR_RESETAR_CRED_LIVRE, Professor.class).executeUpdate();
	}*/
}
