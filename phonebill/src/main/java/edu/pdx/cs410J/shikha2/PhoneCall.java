package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
    String Caller_number = null;
    String Callee_number = null;
    String getStartTime = null;
    String getEndTime = null;

    public void setCaller_number(String caller_number) {
        this.Caller_number = caller_number;
    }

    public void setCallee_number(String callee_number) {
        this.Callee_number = callee_number;
    }

    public void setGetStartTime(String getStartTime) {
        this.getStartTime = getStartTime;
    }

    public void setGetEndTime(String getEndTime) {
        this.getEndTime = getEndTime;
    }

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

