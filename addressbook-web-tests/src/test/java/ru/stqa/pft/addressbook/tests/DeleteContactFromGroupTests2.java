package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class DeleteContactFromGroupTests2 extends TestBase {

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
      app.contact().create(new ContactData().withFirstname("TestName"), true);
    }
  }

  @Test
  public void testDeleteContactFromGroup() throws Exception{
    Groups groupsList = new Groups();
    GroupData modifyGroup = app.db().groups().iterator().next();

    // создать изменяемый контакт
    ContactData modifyContact = new ContactData();

    // получить список всех контактов
    Contacts getUsersListBefore = app.db().contacts();
    int numberOfUsers = 0;

    for (ContactData contact : getUsersListBefore) {
      if (contact.getGroups().size() != 0) {
        groupsList = contact.getGroups();
        for (GroupData group : groupsList) {
          if (group.getId() == modifyGroup.getId()) {
            modifyContact = contact;
          }
          break;
        }
        break;
      }
      numberOfUsers += 1;
    }
    if (numberOfUsers == getUsersListBefore.size()) {
      for (ContactData contact_second_try : getUsersListBefore) {
        if (contact_second_try.getGroups().size() == 0) {
          modifyContact = contact_second_try;
          app.contact().contactAddToGroup(modifyContact, modifyGroup);
          break;
        }
      }
    }
    app.goTo().homePage();
    app.contact().filterByGroup(modifyGroup);
    app.contact().contactDelToGroup(modifyContact);

    Groups groupBefore = modifyContact.ActionsWithGroup(modifyGroup, false).getGroups();

    // После добавления пользователя в группу получаем обновленный список и проверяем, действительно ли пользователь был удален из группы
    Contacts getUsersListAfter = app.db().contacts();
    int givenId = modifyContact.getId();
    Groups newGroupsList = getUsersListAfter.stream().filter(c -> c.getId() == givenId).findFirst().get().getGroups();
    assertThat(groupBefore, equalTo(newGroupsList));
  }
}
