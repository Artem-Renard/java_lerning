package ru.stqa.pft.addressbook.model;

public class ContactData {
  private final String firstname;
  private final String lastname;
  private final String homePhone;
  private final String email;

  public ContactData(String firstname, String lastname, String homePhone, String email) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.homePhone = homePhone;
    this.email = email;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getHomePhone() {
    return homePhone;
  }

  public String getEmail() {
    return email;
  }
}
