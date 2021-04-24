package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper extends BaseHelper {

  public SessionHelper(WebDriver wd) {
    super(wd);
  }

  public void login(String username, String password) {
    wd.get("http://localhost/addressbook/");
    type(By.name("user"), username);
    type(By.name("pass"), password);
    click(By.xpath("//input[@value='Login']"));
  }

  public void logout() {
    click(By.linkText("Logout"));
  }

}
