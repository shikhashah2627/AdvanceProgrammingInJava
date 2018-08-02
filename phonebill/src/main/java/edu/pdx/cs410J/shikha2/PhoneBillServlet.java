package edu.pdx.cs410J.shikha2;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class PhoneBillServlet extends HttpServlet
{
    static final         String                    CUSTOMER_PARAMETER        = "customer";
    private static final String                    CALLER_NUMBER             = "callerNumber";
    private static final String                    CALLEE_PARAMETER          = "calleeNumber";
    private static final String                    START_DATE_TIME_PARAMETER = "startTime";
    private static final String                    END_DATE_TIME_PARAMETER   = "endTime";
    private final        Map<String, List<String>> call_list                 = new HashMap<>();

    private Map<String, PhoneBill> bills = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * customer specified in the "customer" HTTP parameter to the HTTP response.  If the
     * "customer" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType( "text/plain" );

        String customer       = getParameter(CUSTOMER_PARAMETER, request);
        String stat_Date_Time = getParameter(START_DATE_TIME_PARAMETER, request);
        String end_Date_Time  = getParameter(END_DATE_TIME_PARAMETER, request);

        if (customer != null && stat_Date_Time == null && end_Date_Time == null) {
            writePrettyPhoneBill(customer, response);
        } else if (stat_Date_Time == null && end_Date_Time == null) {
            writePrettyPhoneBill(customer, response);
        } else {
            try {
                getPhoneBillFromSearch(customer, stat_Date_Time, end_Date_Time, response);
            } catch (ParseException e) {
                PrintWriter writer = response.getWriter();
                writer.println("Parameters are not entered properly.");
                e.printStackTrace();
            }
        }
    }

    /**
     * @param customer       - customer name
     * @param stat_date_time - starting date of required bill.
     * @param end_date_time  - end date of required bill.
     * @param response
     * @throws IOException
     * @throws ParseException
     */
    private void getPhoneBillFromSearch(String customer, String stat_date_time, String end_date_time, HttpServletResponse response) throws IOException, ParseException {
        PhoneBill   bill   = getPhoneBill(customer);
        PrintWriter writer = response.getWriter();
        if (bill == null) {
            writer.println("No bill for such customer or there is an empty bill.");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {

            PrettyPrinter    pretty = new PrettyPrinter(writer);
            SimpleDateFormat sdf    = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            sdf.setLenient(false);
            stat_date_time = sdf.format(sdf.parse(stat_date_time)).toLowerCase();
            end_date_time = sdf.format(sdf.parse(end_date_time)).toLowerCase();
            if (sdf.parse(stat_date_time).compareTo(sdf.parse(end_date_time)) > 0) {
                writer.println("Your Start Date of criteria is bigger than End Date");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                // pretty print returns calls in sorted manner.
                Messages.printSearchedCallValues(writer, bill, stat_date_time, end_date_time);
                response.setStatus(HttpServletResponse.SC_OK);
            }

        }
    }

    /**
     * <code>writePrettyPhoneBill</code> writes the whole bill.
     *
     * @param customer - customer name
     * @param response
     * @throws IOException
     */
    private void writePrettyPhoneBill(String customer, HttpServletResponse response) throws IOException {
        PhoneBill   bill   = getPhoneBill(customer);
        PrintWriter writer = response.getWriter();
        if (bill == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            Messages.printAllCallValues(writer,bill);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Handles an HTTP POST request by storing the call entry for the
     * "customer" and "call" request parameters.  It writes the call
     * entry to the HTTP response.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType( "text/plain" );

        String customer       = getParameter(CUSTOMER_PARAMETER, request);
        String caller         = getParameter(CALLER_NUMBER, request);
        String callee         = getParameter(CALLEE_PARAMETER, request);
        String stat_Date_Time = getParameter(START_DATE_TIME_PARAMETER, request);
        String end_Date_Time  = getParameter(END_DATE_TIME_PARAMETER, request);

        if (customer == null) {
            missingRequiredParameter(response, "customer name");
        } else if (caller == null) {
            missingRequiredParameter(response, "caller number");
        } else if (callee == null) {
            missingRequiredParameter(response, "callee number");
        } else if (stat_Date_Time == null) {
            missingRequiredParameter(response, "stat_Date_Time");
        } else if (end_Date_Time == null) {
            missingRequiredParameter(response, "end_Date_Time");
        }

        String startTime1[] = stat_Date_Time.split(" ");
        String endTime1[]   = end_Date_Time.split(" ");

        Validation val  = new Validation(customer, caller, callee, startTime1[0], startTime1[1], endTime1[0], endTime1[1], startTime1[2], endTime1[2], response);
        PhoneCall  call = new PhoneCall(val);

        PhoneBill bill = getPhoneBill(customer);
        if (bill == null) {
            bill = new PhoneBill(customer);
            addPhoneBill(bill);
        }

        bill.addPhoneCall(call);

        PrintWriter pw = response.getWriter();
        //prints the new call information.
        pw.println(Messages.new_call_information(call.toString()));

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);

    }


    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        this.call_list.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    public void ErroneousMessage(HttpServletResponse response, String element, String errorMessage) throws UnsupportedOperationException, IOException {
        String message = Messages.inAppropriateRequiredParameter(element, errorMessage);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }

    }

    @VisibleForTesting
    String getDefinition(String customer) {

        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public PhoneBill getPhoneBill(String customer) {
        return this.bills.get(customer);
    }

    public void addPhoneBill(PhoneBill bill) {
        this.bills.put(bill.getCustomer(), bill);
    }
}
