package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class DeleteGroupTests extends TestBase {

  @Test
  public void testDeleteGroup() throws Exception {
 app.goToGroupPage();
    app.selectGroup();
    app.deleteSelectedGroups();
    app.returnToGroupPage();
    app.logout();
  }

}
