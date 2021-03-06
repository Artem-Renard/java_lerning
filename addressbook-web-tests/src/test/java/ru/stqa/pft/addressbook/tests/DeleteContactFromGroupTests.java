package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class DeleteContactFromGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
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
    // проверка наличия контакта в группе
    Contacts beforeContact = app.db().contacts(); // получение списка контактов до теста
    ContactData contactForGroup = beforeContact.iterator().next(); // выбор произвольного контакта с группой
    if (contactForGroup.getGroups().size() == 0) {
      Groups beforeGroup = app.db().groups(); // получение списка групп до теста
      ContactData modifiedContact = beforeContact.iterator().next(); // выбор произвольного контакта
      GroupData modifiedGroup = beforeGroup.iterator().next(); // выбор произвольной группы
      app.goTo().homePage();
      app.contact().contactAddToGroup(modifiedContact, modifiedGroup);
      app.goTo().homePage();
    }
  }

  @Test
  public void testDeleteContactFromGroup() {
    Contacts beforeContact = app.db().contacts(); //список контактов из бд
    ContactData contactForGroup = beforeContact.iterator().next(); // выбор произвольного контакта
    Groups beforeInGroups = app.db().contactCountGroups(); // до удаления контакта в группы
    String name = contactForGroup.getGroups().iterator().next().getName();
    app.goTo().homePage();
    /*app.contact().contactDelToGroup(contactForGroup, name);*/
    app.goTo().homePage();

    // группа из которой удалили контакт
    GroupData groupForContact = contactForGroup.getGroups()
            .stream().filter(g -> g.getName().equals(name)).findFirst().get();

    Contacts afterContact = app.db().contacts();
    // хэширование по размеру контактов , если падает то дальше тест не выполняется
    assertThat(afterContact.size(), equalTo(beforeContact.size())); // проверка на совпадение колич-ва контактов

    Groups afterInGroups = app.db().contactCountGroups(); // после удаление контакта из группы
    // проверка на соответствие
    assertThat((afterInGroups), equalTo(new Groups(beforeInGroups.without(groupForContact))));
  }
}
