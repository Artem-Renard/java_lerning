package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Сontacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactCreationTests  extends TestBase  {

  @Test
  public void testContactCreation() {
    app.goTo().homePage();
    Сontacts before = app.contact().all();
    File photo = new File("src/test/resources/watermelon.png");
    ContactData contact = new ContactData().withFirstname("TestName1").withLastname("TestLastName1").withGroup("[none]")
            .withPhoto(photo);
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