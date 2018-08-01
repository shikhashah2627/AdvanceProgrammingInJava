package edu.pdx.cs410J.shikha2;

import org.junit.Ignore;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;

import static edu.pdx.cs410J.shikha2.PhoneBillServlet.CUSTOMER_PARAMETER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class PhoneBillServletTest {

  @Test
  public void initiallyServletContainsNoPhoneBills() throws IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

      HttpServletRequest  mockRequest     = mock(HttpServletRequest.class);
      HttpServletResponse mockResponse    = mock(HttpServletResponse.class);
      PrintWriter         mockPrintWriter = mock(PrintWriter.class);

      when(mockResponse.getWriter()).thenReturn(mockPrintWriter);

    when(mockRequest.getParameter(CUSTOMER_PARAMETER)).thenReturn("Customer");

      servlet.doGet(mockRequest, mockResponse);

      verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
  }

    @Ignore
  @Test
  public void addPhoneBill() throws IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

      String customer = "Customer";
      String caller   = "123-456-8901";
      String callee   = "234-567-1234";

      long startTime = System.currentTimeMillis();
      long endTime   = System.currentTimeMillis() + 100000L;

      HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getParameter("Customer_Name")).thenReturn(customer);
        when(mockRequest.getParameter("Caller_number")).thenReturn(caller);
        when(mockRequest.getParameter("Callee_number")).thenReturn(callee);
        when(mockRequest.getParameter("Start_Date_Time")).thenReturn(String.valueOf(startTime));
        when(mockRequest.getParameter("End_Date_Time")).thenReturn(String.valueOf(endTime));

      HttpServletResponse mockResponse    = mock(HttpServletResponse.class);
      PrintWriter         mockPrintWriter = mock(PrintWriter.class);

      when(mockResponse.getWriter()).thenReturn(mockPrintWriter);

      servlet.doPost(mockRequest, mockResponse);

      verify(mockResponse).setStatus(HttpServletResponse.SC_OK);

      PhoneBill bill = servlet.getPhoneBill(customer);
      //assertThat(bill, not(nullValue()));
      assertThat(bill.getCustomer(), equalTo(customer));

      Collection<PhoneCall> calls = bill.getPhoneCalls();
      assertThat(calls.size(), equalTo(1));

      PhoneCall call = calls.iterator().next();
      assertThat(call.getCaller(), equalTo(caller));
      assertThat(call.getCallee(), equalTo(callee));
      assertThat(call.getStartTime(), equalTo(new Date(startTime)));
      assertThat(call.getEndTime(), equalTo(new Date(endTime)));
  }


  @Test
  public void getReturnsPrettyPhoneBill() throws IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String     customer  = "Customer";
    PhoneBill  bill      = new PhoneBill(customer);
    Validation val       = new Validation(customer, "123-456-7890", "122-343-4546", "11/11/2012", "12:34", "11/14/2012", "11:11", "PM", "AM");
    PhoneCall  phoneCall = new PhoneCall(val);

    bill.addPhoneCall(phoneCall);
    servlet.addPhoneBill(bill);

    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    PrintWriter mockPrintWriter = mock(PrintWriter.class);

    when(mockResponse.getWriter()).thenReturn(mockPrintWriter);

    when(mockRequest.getParameter(CUSTOMER_PARAMETER)).thenReturn("Customer");

    servlet.doGet(mockRequest, mockResponse);

    verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
    verify(mockPrintWriter).println(customer);
    verify(mockPrintWriter).println(phoneCall.toString());
  }

}
