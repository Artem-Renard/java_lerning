package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests  extends TestBase  {

  @Test
  public void testContactCreation() throws Exception {

    app.goToAddNewContact();
    app.fillAddNewContactForm(new ContactData("TestName", "TestLastName", "TestHomeTelephone", "TestE-mail@test.ru"));
    app.submitAddNewContact();
    app.returnHomePage();
    app.logout();
  }

}
