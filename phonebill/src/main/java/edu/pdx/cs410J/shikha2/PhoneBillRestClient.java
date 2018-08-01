package edu.pdx.cs410J.shikha2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class PhoneBillRestClient extends HttpRequestHelper {
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";

    private final String url;

    private Map<String, PhoneBill> bills = new HashMap<>();


    /**
     * Creates a client to the Phone Bil REST service running on the given host and port
     *
     * @param hostName The name of the host
     * @param port     The port
     */
    public PhoneBillRestClient(String hostName, int port) {
        this.url = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET);
    }

    /*
     * Returns all phone call entries from the server

    public Map<String, String> getPhoneBill(String customerName) throws IOException {
      Response response = get(this.url);
      throw new NoSuchPhoneBillException(customerName);
    }*/

    /**
     * Returns the call from the specific search start and End time
     */
    public String getPhoneBillFromSearch(String Customer_Name, String Start_Date_Time, String End_Date_Time) throws IOException {
        Response    response = get(this.url, "Customer_Name", "Start_Date", "End_Date_Time", Customer_Name, Start_Date_Time, End_Date_Time);
        PhoneBill   bill     = getPhoneBill(Customer_Name);
        PrintWriter writer;
        Messages.printSearchedCallValues(writer, bill, Start_Date_Time, End_Date_Time);
        throwExceptionIfNotOkayHttpStatus(response);
        String content = response.getContent();
        return null;
        //return Messages.parseDictionaryEntry(content).getValue();
    }

    public void addCall(String Customer, PhoneCall call) throws IOException {
        Response response = postToMyURL("Customer_Name", Customer, "Start_Date", Start_Date_Time, "End_Date_Time", End_Date_Time);
        throwExceptionIfNotOkayHttpStatus(response);
    }

    @VisibleForTesting
    Response postToMyURL(String... dictionaryEntries) throws IOException {
        return post(this.url, dictionaryEntries);
    }

    public void removeAllDictionaryEntries() throws IOException {
        Response response = delete(this.url);
        throwExceptionIfNotOkayHttpStatus(response);
    }

    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getCode();
        if (code == HTTP_NOT_FOUND) {
            String customer = response.getContent();
            throw new NoSuchPhoneBillException(customer);
        } else if (code != HTTP_OK) {
            //throw new PhoneBillRestException(code);
        }
        return response;
    }

    public void addPhoneCall(String customerName, PhoneCall call) throws IOException {
        String[] postParameters = {
                "Customer_Name", customerName,
                "Caller_number", call.getCaller(),
                "Callee_number", call.getCallee(),
                "Start_Date_Time", call.getStartTimeString(),
                "End_Date_Time", call.getEndTimeString()
        };

        Response response = postToMyURL(postParameters);
        throwExceptionIfNotOkayHttpStatus(response);
    }

    public void getAllPhoneCalls(String customer) throws IOException {
        Response  response = get(this.url, "Customer_Name", customer);
        PhoneBill bill     = new PhoneBill(response.getContent());
        //PrintWriter pw = response.ge
        //Messages.printAllCallValues(PrintWriter pw,bill);
        bill.getPhoneCalls();
        if (bill == null) {
            //response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {

        }
    }


    public void getPrettyPhoneBill(String customer) throws IOException {
        Response  response = get(this.url, "Customer_Name", customer);
        PhoneBill bill     = new PhoneBill(response.getContent());
        bill.getPhoneCalls();
        if (bill == null) {
            //response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            PrintWriter   writer = response.getWriter();
            PrettyPrinter pretty = new PrettyPrinter(writer);
            // pretty print returns calls in sorted manner.
            PhoneBill bill1 = (PhoneBill) pretty.sorted(bill);
            writer.println(bill.getCustomer());
            bill1.getPhoneCalls().forEach((call) -> writer.println(call.toString()));
            response.setStatus(HttpServletResponse.SC_OK);
        }
        throwExceptionIfNotOkayHttpStatus(response);
    }
    /*
    private class PhoneBillRestException extends RuntimeException {
      public PhoneBillRestException(int httpStatusCode) {
        super("Got an HTTP Status Code of " + httpStatusCode);
      }
    }

    public void removeAllPhoneBills() throws IOException {
        Response response = delete(this.url);
        throwExceptionIfNotOkayHttpStatus(response);
    }*/

    public PhoneBill getPhoneBill(String customer) {
        return this.bills.get(customer);
    }

    public void addPhoneBill(PhoneBill bill) {
        this.bills.put(bill.getCustomer(), bill);
    }
}
