package edu.pdx.cs410J.shikha2.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.shikha2.client.PhoneBill;
import edu.pdx.cs410J.shikha2.client.PhoneBillService;
import edu.pdx.cs410J.shikha2.client.PhoneCall;

import java.text.DateFormat;
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
   *  @param call
   * @param Customer_Name
   */
  @Override
  public AbstractPhoneBill addNewPhoneCall(PhoneCall call, String Customer_Name) {
      PhoneBill new_bill = new PhoneBill(Customer_Name);
        PhoneCall call1 = new PhoneCall();
      if(bill.get(Customer_Name) == null) {
          new_bill.addPhoneCall(call);
          bill.put(Customer_Name,new_bill);
      } else {
          SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

          DateFormat dfm = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
          // below part returns date time in short format
          try {
              Date start_date = dfm.parse(sdf.format(call.getStartTime()).toLowerCase());

              String formatted_start_time = DateFormat.getDateTimeInstance(
                      DateFormat.SHORT, DateFormat.SHORT).format(start_date).toLowerCase();
              Date end_date = dfm.parse(sdf.format(call.getEndTime()).toLowerCase());
              String formatted_end_time = DateFormat.getDateTimeInstance(
                      DateFormat.SHORT, DateFormat.SHORT).format(end_date).toLowerCase();
              call1 = new PhoneCall(call.getCaller(),call.getCallee(),call.getStartTime(),call.getEndTime(),formatted_start_time,formatted_end_time);
          } catch (ParseException e) {
              e.printStackTrace();
          }
          bill.get(Customer_Name).addPhoneCall(call1);
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
    public AbstractPhoneBill searchPhoneCall(String Customer_Name, String Start_Date, String End_date)  {
        if(bill.get(Customer_Name) == null){
            return null;
        } else if (Start_Date == null && End_date == null) {
            PhoneBill             full_bill = new PhoneBill(Customer_Name);
            PhoneBill  bill1      = bill.get(Customer_Name);
            Collection           calls      = bill1.getPhoneCalls();
            SortedSet<PhoneCall> call_list    =new TreeSet<>();
            call_list.addAll(calls);
            for (Object call : call_list) {
               full_bill.addPhoneCall((PhoneCall) call);
            }
            return full_bill;
        } else {
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
