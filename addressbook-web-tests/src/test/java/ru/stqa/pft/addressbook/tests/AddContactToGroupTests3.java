package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class AddContactToGroupTests3 extends TestBase {

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
    // получить список всех контактов с группами
    Groups contactsListWithGroup = app.db().contactCountGroups();





    if (app.db().contactCountGroups().size() == 0) {

    }
  }
}
