package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification () {
    app.getContactHelper().returnHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData(
                      "TestName",
                      "TestLastName",
                      "TestHomeTelephone",
                      "TestE-mail@test.ru",
                      "[none]"),
              true);
    }
    app.getContactHelper().editContact();
    app.getContactHelper().fillContactForm(new ContactData(
            "1TestName",
            "1TestLastName",
            "1TestHomeTelephone",
            "1TestE-mail@test.ru",
            null),
            false);
    app.getContactHelper().initContactModification();
    app.getContactHelper().returnHomePage();
  }
}
