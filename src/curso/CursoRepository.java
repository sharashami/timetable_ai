package curso;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import eixo.Eixo;
import professor.Professor;
import utils.Definicoes;

public class CursoRepository {

	private EntityManager entityManager;

	public CursoRepository(EntityManager entityManager){
		this.entityManager = entityManager;
	}

	public Curso editar(Curso e) {
		entityManager.merge(e);
	    this.entityManager.flush();
		return e;
	}

	public Curso adicionar(Curso e) {
		entityManager.persist(e);
	    this.entityManager.flush();
		return e;
	}

	public Curso remover(Curso e) {
		Curso temp = new Curso();
	    temp = this.entityManager.find(Curso.class, e.getId());
	    this.entityManager.remove(temp);
		return e;
	}


    @SuppressWarnings("unchecked")
	public List getCursos() {
		return this.entityManager.createNamedQuery(Definicoes.CURSO_LISTAR).getResultList();
	}
    
    public List getCursos(Eixo eixo) {
    	List cursos = null;
    		
    		try {  

    			cursos =  this.entityManager.createNamedQuery(Definicoes.CURSO_LISTAR_EIXO)
    		    		.setParameter("eixo", eixo.getId()).getResultList();   
    		    
    		}
    		catch(NoResultException e){
    			
    			cursos = null;  
    		}
    		
    		
  
	    
	    return cursos;
    }

    public Long getCount() {
        return (Long) this.entityManager.createNamedQuery(Definicoes.CURSO_COUNT)
                .getSingleResult();
    }

    public Curso buscar(Long ID){
        return this.entityManager.find(Curso.class, ID);
    }
}
