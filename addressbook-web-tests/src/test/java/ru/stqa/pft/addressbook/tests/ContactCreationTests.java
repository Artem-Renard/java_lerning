package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.contacts;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactCreationTests  extends TestBase  {

  @Test
  public void testContactCreation() {
    app.goTo().homePage();
    contacts before = app.contact().all();
    ContactData contact = new ContactData().withFirstname("TestName1").withLastname("TestLastName1").withGroup("[none]");
    app.contact().create(contact, true);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

  @Test
  public void testBadContactCreation() {
    app.goTo().homePage();
    contacts before = app.contact().all();
    ContactData contact = new ContactData().withFirstname("TestName1'").withLastname("TestLastName1").withGroup("[none]");
    app.contact().create(contact, true);
    assertThat(app.contact().count(), equalTo(before.size()));
    contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size()));
  }
}