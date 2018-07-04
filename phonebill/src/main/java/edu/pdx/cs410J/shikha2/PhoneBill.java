package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {

    String Customer;

    public PhoneBill(String customer) {
        Customer = customer;
    }

    private  Collection<PhoneCall> calls = new ArrayList<>();

    /**
     * @return Customer name is returned.
     */
    @Override
    public String getCustomer() {
        return Customer;
    }

    @Override
    public void addPhoneCall(PhoneCall phoneCall) {
        this.calls.add(phoneCall);
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls;
    }
}
