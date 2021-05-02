package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class DeleteContactTests  extends TestBase {

  @Test
  public void testDeleteContact () throws InterruptedException {
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
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectContact();
    app.getContactHelper().acceptDeleteSelectContact();
    app.getContactHelper().goHomePage();
  }
}
