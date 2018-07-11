package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {

    String Customer;

    /**
     * The below constructor <code>PhoneBill</code> assigns the customer name that is passed.
     *
     * @param customer
     */
    public PhoneBill(String customer) {
        Customer = customer;
    }

    private Collection<PhoneCall> calls = new ArrayList<>();

    /**
     * The following overriden function <code>getCustomer</code> returns the customer
     * name passed from command line argument.
     * 
     * @return Customer name is returned.
     */
    @Override
    public String getCustomer() {
        return Customer;
    }

    /**
     * The following overriden method <code>addPhoneCall</code> adds the phone call information
     * passed through the command line arguments using call the collection object.
     *
     * @param phoneCall
     */
    @Override
    public void addPhoneCall(PhoneCall phoneCall) {
        this.calls.add(phoneCall);
    }

    /**
     * The following overriden function <code>Collection</code> is the collection of phone calls
     * with call information  and returns final method of Phone Call.
     *
     * @return Call Information
     */
    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls;
    }
}
