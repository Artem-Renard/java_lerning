package ru.stqa.pft.addressbook.tests;


import org.junit.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactPhoneTests extends TestBase {

    @Test () {
        public void testContactPhone() {
            app.goTo().homePage();
            ContactData contact = app.contact().all().iterator().next();
            ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        }
    }
}
