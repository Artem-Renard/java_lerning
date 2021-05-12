package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Сontacts;

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
    type (By.name("mobile"), contactData.getMobilePhone());
    type (By.name("work"), contactData.getWorkPhone());
    type (By.name("email"), contactData.getEmail());
    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
   }

  public void initContactCreation() {
    click (By.linkText("add new"));
  }

  public void goHomePage() {
    click (By.linkText("home"));
  }

  public void editContactById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']",id))).click();
    //wd.get("http://localhost/addressbook/view.php?id=" + id);
  }

  public void editContactByPage() {
    wd.findElement(By.name("modifiy")).click();
  }

  public void initContactModification() {
    click (By.xpath("//div[@id='content']/form/input[22]"));
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();;
  }

  public void deleteSelectContact() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void acceptDeleteSelectContact() {
    wd.switchTo().alert().accept();
    wd.findElement(By.cssSelector("div.msgbox"));
  }

  public void create(ContactData contact, boolean creation) {
    initContactCreation();
    fillContactForm(contact,creation);
    submitAddNewContact();
    contactCache = null;
    goHomePage();
  }

  public void modify(ContactData contact) {
    editContactById(contact.getId());
    //editContactByPage();
    fillContactForm(contact, false);
    initContactModification();
    contactCache = null;
    goHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectContact();
    acceptDeleteSelectContact();
    contactCache = null;
    goHomePage();
  }

  private Сontacts contactCache = null;

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int count(){
    return wd.findElements(By.name("selected[]")).size();
  }

  public Сontacts all() {
    if (contactCache != null){
      return new Сontacts(contactCache);
    }
    contactCache = new Сontacts();
    List <WebElement> rows = wd.findElements(By.name("entry"));
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(row.findElement(By.xpath(".//td/input")).getAttribute("value"));
      String firstname = row.findElement(By.xpath(".//td[3]")).getText();
      String lastname = row.findElement(By.xpath(".//td[2]")).getText();
      contactCache.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
    }
    return contactCache;
  }

  public ContactData infoFromEditForm (ContactData contact) {
    editContactById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
  }



}
