package utils.exception;

import java.sql.BatchUpdateException;
import java.sql.SQLException;

import org.hibernate.exception.SQLGrammarException;

public class ApplicationException extends Exception {
	
	private final int JDBCConnectionException = 0;
	private final int SQLGrammarException = 1;
	private final int ConstraintViolationException = 2;
	private final int LockAcquisitionException = 3;
	private final int GenericJDBCException = 4;
	private final int DataException = 5;
	
	public static void main(String[] args) {
		String detalhes ="duplicar valor da chave viola a restrição de unicidade \"professor_cpf_key\""
				+ "  Detalhe: Chave (cpf)=(11111111111) já existe.";;
		String campo = detalhes.split("=")[0]
							.split("\\(")[1]
									.replace(")", ""); detalhes  =detalhes + " Campo "+campo+" já existe.";
	System.out.println(detalhes);
	}
	private Exception exception;
	private String detalhes;
	public ApplicationException(Exception e) {
		exception=(Exception) e.getCause();

		System.out.println("switch");
		switch (getTipoCausaExcecao()) {
			case ConstraintViolationException:{
				System.out.println("ConstraintViolationException");
				
				BatchUpdateException batch = (BatchUpdateException) exception.getCause();
				SQLException sqlException = batch.getNextException();
				//23505 sqlException.getSQLState()
				
				detalhes = "Violação de restrição, não é permitido registro duplicado.";
				try{ String campo = sqlException.getMessage().split("=")[0].split("\\(")[1].replace(")", ""); detalhes =detalhes + " Campo ["+campo+"] já existe.";}catch (java.util.regex.PatternSyntaxException e2) {e2.printStackTrace();} 
				
				//System.out.println(detalhes+"\nState"+sqlException.getSQLState()+"\nSqlException "+sqlException.getErrorCode()+"\nSqlException Message "+sqlException.getMessage());
			}break;
			
			case DataException:{
				System.out.println("DataException");
				/*
				*/
				SQLException sqlException = ((org.hibernate.exception.DataException)exception).getSQLException();
				SQLException nextSqlException =sqlException.getNextException();
				//22001 sqlException.getSQLState()
				
				detalhes = "Operação incompatível com as definições da base dados, ";				
				try{  detalhes =detalhes +nextSqlException.getMessage().split(":")[1] ;}catch (java.util.regex.PatternSyntaxException e2) {e2.printStackTrace();} 
			}break;
			case SQLGrammarException:{
				System.out.println("SQLGrammarException");
				org.postgresql.util.PSQLException batch = (org.postgresql.util.PSQLException) exception.getCause();
				detalhes = "Erro relacionado a permissão, tabela/relação não encontrada, etc. ";				
				try{  detalhes =detalhes +batch.getMessage().split(":")[1] ;}catch (java.util.regex.PatternSyntaxException e2) {e2.printStackTrace();} 
			}break;
			default:
				break;
		}
		
		
		//javax.persistence.TransactionRequiredException: no transaction is in progress
		/*
		if(exception.getCause() instanceof ConstraintViolationException){
			ConstraintViolationException causa = (ConstraintViolationException) exception.getCause();
			
			BatchUpdateException batchUpdateException = (BatchUpdateException) causa.getCause();
			SQLException sqlException = batchUpdateException.getNextException();
			
			    
			String detalhes = "Sql: "+causa.getSQL() + "\nCode: "+causa.getErrorCode()+" \nSlqException "+causa.getSQLException()
					+"\nState"+causa.getSQLState()+"\nSqlException "+sqlException.getErrorCode()+"\nSqlException Message "+sqlException.getMessage();
			this.detalhes = detalhes;
			//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ",detalhes));
		}else if(exception.getCause() instanceof DataException){//tamanho de campo
			
			SQLException sqlException = ((DataException)exception.getCause()).getSQLException();
			
			    
			String detalhes = "Erro devido a estrutura da tabela.\n SlqException "+((JDBCException) exception.getCause()).getSQLException()
					+"\nState"+sqlException.getSQLState()+"\nSqlException "+sqlException.getErrorCode()+"\nSqlException Message "+sqlException.getMessage();
			this.detalhes = detalhes;
			//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ",detalhes));
		}else{
			
			BatchUpdateException batchUpdateException = (BatchUpdateException) exception.getCause().getCause();
			
			if(batchUpdateException.getNextException() instanceof SQLException){
				SQLException sqlException = batchUpdateException.getNextException();
				String detalhes = "SlqException "+((JDBCException) exception.getCause()).getSQLException()
					+"\nState"+((SQLException) exception.getCause()).getSQLState()+"\nSqlException "+sqlException.getErrorCode()+"\nSqlException Message "+sqlException.getMessage();
				this.detalhes = detalhes;
			}
			
		}
		*/
	}
	
	private int getTipoCausaExcecao(){
	
		Throwable cause = exception;
		if(cause instanceof org.hibernate.exception.JDBCConnectionException)
			return JDBCConnectionException;
		if(cause instanceof org.hibernate.exception.ConstraintViolationException)
			return ConstraintViolationException;
		if(cause instanceof org.hibernate.exception.GenericJDBCException)
			return GenericJDBCException;
		if(cause instanceof org.hibernate.exception.LockAcquisitionException)
			return LockAcquisitionException;
		if(cause instanceof org.hibernate.exception.SQLGrammarException)
			return SQLGrammarException;
		if(cause instanceof org.hibernate.exception.DataException)
			return DataException;
		return -1;
		
	}
/**
 */
	@Override
	public String getMessage() {
		return (detalhes != null)?detalhes:"Ocorreu um erro, implementar tratamento em utils.exception.ApplicationException. Detalhes: "+super.getMessage();
	}
}
