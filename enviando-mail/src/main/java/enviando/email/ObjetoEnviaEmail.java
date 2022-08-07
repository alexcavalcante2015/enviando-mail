package enviando.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {
	
	private String userName = "alexcomte524@gmail.com";
	private String senha = "wbxdcoayrwczauiv";
	
	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";
	
	public ObjetoEnviaEmail(String listaDestinatarios, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinatarios;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}
	
	public void enviarEmail(boolean envioHtml) throws Exception{
		Properties properties = new Properties();
		
		properties.put("mail.smtp.ssl.trust", "*");
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
		
		Address[] toUser = InternetAddress.parse(listaDestinatarios);
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente)); /*Quem está enviando*/
		message.setRecipients(Message.RecipientType.TO, toUser); /*Email de destino*/
		message.setSubject(assuntoEmail); /*Assunto do e-mail*/
		
		if (envioHtml) {
			message.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			message.setText(textoEmail); /*Corpo do e-email*/
		}
		
		Transport.send(message);
	}
	
	public void enviarEmailAnexo(boolean envioHtml) throws Exception{
		Properties properties = new Properties();
		
		properties.put("mail.smtp.ssl.trust", "*");
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
		
		Address[] toUser = InternetAddress.parse(listaDestinatarios);
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente)); /*Quem está enviando*/
		message.setRecipients(Message.RecipientType.TO, toUser); /*Email de destino*/
		message.setSubject(assuntoEmail); /*Assunto do e-mail*/
		
		/*Parte 1 do e-mail que é o texto e a descrição do e-mail*/
		MimeBodyPart corporEmail = new MimeBodyPart();
		
		if (envioHtml) {
			corporEmail.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			corporEmail.setText(textoEmail); /*Corpo do e-email*/
		}
	
		/*Parte 2 do e-mail que são os anexos em pdf ou qualquer outra coisa*/
		MimeBodyPart anexoEmail = new MimeBodyPart();
		
		/*Onde é passado o simuladorDePdf você passa o seu arquivo gravado no banco de dados ou  qualquer outro local*/
		anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(simuladorDePdf(), "application/pdf")));
		anexoEmail.setFileName("anexoemail.pdf");
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(corporEmail);
		multipart.addBodyPart(anexoEmail);
		
		message.setContent(multipart);
		
		Transport.send(message);
	}
	
	/**
	 * Esse método simula o PDF ou qualquer arquivo que possa ser enviado por anexo no email.
	 * Você pode pegar o arquivo no seu banco de dados base64, byte[], Stream de aArquivos.
	 * Pode está em um banco de dados, ou em uma pasta, ou qualquer lugar.
	 * 
	 * Retorna um PDF em branco com o texto do Parágrafo de exemplo.
	 * */
	private FileInputStream simuladorDePdf() throws Exception{
		Document document = new Document();
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteúdo do PDF anexo com Java Mail, esse texto é do PDF"));
		document.close();
		
		return new FileInputStream(file);
	}
	
}
