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
    // проверка в БД наличие групп и если их нет, то создание одной
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test3"));
    }
    // проверка в БД наличие контактов и если их нет, то создание одного
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      Groups groups = app.db().groups();
      app.contact().create(new ContactData().withFirstname("TestName").inGroup(groups.iterator().next()), true);
    }
  }

  @Test
  public void testAddContactToGroup() {
    Contacts beforeContact = app.db().contacts(); // получение списка контактов до теста
    Groups beforeGroup = app.db().groups(); // получение списка групп до теста
    ContactData modifiedContact = beforeContact.iterator().next(); // выбор произвольного контакта
    GroupData modifiedGroup = beforeGroup.iterator().next(); // выбор произвольной группы
    app.goTo().homePage();
    app.contact().selectAllGroupForContacts(); // выбор всех контактов (all) на странице контактов
    app.contact().selectContactById(modifiedContact.getId()); // выбор контакта по Id
    app.contact().selectGroupForAddingToContact(modifiedGroup.getId()); // выбор группы для добавления в нее контакта
    app.contact().addGroupToContact();
    app.goTo().homePage();
  }

}
