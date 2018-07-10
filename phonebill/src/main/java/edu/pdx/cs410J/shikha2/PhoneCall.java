package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {

    public PhoneCall() {
    }
    String Caller_number, Callee_number, getStartTime, getEndTime;

    /**
     * This creates <code>phonecall</code>
     *
     * @param Caller_number Argument Caller Number is the number from whom call is received.
     * @param Callee_number Argument Callee_Number is the number of the person who was called.
     * @param start_date    Start Date is the date when the call was received.
     * @param start_time    Start Time is the time when the received call started.
     * @param end_date      End Date is the date when the call ended.
     * @param end_time      End time is the time when the call ended.
     */

    public PhoneCall(String Caller_number, String Callee_number, String start_date, String start_time, String end_date, String end_time) {
        this.Caller_number = Caller_number;
        this.Callee_number = Callee_number;
        this.getStartTime = start_date + " " + start_time;
        this.getEndTime = end_date + " " + end_time;
    }

    /**
     * The following overriden function <code>getCaller</code> returns the caller number passed from command line argument.	
     * @return caller number
     */
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

