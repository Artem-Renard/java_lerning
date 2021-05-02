package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.getGroupHelper().goToGroupPage();
    int befor = app.getGroupHelper().getGroupCount();
    app.getNavigationHelper().gotoGroupPage();
    app.getGroupHelper().createGroup(new GroupData(
            "test1",
            "test2",
            "test3"));
    int after = app.getGroupHelper().getGroupCount();
    Assert.assertEquals(after, befor + 1);
  }

}
