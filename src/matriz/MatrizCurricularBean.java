package matriz;



import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import disciplina.DisciplinaRepository;

@ManagedBean
@SessionScoped
public class MatrizCurricularBean {
	
	
	private MatrizCurricular matriz = new MatrizCurricular();
	private List<MatrizCurricular> matrizes;
	
	public MatrizCurricular getMatriz() {
		return matriz;
	}

	public void setMatriz(MatrizCurricular matriz) {
		this.matriz = matriz;
	}

	public MatrizCurricular adicionar() {
		MatrizCurricularRepository repository = new MatrizCurricularRepository(this.getManager());
		repository.adicionar(matriz);
		this.matriz = new MatrizCurricular();
		
		FacesMessage mensagem = new FacesMessage("Registro adicionado.");
        FacesContext.getCurrentInstance().addMessage(null ,mensagem);
		
		return matriz;
	}
	
	public List<MatrizCurricular> getMatrizes() { 	
		MatrizCurricularRepository repository = new MatrizCurricularRepository(this.getManager());
        this.matrizes = repository.getMatriz();
        matriz = new MatrizCurricular();
        return this.matrizes;
    }
	
	public Long getCount() {
		DisciplinaRepository repository = new DisciplinaRepository(this.getManager());
        return repository.getCount();
 }
	
	public MatrizCurricular excluir() {
		MatrizCurricularRepository repository = new MatrizCurricularRepository(this.getManager());
        repository.remover(matriz);
        
		return matriz;
	}
	
	public String preparaEditar(MatrizCurricular matriz) {
        this.setMatriz(matriz);
        return "editar?faces-redirect=true";
    }

    public void editar() {
    	MatrizCurricularRepository repository = new MatrizCurricularRepository(this.getManager());
        repository.editar(matriz);
        
        FacesMessage mensagem = new FacesMessage("Registro alterado.");
        FacesContext.getCurrentInstance().addMessage(null ,mensagem);
        
        this.matriz = new MatrizCurricular();
    }
	
	private EntityManager getManager() {
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
	    HttpServletRequest request = (HttpServletRequest) ec.getRequest();
	    return (EntityManager) request.getAttribute("entityManager");
	}


//	public String inserirMatriz(Curso curso, int id) {
//		return null;
//	}
//
//	public String editarMatriz(Curso curso, List disciplinas, boolean ativo) {
//		return null;
//	}

}
