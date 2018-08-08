package login;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;


public class EmailUtils {

	private static final String HOSTNAME = "smtp.gmail.com";
	private static final String USERNAME = "Kleber";
	private static final String PASSWORD = "";
	private static final String EMAILORIGEM = "";

	public static SimpleEmail conectaEmail(){
		SimpleEmail email = new SimpleEmail();
		email.setSSLOnConnect(true);
		email.setHostName(HOSTNAME);
		email.setSmtpPort(587);
		email.setAuthentication(EMAILORIGEM, PASSWORD);
		try {
			email.setFrom(EMAILORIGEM, USERNAME);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return email;
	}

	public static boolean enviaEmail(String titulo, String mensagem, String destino) {
		
		HtmlEmail email = new HtmlEmail();
		email.setHostName( HOSTNAME );
		email.setSslSmtpPort( "465" );
		email.setStartTLSRequired(true);
		email.setSSLOnConnect(true);
		email.setAuthenticator( new DefaultAuthenticator( EMAILORIGEM ,  PASSWORD ) );
		try {
		    email.setFrom( EMAILORIGEM);
		     
		    email.setDebug(true);
		     
		    email.setSubject( titulo );
		    email.setHtmlMsg( mensagem );
		    email.addTo( destino );//por favor trocar antes de testar!!!!
		     
		    email.send();
		     
		} catch (EmailException e) {
			System.out.println("ERRO AO ENVIAR EMAIL!!!");
		    //e.printStackTrace();
		    
		    return false;
		}

		return true;
	}
}
