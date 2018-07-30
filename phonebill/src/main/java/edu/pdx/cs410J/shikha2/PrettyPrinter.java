package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.util.*;

public class PrettyPrinter implements PhoneBillDumper {

    //String file_name
    String                     customer_name;
    Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();


    /**
     * <code>PrettyPrinter</code> constructor with only one parameter.
     * If output is required as standard output and hence file name is set as null.
     *
     * @param customer_name
     */
    public PrettyPrinter(String customer_name) {
        //this.file_name = "";
        this.customer_name = customer_name;
    }

    /**
     * Dumps a phone bill to some destination.
     *
     * @param bill
     */
    @Override
    public void dump(AbstractPhoneBill bill) {

    }

    public AbstractPhoneBill sorted() {
        List<ArrayList<String>> sortedbill = new ArrayList<ArrayList<String>>();
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            Integer      key    = entry.getKey();
            List<String> values = entry.getValue();
            sortedbill.add(new ArrayList<String>(values));
        }

        //sorting the bill first with start time and then phone numbers
        sortedbill.sort(Comparator.comparing((List<String> l) -> l.get(3)).thenComparing(l -> l.get(1)));

        Collection<PhoneCall> calls = new ArrayList<>();

        for (int i = 0; i < sortedbill.size(); i++) {
            String[]   start_date = sortedbill.get(i).get(3).split(" ");
            String[]   end_date   = sortedbill.get(i).get(4).split(" ");
            Validation val        = new Validation(sortedbill.get(i).get(0), sortedbill.get(i).get(1), sortedbill.get(i).get(2), start_date[0], start_date[1], end_date[0], end_date[1], start_date[2], end_date[2]);
            PhoneCall  call       = new PhoneCall(val);
            calls.add(call);
        }

        PhoneBill bill = new PhoneBill(customer_name);
        for (PhoneCall c : calls) {
            bill.addPhoneCall(c);
        }

        return bill;
    }
}

