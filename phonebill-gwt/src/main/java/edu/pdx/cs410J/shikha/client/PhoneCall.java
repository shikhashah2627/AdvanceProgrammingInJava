package edu.pdx.cs410J.shikha.client;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Date;

public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {

    String Caller_number, Callee_number, formatted_start_time, formatted_end_time;
    Date Date_Start_Time, Date_End_time;
  /**
   * In order for GWT to serialize this class (so that it can be sent between
   * the client and the server), it must have a zero-argument constructor.
   */
  public PhoneCall() {

  }

    public PhoneCall(String Caller_Number, String Callee_Number, Date Start_Date_Time, Date End_Date_Time,
                     String Start_Time_String, String End_Time_String) {
        this.Caller_number = Caller_Number;
        this.Callee_number = Callee_Number;
        this.Date_Start_Time = Start_Date_Time;
        this.Date_End_time = End_Date_Time;

        this.formatted_start_time = Start_Time_String;
        this.formatted_end_time = End_Time_String;
    }

    public PhoneCall(String Caller_Number, String Callee_Number, Date Start_Date_Time, Date End_Date_Time) {
        this.Caller_number = Caller_Number;
        this.Callee_number = Callee_Number;
        this.Date_Start_Time = Start_Date_Time;
        this.Date_End_time = End_Date_Time;
    }

  @Override
  public String getCaller() {
    return Caller_number;
  }

  @Override
  public Date getStartTime() {
    return Date_Start_Time;
  }

  @Override
  public String getStartTimeString() {

      return formatted_start_time;
  }

  @Override
  public String getCallee() {
    return Callee_number;
  }

  @Override
  public Date getEndTime() {
    return Date_End_time;
  }

  @Override
  public String getEndTimeString() {
    return formatted_end_time;
  }

  public long durationOfCall(){
    long different = (this.getEndTime().getTime() - this.getStartTime().getTime())/6000;
    return different;
  }
    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure
     * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
     * for all {@code x} and {@code y}.  (This
     * implies that {@code x.compareTo(y)} must throw an exception iff
     * {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
     * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
     * all {@code z}.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param call the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(PhoneCall call) {
        int difference;
        difference = this.getStartTime().compareTo(call.getStartTime());

        if(difference == 0){
            difference = this.getCaller().compareTo(call.getCaller());
        }
        return difference;
    }
}
