package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PhoneCall extends AbstractPhoneCall {

    String Caller_number, Callee_number, formatted_start_time, formatted_end_time;
    Date getStartTime1, getEndTime1;

    public PhoneCall(Validation val) {
       this.Caller_number =  val.caller_number;
       this.Callee_number = val.callee_number;
       SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:m a");
       this.getStartTime1 = val.start_date;
       this.getEndTime1 = val.end_date;
       this.formatted_start_time = sdf.format(getStartTime1).toLowerCase();
       this.formatted_end_time = sdf.format(getEndTime1).toLowerCase();
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
    public Date getStartTime() {
        return getStartTime1;
    }

    /**
     * Returns a textual representation of the time that this phone call
     * was originated.
     */
    @Override
    public String getStartTimeString() { return formatted_start_time; }

    /**
     * The following overriden function <code>getEndTimeString</code> returns the end call date and time 
     * passed from command line argument.
     * @return end time of the call.
     */
    @Override
    public Date getEndTime() { return getEndTime1; }

    /**
     * Returns a textual representation of the time that this phone call
     * was completed.
     */
    @Override
    public String getEndTimeString() {
        return formatted_end_time;
    }


}

