package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends BaseHelper {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitAddNewContact() {
    wd.findElement(By.name("submit")).click();
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type (By.name("firstname"), contactData.getFirstname());
    type (By.name("lastname"), contactData.getLastname());
    type (By.name("home"), contactData.getHomePhone());
    type (By.name("email"), contactData.getEmail());

    if (creation) {
      new Select(wd.findElement(By.name("maintable"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("maintable")));
    }
   }

  public void initContactCreation() {
    click (By.linkText("add new"));
  }

  public void goHomePage() {
    click (By.linkText("home"));
  }

  public void editContact() {
    click (By.cssSelector("img[alt=\"Edit\"]"));
  }

  public void initContactModification() {
    click (By.xpath("(//input[@name='update'])[2]"));
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void deleteSelectContact() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void acceptDeleteSelectContact() {
    wd.switchTo().alert().accept();
    wd.findElement(By.cssSelector("div.msgbox"));
  }

  public void createContact(ContactData contact, boolean creation) {
    initContactCreation();
    fillContactForm(contact,creation);
    submitAddNewContact();
    goHomePage();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> getContactList() {
    List <ContactData> contacts = new ArrayList<ContactData>();
    List <WebElement> elements = wd.findElements(By.name("selected[]"));
    for (WebElement element : elements) {
      String firstname = element.getText();
      ContactData contact = new ContactData(firstname, null, null, null, null);
      contacts.add(contact);
    }
    return contacts;
  }
}
