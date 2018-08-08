package disciplina;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import semana.Turno;
import utils.Definicoes;
import utils.exception.ApplicationException;

public class OfertaDisciplinaRepository {
	private EntityManager entityManager;
	
	public OfertaDisciplinaRepository(EntityManager entityManager){
		this.entityManager = entityManager;
	}

	public DisciplinaOferta ministrar(DisciplinaOferta d) {
		this.entityManager.persist(d);
        this.entityManager.flush();
		return d;
	}

    @SuppressWarnings("unchecked")
	public java.util.List getDisciplinasOfertadas() {
		return this.entityManager.createNamedQuery(Definicoes.DISCIPLINA_OFERTA_LISTAR).getResultList();
	}
    
    public void ofertar(long idDisciplina, long idTurno) throws ApplicationException {
		Disciplina disciplinaTemp = new Disciplina();
		disciplinaTemp = this.entityManager.find(Disciplina.class, idDisciplina);
		//FIXME TESTE 01System.out.println("Disciplina: "+ disciplinaTemp);
	
		Turno turno = new Turno();
		turno = this.entityManager.find(Turno.class, (int)idTurno);
		//FIXME TESTE 01System.out.println("Turno: "+ turno);
		
		DisciplinaOferta disciplicaOferta = new DisciplinaOferta();
		disciplicaOferta.setDisciplina(disciplinaTemp);
		disciplicaOferta.setCreditosRestantes(disciplinaTemp.getCredito());
		disciplicaOferta.setCreditosTotais(disciplinaTemp.getCredito());
		disciplicaOferta.setTurno(turno);
		//FIXME TESTE 01System.out.println("Disciplina Oferta: "+ disciplicaOferta);
		
		try {
			this.entityManager.persist(disciplicaOferta);
			this.entityManager.flush();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}

}
