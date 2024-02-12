package email.services.email;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailClient {
	private final Session session;
	private final String fromEmail;

	public EmailClient(String email, String password) {
		this.fromEmail = email;

		// Configure the email client properties
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.ionos.fr");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		// Authenticator
		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		};

		// Create the session
		this.session = Session.getInstance(props, auth);
	}

	private MimeMessage createMessage(String toEmail, String subject, String body) throws MessagingException {
		InternetAddress from;
		try {
			from = new InternetAddress(this.fromEmail, "NoReply-JD");
		} catch (Exception e) {
			from = new InternetAddress(this.fromEmail);
		}
		MimeMessage msg = new MimeMessage(session);
		msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		msg.setFrom(from);
		msg.setSentDate(new Date());
		msg.setReplyTo(InternetAddress.parse(this.fromEmail, false));
		msg.setSubject(subject, "UTF-8");
		msg.setText(body, "UTF-8");
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
		return msg;
	}

	public void sendEmail(String toEmail, String subject, String body) {
		try {
			MimeMessage msg = createMessage(toEmail, subject, body);
			Transport.send(msg);
			System.out.println("Email Sent Successfully!!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void sendAttachmentEmail(String toEmail, String subject, String body, File file) {
		try {
			MimeMessage msg = createMessage(toEmail, subject, body);

			// Prepare the email with attachment
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(body);
			MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			attachmentBodyPart.setDataHandler(new DataHandler(new FileDataSource(file.getAbsolutePath())));
			attachmentBodyPart.setFileName(file.getName());

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(attachmentBodyPart);
			msg.setContent(multipart);

			Transport.send(msg);
			System.out.println("Email Sent Successfully with attachment!!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
