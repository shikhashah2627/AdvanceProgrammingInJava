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
    String phone_number = "123-345-7893";
    Project1 call = new Project1("??",phone_number,"??");

    }

  @Test(expected = UnsupportedOperationException.class)
  public void checkSyntaxForCustomerName() {
    String name = "shikha shah";
    String phone_number = "123-345-7893";
    String Time="1/2/3";
    Project1 call = new Project1(name,phone_number,Time);
  }
  }
