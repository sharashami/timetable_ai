package matriz;



import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import utils.Definicoes;

public class MatrizCurricularRepository implements Serializable {

	
//	public MatrizCurricular editar(MatrizCurricular m) {
//		manager.getTransaction().begin();
//		manager.merge(m);
//		manager.getTransaction().commit();
//		return m;
//	}
//
//	public MatrizCurricular adicionar(MatrizCurricular m) {
//		this.entityManager.persist(m);
//        this.entityManager.flush();
//		return m;
//	}
//
//	public MatrizCurricular remover(MatrizCurricular m) {	
//		m = buscar(m.getId());
//		manager.getTransaction().begin();
//		manager.remove(m);
//		manager.getTransaction().commit();
//		return m;
//	}
//	
//	public MatrizCurricular buscar(long codigo) {
//		return manager.find(MatrizCurricular.class , codigo);
//	}
//	
//	public java.util.List<MatrizCurricular> martrizCurricularCurso(Curso c){
//		return this.manager.createNamedQuery(Definicoes.MATRIZ_CURRICULAR_CURSO, MatrizCurricular.class).setParameter("cursoParam",c.getId()).getResultList();
//	}
	
	
private EntityManager entityManager;
	
	public MatrizCurricularRepository(EntityManager entityManager){
		this.entityManager = entityManager;
	}

	public MatrizCurricular adicionar(MatrizCurricular m) {
		this.entityManager.persist(m);
        this.entityManager.flush();
		return m;
	}
	
	public Long getCount() {
        return (Long) this.entityManager.createNamedQuery(Definicoes.DISCIPLINA_COUNT)
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List getMatriz() {
        return this.entityManager.createNamedQuery(Definicoes.DISCIPLINAS_LISTAR)
                .getResultList();
    }
    
    public MatrizCurricular editar(MatrizCurricular m) {
		this.entityManager.merge(m);
	      this.entityManager.flush();
		return m;
	}
	
	public MatrizCurricular remover(MatrizCurricular m) {
		MatrizCurricular matrizTemp = new MatrizCurricular();
		matrizTemp = this.entityManager.find(MatrizCurricular.class, m.getId());
	    this.entityManager.remove(matrizTemp);
		return m;
	}
	
	public MatrizCurricular buscar(int codigo) {
		return entityManager.find(MatrizCurricular.class , codigo);
	}

}
