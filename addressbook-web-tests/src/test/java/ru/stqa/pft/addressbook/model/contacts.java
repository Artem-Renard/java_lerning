package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;

public class contacts extends ForwardingSet<ContactData> {

  private Set<ContactData> delegate;

  public contacts(contacts contacts) {
    this.delegate = new HashSet<ContactData>(contacts.delegate);
  }

  public contacts() {
    this.delegate = new HashSet<ContactData>();
  }

  @Override
  protected Set<ContactData> delegate() {
    return delegate;
  }

  public contacts withAdded (ContactData contact) {
    contacts contacts = new contacts(this);
    contacts.add(contact);
    return contacts;
  }

  public contacts without (ContactData contact) {
    contacts contacts = new contacts(this);
    contacts.remove(contact);
    return contacts;
  }
}
