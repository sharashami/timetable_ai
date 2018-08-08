package eixo;

import java.util.List;

import javax.persistence.EntityManager;

import utils.Definicoes;

public class EixoRepository {

	private EntityManager entityManager;

	public EixoRepository(EntityManager entityManager){
		this.entityManager = entityManager;
	}

	public Eixo editar(Eixo e) {
		entityManager.merge(e);
	    this.entityManager.flush();
		return e;
	}

	public Eixo adicionar(Eixo e) {
		entityManager.persist(e);
	    this.entityManager.flush();
		return e;
	}

	public Eixo remover(Eixo e) {
		Eixo temp = new Eixo();
	    temp = this.entityManager.find(Eixo.class, e.getId());
	    this.entityManager.remove(temp);
		return e;
	}

    @SuppressWarnings("unchecked")
	public List getEixos() {
		return this.entityManager.createNamedQuery(Definicoes.EIXO_LISTAR).getResultList();
	}

    public Long getCount() {
        return (Long) this.entityManager.createNamedQuery(Definicoes.EIXO_COUNT)
                .getSingleResult();
    }
    
    public Eixo buscar(Integer ID){
        return this.entityManager.find(Eixo.class, ID);
    }
}
