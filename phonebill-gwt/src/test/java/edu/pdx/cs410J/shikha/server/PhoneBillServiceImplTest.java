package edu.pdx.cs410J.shikha.server;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.shikha.client.PhoneCall;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PhoneBillServiceImplTest {

  private static final  PhoneCall call = new PhoneCall();
  String Customer_Name = "Customer";

  @Test
  public void serviceReturnsExpectedPhoneBill() {
    PhoneBillServiceImpl service = new PhoneBillServiceImpl();
    AbstractPhoneBill    bill    = service.addNewPhoneCall(call,Customer_Name);
    assertThat(bill.getPhoneCalls().size(), equalTo(1));
  }
}
