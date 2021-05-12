package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;

public class Сontacts extends ForwardingSet<ContactData> {

  private Set<ContactData> delegate;

  public Сontacts(Сontacts contacts) {
    this.delegate = new HashSet<ContactData>(contacts.delegate);
  }

  public Сontacts() {
    this.delegate = new HashSet<ContactData>();
  }

  @Override
  protected Set<ContactData> delegate() {
    return delegate;
  }

  public Сontacts withAdded (ContactData contact) {
    Сontacts contacts = new Сontacts(this);
    contacts.add(contact);
    return contacts;
  }

  public Сontacts without (ContactData contact) {
    Сontacts contacts = new Сontacts(this);
    contacts.remove(contact);
    return contacts;
  }
}
