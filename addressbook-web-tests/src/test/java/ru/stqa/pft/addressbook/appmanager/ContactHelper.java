package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends BaseHelper {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitAddNewContact() {
    wd.findElement(By.name("submit")).click();
  }

  public void fillAddNewContactForm(ContactData contactData) {
    type (By.name("firstname"), contactData.getFirstname());
    type (By.name("lastname"), contactData.getLastname());
    type (By.name("home"), contactData.getHomephone());
    type (By.name("email"), contactData.getEmail());
  }

  public void goToAddNewContact() {
    click (By.linkText("add new"));
  }

  public void returnToHomePage() {
    click (By.linkText("home"));
  }

  public void editContact() {
    click (By.cssSelector("img[alt=\"Edit\"]"));
  }

  public void updateContactForm() {
    click (By.xpath("(//input[@name='update'])[2]"));
  }
}
