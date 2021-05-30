package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class AddContactToGroupTests2 extends TestBase {

  private int maxId;

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
      app.contact().create(new ContactData().withFirstname("TestName"), true);
    }
  }

  @Test
  public void testAddUserToGroup() throws Exception {
    // получить группу в которую планируется добавляться контакт
    GroupData modifyGroup = app.db().groups().iterator().next();

    // создать изменяемый контакт
    ContactData modifyContact = new ContactData();

    // получить список всех контактов
    Contacts getContactsListBefore = app.db().contacts();
    int i = 0;

    // Добавьте цикл с проверкой, что хотя бы у одного пользователя нет группы, и добавьте его в выбранную группу
    for (ContactData contact : getContactsListBefore) {
      if (contact.getGroups().size() == 0) {
        modifyContact = contact;
        app.contact().contactAddToGroup(modifyContact, modifyGroup);
        break;
      }
      i += 1;

      // Если пользователь без группы не найден создать нового и добавить его в выбранную группу
      if (i == getContactsListBefore.size()) {
        app.goTo().homePage();
        app.contact().create(new ContactData().withFirstname("TestName"), true);
        app.goTo().homePage();
        Contacts getUsersAfterCreation = app.db().contacts();
        for (ContactData eachContact : getUsersAfterCreation) {
          if (eachContact.getId() > maxId) {
            modifyContact = eachContact;
            app.contact().contactAddToGroup(modifyContact, modifyGroup);
          }
        }
      }
    }
    Groups groupBefore = modifyContact.ActionsWithGroup(modifyGroup, true).getGroups();

    // После добавления пользователя в группу получить обновленный список и проверить, действительно ли пользователь был добавлен в группу
    Contacts getContactsListAfter = app.db().contacts();
    int givenId = modifyContact.getId();
    Groups newGroupsList = getContactsListAfter.stream().filter(c -> c.getId() == givenId).findFirst().get().getGroups();
    assertThat(groupBefore, equalTo(newGroupsList));
  }
}
