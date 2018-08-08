package curso;

import java.util.List;

import javax.persistence.EntityManager;

import utils.Definicoes;

public class CursosPeriodosRepository {

	private EntityManager entityManager;

	public CursosPeriodosRepository(EntityManager entityManager){
		this.entityManager = entityManager;
	}

	public Curso editar(Curso e) {
		entityManager.merge(e);
	    this.entityManager.flush();
		return e;
	}

	public CursosPeriodos adicionar(CursosPeriodos e) {
		entityManager.persist(e);
	    this.entityManager.flush();
		return e;
	}

	public CursosPeriodos remover(CursosPeriodos e) {
		CursosPeriodos temp = new CursosPeriodos();
	    temp = (CursosPeriodos) this.entityManager.createNamedQuery(Definicoes.PERIODO_GET).setParameter("idCurso", e.getCurso().getId()).setParameter("idTurno", e.getTurno().getId()).getSingleResult();
	    this.entityManager.remove(temp);
		return temp;
	}


    @SuppressWarnings("unchecked")
	public List getCursos() {
		return this.entityManager.createNamedQuery(Definicoes.CURSO_LISTAR).getResultList();
	}

    public Long getCount() {
        return (Long) this.entityManager.createNamedQuery(Definicoes.CURSO_COUNT)
                .getSingleResult();
    }

    public Curso buscar(Long ID){
        return this.entityManager.find(Curso.class, ID);
    }

    @SuppressWarnings("unchecked")
	public List getPeriodosCurso(Curso curso) {
		return this.entityManager.createNamedQuery(Definicoes.PERIODO_DO_CURSO).setParameter("idCurso", curso.getId()).getResultList();
	}
}
