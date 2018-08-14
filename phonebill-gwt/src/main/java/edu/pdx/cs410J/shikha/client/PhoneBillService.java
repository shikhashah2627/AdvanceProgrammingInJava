package edu.pdx.cs410J.shikha.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.AbstractPhoneBill;

/**
 * A GWT remote service that returns a dummy Phone Bill
 */
@RemoteServiceRelativePath("phoneBill")

public interface PhoneBillService extends RemoteService{

  /**
   * Returns the a dummy Phone Bill
   */
  AbstractPhoneBill addNewPhoneCall(PhoneCall call, String Customer_Name);

  /**
   * Always throws an undeclared exception so that we can see GWT handles it.
   */
  void throwUndeclaredException();

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  void throwDeclaredException() throws IllegalStateException;

  AbstractPhoneBill searchPhoneCall(String Customer_Name, String Start_Date, String End_date);


}

