package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;


public class AddContactToGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions () {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test3"));
    }
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      Groups groups = app.db().groups();
      app.contact().create(new ContactData().withFirstname("TestName").inGroup(groups.iterator().next()), true);
    }
  }

  @Test
  public void testAddContactToGroup() {
    Contacts contact = app.db().contacts();
    Groups group = app.db().groups();
    ContactData contactForGroup = contact.iterator().next(); // выбор произвольного контакта
    GroupData groupForContact = group.iterator().next(); // выбор произвольной группы
    app.goTo().homePage();
    app.contact().selectAllGroupForContacts();
    app.contact().selectContactById(contactForGroup.getId());
    app.contact().selectGroupForAddingToContact(groupForContact.getId());
    app.contact().addGroupToContact();
    app.goTo().homePage();
  }

}
