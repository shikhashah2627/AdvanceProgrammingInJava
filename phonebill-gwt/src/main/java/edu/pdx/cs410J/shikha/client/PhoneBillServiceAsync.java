package edu.pdx.cs410J.shikha.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractPhoneBill;

/**
 * The client-side interface to the phone bill service
 */
public interface PhoneBillServiceAsync {


  /**
   * Always throws an exception so that we can see how to handle uncaught
   * exceptions in GWT.
   */
  void throwUndeclaredException(AsyncCallback<Void> async);

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  void throwDeclaredException(AsyncCallback<Void> async);

  /**
   * Returns the a dummy Phone Bill
   */


  void searchPhoneCall(String Customer_Name, String Start_Date, String End_date, AsyncCallback<AbstractPhoneBill> async);

  /**
   * Returns the a dummy Phone Bill as per the criteria.
   */
  void addNewPhoneCall(PhoneCall call, String Customer_Name, AsyncCallback<AbstractPhoneBill> async);

  void printOutput(String output_value, AsyncCallback<String> async);

}
