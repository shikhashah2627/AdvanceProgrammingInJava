package edu.pdx.cs410J.shikha2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractPhoneBill;

/**
 * The client-side interface to the phone bill service
 */
public interface PhoneBillServiceAsync {


    /**
     * Returns the a dummy Phone Bill
     */
    void addNewPhoneCall(PhoneCall call, String Customer_Name, AsyncCallback<AbstractPhoneBill> async);

    /**
     * Always throws an undeclared exception so that we can see GWT handles it.
     */
    void throwUndeclaredException(AsyncCallback<Void> async);

    /**
     * Always throws a declared exception so that we can see GWT handles it.
     */
    void throwDeclaredException(AsyncCallback<Void> async);

    void searchPhoneCall(String Customer_Name, String Start_Date, String End_date, AsyncCallback<AbstractPhoneBill> async);
}

