package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.model.MailMessage;
import ru.stqa.pft.model.UserData;
import ru.stqa.pft.model.Users;

import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class ChangePasswordTests extends TestBase {

  private int maxId;

  @BeforeMethod
  public void startMailServer(){
    app.mail().start();
  }

  @Test
  public void testChangeUserPassword() throws Exception {
    long now = System.currentTimeMillis();
    String newPassword = "Password" + now;

    int i=0;

    Users usersList = app.db().users();

    UserData selectUser = new UserData();
    for (UserData user : usersList) {
      if (!(user.getUsername().equals("administrator"))) {
        selectUser = user;
        break;
      }
      i += 1;
      if (i == usersList.size()) {
        String email = String.format("user%s@localhost.localdomain", now);
        String newUser = String.format("user%s", now);
        String password = "password";
        app.registration().start(newUser, email);
        List<MailMessage> mailRegisterMessages = app.mail().waitForMail(2, 10000);
        MailMessage mailMessage=mailRegisterMessages.stream().filter((m)->m.to.equals(email)).findFirst().get();
        String confirmationLink = findConfirmationLink(mailMessage, email);
        app.registration().finish(confirmationLink, newUser, password);
        Users usersListAfter = app.db().users();
        for (UserData eachUser : usersListAfter) {
          if (eachUser.getId() > maxId) {
            selectUser = eachUser;
          }
        }
      }
    }


    String admin = "administrator";
    String password = "root";
    app.administratorActions().loginThroughUi(admin, password);
    app.administratorActions().goToManageUserPage();
    app.administratorActions().resetPasswordSelectUser(selectUser);

    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    MailMessage mailMessage = mailMessages.get(mailMessages.size() - 1);
    String confirmationLink = findConfirmationLink(mailMessage, selectUser.getEmail());

    app.administratorActions().changePassword(confirmationLink, selectUser.getUsername(), newPassword);
    assertTrue(app.newSession().login(selectUser.getUsername(), newPassword));
  }

  private String findConfirmationLink(MailMessage mailMessage, String email) {
    VerbalExpression regex= VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun=true)
  public void stopMailServer(){
    app.mail().stop();
  }
}
