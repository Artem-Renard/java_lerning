package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactHelper extends BaseHelper {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type (By.name("firstname"), contactData.getFirstname());
    type (By.name("lastname"), contactData.getLastname());
    type (By.name("address"), contactData.getAddress());
    type (By.name("home"), contactData.getHomePhone());
    type (By.name("mobile"), contactData.getMobilePhone());
    type (By.name("work"), contactData.getWorkPhone());
    type (By.name("email"), contactData.getEmail());
    type (By.name("email2"), contactData.getEmail2());
    type (By.name("email3"), contactData.getEmail3());
    attach (By.name("photo"), contactData.getPhoto());

    if (creation) {
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
   }

  public void submitAddNewContact() {
    wd.findElement(By.name("submit")).click();
  }

  public void initContactCreation() {
    click (By.linkText("add new"));
  }

  public void goHomePage() {
    click (By.linkText("home"));
  }

  public void editContactById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']",id))).click();
  }

  public void initContactModification() {
    click (By.xpath("//div[@id='content']/form/input[22]"));
  }

  public void selectAllGroupForContacts() {
    new Select(wd.findElement(By.cssSelector("select[name=\"group\"]"))).selectByVisibleText("[all]");
  }

  /*
  public void selectGroupForAddingToContact(int groupsId) {
    new Select(wd.findElement(By.cssSelector("select[name=\"to_group\"]"))).selectByValue("" + groupsId +"");
  }
   */

  /*
  public void selectGroupWithContacts(int groupsId) {
    new Select(wd.findElement(By.cssSelector("select[name=\"group\"]"))).selectByValue("" + groupsId +"");
  }
   */

  public void selectGroupForAddingToContact(GroupData group) {
    new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(group.getName());
  }

  public void selectGroupWithContacts(String name) {
    new Select(wd.findElement(By.name("group"))).selectByVisibleText(name);
  }

  public void addGroupToContact() {
    wd.findElement(By.name("add")).click();
  }

  public void deleteContactFromGroup() {
    wd.findElement(By.name("remove")).click();
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void deleteSelectContact() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void acceptDeleteSelectContact() {
    wd.switchTo().alert().accept();
    wd.findElement(By.cssSelector("div.msgbox"));
  }

  public void filterByGroup(GroupData groupsList) {
    new Select(wd.findElement(By.name("group"))).selectByVisibleText(groupsList.getName());
  }

  public int count(){
    return wd.findElements(By.name("selected[]")).size();
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

  public void contactAddToGroup(ContactData contact, GroupData group) {
    /*selectAllGroupForContacts(); // выбор всех контактов (all) на странице контактов*/
    selectContactById(contact.getId()); // выбрали (отметили) контакт в форме
    new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(group.getName()); // в выпадающем списке выбрали имя
    addGroupToContact(); // активировали кнопку добавить группу
  }

  public void contactDelToGroup(ContactData contact) {
    /*selectGroupWithContacts(name); // в выпадающем списке выбрали имя группы в которую входит контакт*/
    selectContactById(contact.getId()); // выбрали (отметили) контакт в форме
    deleteContactFromGroup(); // активировали кнопку удалить контакт из выбранной группы с именем name
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null){
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List <WebElement> rows = wd.findElements(By.name("entry"));
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String[] email = cells.get(4).getText().split("\n");
      String allPhones = cells.get(5).getText();
      String[] phones = cells.get(5).getText().split("\n");
      contactCache.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname)
              .withAddress(address)
              .withAllEmails(allEmails)
              .withAllPhones(allPhones));
    }
    return contactCache;
  }

  public ContactData infoFromEditForm (ContactData contact) {
    editContactById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname)
            .withAddress(address)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
            .withEmail(email).withEmail2(email2).withEmail3(email3);
  }

  public void editContactByPage() {
    wd.findElement(By.name("modifiy")).click();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public void addToGroup (ContactData contact) {
    selectContactById(contact.getId());
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

}
