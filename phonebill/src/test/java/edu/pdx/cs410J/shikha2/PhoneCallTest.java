package edu.pdx.cs410J.shikha2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneCall} class.
 */
public class PhoneCallTest {

  @Test (expected = UnsupportedOperationException.class)
  public void getStartTimeStringNeedsToBeImplemented() {
    PhoneCall call = new PhoneCall();
    call.getStartTimeString();
  }

  @Test
  public void initiallyAllPhoneCallsHaveTheSameCallee() {
    PhoneCall call = new PhoneCall();
    assertThat(call.getCallee(), containsString("not implemented"));
  }

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    PhoneCall call = new PhoneCall();
    assertThat(call.getStartTime(), is(nullValue()));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void checkSyntaxForPhoneNumber() {
      String phone_number = "123-345-789";
      Project1.number_format_check(phone_number);

    }

  @Test(expected = UnsupportedOperationException.class)
  public void checkSyntaxForCustomerName() {
      String name = "shikha $hah";
      Project1.name_check(name);
  }

  }

