package professor.indisponibilidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import login.LoginBean;
import professor.Professor;
import semana.DiaDaSemana;
import semana.SemanaRepository;
import semana.Turno;
import utils.exception.ApplicationException;
import configuracao.ConfiguracaoBean;

@ManagedBean
@RequestScoped
public class ProfessorIndisponibilidadeBean  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private transient List<IndisponibilidadeView> indisponibilidadeManhaView;
    private transient List<IndisponibilidadeView> indisponibilidadeTardeView;
    private transient List<IndisponibilidadeView> indisponibilidadeNoiteView;
	private ProfessorIndisponibilidadeRepository repo = new ProfessorIndisponibilidadeRepository(getManager());
    
    private IndisponibilidadeView indisponibilidadeSelecionada;
    private transient List<ProfessorIndisponibilidade> professorIndisponibilidade = null;
    private transient List<DiaDaSemana> diasSemana = null;
    private transient Professor professor = null;
    
    public List<DiaDaSemana> getDiasSemana() {
    	if(diasSemana == null){
		    SemanaRepository repository = new SemanaRepository(this.getManager());
		    
		    diasSemana = repository.getDiasSemana(new ConfiguracaoBean().numeroDias());
    	}
       return this.diasSemana;
	}
    
    public void updateIndisponibilidade(){
    	int periodo= indisponibilidadeSelecionada.getPeriodoValor();
    	if(isIndisponivel(indisponibilidadeSelecionada.getDia().getId(), indisponibilidadeSelecionada.getTurno().getId(), periodo)){
    		
    		try {

	    		ProfessorIndisponibilidade p = new ProfessorIndisponibilidade();
	    		p.setDiaDaSemana(indisponibilidadeSelecionada.getDia());
	    		p.setTurno(indisponibilidadeSelecionada.getTurno());
	    		p.setPeriodo(periodo);
	    		p.setProfessor(professor);
	    		
    			repo.remover(p);
    			p.setPeriodo(periodo+1);
				repo.remover(p);
			} catch (ApplicationException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro remover :",e.getMessage())); 
			}
    	}else {
    		try {
	    		ProfessorIndisponibilidade p = new ProfessorIndisponibilidade();
	    		p.setDiaDaSemana(indisponibilidadeSelecionada.getDia());
	    		p.setTurno(indisponibilidadeSelecionada.getTurno());
	    		p.setPeriodo(periodo);
	    		p.setProfessor(professor);
	    		p.setId(null);
	    		repo.adicionar(p);
	    		
	    		p = new ProfessorIndisponibilidade();
	    		p.setDiaDaSemana(indisponibilidadeSelecionada.getDia());
	    		p.setTurno(indisponibilidadeSelecionada.getTurno());
	    		p.setPeriodo(periodo);
	    		p.setProfessor(professor);
	    		p.setPeriodo(periodo+1);
	    		p.setId(null);
	    		repo.adicionar(p);
	    	} catch (ApplicationException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro adicionar :",e.getMessage()));    		
	    		
			}
    	}
    	professorIndisponibilidade = null;
    	indisponibilidadeManhaView = null;
    	indisponibilidadeTardeView = null;
    	indisponibilidadeNoiteView = null;
    	
    }
    
    public boolean isIndisponivel(int dia, int turno, int periodo){
    	
    	if(professorIndisponibilidade == null){
    		professor = new LoginBean().getUsuario();
    		professorIndisponibilidade = new ProfessorIndisponibilidadeRepository(getManager()).getIndisponibilidadeProfessor(professor);
    	}
    	
    	
    	for (int i = 0; i < professorIndisponibilidade.size(); i++) {
    		int profDia = professorIndisponibilidade.get(i).getDiaDaSemana().getId();
    		int profTurno = professorIndisponibilidade.get(i).getTurno().getId();
    		int profPeriodo = professorIndisponibilidade.get(i).getPeriodo();
			
    		if(dia == profDia && profTurno == turno  && profPeriodo == periodo){
    			return true;
    		}
    		
    		//se esta verificando no mesmo turno e o dia procurado já passou então não tem como achar pq esta vindo ordenado turno/dia/periodo
    		if(turno == profTurno && profDia > dia)
    			return false;
    			
		}
    	return false;
    }
    
	public List<IndisponibilidadeView> getIndisponibilidadeManhaView() {
		indisponibilidadeManhaView = new ArrayList<IndisponibilidadeView>();
		SemanaRepository repoSemana = new SemanaRepository(getManager());
		
		
		//percorre os periodos, os impares pq
		int p = 1;
		while (p<6) {
			String periodoDesc = (p==1)?"AB":((p==3)?"CD":"EF");


			for (int j = 0; j < getDiasSemana().size(); j++) {
				IndisponibilidadeView v = new IndisponibilidadeView();
				v.setPeriodo(periodoDesc);
				v.setIndisponivel(isIndisponivel(diasSemana.get(j).getId(), 1, p));
				v.setDia(diasSemana.get(j));
				v.setTurno(repoSemana.getTurno(1));
				v.setPeriodoValor(p);
				indisponibilidadeManhaView.add(v);
			}
			
			p=p+2;
			/*
			
			
			
			if(i == 0)
			p.setDescricao("AB");
			if(i == 1)
			p.setDescricao("CD");
			if(i == 2)
			p.setDescricao("EF");
			v.setPeriodo(p);
			v.setIndisponivel(true);			
			indisponibilidadeManhaView.add(v);
			 
			v = new IndisponibilidadeView();
			v.setIndisponivel(true);
			v.setPeriodo(p);
			indisponibilidadeManhaView.add(v);

			v = new IndisponibilidadeView();
			v.setIndisponivel(true);
			v.setPeriodo(p);
			indisponibilidadeManhaView.add(v);
			
			v = new IndisponibilidadeView();
			v.setIndisponivel(true);
			v.setPeriodo(p);
			indisponibilidadeManhaView.add(v);
			
			v = new IndisponibilidadeView();
			v.setIndisponivel(true);
			v.setPeriodo(p);
			indisponibilidadeManhaView.add(v);
			
			v = new IndisponibilidadeView();
			v.setIndisponivel(false);
			v.setPeriodo(p);*/
			
		}
		
		return indisponibilidadeManhaView;
	}
	
	public void setIndisponibilidadeManhaView(
			List<IndisponibilidadeView> indisponibilidadeManhaView) {
		this.indisponibilidadeManhaView = indisponibilidadeManhaView;
	}
	public List<IndisponibilidadeView> getIndisponibilidadeTardeView() {
		indisponibilidadeTardeView = new ArrayList<IndisponibilidadeView>();
		SemanaRepository repoSemana = new SemanaRepository(getManager());
		
		//percorre os periodos, os impares pq
		int p = 1;
		while (p<6) {
			String periodoDesc = (p==1)?"AB":((p==3)?"CD":"EF");
	
			
			for (int j = 0; j < getDiasSemana().size(); j++) {
				IndisponibilidadeView v = new IndisponibilidadeView();
				v.setPeriodo(periodoDesc);
				v.setIndisponivel(isIndisponivel(diasSemana.get(j).getId(), 2, p));
				v.setDia(diasSemana.get(j));
				v.setTurno(repoSemana.getTurno(2));
				v.setPeriodoValor(p);
				indisponibilidadeTardeView.add(v);
			}
			
			p=p+2;
		}
		return indisponibilidadeTardeView;
	
	}
	public void setIndisponibilidadeTardeView(List<IndisponibilidadeView> indisponibilidadeTardeView) {
		this.indisponibilidadeTardeView = indisponibilidadeTardeView;
	}
	public List<IndisponibilidadeView> getIndisponibilidadeNoiteView() {
		indisponibilidadeNoiteView = new ArrayList<IndisponibilidadeView>();
		SemanaRepository repoSemana = new SemanaRepository(getManager());
		
		//percorre os periodos, os impares pq
		int p = 1;
		while (p<6) {
			String periodoDesc = (p==1)?"AB":((p==3)?"CD":"EF");
	
			
			for (int j = 0; j < getDiasSemana().size(); j++) {
				IndisponibilidadeView v = new IndisponibilidadeView();
				v.setPeriodo(periodoDesc);
				v.setIndisponivel(isIndisponivel(diasSemana.get(j).getId(), 3, p));
				v.setDia(diasSemana.get(j));
				v.setTurno(repoSemana.getTurno(3));
				v.setPeriodoValor(p);
				indisponibilidadeNoiteView.add(v);
			}
			
			p=p+2;
		}
		/*for (int i = 0; i < 3; i++) {
			IndisponibilidadeView v = new IndisponibilidadeView();
			Periodo p = new Periodo();
			if(i == 0)
			p.setDescricao("AB");
			if(i == 1)
			p.setDescricao("CD");
			if(i == 2)
			p.setDescricao("EF");
			v.setPeriodo(p);
			v.setIndisponivel(true);			
			indisponibilidadeNoiteView.add(v);
			 
			v = new IndisponibilidadeView();
			v.setIndisponivel(true);
			v.setPeriodo(p);
			indisponibilidadeNoiteView.add(v);

			v = new IndisponibilidadeView();
			v.setIndisponivel(true);
			v.setPeriodo(p);
			indisponibilidadeNoiteView.add(v);
			
			v = new IndisponibilidadeView();
			v.setIndisponivel(true);
			v.setPeriodo(p);
			indisponibilidadeNoiteView.add(v);
			
			v = new IndisponibilidadeView();
			v.setIndisponivel(true);
			v.setPeriodo(p);
			indisponibilidadeNoiteView.add(v);
			
			v = new IndisponibilidadeView();
			v.setIndisponivel(false);
			v.setPeriodo(p);
			indisponibilidadeNoiteView.add(v);
		}
		*/
		return indisponibilidadeNoiteView;
	
	}
	public void setIndisponibilidadeNoiteView(List<IndisponibilidadeView> indisponibilidadeNoiteView) {
		this.indisponibilidadeNoiteView = indisponibilidadeNoiteView;
	}


	private EntityManager getManager() {
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
	    HttpServletRequest request = (HttpServletRequest) ec.getRequest();
	    return (EntityManager) request.getAttribute("entityManager");
	}

	public IndisponibilidadeView getIndisponibilidadeSelecionada() {
		return indisponibilidadeSelecionada;
	}

	public void setIndisponibilidadeSelecionada(
			IndisponibilidadeView indisponibilidadeSelecionada) {
		this.indisponibilidadeSelecionada = indisponibilidadeSelecionada;
	}

}
