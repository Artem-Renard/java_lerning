package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification () {
    app.getContactHelper().returnToHomePage();
    app.getContactHelper().editContact();
    app.getContactHelper().fillAddNewContactForm(new ContactData("1TestName", "1TestLastName", "1TestHomeTelephone", "1TestE-mail@test.ru"));
    app.getContactHelper().updateContactForm();
    app.getNavigationHelper().returnHomePage();
  }
}
