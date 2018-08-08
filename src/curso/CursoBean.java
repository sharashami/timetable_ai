package curso;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import com.sun.corba.se.spi.activation.Repository;

import disciplina.Disciplina;
import disciplina.DisciplinaRepository;
import disciplina.OfertaDisciplinaRepository;
import semana.SemanaRepository;
import semana.Turno;
import utils.exception.ApplicationException;
import eixo.Eixo;
import eixo.EixoRepository;

@ManagedBean
@SessionScoped
public class CursoBean {
	private Curso curso = new Curso();
	private List<Curso> cursos;
	private List<CursosPeriodos> periodosCurso;
	private List<Integer> semestres;
	private List<Disciplina> disciplinas;
	
	private Integer eixoID;
	private Integer turnoID;
	private Integer qtdePeriodos;
	private transient String idEixo = "1";
	private transient String idCurso;
	private transient String idSemestre = "0";
	private transient String idTurno = "0";
	private transient String idTurnoOferta = "1";
	private transient String idDisciplinaOfertada="0"; 

	public Integer getQtdePeriodos() {
		return qtdePeriodos;
	}

	public void setQtdePeriodos(Integer qtdePeriodos) {
		this.qtdePeriodos = qtdePeriodos;
	}

	public void adicionar(){ 
		EixoRepository eixoRepository = new EixoRepository(this.getManager());
		Eixo eixo = eixoRepository.buscar(eixoID);
		this.curso.setEixo(eixo);

		CursoRepository repository = new CursoRepository (this.getManager());
		repository.adicionar(this.curso);

		this.curso = new Curso();

		FacesMessage mensagem = new FacesMessage("Registro adicionado.");
		FacesContext.getCurrentInstance().addMessage(null ,mensagem);
	}

	public Integer getEixoID() {
		return eixoID;
	}

	public void setEixoID(Integer eixoID) {
		this.eixoID = eixoID;
	}

	public Long getCount() {
		CursoRepository repository = new CursoRepository(this.getManager());

		return repository.getCount();
	}
	
	public List<Curso> getCursos() {

		CursoRepository repository = new CursoRepository(this.getManager());
		
		Eixo eixo = new Eixo();
		eixo.setId(Integer.parseInt(idEixo));
		this.cursos = repository.getCursos(eixo);
		
		if(this.cursos.size() > 0)
		{
			this.curso  = this.cursos.get(0);
			this.idCurso = ""+this.curso.getId(); 
		}else{
			this.curso  = null;
			this.idCurso = "0";
		}
		return this.cursos;
	}
	
	public String getIdEixo() {
		return idEixo;
	}

	public void setIdEixo(String idEixo) {
		this.idEixo = idEixo;
	}
	
	public String getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public void remover(Curso curso) {
		CursoRepository repository = new CursoRepository(this.getManager());
		repository.remover(curso);

		this.cursos = null;
	}
	
	public String getIdSemestre() {
		return idSemestre;
	}

	public void setIdSemestre(String idSemestre) {
		this.idSemestre = idSemestre;
	}

	public String getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	
	public String getIdTurnoOferta() {
		return idTurnoOferta;
	}

	public void setIdTurnoOferta(String idTurnoOferta) {
		this.idTurnoOferta = idTurnoOferta;
	}
	

	public String getIdDisciplinaOfertada() {
		return idDisciplinaOfertada;
	}

	public void setIdDisciplinaOfertada(String idDisciplinaOfertada) {
		this.idDisciplinaOfertada = idDisciplinaOfertada;
	}

	public void removerPeriodo(CursosPeriodos c) {
		EntityManager entity = this.getManager();
		CursoRepository cursoRepository = new CursoRepository(entity);
		Curso curso = cursoRepository.buscar(c.getCurso().getId());

		SemanaRepository semanaRepository = new SemanaRepository(entity);
		Turno turno = semanaRepository.buscarTurno(c.getTurno().getId());

		CursosPeriodosRepository repository = new CursosPeriodosRepository(entity);
		c.setCurso(curso);
		c.setTurno(turno);
		repository.remover(c);

		this.periodosCurso = getPeriodosCurso();
	}
	public String preparaEditar(Curso curso) {
		this.setCurso(curso);
		return "editar?faces-redirect=true";
	}

