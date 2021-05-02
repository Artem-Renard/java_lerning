package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests  extends TestBase  {

  @Test
  public void testContactCreation() throws Exception {
    int befor = app.getContactHelper().getContactCount();
    app.getContactHelper().createContact(new ContactData(
                    "TestName",
                    "TestLastName",
                    "TestHomeTelephone",
                    "TestE-mail@test.ru",
                    "test1"),
            false);
    app.getContactHelper().goHomePage();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, befor + 1);
  }
}
