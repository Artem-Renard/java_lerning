package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification () {
    app.getContactHelper().goHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData(
                      "TestName",
                      "TestLastName",
                      "TestHomeTelephone",
                      "TestE-mail@test.ru",
                      "[none]"),
                      true);
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().editContact();
    app.getContactHelper().fillContactForm(new ContactData(
            "TestName",
            "TestLastName",
            "TestHomeTelephone",
            "TestE-mail@test.ru",
            "[none]"),
            false);
    app.getContactHelper().initContactModification();
    app.getContactHelper().goHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    Assert.assertEquals(before, after);
  }
}
