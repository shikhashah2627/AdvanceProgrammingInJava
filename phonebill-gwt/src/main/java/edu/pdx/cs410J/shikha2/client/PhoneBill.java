package edu.pdx.cs410J.shikha2.client;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.lang.Override;
import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
  private Collection<PhoneCall> calls = new ArrayList<>();
  String Customer_Name;

  /**
   * In order for GWT to serialize this class (so that it can be sent between
   * the client and the server), it must have a zero-argument constructor.
   */
  public PhoneBill() {

  }

  public PhoneBill(String Customer_Name) {
    this.Customer_Name = Customer_Name;
  }

  @Override
  public String getCustomer() {
    return Customer_Name;
  }

  @Override
  public void addPhoneCall(PhoneCall call) {
    this.calls.add(call);
  }

  @Override
  public Collection<PhoneCall> getPhoneCalls() {
    return this.calls;
  }
}
