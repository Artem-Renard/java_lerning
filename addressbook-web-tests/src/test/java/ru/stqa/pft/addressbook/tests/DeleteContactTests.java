package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class DeleteContactTests  extends TestBase {

  @Test
  public void testDeleteContact () throws InterruptedException {
    app.getContactHelper().returnHomePage();
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectContact();
    app.getContactHelper().acceptDeleteSelectContact();
    app.getContactHelper().expectHomePage();
    app.getContactHelper().returnHomePage();
  }
}
