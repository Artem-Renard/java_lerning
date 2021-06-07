package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testRegistration() throws MessagingException, IOException {
    // идентификатор для создания новых пользователей при каждом запуске теста
    long now = System.currentTimeMillis();
    String user = String.format("user%s", now);
    String password = "password";
    String email = String.format("user%s@localhost.localdomain", now);
    // регистрация пользователя
    app.registration().start(user, email);
    // получение почты с внутреннего сервера
    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
    // извлечение ссылки для подтверждения регистрации из писем отправленных на email
    String confirmationLink = findConfirmationLink(mailMessages, email);
    // подтверждение регистрации пользователя
    app.registration().finish(confirmationLink, user, password);
    // проверка входа с помощью протокола HTTP
    assertTrue(app.newSession().login(user, password));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    // ищем письмо отправленное на email используя filter передаваемый предикатом
    // (функция возвращающая или ложь или истину) (m) -> m.to.equals(email) остануться только те сообщения в потоке,
    // которые были отправленны на email и берем первое сообщение
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    // с помощью библиотеки регулярных выражений ищем
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    // применили регулярное выражение к тексту письма и вернули
    // т.е. возвращается тот кусок текста, который соответствует построенному regex (регулярному выражению)
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}