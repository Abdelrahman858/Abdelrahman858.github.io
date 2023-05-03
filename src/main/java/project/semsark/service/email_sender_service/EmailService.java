package project.semsark.service.email_sender_service;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.semsark.exception.HelperMessage;

import java.io.IOException;


@Service
public class EmailService {

    @Autowired
    SendGrid sendGrid;

    public void sendEmail(String toEmail,
                          String body,
                          String subject) {

        try {
            Mail mail = prepareMail(toEmail, body, subject);

            Request request = new Request();

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");

            request.setBody(mail.build());


            Response response = sendGrid.api(request);

            if (response != null) {
                System.out.println("response code from sendgrid" + response.getHeaders());

            }

        } catch (IOException e) {


            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HelperMessage.EMAIL_WRONG);
        }

    }

    public Mail prepareMail(String toEmail,
                            String body,
                            String subject) {


        Email fromEmail = new Email();

        fromEmail.setEmail("abdelrahmn.ahmed119@gmail.com");

        Email to = new Email();
        to.setEmail(toEmail);
        Mail mail = new Mail(fromEmail, subject, to, new Content("text/html", convertHtmlToString(body)));


        Personalization personalization = new Personalization();

        personalization.addTo(to);
        mail.addPersonalization(personalization);

        return mail;
    }

    public String convertHtmlToString(String otp) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <link href=\"https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Slices</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<style>\n" +
                "    @media screen {\n" +
                "        @font-face {\n" +
                "            font-family: 'Lato';\n" +
                "            font-style: normal;\n" +
                "            font-weight: 400;\n" +
                "            src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');\n" +
                "        }\n" +
                "\n" +
                "        @font-face {\n" +
                "            font-family: 'Lato';\n" +
                "            font-style: normal;\n" +
                "            font-weight: 700;\n" +
                "            src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');\n" +
                "        }\n" +
                "\n" +
                "        @font-face {\n" +
                "            font-family: 'Lato';\n" +
                "            font-style: italic;\n" +
                "            font-weight: 400;\n" +
                "            src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');\n" +
                "        }\n" +
                "\n" +
                "        @font-face {\n" +
                "            font-family: 'Lato';\n" +
                "            font-style: italic;\n" +
                "            font-weight: 700;\n" +
                "            src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    /* CLIENT-SPECIFIC STYLES */\n" +
                "    body,\n" +
                "    table,\n" +
                "    td,\n" +
                "    a {\n" +
                "        -webkit-text-size-adjust: 100%;\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "    }\n" +
                "\n" +
                "    table,\n" +
                "    td {\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "    }\n" +
                "\n" +
                "    img {\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "    }\n" +
                "\n" +
                "    /* RESET STYLES */\n" +
                "    img {\n" +
                "        border: 0;\n" +
                "        height: auto;\n" +
                "        line-height: 100%;\n" +
                "        outline: none;\n" +
                "        text-decoration: none;\n" +
                "    }\n" +
                "\n" +
                "    table {\n" +
                "        border-collapse: collapse !important;\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "        height: 100% !important;\n" +
                "        margin: 0 !important;\n" +
                "        padding: 0 !important;\n" +
                "        width: 100% !important;\n" +
                "        background-color: #f4f4f4;\n" +
                "    }\n" +
                "\n" +
                "    /* iOS BLUE LINKS */\n" +
                "    a[x-apple-data-detectors] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: none !important;\n" +
                "        font-size: inherit !important;\n" +
                "        font-family: inherit !important;\n" +
                "        font-weight: inherit !important;\n" +
                "        line-height: inherit !important;\n" +
                "    }\n" +
                "\n" +
                "    /* MOBILE STYLES */\n" +
                "    @media screen and (max-width: 600px) {\n" +
                "        h1 {\n" +
                "            font-size: 32px !important;\n" +
                "            line-height: 32px !important;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    /* ANDROID CENTER FIX */\n" +
                "    div[style*=\"margin: 16px 0;\"] {\n" +
                "        margin: 0 !important;\n" +
                "    }\n" +
                "</style>\n" +
                "\n" +
                "<div style=\"display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Lato', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\">\n" +
                "    We're thrilled to have you here! Get ready to dive into your new account.\n" +
                "</div>\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "    <!-- LOGO -->\n" +
                "    <tr>\n" +
                "        <td align=\"center\" bgcolor=\"#ed4e53\">\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width: 600px;\" width=\"100%\">\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" style=\"padding: 40px 10px 40px 10px;\" valign=\"top\"></td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td align=\"center\" bgcolor=\"#ed4e53\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width: 600px;\" width=\"100%\">\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\"\n" +
                "                        valign=\"top\">\n" +
                "                        <h1 style=\"font-size: 48px; font-weight: 400; margin: 2;\">Welcome!</h1> <img\n" +
                "                            height=\"120\" src=\"http://androthemes.com/themes/react/slices/assets/img/logo.png\"\n" +
                "                            style=\"display: block; border: 0px;\" width=\"125\"/>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td align=\"center\" bgcolor=\"#f4f4f4\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width: 600px;\" width=\"100%\">\n" +
                "                <tr>\n" +
                "                    <td align=\"left\" bgcolor=\"#ffffff\"\n" +
                "                        style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                        <p style=\"margin: 0;\">We're excited to have you get started. First, you need to confirm your\n" +
                "                            account. Just press the button below.</p>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td align=\"left\" bgcolor=\"#ffffff\">\n" +
                "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                            <tr>\n" +
                "                                <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 20px 30px 60px 30px;\">\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                                        <tr>\n" +
                "                                            <td align=\"center\" bgcolor=\"#ed4e53\" style=\"border-radius: 3px;\">\n" +
                "                                                <h1>" + otp + "</h1>" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr> <!-- COPY -->\n" +
                "\n" +
                "\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }

}