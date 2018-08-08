package periodoletivo;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import professor.ProfessorRepository;

@ManagedBean
@SessionScoped
public class SemestreLetivoBean {
	private PeriodoLetivo semestreLetivo = new PeriodoLetivo();
    private List<PeriodoLetivo> semestresLetivos;
	
    public void adicionar(){
    	SemestreLetivoRepository repository = new SemestreLetivoRepository (this.getManager());
    	semestreLetivo.setAtivo(false);
		repository.inserir(this.semestreLetivo);
		this.semestreLetivo = new PeriodoLetivo();

        FacesMessage mensagem = new FacesMessage("Registro adicionado.");
        FacesContext.getCurrentInstance().addMessage(null ,mensagem);
		
    }

	 public Long getCount() {
	        SemestreLetivoRepository repository = new SemestreLetivoRepository(this.getManager());
	         
	        return repository.getCount();
	 }
	 public List<PeriodoLetivo> getSemestresLetivos() { 	
            SemestreLetivoRepository repository = new SemestreLetivoRepository(this.getManager());
            this.semestresLetivos = repository.getSemestresLetivos();
            this.semestreLetivo = new PeriodoLetivo();
	        return this.semestresLetivos;
	    }

		public PeriodoLetivo getSemestreLetivo() {
			return semestreLetivo;
		}

		public void setSemestreLetivo(PeriodoLetivo semestreLetivo) {
			this.semestreLetivo = semestreLetivo;
		}

	    public void remover(PeriodoLetivo semestreLetivo) {
	    	SemestreLetivoRepository repository = new SemestreLetivoRepository(this.getManager());
	        repository.remover(semestreLetivo);
	     
	        this.semestresLetivos = null;
	    }
	    
	    public String preparaEditar(PeriodoLetivo semestreLetivo) {
	        this.setSemestreLetivo(semestreLetivo);
	        return "editar?faces-redirect=true";
	    }
	    public void editar() {
	    	SemestreLetivoRepository repository = new SemestreLetivoRepository(this.getManager());
	        repository.editar(semestreLetivo);
	        
	        FacesMessage mensagem = new FacesMessage("Registro alterado.");
	        FacesContext.getCurrentInstance().addMessage(null ,mensagem);
	        
			this.semestreLetivo = new PeriodoLetivo();
	    }
	    public void iniciarSemestreLetivo(PeriodoLetivo s) {
	    	EntityManager entity= (EntityManager) this.getManager();

	    	SemestreLetivoRepository repository = new SemestreLetivoRepository(entity);
	        repository.iniciarSemestreLetivo(s);
	        
	        ProfessorRepository repositoryProf = new ProfessorRepository(entity);
	        repositoryProf.resetarCreditoLivre();
	        
	    }
		private EntityManager getManager() {
		    FacesContext fc = FacesContext.getCurrentInstance();
		    ExternalContext ec = fc.getExternalContext();
		    HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		    return (EntityManager) request.getAttribute("entityManager");
		}
		
}
