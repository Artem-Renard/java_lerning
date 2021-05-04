package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.HashSet;
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
    ContactData contact = new ContactData(before.get(before.size() - 1).getId(),
            "TestName",
            "TestLastName",
            "TestHomeTelephone",
            "TestE-mail@test.ru",
            "[none]");
    app.getContactHelper().fillContactForm(contact, false);
    app.getContactHelper().initContactModification();
    app.getContactHelper().goHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() - 1);
    before.add(contact);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
  }
}
