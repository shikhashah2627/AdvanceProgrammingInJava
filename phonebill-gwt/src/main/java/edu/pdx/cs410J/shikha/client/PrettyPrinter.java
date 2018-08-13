package edu.pdx.cs410J.shikha.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import edu.pdx.cs410J.PhoneBillDumper;


public class PrettyPrinter implements PhoneBillDumper<PhoneBill>, IsSerializable {

    private String finaloutput;


    public PrettyPrinter(){
    }

    public PrettyPrinter (String finaloutput) {
       this.finaloutput = finaloutput;
    }

    @Override
    public void dump(PhoneBill bill) {

    }

}