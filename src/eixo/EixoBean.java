package eixo;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@SessionScoped
public class EixoBean {
	private Eixo eixo = new Eixo();
    private List<Eixo> eixos;
	
    public void adicionar(){
    	EixoRepository repository = new EixoRepository (this.getManager());
		repository.adicionar(this.eixo);
		this.eixo = new Eixo();
		
        FacesMessage mensagem = new FacesMessage("Registro adicionado.");
        FacesContext.getCurrentInstance().addMessage(null ,mensagem);
    }

	 public Long getCount() {
	        EixoRepository repository = new EixoRepository(this.getManager());
	         
	        return repository.getCount();
	 }
	 public List<Eixo> getEixos() {
	        
            EixoRepository repository = new EixoRepository(this.getManager());
            this.eixos = repository.getEixos();
	         this.eixo = new Eixo();
	        return this.eixos;
	    }

		public Eixo getEixo() {
			return eixo;
		}

		public void setEixo(Eixo eixo) {
			this.eixo = eixo;
		}

	    public void remover(Eixo eixo) {
	    	EixoRepository repository = new EixoRepository(this.getManager());
	        repository.remover(eixo);
	     
	        this.eixos = null;
	    }
	    
	    public String preparaEditar(Eixo eixo) {
	        this.setEixo(eixo);
	        return "editar?faces-redirect=true";
	    }

	    public void editar() {
	    	EixoRepository repository = new EixoRepository(this.getManager());
	        repository.editar(eixo);
	        
	        FacesMessage mensagem = new FacesMessage("Registro alterado.");
	        FacesContext.getCurrentInstance().addMessage(null ,mensagem);
	    }

		private EntityManager getManager() {
		    FacesContext fc = FacesContext.getCurrentInstance();
		    ExternalContext ec = fc.getExternalContext();
		    HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		    return (EntityManager) request.getAttribute("entityManager");
		}
}
