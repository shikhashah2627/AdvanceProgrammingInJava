package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {

    String Caller_number, Callee_number, getStartTime, getEndTime;

    public PhoneCall(Validation val) {
       this.Caller_number =  val.caller_number;
       this.Callee_number = val.callee_number;
       this.getStartTime = val.start_date_time;
       this.getEndTime = val.end_date_time;
    }

    @Override
    public String getCaller() {
        return Caller_number;
    }

    /**
     * The following overriden function <code>getCallee</code> returns the callee number passed from command line argument.	
     * @return callee number.
     */
    @Override
    public String getCallee() {
        return Callee_number;
    }

    /**
     * The following overriden function <code>getStartTimeString</code> returns the start call date and time 
     * passed from command line argument.
     * @return start time of the call.
     */
    @Override
    public String getStartTimeString() {
        return getStartTime;
    }

    /**
     * The following overriden function <code>getEndTimeString</code> returns the end call date and time 
     * passed from command line argument.
     * @return end time of the call.
     */
    @Override
    public String getEndTimeString() {
        return getEndTime;
    }


}

