package edu.pdx.cs410J.shikha.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.text.ParseException;

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
  void addNewPhoneCall(PhoneCall call, String Customer_Name, AsyncCallback<PhoneBill> async);

    /**
     *
     * @param Customer_Name
     * @param Start_Date
     * @param End_date
     * @param async
     */
  void searchPhoneCall(String Customer_Name,String Start_Date, String End_date, AsyncCallback<PhoneBill> async ) throws ParseException;
}
