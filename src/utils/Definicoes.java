package utils;

public interface Definicoes {
	public static final int STRING_NOME = 200;
	public static final String DADOS_CONEXAO = "horario-pu";
	public static final String PROFESSOR_LISTAR = "Professor.findAll";
	public static final String PROFESSOR_LISTAR_POR_NOME = "Professor.findByNome";
	public static final String PROFESSOR_RESETAR_CRED_LIVRE = "Professor.resetarCreditosLivres";
	public static final String PROFESSOR_COUNT = "Professor.count";
	public static final String PROFESSOR_GET_LOGIN = "Professor.login";
	public static final String PROFESSOR_INDISPONIBILIDADE = "ProfessorIndisponibilidade.findByProfessor";
	public static final String PROFESSOR_INDISPONIBILIDADE_GET = "ProfessorIndisponibilidade.find";
	public static final String PROFESSOR_INDISPONIBILIDADE_POR_TURNO = "ProfessorIndisponibilidade.findByTurno";
	
	public static final String PERIODO_LETIVO_INICIAR = "Periodo.iniciar";
	public static final String PERIODO_LETIVO_ATIVAR = "Periodo.ativar";
	public static final String PERIODO_LETIVO_COUNT = "Periodo.count";
	public static final String PERIODO_LETIVO_LISTAR = "Periodo.findAll";
	public static final String PERIODO_LETIVO_GET_ATIVO = "Periodo.getAtivo";
	
	public static final String DIA_SEMANA_LISTAR = "DiaDaSemana.listar";
	public static final String DIA_SEMANA_LISTAR_N_DIAS = "DiaDaSemana.listarByNumDias";
	
	
	public static final String DISCIPLINAS_LISTAR = "Disciplinas.listar";
	public static final String DISCIPLINAS_BUSCAR = "Disciplinas.buscar"; 
	public static final String DISCIPLINA_COUNT = "Disciplina.count";
	
	public static final String DISCIPLINA_OFERTA_LISTAR = "DisciplinaOferta.ofertadas";
	public static final String DISCIPLINA_OFERTA_PROFESSOR_LISTAR = "DisciplinaOfertaProfessor.listar";
	public static final String DISCIPLINA_OFERTA_PROFESSOR_LISTAR_POR_CURSO_TURNO = "DisciplinaOfertaProfessor.listarByCursoTurno";
	
	public static final String MATRIZ_CURRICULAR_CURSO = "Matriz_curricular.curso";
	public static final String MATRIZ_COUNT = "Matriz_curricular.count";
	
	public static final String CURSO_LISTAR = "Curso.listar";
	public static final String CURSO_LISTAR_EIXO = "Curso.listarByEixo";
	public static final String CURSO_COUNT = "Curso.count";
	
	public static final String EIXO_LISTAR = "Eixo.listar";
	public static final String EIXO_COUNT = "Eixo.count";
	
	public static final String TURNO_LISTAR = "Turno.listar";
	public static final String PERIODO_COUNT = "Periodos.count";
	
	public static final String PERIODO_DO_CURSO = "Periodos.periodoDoCurso";
	public static final String PERIODO_GET = "Periodos.getPeriodo";
	
	public static final String CONFIGURACAO_GET = "Configuracao.getConfiguracao";
	public static final String SEMESTRE_LETIVO_COUNT = null;
	public static final String SEMESTRE_LETIVO_LISTAR = null;
	public static final String SEMESTRE_LETIVO_INICIAR = null;
	public static final String SEMESTRE_LETIVO_ATIVAR = null;
	
}
