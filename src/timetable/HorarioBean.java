package timetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.startup.ClassLoaderFactory.RepositoryType;

import com.sun.org.apache.bcel.internal.generic.DADD;

import configuracao.ConfiguracaoBean;
import configuracao.ConfiguracaoBean.CONFIG_CHAVES;
import configuracao.ConfiguracaoRepository;
import disciplina.DisciplinaOfertaProfessorRepository;
import login.Hash;
import periodoletivo.PeriodoLetivo;
import periodoletivo.SemestreLetivoRepository;
import professor.Professor;
import professor.ProfessorRepository;
import professor.indisponibilidade.ProfessorIndisponibilidade;
import professor.indisponibilidade.ProfessorIndisponibilidadeRepository;
import semana.DiaDaSemana;
import semana.SemanaRepository;
import semana.Turno;
import timetable.ga.app.IEntryData;
import timetable.ga.app.ITimetableParameters;
import timetable.ga.app.TimetableApp;
import timetable.ga.model.CourseOffers;
import timetable.ga.model.SubjectTeacherRelation;
import utils.exception.ApplicationException;

@ManagedBean
@SessionScoped
public class HorarioBean  implements Serializable{
	/**
	 * 	comp. (1 a 8) + redes pela manha (1 nova grade)
		comp (1) + info pela tarde (1 a 4)
		redes a noite (dois ultimo da grade antiga)
	 */
	private static final long serialVersionUID = 1L;
    private transient List<CourseOffers> resultado = new  ArrayList<CourseOffers>();
    private transient List<DiaDaSemana> diasSemana;
    
    private transient boolean mostrarTabela = false;
    private transient PeriodoLetivo periodoLetivo;

	private TimetableApp timetableApp;
	
    public String gerarHorario(){
    	//pegar de tabela configurações
    	
		ITimetableParameters parameters = new ITimetableParameters() {
	    	ConfiguracaoBean confBean = new ConfiguracaoBean();

			@Override
			public int getPopulationSize() {
				return confBean.agTamanhoPopulacao();
			}
			
			@Override
			public int getNumberOfSlotsPerSemesters() {
				return confBean.agSlotsPorSemestre();
			}
			
			@Override
			public int getNumberOfDays() {
				return confBean.numeroDias();
			}
			
			@Override
			public int getCreditsPerSlot() {
				return confBean.agCreditosPorSlot();
			}

			@Override
			public int getMutatioRate() {
				return confBean.agTaxaMutacao();
			}

			@Override
			public double getCrossoverRate() {
				return confBean.agTaxaCrossover();
			}
		};

		timetableApp = new TimetableApp(parameters);
		IEntryData entryData = new IEntryData() {		    
			@Override
			public List<CourseOffers> getCourseOffers() {
				DisciplinaOfertaProfessorRepository dados = new DisciplinaOfertaProfessorRepository(getManager());
				return dados.getTimetableEntryData();
			}
		};
		timetableApp.setEntryData(entryData);
		Runnable t = new Runnable() {
			
			@Override
			public void run() {

				
			    resultado = timetableApp.doTimetable();
			}
		};
	    Thread thread = new Thread(t);
	    thread.start();
	    mostrarTabela = true;
	    try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("passouuu");
	    //System.out.println(" dd "+resultado.size()+" "+diasSemana.size());
	    return null;
    }

	private EntityManager getManager() {
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
	    HttpServletRequest request = (HttpServletRequest) ec.getRequest();
	    return (EntityManager) request.getAttribute("entityManager");
	}

	public List<CourseOffers> getResultado() {
		return resultado;
	}

    public List<DiaDaSemana> getDiasSemana() {
    	if(diasSemana == null){
		    SemanaRepository repository = new SemanaRepository(this.getManager());
		    
		    diasSemana = repository.getDiasSemana(new ConfiguracaoBean().numeroDias());
    	}
       return this.diasSemana;
	}
    

	public boolean isMostrarTabela() {
		return mostrarTabela;
	}

	public PeriodoLetivo getPeriodoLetivo() {
		if(periodoLetivo == null){
			SemestreLetivoRepository repo = new SemestreLetivoRepository(getManager());
			periodoLetivo = repo.getPeriodoLetivoAtivo();
		}
		return periodoLetivo;
	}

	public void setPeriodoLetivo(PeriodoLetivo periodoLetivo) {
		this.periodoLetivo = periodoLetivo;
	}

	public TimetableApp getTimetableApp() {
		return timetableApp;
	}


}
