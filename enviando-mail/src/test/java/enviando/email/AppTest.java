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
	
	
   
	@org.junit.Test
	public void testeEmail() throws Exception{
		
		ObjetoEnviaEmail enviaEmail = 
				new ObjetoEnviaEmail("alexcavalcante2015@gmail.com, mastodonalex2@gmail.com", 
									"Alex do JDev Treinamento",
									"Testando e-mail com Java", 
									"Esse texto é a descrição do meu email");
		
		enviaEmail.enviarEmail();
		
		/*Caso o email não esteja sendo enviado então
		 * coloque um tempo de espera mais isso só pode
		 * usado para testes*/
		Thread.sleep(5000);
		
		
	}
	
}
