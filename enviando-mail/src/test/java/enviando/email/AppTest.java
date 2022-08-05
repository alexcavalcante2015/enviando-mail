package enviando.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	private String userName = "alexcomte524@gmail.com";
	private String senha = "wbxdcoayrwczauiv";
   
	@org.junit.Test
	public void testeEmail() {
		try {
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true"); /*Autorização*/
		properties.put("mail.smtp.starttls", "true"); /*Autenticação*/
		properties.put("mail.smtp.host", "smtp.gmail.com"); /*Servidor gmail Google*/
		properties.put("mail.smtp.port", "465"); /*Porta do servidor*/
		properties.put("mail.smtp.socketFactory.port", "465"); /*Especifica a porta a ser conectada pelo socket*/
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); /*Classe socket de conexão ao SMTP*/
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});
		
		Address[] toUser = InternetAddress.parse("alexcavalcante2015@gmail.com, mastodonalex2@gmail.com, alexcomte524@gmail.com");
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName)); /*Quem está enviando*/
		message.setRecipients(Message.RecipientType.TO, toUser); /*Email de destino*/
		message.setSubject("Chegou o e-mail enviado com Java"); /*Assunto do e-mail*/
		message.setText("Olá programador, você acaba de receber um e-mail enviado com Java do curso Formação Java Web do Alex"); /*Corpo do e-email*/
		
		Transport.send(message);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
