package edu.pdx.cs410J.shikha.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.shikha.client.PhoneBill;
import edu.pdx.cs410J.shikha.client.PhoneCall;
import edu.pdx.cs410J.shikha.client.PhoneBillService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The server-side implementation of the Phone Bill service
 */
public class PhoneBillServiceImpl extends RemoteServiceServlet implements PhoneBillService
{
    private Map<String, PhoneBill> bill = new HashMap<>();

  /**
   * Returns the a dummy Phone Bill
   *
   * @param call
   * @param Customer_Name
   */
  @Override
  public PhoneBill addNewPhoneCall(PhoneCall call, String Customer_Name) {
      PhoneBill new_bill = new PhoneBill(Customer_Name);

      if(bill.size() == 0) {
          new_bill.addPhoneCall(call);
          bill.put(Customer_Name,new_bill);
      } else {
          bill.get(Customer_Name).addPhoneCall(call);
      }

      return bill.get(Customer_Name);
  }

  @Override
  public void throwUndeclaredException() {
    throw new IllegalStateException("Expected undeclared exception");
  }

  @Override
  public void throwDeclaredException() throws IllegalStateException {
    throw new IllegalStateException("Expected declared exception");
  }

    @Override
    public PhoneBill searchPhoneCall(String Customer_Name, String Start_Date, String End_date)  {
        if(bill.get(Customer_Name) == null){
            return null;
        }
        else {
            PhoneBill             search_bill = new PhoneBill(Customer_Name);
            PhoneBill              bill1      = bill.get(Customer_Name);
            Collection           calls      = bill1.getPhoneCalls();
            SortedSet<PhoneCall> call_list    =new TreeSet<>();
            call_list.addAll(calls);

            //convert the given parameters into proper date format
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

            for (Object call : call_list) {

                try {
                    if(sdf.parse(Start_Date).compareTo(((PhoneCall) call).getStartTime()) <= 0) {
                        try {
                            if(sdf.parse(End_date).compareTo(((PhoneCall) call).getEndTime()) >= 0) {
                                search_bill.addPhoneCall((PhoneCall) call);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            return search_bill;
        }
    }

    /**
   * Log unhandled exceptions to standard error
   *
   * @param unhandled
   *        The exception that wasn't handled
   */
  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }

}
