package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Сontacts;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactCreationTests extends TestBase  {

  @DataProvider
  public Iterator<Object[]> validContacts() {
    List<Object[]> list = new ArrayList<Object[]>();
    File photo = new File("src/test/resources/watermelon.png");
    list.add(new Object[] {new ContactData().withFirstname("TestName1").withLastname("TestLastName1").withGroup("[none]").withPhoto(photo)});
    list.add(new Object[] {new ContactData().withFirstname("TestName2").withLastname("TestLastName2").withGroup("[none]").withPhoto(photo)});
    list.add(new Object[] {new ContactData().withFirstname("TestName3").withLastname("TestLastName3").withGroup("[none]").withPhoto(photo)});
    return list.iterator();
  }

  @Test(dataProvider = "validContacts")
  public void testContactCreation (ContactData contact){
    app.goTo().homePage();
    Сontacts before = app.contact().all();
    app.contact().create(contact, true);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Сontacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

  @Test
  public void testBadContactCreation() {
    app.goTo().homePage();
    Сontacts before = app.contact().all();
    ContactData contact = new ContactData().withFirstname("TestName1'").withLastname("TestLastName1").withGroup("[none]");
    app.contact().create(contact, true);
    assertThat(app.contact().count(), equalTo(before.size()));
    Сontacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size()));
  }

  /*
  @Test
  public void testCurrentDir(){
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath());
    File photo = new File("src/test/resources/watermelon.png");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }
   */
}