	public String preparaDefinirPeriodos(Curso curso) {
		this.setCurso(curso);
		periodosCurso = getPeriodosCurso();
		turnoID = null;
		qtdePeriodos = null;
		return "definir_periodos?faces-redirect=true";
	}

	public void editar() {
		CursoRepository repository = new CursoRepository(this.getManager());
		repository.editar(curso);

		FacesMessage mensagem = new FacesMessage("Registro alterado.");
		FacesContext.getCurrentInstance().addMessage(null ,mensagem);
	}

	private EntityManager getManager() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		return (EntityManager) request.getAttribute("entityManager");
	}

	public Integer getTurnoID() {
		return turnoID;
	}

	public void setTurnoID(Integer turnoID) {
		this.turnoID = turnoID;
	}

	public void adicionarPeriodo(){
		EntityManager entity = this.getManager();

		SemanaRepository eixoRepository = new SemanaRepository(entity);
		Turno turno = eixoRepository.buscarTurno(turnoID);

		CursoRepository cursoRepository = new CursoRepository(entity);
		this.curso = cursoRepository.buscar(curso.getId());

		CursosPeriodos cursoPeriodo = new CursosPeriodos();
		cursoPeriodo.setCurso(curso);
		cursoPeriodo.setTurno(turno);
		cursoPeriodo.setQtdePeriodos(qtdePeriodos);

		CursosPeriodosRepository repository = new CursosPeriodosRepository (entity);
		repository.adicionar(cursoPeriodo);

		this.qtdePeriodos = null;
		this.turnoID = null;

		FacesMessage mensagem = new FacesMessage("Período do curso adicionado com sucesso.");
		FacesContext.getCurrentInstance().addMessage(null ,mensagem);
	}

	public List<CursosPeriodos> getPeriodosCurso() {
		CursoRepository cursoRepository = new CursoRepository(this.getManager());
		this.curso = cursoRepository.buscar(this.curso.getId());

		CursosPeriodosRepository repository = new CursosPeriodosRepository(this.getManager());
		this.periodosCurso = repository.getPeriodosCurso(this.curso);


		return this.periodosCurso;
	}
	
	public List<Integer> getSemestres() {

		CursoRepository repository = new CursoRepository(this.getManager());

		Curso curso = new Curso();
		curso = repository.buscar(Long.parseLong(this.idCurso));
		this.semestres = null;
		
		if( curso != null)
		{
			
			this.semestres = new ArrayList<Integer>();
			
			for(int i = 1; i <= curso.getSemetre(); i++){
				this.semestres.add(i);
			}
			
			
		}
		return this.semestres;
		
	}

	public void setSemestres(List<Integer> semestres) {
		this.semestres = semestres;
	}

	public void alterarEixo() {
		this.cursos = null;
		this.semestres = null;
		this.disciplinas = null;
		this.idSemestre = "0";
	}
	
	public void alterarCurso() {		
		this.semestres = null;
		this.disciplinas = null;
		this.idSemestre = "0";
	}
	
	public void alterarSemestre() {		
		this.disciplinas = null;
	}
	
	public void alterarTurno() {		
		this.disciplinas = null;
	}

	public List<Disciplina> getDisciplinas() {
		
		long idEixo = Long.parseLong(this.idEixo);
		long idCurso = Long.parseLong(this.idCurso);
		long idSemestre = Long.parseLong(this.idSemestre);
		long idTurno = Long.parseLong(this.idTurno);
		
		
		DisciplinaRepository repository = new DisciplinaRepository(this.getManager());
        this.disciplinas = repository.getDisciplinasBuscar(idEixo, idCurso, idSemestre, idTurno);
		return this.disciplinas;
	}

	public void setDisplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	
	public void ofertar(){
		//FIXME TESTE 01System.out.println("Turno: "+this.idTurnoOferta+" Disciplina: "+this.idDisciplinaOfertada);
		
		long idTurno = Long.parseLong(this.idTurnoOferta);
		long idDisciplina = Long.parseLong(this.idDisciplinaOfertada);
		
		try {
			OfertaDisciplinaRepository repository = new OfertaDisciplinaRepository(this.getManager());
	        repository.ofertar(idDisciplina, idTurno);
		}catch (ApplicationException ex) {
	    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao fertar :",ex.getMessage()));
    	}
	}
	
	public void desofertar(){
		
	}
	
	
}
