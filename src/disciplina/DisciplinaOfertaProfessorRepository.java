package disciplina;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import professor.Professor;
import professor.indisponibilidade.ProfessorIndisponibilidade;
import professor.indisponibilidade.ProfessorIndisponibilidadeRepository;
import semana.SemanaRepository;
import semana.Turno;
import timetable.ga.model.CourseOffers;
import timetable.ga.model.ShiftOffers;
import timetable.ga.model.Subject;
import timetable.ga.model.SubjectTeacherRelation;
import timetable.ga.model.Teacher;
import timetable.ga.model.Unavailability;
import utils.Definicoes;
import curso.Curso;
import curso.CursoRepository;

public class DisciplinaOfertaProfessorRepository {
	private EntityManager entityManager;
	
	public DisciplinaOfertaProfessorRepository(EntityManager entityManager){
		this.entityManager = entityManager;
	}

	public DisciplinaOferta ministrar(DisciplinaOferta d) {
		this.entityManager.persist(d);
        this.entityManager.flush();
		return d;
	}

    @SuppressWarnings("unchecked")
	public java.util.List getDisciplinasOfertasProfessor() {
		return this.entityManager.createNamedQuery(Definicoes.DISCIPLINA_OFERTA_PROFESSOR_LISTAR).getResultList();
	}

    @SuppressWarnings("unchecked")
	public java.util.List<CourseOffers> getTimetableEntryData() {
    	ProfessorIndisponibilidadeRepository profIndisponRepo = new ProfessorIndisponibilidadeRepository(entityManager);
    	
    	List<Curso> cursos =  new CursoRepository(entityManager).getCursos();
    	List<Turno> turnos = new SemanaRepository(entityManager).getTurnos();
    	List<CourseOffers> ofertasPorCurso = new ArrayList<CourseOffers>();
    	
    	for (int l = 0; l < cursos.size(); l++) {
    		CourseOffers curso = new CourseOffers();
    		curso.setName(cursos.get(l).getDescricao());
			for (int j = 0; j < turnos.size(); j++) {
				java.util.List  ofertasCursoTurno = this.entityManager.createNamedQuery(Definicoes.DISCIPLINA_OFERTA_PROFESSOR_LISTAR_POR_CURSO_TURNO).setParameter("id_curso", cursos.get(l).getId()).setParameter("id_turno", turnos.get(j).getId()).getResultList();
		    	java.util.List<SubjectTeacherRelation> courseShiftOffer = new ArrayList<SubjectTeacherRelation>();
				
		    	for(int i = 0; i < ofertasCursoTurno.size(); i++){
		    		
		    		DisciplinaOfertaProfessor d =(DisciplinaOfertaProfessor) ofertasCursoTurno.get(i);
		    		Disciplina disc = d.getDisciplinaOferta().getDisciplina();
		    		Professor professor = d.getProfessor();
		    		List<ProfessorIndisponibilidade> lProfIndisp = profIndisponRepo.getIndisponibilidadeProfessor(professor);
		    		List<Teacher> lProfessor= new ArrayList<Teacher>();
		    		
		    		
		    		Teacher timetableTeacher =new Teacher(professor.getNomeBreve(), professor.getId().intValue(),professor.getCreditosTotais()-professor.getCreditosLivres()); 
		    		//adiciona a indiponibilidade
		    		Unavailability unavailability = new Unavailability();
		    		for(int k = 0; k < lProfIndisp.size(); k++){
		    			unavailability.setUnavailable(lProfIndisp.get(k).getDiaDaSemana().getId(),lProfIndisp.get(k).getTurno().getId());
		    		}
		    		timetableTeacher.setUnavailability(unavailability);
		    		
		    		lProfessor.add(timetableTeacher);
		    		
		    		SubjectTeacherRelation s = new SubjectTeacherRelation();
		    		s.setCode(d.getId());
		    		s.setSemester(disc.getSemestre());
		    		s.setShift(d.getDisciplinaOferta().getTurno().getId());
		    		s.setShiftName(d.getDisciplinaOferta().getTurno().getDescricao());
		    		//FIXME  nas situações de dois professores com mesma oferta, vem duas linhas separadas nao agrupadas, ou seja vai acabar criando dois registros para mesmo oferta
		    		s.setSubject(new Subject(disc.getDescricao(),disc.getId().intValue(), disc.getSemestre(),disc.getCredito(), lProfessor));
		    		 
		    		courseShiftOffer.add(s);
		    		//System.out.println(d.getDisciplinaOferta().getDisciplina().getDescricao()+ " "+d.getProfessor().getNomeBreve());
		    	}
				
				if(!ofertasCursoTurno.isEmpty()){
					ShiftOffers shiftOffers = new ShiftOffers();
					shiftOffers.setOffers(courseShiftOffer);
					curso.addShiftOffers(shiftOffers);
				}
			}
			if(!curso.getShiftOffers().isEmpty())
				ofertasPorCurso.add(curso);
		}
		return ofertasPorCurso;
	}

}
