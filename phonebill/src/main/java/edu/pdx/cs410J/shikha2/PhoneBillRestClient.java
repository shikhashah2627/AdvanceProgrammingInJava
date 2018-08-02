package edu.pdx.cs410J.shikha2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
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
     * <code>getPhoneBillFromSearch</code> helps to find the bill from the given criteria.
     *
     * @param Customer_Name customer name is passed.
     * @param Start_Date_Time start date of the search
     * @param End_Date_Time end date of the search
     * @return bill from start date to end date.
     * @throws IOException
     */
    public String getPhoneBillFromSearch(String Customer_Name, String Start_Date_Time, String End_Date_Time) throws IOException {
        Response response = get(this.url, "customer", Customer_Name, "startTime", Start_Date_Time, "endTime", End_Date_Time);
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
     *
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
                "customer", customerName,
                "callerNumber", call.getCaller(),
                "calleeNumber", call.getCallee(),
                "startTime", Start_date,
                "endTime", End_date
        };

        Response response = postToMyURL(postParameters);
        throwExceptionIfNotOkayHttpStatus(response);
    }

    /**
     * <code>getAllPhoneCalls</code> if only customer name is passed.
     *
     * @param customer when customer name is passed
     * @return bill from the server
     * @throws IOException
     */
    public String getAllPhoneCalls(String customer) throws IOException {
        Response response = get(this.url, "customer", customer);
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
