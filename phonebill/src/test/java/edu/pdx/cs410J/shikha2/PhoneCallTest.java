package edu.pdx.cs410J.shikha2;

import org.junit.Ignore;
import org.junit.Test;

import java.text.MessageFormat;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the {@link PhoneCall} class.
 */
public class PhoneCallTest {

  /*
  @Ignore
  @Test //(expected = UnsupportedOperationException.class)
  public void getStartTimeStringNeedsToBeImplemented() {
    PhoneCall call = new PhoneCall("??", "??", "??", "??", "??", "??");
    assertThat(call.getStartTimeString(), containsString("??"));
  }

  @Ignore
  @Test
  public void initiallyAllPhoneCallsHaveTheSameCallee() {
    PhoneCall call = new PhoneCall("??", "??", "??", "??", "??", "??");
    assertThat(call.getCallee(), containsString("??"));
  }

  /*
  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    PhoneCall call = new PhoneCall("??", "??", "??", "??", "??", "??");
    assertThat(call.getStartTime(), is(nullValue()));
  }*/

  @Ignore
  @Test(expected = UnsupportedOperationException.class)
  public void checkSyntaxForPhoneNumber() {
    String phone_number = "123-345-789";
    Project1.number_format_check(phone_number);
  }

  @Ignore
  @Test(expected = UnsupportedOperationException.class)
  public void checkSyntaxForCustomerName() {
    String name = "shikha $hah";
    Project1.name_check(name);
  }

  @Test
  public void forLoopContinueLabel() {
    int count = 0;
    outerLabel:
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        count++;
        if (count > 2) {
          continue outerLabel;
        }
      }
      count += 10;
    }
  }
  @Test
  public void objectToString() {
      Object object = new Object();
      // TODO: Why is it best practice to ALWAYS override toString?
      String expectedToString = MessageFormat.format("{0}@{1}", Object.class.getName(), Integer.toHexString(object.hashCode()));
    System.out.println(expectedToString);
      //assertEquals(expectedToString, "Object@3647ba9b"); // hint: object.toString()
    }
  }

