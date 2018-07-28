package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration test that tests the REST calls made by {@link PhoneBillRestClient}
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhoneBillRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");
  private String customerName;

  private PhoneBillRestClient newPhoneBillRestClient() {
    int port = Integer.parseInt(PORT);
    return new PhoneBillRestClient(HOSTNAME, port);
  }

  @Test
  public void test0RemoveAllDictionaryEntries() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    client.removeAllDictionaryEntries();
  }

  @Test(expected = NoSuchPhoneBillException.class)
  public void test1EmptyServerThrowsNoPhoneBill() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    client.getPhoneBill(customerName);
  }

  @Ignore
  @Test
  public void test2AddOnePhoneCall() throws IOException {
    PhoneBillRestClient client         = newPhoneBillRestClient();
    String              Caller_Number = "123-456-7890";
    String      name = "shikha shah";
    String     Callee_Number="123-545-6743";
    Validation val            = new val(name,Caller_Number,Callee_Number, "11/11/2012", "12:34", "11/14/2012", "11:11" , "PM", "AM");
    PhoneCall  call           = new call(val);
    String     testWord       = "TEST WORD";
    String     testDefinition = "TEST DEFINITION";
    client.addPhoneCall(testWord, call);

    String definition = client.getDefinition(testWord);
    assertThat(definition, equalTo(testDefinition));
  }

  @Test
  public void test4MissingRequiredParameterReturnsPreconditionFailed() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    HttpRequestHelper.Response response = client.postToMyURL();
    assertThat(response.getContent(), containsString(Messages.missingRequiredParameter("word")));
    assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
  }

}
