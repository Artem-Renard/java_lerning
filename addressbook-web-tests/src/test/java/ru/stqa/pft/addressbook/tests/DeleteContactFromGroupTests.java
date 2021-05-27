package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class DeleteContactFromGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
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
  public void testDeleteContactFromGroup() {
    Contacts contact = app.db().contacts(); //список контактов из бд
    ContactData contactForGroup = contact.iterator().next(); // выбор произвольного контакта с группой
    int contactIdForGroup = contactForGroup.getGroups().iterator().next().getId();
    app.goTo().homePage();
    app.contact().selectGroupWithContacts(contactIdForGroup); // в выпадающем списке выбрали имя группы в которую входит сонтакт
    app.contact().selectContactById(contactForGroup.getId());
    app.contact().deleteContactFromGroup();
    app.goTo().homePage();
  }
}
