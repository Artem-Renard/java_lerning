package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class AddContactToGroupTests extends TestBase {

  // предусловие, инициализация локальная - подготовка состояния
  @BeforeMethod
  public void ensurePreconditions () {
    // проверка наличия в БД групп, если их нет, то создает одну
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test3"));
    }
    // проверка наличия в БД контакта, если его нет, то создает один
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
    Groups beforeInGroups = app.db().groups(); // до добавления контакта в группы
    app.goTo().homePage();
    app.contact().selectAllGroupForContacts(); // выбор всех контактов (all) на странице контактов
    app.contact().selectContactById(modifiedContact.getId()); // выбор контакта по Id
    app.contact().selectGroupForAddingToContact(modifiedGroup.getId()); // выбор группы для добавления в нее контакта
    app.contact().addGroupToContact();
    app.goTo().homePage();

    Contacts afterContact = app.db().contacts();
    assertThat(afterContact.size(), equalTo(beforeContact.size())); // проверка на совпадение колич-ва контактов
    Groups afterInGroups = app.db().groups(); // после добавления контакта в группы
    assertThat((afterInGroups), equalTo(new Groups(beforeInGroups.withAdded(modifiedGroup)))); // проверка на соответствие

  }
}
