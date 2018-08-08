package semestreletivo;

import java.util.List;

import javax.persistence.EntityManager;

import utils.Definicoes;

public class SemestreLetivoRepository {
	private EntityManager entityManager;

	public SemestreLetivoRepository(EntityManager entityManager){
		this.entityManager = entityManager;
		
	}

	public void inserir(SemestreLetivo s) {
		 this.entityManager.persist(s);
	     this.entityManager.flush();
	}

    public Long getCount() {
        return (Long) this.entityManager.createNamedQuery(Definicoes.SEMESTRE_LETIVO_COUNT)
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List getSemestresLetivos() {
        return this.entityManager.createNamedQuery(Definicoes.SEMESTRE_LETIVO_LISTAR)
                .getResultList();
    }
    
	public void remover(SemestreLetivo s) {
		SemestreLetivo temp = new SemestreLetivo();
		temp = this.entityManager.find(SemestreLetivo.class, s.getId());
	    this.entityManager.remove(temp);
	}
	
	public void editar(SemestreLetivo s) {
		 this.entityManager.merge( s);
	      this.entityManager.flush();
	}
	public void iniciarSemestreLetivo(SemestreLetivo s) {
		SemestreLetivo temp = new SemestreLetivo();
		temp = this.entityManager.find(SemestreLetivo.class, s.getId());
		this.entityManager.createNamedQuery(Definicoes.SEMESTRE_LETIVO_INICIAR).setParameter("id", temp.getId()).executeUpdate();
		this.entityManager.createNamedQuery(Definicoes.SEMESTRE_LETIVO_ATIVAR).setParameter("id", temp.getId()).executeUpdate();
	}
	
/*
	public SemestreLetivo ler(long codigo) {
		return entityManager.find( SemestreLetivo.class , codigo);
	}
	
	*//**
	 * Ativa o semestre em quest�o e desativa os demais. Seta o creditosLivres = creditoTotais em Professor cujo ativo = true.
	 *//*
	public void iniciarSemestreLetivo(SemestreLetivo s) {
		this.entityManager.createNamedQuery("UPDATE SemestreLetivo sl SET sl.ativo = false", SemestreLetivo.class).executeUpdate();
		entityManager.getTransaction().begin();
		entityManager.merge(s);
		new ProfessorRepository(entityManager).resetarCreditoLivre();
		entityManager.getTransaction().commit();
		
	}*/
}
