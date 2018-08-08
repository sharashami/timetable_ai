package semana;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class SemanaBean implements Serializable{
    private List<Turno> turnos;
    private Long countPeriodos;
	
	 public List<Turno> getTurnos() {
           this.turnos = new SemanaRepository(this.getManager()).getTurnos();
           return this.turnos;
        		   
	 }

	 public Long getCountPeriodos() {
		 this.countPeriodos= new SemanaRepository(this.getManager()).getCountPeriodos();
         return this.countPeriodos;
	 }
	private EntityManager getManager() {
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
	    HttpServletRequest request = (HttpServletRequest) ec.getRequest();
	    return (EntityManager) request.getAttribute("entityManager");
	}
}
