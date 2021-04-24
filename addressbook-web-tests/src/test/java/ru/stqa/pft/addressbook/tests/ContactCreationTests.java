package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests  extends TestBase  {

  @Test
  public void testContactCreation() throws Exception {

    app.getContactHelper().goToAddNewContact();
    app.getContactHelper().fillAddNewContactForm(new ContactData("TestName", "TestLastName", "TestHomeTelephone", "TestE-mail@test.ru"));
    app.getContactHelper().submitAddNewContact();
    app.getNavigationHelper().returnHomePage();
    app.getSessionHelper().logout();
  }

}
