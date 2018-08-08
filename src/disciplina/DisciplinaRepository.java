package disciplina;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import professor.Professor;
import semana.Turno;
import sun.security.x509.DistributionPointName;
import utils.Definicoes;
import utils.exception.ApplicationException;

public class DisciplinaRepository {
	private EntityManager entityManager;

	public DisciplinaRepository(EntityManager entityManager){
		this.entityManager = entityManager;
	}

	public void adicionar(Disciplina d) throws ApplicationException {
		try {
			this.entityManager.persist(d);
			this.entityManager.flush();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}

	public Long getCount() {
		return (Long) this.entityManager.createNamedQuery(Definicoes.DISCIPLINA_COUNT)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List getDisciplinas() {
		return this.entityManager.createNamedQuery(Definicoes.DISCIPLINAS_LISTAR)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List getDisciplinasBuscar(long idEixo, long idCurso, long idSemestre, long idTurno) {

		List<Disciplina> disiplinasFinal = new ArrayList<Disciplina>();
		
		if(idSemestre == 0  && idTurno == 0){
			//String query="SELECT d FROM Disciplina d, DisciplinaOferta do WHERE d.id = do.disciplina.id AND do.disciplina.curso.id = :curso)";
			String query="SELECT DISTINCT do.disciplina.id FROM DisciplinaOferta do WHERE do.disciplina.curso.id = :curso";
			Query q  = this.entityManager.createQuery(query);
			q = q.setParameter("curso", idCurso);
			
			List<Long> disiplinasOfertas = new ArrayList<Long>();
			disiplinasOfertas = q.getResultList();
			
			for (int i = 0; i < disiplinasOfertas.size(); i++){
				Long d = disiplinasOfertas.get(i);
				
				query="SELECT d FROM Disciplina d, DisciplinaOferta do WHERE do.disciplina.id = :ofertada";
				q  = this.entityManager.createQuery(query);
				q = q.setParameter("ofertada", d);
				List<Disciplina> disiplinas = new ArrayList<Disciplina>();
				disiplinas = q.getResultList();
				
			
				String query2 = "SELECT do FROM Disciplina d, DisciplinaOferta do WHERE do.disciplina.id = :idOfertada";
				q  = this.entityManager.createQuery(query2);
				q = q.setParameter("idOfertada", d);
				
				List<DisciplinaOferta> disiplinasOfertas2 = new ArrayList<DisciplinaOferta>();
				disiplinasOfertas2 = q.getResultList();
				
				
				for (int j = 0; j < disiplinas.size(); j++){
					Disciplina d1 = new Disciplina(); 
					d1.setAtivo(disiplinas.get(j).isAtivo());
					d1.setCodigo(disiplinas.get(j).getCodigo());
					d1.setCredito(disiplinas.get(j).getCredito());
					d1.setCurso(disiplinas.get(j).getCurso());
					d1.setDescricao(disiplinas.get(j).getDescricao());
					d1.setId(disiplinas.get(j).getId());
					d1.setOfertada(disiplinas.get(j).isOfertada());
					d1.setSemestre(disiplinas.get(j).getSemestre());
					
					
					
					if(disiplinasOfertas2.get(j).getTurno().getId() == 1)
					{
						d1.setTurno("Manha");
					}else if(disiplinasOfertas2.get(j).getTurno().getId() == 2){
						d1.setTurno("Tarde");
					}else if(disiplinasOfertas2.get(j).getTurno().getId() == 3){
						d1.setTurno("Noite");
					}
					d1.setOfertada(true);
					disiplinasFinal.add(d1);
				}
				
				//if(disiplinas.size() < 3){ //numero de 
					query = "SELECT d FROM Disciplina d WHERE d.curso.id = :curso AND d.id = :id";
					q  = this.entityManager.createQuery(query);
					q = q.setParameter("curso", idCurso).setParameter("id", d);
					
					List<Disciplina> disiplinas2 = new ArrayList<Disciplina>();
					disiplinas2 = q.getResultList();
					
					for (int a = 0; a < disiplinas2.size(); a++){
						Disciplina d2 = disiplinas2.get(a);
						d2.setTurno(null);
						d2.setOfertada(false);
						disiplinasFinal.add(d2);
					}
				//}
				
			}
			
		}else if(idSemestre != 0  && idTurno == 0){
			String query="SELECT DISTINCT do.disciplina.id FROM DisciplinaOferta do WHERE do.disciplina.curso.id = :curso AND do.disciplina.semestre = :semestre";
			Query q  = this.entityManager.createQuery(query);
			q = q.setParameter("curso", idCurso).setParameter("semestre", (int)idSemestre);
			
			List<Long> disiplinasOfertas = new ArrayList<Long>();
			disiplinasOfertas = q.getResultList();
			
			for (int i = 0; i < disiplinasOfertas.size(); i++){
				Long d = disiplinasOfertas.get(i);
				
				query="SELECT d FROM Disciplina d, DisciplinaOferta do WHERE do.disciplina.id = :ofertada AND do.disciplina.semestre = :semestre";
				q  = this.entityManager.createQuery(query);
				q = q.setParameter("ofertada", d).setParameter("semestre", (int)idSemestre);;
				List<Disciplina> disiplinas = new ArrayList<Disciplina>();
				disiplinas = q.getResultList();
				
			
				String query2 = "SELECT do FROM Disciplina d, DisciplinaOferta do WHERE do.disciplina.id = :idOfertada AND do.disciplina.semestre = :semestre";
				q  = this.entityManager.createQuery(query2);
				q = q.setParameter("idOfertada", d).setParameter("semestre", (int)idSemestre);
				
				List<DisciplinaOferta> disiplinasOfertas2 = new ArrayList<DisciplinaOferta>();
				disiplinasOfertas2 = q.getResultList();
				
				
				for (int j = 0; j < disiplinas.size(); j++){
					Disciplina d1 = new Disciplina(); 
					d1.setAtivo(disiplinas.get(j).isAtivo());
					d1.setCodigo(disiplinas.get(j).getCodigo());
					d1.setCredito(disiplinas.get(j).getCredito());
					d1.setCurso(disiplinas.get(j).getCurso());
					d1.setDescricao(disiplinas.get(j).getDescricao());
					d1.setId(disiplinas.get(j).getId());
					d1.setOfertada(disiplinas.get(j).isOfertada());
					d1.setSemestre(disiplinas.get(j).getSemestre());
					
					
					
					if(disiplinasOfertas2.get(j).getTurno().getId() == 1)
					{
						d1.setTurno("Manha");
					}else if(disiplinasOfertas2.get(j).getTurno().getId() == 2){
						d1.setTurno("Tarde");
					}else if(disiplinasOfertas2.get(j).getTurno().getId() == 3){
						d1.setTurno("Noite");
					}
					d1.setOfertada(true);
					disiplinasFinal.add(d1);
				}
				
				//if(disiplinas.size() < 3){ //numero de 
					query = "SELECT d FROM Disciplina d WHERE d.curso.id = :curso AND d.id = :id AND d.semestre = :semestre";
					q  = this.entityManager.createQuery(query);
					q = q.setParameter("curso", idCurso).setParameter("id", d).setParameter("semestre", (int)idSemestre);
					
					List<Disciplina> disiplinas2 = new ArrayList<Disciplina>();
					disiplinas2 = q.getResultList();
					
					for (int a = 0; a < disiplinas2.size(); a++){
						Disciplina d2 = disiplinas2.get(a);
						d2.setTurno(null);
						d2.setOfertada(false);
						disiplinasFinal.add(d2);
					//}
				}
				
			}
			

		}else if(idSemestre == 0  && idTurno != 0){
			
			String query="SELECT DISTINCT do.disciplina.id FROM DisciplinaOferta do WHERE do.disciplina.curso.id = :curso AND do.turno.id = :turno";
			Query q  = this.entityManager.createQuery(query);
			q = q.setParameter("curso", idCurso).setParameter("turno", (int)idTurno);
			
			List<Long> disiplinasOfertas = new ArrayList<Long>();
			disiplinasOfertas = q.getResultList();
			
			for (int i = 0; i < disiplinasOfertas.size(); i++){
				Long d = disiplinasOfertas.get(i);
				
				query="SELECT d FROM Disciplina d, DisciplinaOferta do WHERE do.disciplina.id = :ofertada AND do.turno.id = :turno";
				q  = this.entityManager.createQuery(query);
				q = q.setParameter("ofertada", d).setParameter("turno", (int)idTurno);
				List<Disciplina> disiplinas = new ArrayList<Disciplina>();
				disiplinas = q.getResultList();
				
			
				String query2 = "SELECT do FROM Disciplina d, DisciplinaOferta do WHERE do.disciplina.id = :idOfertada AND do.turno.id = :turno";
				q  = this.entityManager.createQuery(query2);
				q = q.setParameter("idOfertada", d).setParameter("turno", (int)idTurno);
				
				List<DisciplinaOferta> disiplinasOfertas2 = new ArrayList<DisciplinaOferta>();
				disiplinasOfertas2 = q.getResultList();
				
				
				for (int j = 0; j < disiplinas.size(); j++){
					Disciplina d1 = new Disciplina(); 
					d1.setAtivo(disiplinas.get(j).isAtivo());
					d1.setCodigo(disiplinas.get(j).getCodigo());
					d1.setCredito(disiplinas.get(j).getCredito());
					d1.setCurso(disiplinas.get(j).getCurso());
					d1.setDescricao(disiplinas.get(j).getDescricao());
					d1.setId(disiplinas.get(j).getId());
					d1.setOfertada(disiplinas.get(j).isOfertada());
					d1.setSemestre(disiplinas.get(j).getSemestre());
					
					
					
					if(disiplinasOfertas2.get(j).getTurno().getId() == 1)
					{
						d1.setTurno("Manha");
					}else if(disiplinasOfertas2.get(j).getTurno().getId() == 2){
						d1.setTurno("Tarde");
					}else if(disiplinasOfertas2.get(j).getTurno().getId() == 3){
						d1.setTurno("Noite");
					}
					d1.setOfertada(true);
					disiplinasFinal.add(d1);
				}
				
			}
			
			
		}else if(idSemestre != 0  && idTurno != 0){
			
			String query="SELECT DISTINCT do.disciplina.id FROM DisciplinaOferta do WHERE do.disciplina.curso.id = :curso AND do.disciplina.semestre = :semestre AND do.turno.id = :turno";
			Query q  = this.entityManager.createQuery(query);
			q = q.setParameter("curso", idCurso).setParameter("semestre", (int)idSemestre).setParameter("turno", (int)idTurno);
			
			List<Long> disiplinasOfertas = new ArrayList<Long>();
			disiplinasOfertas = q.getResultList();
			
			for (int i = 0; i < disiplinasOfertas.size(); i++){
				Long d = disiplinasOfertas.get(i);
				
				query="SELECT d FROM Disciplina d, DisciplinaOferta do WHERE do.disciplina.id = :ofertada AND do.disciplina.semestre = :semestre AND do.turno.id = :turno";
				q  = this.entityManager.createQuery(query);
				q = q.setParameter("ofertada", d).setParameter("semestre", (int)idSemestre).setParameter("turno", (int)idTurno);
				List<Disciplina> disiplinas = new ArrayList<Disciplina>();
				disiplinas = q.getResultList();
				
			
				String query2 = "SELECT do FROM Disciplina d, DisciplinaOferta do WHERE do.disciplina.id = :idOfertada AND do.disciplina.semestre = :semestre AND do.turno.id = :turno";
				q  = this.entityManager.createQuery(query2);
				q = q.setParameter("idOfertada", d).setParameter("semestre", (int)idSemestre).setParameter("turno", (int)idTurno);
				
				List<DisciplinaOferta> disiplinasOfertas2 = new ArrayList<DisciplinaOferta>();
				disiplinasOfertas2 = q.getResultList();
				
				
				for (int j = 0; j < disiplinas.size(); j++){
					Disciplina d1 = new Disciplina(); 
					d1.setAtivo(disiplinas.get(j).isAtivo());
					d1.setCodigo(disiplinas.get(j).getCodigo());
					d1.setCredito(disiplinas.get(j).getCredito());
					d1.setCurso(disiplinas.get(j).getCurso());
					d1.setDescricao(disiplinas.get(j).getDescricao());
					d1.setId(disiplinas.get(j).getId());
					d1.setOfertada(disiplinas.get(j).isOfertada());
					d1.setSemestre(disiplinas.get(j).getSemestre());
				
					
					if(disiplinasOfertas2.get(j).getTurno().getId() == 1)
					{
						d1.setTurno("Manha");
					}else if(disiplinasOfertas2.get(j).getTurno().getId() == 2){
						d1.setTurno("Tarde");
					}else if(disiplinasOfertas2.get(j).getTurno().getId() == 3){
						d1.setTurno("Noite");
					}
					d1.setOfertada(true);
					disiplinasFinal.add(d1);
				}
				
			}
			
		}
		
		return disiplinasFinal;
  
	}

	public void editar(Disciplina d) throws ApplicationException {
		try{
			this.entityManager.merge(d);
			this.entityManager.flush();

		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}

	public Disciplina remover(Disciplina d) {
		Disciplina disciplinaTemp = new Disciplina();
		disciplinaTemp = this.entityManager.find(Disciplina.class, d.getId());
		this.entityManager.remove(disciplinaTemp);
		return d;
	}

	public Disciplina buscar(Long codigo) {
		return entityManager.find(Disciplina.class , codigo);
	}
}
