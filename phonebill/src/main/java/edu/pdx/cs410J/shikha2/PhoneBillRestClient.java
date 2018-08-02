package edu.pdx.cs410J.shikha2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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

    /**
     * Returns the call from the specific search start and End time
     */
    public String getPhoneBillFromSearch(String Customer_Name, String Start_Date_Time, String End_Date_Time) throws IOException {
        Response    response = get(this.url, "Customer_Name", Customer_Name, "Start_Date_Time", Start_Date_Time, "End_Date_Time",  End_Date_Time);
        throwExceptionIfNotOkayHttpStatus(response);
        return response.getContent();
    }


    @VisibleForTesting
    Response postToMyURL(String... phoneCallInformation) throws IOException {
        return post(this.url, phoneCallInformation);
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
            throw new PhoneBillRestException(code);
        }
        return response;
    }

    /**
     * <code>addPhoneCall</code> adds the phone call to bill from command line arguments.
     * @param customerName
     * @param call
     * @throws IOException
     */
    public void addPhoneCall(String customerName, PhoneCall call) throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String Start_date,End_date;
        Start_date = sdf.format(call.getStartTime());
        End_date = sdf.format(call.getEndTime());
        String[] postParameters = {
                "Customer_Name", customerName,
                "Caller_number", call.getCaller(),
                "Callee_number", call.getCallee(),
                "Start_Date_Time", Start_date,
                "End_Date_Time", End_date
        };

        Response response = postToMyURL(postParameters);
        throwExceptionIfNotOkayHttpStatus(response);
    }

    public String getAllPhoneCalls(String customer) throws IOException {
        Response  response = get(this.url, "Customer_Name", customer);
        throwExceptionIfNotOkayHttpStatus(response);
        return response.getContent();
    }

    private class PhoneBillRestException extends RuntimeException {
      public PhoneBillRestException(int httpStatusCode) {
        super("Got an HTTP Status Code of " + httpStatusCode);
      }
    }

    public void removeAllPhoneBills() throws IOException {
        Response response = delete(this.url);
        throwExceptionIfNotOkayHttpStatus(response);
    }

}
