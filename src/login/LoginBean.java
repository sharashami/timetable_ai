package login;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import professor.Professor;
import professor.ProfessorRepository;
import utils.exception.ApplicationException;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
	
	//entidade mapeada da visao
	private Professor professor = new Professor();
	private String id = new String();
	
	private Professor usuario =null;
	//retornar a entidade 
	private EntityManager getManager() {
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
	    HttpServletRequest request = (HttpServletRequest) ec.getRequest();
	    return (EntityManager) request.getAttribute("entityManager");
	}
	

	public Professor getUsuario() {
		System.out.println("get usuario");
		if(usuario == null){
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
			usuario = (Professor) session.getAttribute("professor");
		}
		return usuario;
	}

	
	public Professor getProfessor() {
		System.out.println("get professor");
		
		if(professor == null){
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
			professor = (Professor) session.getAttribute("professor");
		}
		return professor;
	}



	public void setProfessor(Professor professor) {
		this.professor = professor;
	}


	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String autenticar(){

		System.out.println("autenticar");
		// conexao com o banco DAO professor
		ProfessorRepository repository = new ProfessorRepository(this.getManager());
		Professor p = repository.getProfessor(this.professor);
		
		if (p == null){ //verificação se o professor existe de acordo com o login, caso não exista retorna messagem de erro para view  
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage("Professor não existe!"));
			return null;
		}
		
		//colocar o hash no banco ao inserir, verificar se é igual pelo o hash
		Hash hash = new Hash(Hash.SHA_256);
		String hashSenhaUsuario = hash.gerarHash(this.professor.getSenha());  
		if( hash.ComparaHash(hashSenhaUsuario, p.getSenha())){ 
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
			session.setAttribute("professor", p);
			professor = p;
			return "/index?faces-redirect=true"; //redirecionar para pagina principal
		}else{  //caso a senha sejam diferente mostrar a mensagem que o usuário nao confere
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage("Senha incorreta!"));
		}
		
		
		return null;
		
	}
	
	public String logout(){
		System.out.println("CHEGOU NO LOGOUT");
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.invalidate();
		
		return "/login/login.xhtml?faces-redirect=true"; //redirecionar para pagina principal
	}
	
	public String enviarEmail(){
		
		System.out.println("metodo enviar email");
		// conexao com o banco DAO professor
		ProfessorRepository repository = new ProfessorRepository(this.getManager());
		Professor p = repository.getProfessor(this.professor);
		
		if (p == null){ //verificação se o professor existe de acordo com o login, caso não exista retorna messagem de erro para view  
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage("Professor não existe!"));
			return null;
		}
		
		String chaveEmail = Hash.gerarChaveEmail();
		p.setChaveEmail(chaveEmail);
		try {
			repository.editar(p);
			String mensagem = "Clique no link abaixo para recuperar a senha:<br>"
					+ "<a href=\"http://localhost:8080/horarioaula/faces/login/confirmarrecuperarsenha.xhtml?id="+p.getId()+"&chave="+chaveEmail+"\">Recuperar Senha</href>";
		
			System.out.println(mensagem);
			EmailUtils.enviaEmail("Recuperação de Senha - Horario Aula",mensagem, p.getLogin());
			
			
			
			
		} catch (ApplicationException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Professor n�o existente :",e.getMessage()));    		
    		this.professor.setId(null);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public void confirmarRecuperarSenha(){
		
		
		this.professor.setId(Long.parseLong(this.id));
		ProfessorRepository repository = new ProfessorRepository(this.getManager());
		Professor p = repository.getProfessor(this.professor);
	
		if (p == null){ //verifica��o se o professor existe de acordo com o login, caso não exista retorna messagem de erro para view  
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}else{
			if(!p.getChaveEmail().equals(this.professor.getChaveEmail())){
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			}else{
				p.setChaveEmail(null);
				
				Hash hash = new Hash(Hash.SHA_256);
		    	p.setSenha(hash.gerarHash("1234"));
		    	try {
					repository.editar(p);
				} catch (ApplicationException e) {
					
					e.printStackTrace();
				}
		
			}
		}
		
	}
	
}
