package marc.dev.emailapp.service;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;

import marc.dev.emailapp.utils.EmailUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.Map;

import static marc.dev.emailapp.utils.EmailUtils.getVerificationUrl;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService{

    private static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    private static final String UTF_8_ENCODING = "UTF-8";
    public static final String TEXT_HTML_ENCODING = "text/html";
    private final String host="http://localhost:8080";
    private String fromEmail="marckenlygbeau@gmail.com";
    private String EMAIL_TEMPLATE ="emailtemplate";
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    @Override
    public void sendSimpleMailMessage(String name, String to, String token) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();

            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(EmailUtils.getEmailMessage(name,host,token));
            emailSender.send(message);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());

        }

    }



    @Override
    public void sendSimpleMailMessageWithAttachments(String name, String to, String token) {
        try{
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(EmailUtils.getEmailMessage(name,host,token));

            //Add Attachments
            FileSystemResource Bridg = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/moon-tip.jpg"));
            FileSystemResource MarcCv = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/full+stack+developer-Marckenley.pdf"));

            helper.addAttachment(Bridg.getFilename(), Bridg);
            helper.addAttachment(MarcCv.getFilename(), MarcCv);


            emailSender.send(message);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());

        }

    }

    @Override
    public void sendSimpleMailMessageWithAttachmentsImages(String name, String to, String token) {

        try{
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(EmailUtils.getEmailMessage(name,host,token));

            //Add Attachments
            FileSystemResource Bridg = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/moon-tip.jpg"));
            FileSystemResource MarcCv = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/full+stack+developer-Marckenley.pdf"));

            helper.addAttachment(Bridg.getFilename(), Bridg);
            helper.addAttachment(MarcCv.getFilename(), MarcCv);

            emailSender.send(message);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());

        }

    }

    @Override
    public void sendSimpleMailMessageWithEmbeddedFiles(String name, String to, String token) {
        try{
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(EmailUtils.getEmailMessage(name,host,token));

            //Add Attachments
            FileSystemResource Bridg = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/moon-tip.jpg"));
            FileSystemResource MarcCv = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/full+stack+developer-Marckenley.pdf"));

            helper.addInline( getContentId(Bridg.getFilename()), Bridg);
            helper.addInline(getContentId(MarcCv.getFilename()), MarcCv);


            emailSender.send(message);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());

        }

    }

    @Override
    public void sendHtmlMail(String name, String to, String token) {
        try{
            Context context = new Context();
            context.setVariables(Map.of("name",name, "url", getVerificationUrl(host,token)));
            String text = templateEngine.process(EMAIL_TEMPLATE, context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(text, true);

            //Add Attachments
            FileSystemResource Bridg = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/moon-tip.jpg"));
            FileSystemResource MarcCv = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/full+stack+developer-Marckenley.pdf"));

            helper.addInline( getContentId(Bridg.getFilename()), Bridg);
            helper.addInline(getContentId(MarcCv.getFilename()), MarcCv);

            emailSender.send(message);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());

        }

    }

    @Override
    public void sendHtmlMailMessageWithEmbeddedFiles(String name, String to, String token) {
        try{


            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
//            helper.setText(text, true);

            Context context = new Context();
            context.setVariables(Map.of("name",name, "url", getVerificationUrl(host,token)));
            String text = templateEngine.process(EMAIL_TEMPLATE, context);

//            Add the Html Email Body

            MimeMultipart mimeMultipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(text, TEXT_HTML_ENCODING);
            mimeMultipart.addBodyPart(messageBodyPart);
//            Add images to the mail body
            BodyPart imageBodyPart= new MimeBodyPart();
            DataSource dataSource = new FileDataSource(System.getProperty("user.home") + "/Downloads/moon-tip.jpg");
            imageBodyPart.setDataHandler(new DataHandler(dataSource));
            imageBodyPart.setHeader("Content-ID","image");
            mimeMultipart.addBodyPart(imageBodyPart);

            message.setContent(mimeMultipart);
            emailSender.send(message);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());

        }

    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }
    private String getContentId(String fileName) {
        return "<" + fileName + ">";
    }
}
