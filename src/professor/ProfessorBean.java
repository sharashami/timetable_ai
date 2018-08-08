package professor;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import login.Hash;
import professor.indisponibilidade.IndisponibilidadeView;
import professor.indisponibilidade.ProfessorIndisponibilidade;
import professor.indisponibilidade.ProfessorIndisponibilidadeRepository;
import semana.DiaDaSemana;
import semana.SemanaRepository;
import semana.Turno;
import utils.exception.ApplicationException;

@ManagedBean
@RequestScoped
public class ProfessorBean  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Professor professor = new Professor();
	private String buscaToken = new String();
    private transient List<Professor> professores;
    private transient List<ProfessorIndisponibilidade> professorIndisponibilidade;
    private transient ProfessorIndisponibilidade professorIndisponibilidadeTab;
    
    private transient List<IndisponibilidadeView> indisponibilidadeTurnoView; 
    
    private transient List<DiaDaSemana> diasSemana;
    private transient String diasSemanaSelecionado;
    
    private transient List<Turno> turnos;
    private transient String turnoSelecionado;
	
    //campos para ministra
    private int creditosAMinstrar;
    private boolean paralela;
    
    public void index(){
    	professores = null;
    }
    public String salvar(){
    	ProfessorRepository repository = new ProfessorRepository (this.getManager());
    	
    	if(professor.getId() == null || professor.getId() == 0){
	    	//adicionar o HASH da senha 123
	    	Hash hash = new Hash(Hash.SHA_256);
	    	
	    	this.professor.setId(null);//vem do form zerado
	    	this.professor.setSenha(hash.gerarHash("1234"));
	    	this.professor.setCreditosLivres(this.professor.getCreditosTotais());
			try{	
				repository.adicionar(this.professor);
	
		        FacesContext.getCurrentInstance().addMessage(null ,new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Registro adicionado com sucesso!"));
				return "/professor/lista?faces-redirect=true";
				
	    	}catch (ApplicationException ex) {
		    	System.out.println(ex.getMessage());
	    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Adicionar :",ex.getMessage()));    		
	    		
		    	this.professor.setId(null);
	    	}
    	}else{
    		try{	
				repository.editar(this.professor);
	
		        FacesContext.getCurrentInstance().addMessage(null ,new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Registro atualizado com sucesso!"));
				return "/professor/lista?faces-redirect=true";
				
	    	}catch (ApplicationException ex) {
		    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Atualizar:",ex.getMessage()));
	    	}
    	}
		return null;
		
    }

    public String addIndisponibilidade(){
    	SemanaRepository repositorySemana = new SemanaRepository (this.getManager());
    	ProfessorRepository repositoryProf= new ProfessorRepository (this.getManager());
    	
    	
    	FacesContext context = FacesContext.getCurrentInstance();
    	HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		Professor professor = (Professor)session.getAttribute("professor");
    	
    	if(professor != null && professor.getId() != null && professor.getId() > 0){
    		ProfessorIndisponibilidade profIndisp = new ProfessorIndisponibilidade();
    		profIndisp.setProfessor(professor);
    		profIndisp.setDiaDaSemana(repositorySemana.getDiaDaSemana(Integer.valueOf(diasSemanaSelecionado)));
    		profIndisp.setTurno(repositorySemana.getTurno(Integer.valueOf(turnoSelecionado)));
    		
			try{	
				repositoryProf.adicionarIndisponibilidade(profIndisp);
	
		        FacesContext.getCurrentInstance().addMessage(null ,new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Registro adicionado com sucesso!"));
				
	    	}catch (ApplicationException ex) {
		    	System.out.println(ex.getMessage());
	    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Adicionar :",ex.getMessage()));    		
	    		
	    	}
    	}else{		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro Adicionar :","Não foi possível capturar os dados do usuário logado."));}
    	return null;
    }
    
	 public Long getCount() {
	        ProfessorRepository repository = new ProfessorRepository(this.getManager());
	         
	        return repository.getCountProfessores();
	 }
	 	 
	 public List<DiaDaSemana> getDiasSemana() {
         SemanaRepository repository = new SemanaRepository(this.getManager());

         diasSemana = repository.getDiasSemana();
         
        return this.diasSemana;
	}

	 public List<Turno> getTurnos() {
		 System.out.println("dia da semana "+diasSemanaSelecionado);
         SemanaRepository repository = new SemanaRepository(this.getManager());

         turnos = repository.getTurnos();
         
        return this.turnos;
	}
	 public List<Professor> getProfessores() {
         ProfessorRepository repository = new ProfessorRepository(this.getManager());
		 if(buscaToken != null && !buscaToken.trim().isEmpty()){
			 this.professores = repository.getProfessoresByNome(buscaToken);
			 
		 }else this.professores = repository.getProfessores();
		this.buscaToken = null;
        this.professor = new Professor(); 
        return this.professores;
	}

	public Professor getProfessor() {
		if(professor == null) professor = new Professor();
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

    public void remover() {
    	ProfessorRepository repository = new ProfessorRepository(this.getManager());
        try {
			repository.remover(professor);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro removido com sucesso", null));
    	}catch (ApplicationException ex) {
	    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro remover :",ex.getMessage()));
    	}
        this.professor = null;
        this.professores = null;
    }

    public void removerIndisponibilidade() {
    	ProfessorIndisponibilidadeRepository repository = new ProfessorIndisponibilidadeRepository(this.getManager());
       /* try {
			repository.remover(professorIndisponibilidadeTab);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro removido com sucesso", null));
    	}catch (ApplicationException ex) {
	    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro remover :",ex.getMessage()));
    	}*/
        this.professorIndisponibilidade = null;
    }
    public void ativarDesativar() {
    	ProfessorRepository repository = new ProfessorRepository(this.getManager());
        try {
        	professor.setAtivo(!professor.isAtivo());
			repository.editar(professor);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro "+((professor.isAtivo())?"ativado":"desativado")+" com sucesso", null));
    	}catch (ApplicationException ex) {
	    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ativar/desativar registro :",ex.getMessage()));
    	}
        this.professor = null;
        this.professores = null;
    }
    

	public String preparaMinistrar(Professor p) {
        this.setProfessor(p);
        return "ministrar_disciplinas?faces-redirect=true";
    }

	private EntityManager getManager() {
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
	    HttpServletRequest request = (HttpServletRequest) ec.getRequest();
	    return (EntityManager) request.getAttribute("entityManager");
	}

	public int getCreditosAMinstrar() {
		return creditosAMinstrar;
	}

	public void setCreditosAMinstrar(int creditosAMinstrar) {
		this.creditosAMinstrar = creditosAMinstrar;
	}

	public boolean isParalela() {
		return paralela;
	}

	public void setParalela(boolean paralela) {
		this.paralela = paralela;
	}

	public String getBuscaToken() {
		return buscaToken;
	}

	public void setBuscaToken(String buscaToken) {
		this.buscaToken = buscaToken;
	}
	public String getDiasSemanaSelecionado() {
		return diasSemanaSelecionado;
	}
	public void setDiasSemanaSelecionado(String diasSemanaSelecionado) {
		this.diasSemanaSelecionado = diasSemanaSelecionado;
	}
	public void handleChange(){  
		//aqui é chamado antes de atualizar o combo do turno, aqui eu seto qual o turno, no caso o 2.
	//	turnoSelecionado="2";
		System.out.println("ajax : "+diasSemanaSelecionado);
	}
	public String getTurnoSelecionado() {
		return turnoSelecionado;
	}
	public void setTurnoSelecionado(String turnoSelecionado) {
		this.turnoSelecionado = turnoSelecionado;
	}
	public List<ProfessorIndisponibilidade> getProfessorIndisponibilidade() {
		if(professorIndisponibilidade == null){
			FacesContext context = FacesContext.getCurrentInstance();
	    	HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
			Professor professor = (Professor)session.getAttribute("professor");
	    	
			ProfessorIndisponibilidadeRepository repository = new ProfessorIndisponibilidadeRepository(this.getManager());
			 
			this.professorIndisponibilidade = repository.getIndisponibilidadeProfessor(professor);
			
		}
		return professorIndisponibilidade;
		
	}
	public ProfessorIndisponibilidade getProfessorIndisponibilidadeTab() {
		return professorIndisponibilidadeTab;
	}
	public void setProfessorIndisponibilidadeTab(
			ProfessorIndisponibilidade professorIndisponibilidadeTab) {
		this.professorIndisponibilidadeTab = professorIndisponibilidadeTab;
	}
	public void setIndisponibilidadeTurnoView(
			List<IndisponibilidadeView> indisponibilidadeTurnoView) {
		this.indisponibilidadeTurnoView = indisponibilidadeTurnoView;
	}

}
