package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests  extends TestBase  {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().createContact(new ContactData(
                    "TestName",
                    "TestLastName",
                    "TestHomeTelephone",
                    "TestE-mail@test.ru",
                    "test1"),
            false);
    app.getContactHelper().returnHomePage();
    app.getSessionHelper().logout();
  }
}
