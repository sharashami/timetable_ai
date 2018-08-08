package disciplina;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import semana.SemanaRepository;
import semana.Turno;
import utils.exception.ApplicationException;
import curso.Curso;
import curso.CursoRepository;
import eixo.Eixo;
import eixo.EixoRepository;

@ManagedBean
@RequestScoped
public class DisciplinaBean   implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Disciplina disciplina = new Disciplina();
	private List<Disciplina> disciplinas;
	private List<Curso> cursos;
	private List<Disciplina> disciplinasOfertadas;
	
	//para o filtro das disciplinas
	private Integer filtroEixoId;
	private Integer filtroCursoId;
	private Integer filtroSemestre;
	private String buscaToken = new String();
	
	
	//campos para ofertar disciplina
	private Integer turnoID;
	private Integer creditosTotais;
	private DisciplinaOferta ofertaDisciplina;
	//campos para ofertar disciplina
	
	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}


    public String salvar(){
    	DisciplinaRepository repository = new DisciplinaRepository (this.getManager());
    	curso.CursoRepository cursoRepository = new curso.CursoRepository(this.getManager());
    	curso.Curso curso = cursoRepository.buscar(disciplina.getCurso().getId());
    	    
    	if(curso == null || curso.getId() == null || curso.getId() == 0){
			FacesContext.getCurrentInstance().addMessage("curso", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Curso não encontrado. Tente novamente.",null));
			return null;
    	}

    	if(disciplina.getId() == null || disciplina.getId() == 0){
			
			try{	
				disciplina.setId(null);
				
				repository.adicionar(this.disciplina);
	
		        FacesContext.getCurrentInstance().addMessage(null ,new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Registro adicionado com sucesso!"));
				return "lista";
				
	    	}catch (ApplicationException ex) {
		    	System.out.println(ex.getMessage());
	    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Adicionar :",ex.getMessage()));    		
	    		
		    	this.disciplina.setId(null);
	    	}
    	}else{
    		try{	
				repository.editar(this.disciplina);
	
		        FacesContext.getCurrentInstance().addMessage(null ,new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Registro atualizado com sucesso!"));
				return "lista";
				
	    	}catch (ApplicationException ex) {
		    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Atualizar:",ex.getMessage()));
	    	}
    	}
		return null;
		
    }
    
	
	public List<Disciplina> getDisciplinas() { 	
		DisciplinaRepository repository = new DisciplinaRepository(this.getManager());
        this.disciplinas = repository.getDisciplinas();
        disciplina = new Disciplina();
        return this.disciplinas;
    }
	
	public Long getCount() {
		DisciplinaRepository repository = new DisciplinaRepository(this.getManager());
        return repository.getCount();
 }
	
	public Disciplina remover(Disciplina d) {
		DisciplinaRepository repository = new DisciplinaRepository(this.getManager());
        repository.remover(d);
        
		return d;
	}
	
	public String preparaEditar(Disciplina disciplina) {
        this.setDisciplina(disciplina);
        return "editar?faces-redirect=true";
    }

	public String preparaOfertar(Disciplina disciplina) {
        this.setDisciplina(disciplina);
        creditosTotais = disciplina.getCredito();
        return "ofertar?faces-redirect=true";
    }

    public void ofertar() {
    	EntityManager entity = this.getManager();
    	
    	DisciplinaRepository disciplinaRepository = new DisciplinaRepository(entity);
    	this.disciplina = disciplinaRepository.buscar(disciplina.getId());
    	
    	SemanaRepository semanaRepository = new SemanaRepository(entity);
    	Turno turno = semanaRepository.buscarTurno(turnoID);
    	
    	ofertaDisciplina = new DisciplinaOferta();
    	ofertaDisciplina.setTurno(turno);
    	ofertaDisciplina.setDisciplina(disciplina);
    	ofertaDisciplina.setCreditosTotais(creditosTotais);
    	ofertaDisciplina.setCreditosRestantes(creditosTotais);
    	
    	OfertaDisciplinaRepository ofertaDiscRpository = new OfertaDisciplinaRepository(entity);
    	ofertaDiscRpository.ministrar(ofertaDisciplina);
        
        FacesMessage mensagem = new FacesMessage("Disciplina ofertada com sucesso.");
        FacesContext.getCurrentInstance().addMessage(null ,mensagem);
    }
    
	private EntityManager getManager() {
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
	    HttpServletRequest request = (HttpServletRequest) ec.getRequest();
	    return (EntityManager) request.getAttribute("entityManager");
	}

	public List<Disciplina> getDisciplinasOfertadas() {
		OfertaDisciplinaRepository ofertaRepository = new OfertaDisciplinaRepository(this.getManager());
        this.disciplinasOfertadas= ofertaRepository.getDisciplinasOfertadas();
        return this.disciplinasOfertadas;
	}

	public Integer getTurnoID() {
		return turnoID;
	}

	public void setTurnoID(Integer turnoID) {
		this.turnoID = turnoID;
	}

	public Integer getCreditosTotais() {
		return creditosTotais;
	}

	public void setCreditosTotais(Integer creditosTotais) {
		this.creditosTotais = creditosTotais;
	}

	public String getBuscaToken() {
		return buscaToken;
	}

	public void setBuscaToken(String buscaToken) {
		this.buscaToken = buscaToken;
	}

	public List<Curso> getCursos() {
		if(cursos == null){
			cursos = new curso.CursoRepository(getManager()).getCursos();
		}
		return cursos;
	}

}
