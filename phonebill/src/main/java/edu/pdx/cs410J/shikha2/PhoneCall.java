package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
    String Caller_number = null;
    String Callee_number = null;
    String getStartTime = null;
    String getEndTime = null;

    /**
     * @return It returns the caller number
     */
    @Override
    public String getCaller() {
        return Caller_number;
    }

    @Override
  public String getCallee() {
        return Callee_number;
    }

    @Override
  public String getStartTimeString() {
        return getStartTime;
    }

    @Override
  public String getEndTimeString() {
        return getEndTime;
    }
}

