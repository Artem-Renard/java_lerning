package ru.stqa.pft.model;
import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class Users extends ForwardingSet<UserData> {

  private Set<UserData> delegate;

  public Users(Users users) {
    this.delegate = new HashSet<UserData>(users.delegate);
  }

  public Users(Collection<UserData> users) {
    this.delegate = new HashSet<UserData>(users);
  }

  public Users() {
    this.delegate = new HashSet<UserData>();
  }

  @Override
  protected Set<UserData> delegate() {
    return delegate;
  }
}
