package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions () {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withFirstname("TestName").withGroup("[none]").withAddress("(Moscow), Zaitseva-5, 123456"), true);
    }
  }

    @Test
    public void testContactAddress() {
      app.goTo().homePage();
      ContactData contact = app.contact().all().iterator().next();
      ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

      assertThat(contact.getAddress(), equalTo(mergeAddress(contactInfoFromEditForm)));
    }

    private String mergeAddress(ContactData contact) {
      return Arrays.asList(contact.getAddress())
              .stream().filter((s) -> ! s.equals(""))
              /*.map(ru.stqa.pft.addressbook.tests.ContactAddressTests::cleaned)*/
              .collect(Collectors.joining("\n"));
    }

    /*public static String cleaned(String address) {
      return address.replaceAll("\\s", "").replaceAll("[-()]","");
     */
    }
