package ru.stqa.pft.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.model.UserData;

public class AdminHelper {

  private final ApplicationManager app;
  private WebDriver wd;

  public AdminHelper(ApplicationManager app) {
    this.app = app;
    wd = app.getDriver();
  }

  public void loginThroughUi(String name, String password) {
    wd.get(app.getProperty("web.baseURL") + "/login_page.php");
    wd.findElement(By.id("username")).click();
    wd.findElement(By.id("username")).clear();
    wd.findElement(By.id("username")).sendKeys(name);
    wd.findElement(By.cssSelector("input[type='submit']")).click();
    wd.findElement(By.id("password")).click();
    wd.findElement(By.id("password")).clear();
    wd.findElement(By.id("password")).sendKeys(password);
    wd.findElement(By.cssSelector("input[type='submit']")).click();
  }

  public void goToManagerUsers() throws InterruptedException {
    wd.get(app.getProperty("web.baseURL") + "/manage_user_page.php");
  }

  public void selectManager(UserData user) {
    wd.findElement(By.xpath("//a[@href='manage_user_edit_page.php?user_id=" + user.getId() + "']")).click();
  }

  public void managerResetPassword(UserData user) {
    selectManager(user);
    wd.findElement(By.cssSelector("input[value='Сбросить пароль']")).click();
  }

  public void changePassword(String confirmationLink, String name, String password) {
    wd.get(confirmationLink);
    wd.findElement(By.name("realname")).sendKeys(name);
    wd.findElement(By.name("password")).sendKeys(password);
    wd.findElement(By.name("password_confirm")).sendKeys(password);
    wd.findElement(By.xpath("//button[@type='submit']")).click();
  }
}
