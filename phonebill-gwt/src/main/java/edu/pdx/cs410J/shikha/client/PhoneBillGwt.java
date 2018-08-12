package edu.pdx.cs410J.shikha.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {
    private       Date                  Date_Start_Date_Time,Date_End_Date_Time;
    private final Alerter               alerter;
    private final PhoneBillServiceAsync phoneBillService;
    private final Logger                logger;
    private DateTimeFormat format = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
    private String Number_pattern = "^\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d";
    private String Name_pattern   = "([a-zA-Z0-9] ?)+[a-zA-Z0-9]";
    PhoneCall call;
    //SortedSet<PhoneCall> call_list = new TreeSet<>();

    private final DeckPanel layoutPanel = new DeckPanel();
    String Customer_Name,Caller_Number,Callee_Number,Start_Date_Time,End_Date_Time;
    public PhoneBillGwt() {
        this(new Alerter() {
            @Override
            public void alert(String message) {
                Window.alert(message);
            }
        });
    }

    @VisibleForTesting
    PhoneBillGwt(Alerter alerter) {
        this.alerter = alerter;
        this.phoneBillService = GWT.create(PhoneBillService.class);
        this.logger = Logger.getLogger("phoneBill");
        Logger.getLogger("").setLevel(Level.INFO);  // Quiet down the default logging
    }

    private void alertOnException(Throwable throwable) {
        Throwable     unwrapped = unwrapUmbrellaException(throwable);
        StringBuilder sb        = new StringBuilder();
        sb.append(unwrapped.toString());
        sb.append('\n');

        for (StackTraceElement element : unwrapped.getStackTrace()) {
            sb.append("  at ");
            sb.append(element.toString());
            sb.append('\n');
        }

        this.alerter.alert(sb.toString());
    }

    private Throwable unwrapUmbrellaException(Throwable throwable) {
        if (throwable instanceof UmbrellaException) {
            UmbrellaException umbrella = (UmbrellaException) throwable;
            if (umbrella.getCauses().size() == 1) {
                return unwrapUmbrellaException(umbrella.getCauses().iterator().next());
            }

        }

        return throwable;
    }

    private void addMenuItems(MenuBar menu) {
        menu.addItem(new MenuItem("Add Call", new Command() {
            @Override
            public void execute() {
                showAddCallScreen();
            }
        }));
        menu.addSeparator();
        menu.addItem(new MenuItem("Search Call", new Command() {
            @Override
            public void execute() {
                showSearchCallScreen();
            }
        }));
        menu.addSeparator();
        MenuBar About_Us = new MenuBar();
        About_Us.setWidth("150px");
        About_Us.addItem(new MenuItem("README", new Command() {
            @Override
            public void execute() {
                showReadmeValue();
            }
        }));

        menu.addItem(new MenuItem("About Us", About_Us));
    }

    private void showSearchCallScreen() {
        this.layoutPanel.showWidget(1);
    }


    private void throwClientSideException() {
        logger.info("About to throw a client-side exception");
        throw new IllegalStateException("Expected exception on the client side");
    }

    private void showUndeclaredException() {
        logger.info("Calling throwUndeclaredException");
        phoneBillService.throwUndeclaredException(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable ex) {
                alertOnException(ex);
            }

            @Override
            public void onSuccess(Void aVoid) {
                alerter.alert("This shouldn't happen");
            }
        });
    }

    private void showDeclaredException() {
        logger.info("Calling throwDeclaredException");
        phoneBillService.throwDeclaredException(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable ex) {
                alertOnException(ex);
            }

            @Override
            public void onSuccess(Void aVoid) {
                alerter.alert("This shouldn't happen");
            }
        });
    }

    // shows the latest call added.
    private void showNewCall(PhoneCall call) {
        logger.info("Calling addNewPhoneCall");
        phoneBillService.addNewPhoneCall(call,Customer_Name,new AsyncCallback<PhoneBill>() {

            @Override
            public void onFailure(Throwable ex) {
                alertOnException(ex);
            }

            @Override
            public void onSuccess(PhoneBill phoneBill) {
                SortedSet<PhoneCall> call_list=new TreeSet<>();
                Collection calls = phoneBill.getPhoneCalls();
                StringBuilder         sb    = new StringBuilder(phoneBill.toString() + "\n");
                call_list.addAll(calls);
                for (PhoneCall call : call_list) {
                    sb.append(call);
                    sb.append("\n");
                }
                alerter.alert(sb.toString());
            }
        });
    }


    private void showReadmeValue() {
        logger.info("Showing README values");
        StringBuilder sb = new StringBuilder();
        sb.append("This is all about the last Project 5 haivng GWT into it which provide the features like : Add Call and Search Call either with Customer Name or by providing" +
                "Start Date and End Date.");
        alerter.alert(sb.toString());
    }

    @Override
    public void onModuleLoad() {
        setUpUncaughtExceptionHandler();

        // The UncaughtExceptionHandler won't catch exceptions during module load
        // So, you have to set up the UI after module load...
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                setupUI();
            }
        });
    }


    private void setupUI() {
        RootPanel rootPanel = RootPanel.get();
        rootPanel.setStyleName("bg-body");
        MenuBar   menuItems = new MenuBar();
        rootPanel.add(menuItems);

        rootPanel.add(layoutPanel);

        addMenuItems(menuItems);
        layoutPanel.add(addFormItems());
        layoutPanel.add(searchPhoneCalls());
        layoutPanel.add(showOutput());

        layoutPanel.showWidget(0);
    }

    private VerticalPanel addFormItems() {
        VerticalPanel layout_form = new VerticalPanel();

        layout_form.add(new Label("Enter the Customer Name: "));
        TextBox customer_name = new TextBox();
        layout_form.add(customer_name);
        // get the entered customer name verified.
        parseCustomerName(customer_name);


        layout_form.add(new Label("Enter the Caller Number in the format of XXX-XXX-XXXX: "));
        TextBox caller_number = new TextBox();
        layout_form.add(caller_number);
        // check the caller number format
        caller_number.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                Caller_Number = getInsertedValues(caller_number);
                if (Caller_Number.matches(Number_pattern)) {
                    PhoneBillGwt.this.Caller_Number = Caller_Number;
                } else {
                    alerter.alert("Caller Phone number doesn't match the asked format.");
                }

            }
        });

        layout_form.add(new Label("Enter the Callee Number in the format of XXX-XXX-XXXX: "));
        TextBox callee_number = new TextBox();
        layout_form.add(callee_number);
        // check the callee number format.
        callee_number.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                Callee_Number = getInsertedValues(callee_number);
                if (Callee_Number.matches(Number_pattern)) {
                    PhoneBillGwt.this.Callee_Number = Callee_Number;
                } else {
                    alerter.alert("Callee Phone number doesn't match the asked format.");
                }

            }
        });


        layout_form.add(new Label("Enter the Start Date and Time: "));
        TextBox start_date_time = new TextBox();
        layout_form.add(start_date_time);
        parseStartDate(start_date_time);

        layout_form.add(new Label("Enter the End Date and Time: "));
        TextBox end_Date_time = new TextBox();
        layout_form.add(end_Date_time);
        parseEndDate(end_Date_time);

        Button Submit = new Button("Submit");
        layout_form.add(Submit);
        Submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if(Caller_Number!=null && Callee_Number!=null && Date_Start_Date_Time!=null && Date_End_Date_Time!=null) {
                    call = new PhoneCall(Caller_Number,Callee_Number,Date_Start_Date_Time,Date_End_Date_Time,Start_Date_Time,End_Date_Time);
                    showNewCall(call);
                }
            }
        });


        return layout_form;
    }


    private void showOutputDeck() {

        this.layoutPanel.showWidget(2);
    }

    private String getInsertedValues(TextBox textBox) {
        try{
            return textBox.getValue();
        } catch(NumberFormatException ex){
            alerter.alert("Please pass the correct Input.");
            return null;
        }
    }

    // show the final output.

    /**
     * <code>showOutput</code> helps to show the final output
     */
    private VerticalPanel showOutput() {
        VerticalPanel layout = new VerticalPanel();
        layout.add(new Label("hi"));

        Button Add_New_Call = new Button("Add one more call");
        Add_New_Call.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                showAddCallScreen();
            }
        });
        return layout;
    }

    private void showAddCallScreen() {
        this.layoutPanel.showWidget(0);
    }

    // show the screen for searching functionality

    /**
     * <code>searchPhoneCalls</code> helps to ask the criteria for searching the phonecall from the bill.
     */
    private VerticalPanel searchPhoneCalls() {
        VerticalPanel layout_form = new VerticalPanel();

        layout_form.add(new Label("Enter the Customer Name: "));
        TextBox customer_name = new TextBox();
        layout_form.add(customer_name);
        parseCustomerName(customer_name);


        Button Submit = new Button("Get All Calls..");
        layout_form.add(Submit);
        Submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                getPhoneBill(Customer_Name);
            }
        });





        layout_form.add(new Label("Enter the Start Date and Time: "));
        TextBox start_date_time = new TextBox();
        layout_form.add(start_date_time);
        parseStartDate(start_date_time);

        layout_form.add(new Label("Enter the End Date and Time: "));
        TextBox end_Date_time = new TextBox();
        layout_form.add(end_Date_time);
        parseEndDate(end_Date_time);

        Button Submit_Search_Criteria = new Button("Submit Criteria");
        layout_form.add(Submit_Search_Criteria);
        Submit_Search_Criteria.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                searchCallsWithinRange(Customer_Name,Start_Date_Time,End_Date_Time);
            }
        });

        return layout_form;
    }

    private void searchCallsWithinRange(String customer_name, String start_date_time, String end_date_time) {
        logger.info("Calling searchPhoneCall Method.");
        try {
            phoneBillService.searchPhoneCall(customer_name,start_date_time,end_date_time,new AsyncCallback<PhoneBill>() {

                @Override
                public void onFailure(Throwable throwable) {

                }

                @Override
                public void onSuccess(PhoneBill phoneBill) {
                    StringBuilder sb = new StringBuilder(phoneBill.toString());
                    SortedSet<PhoneCall> call_list = new TreeSet<>();
                    Collection flights = phoneBill.getPhoneCalls();
                    call_list.addAll(flights);

                    for (PhoneCall call : call_list) {
                        sb.append(call);
                        sb.append("\n");
                    }
                    alerter.alert(sb.toString());
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void getPhoneBill(String customer_name) {
    }

    private void parseCustomerName(TextBox customer_name) {
        customer_name.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                Customer_Name = getInsertedValues(customer_name);
                if (Customer_Name.matches(Name_pattern)) {
                    PhoneBillGwt.this.Customer_Name = Customer_Name;
                } else {
                    alerter.alert("Customer Name should not include any special characters.");
                }
            }
        });
    }

    private void parseEndDate(TextBox end_Date_time) {
        end_Date_time.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                End_Date_Time = getInsertedValues(end_Date_time);
                try {
                    PhoneBillGwt.this.Date_End_Date_Time = format.parse(End_Date_Time);
                    End_Date_Time = format.format(Date_End_Date_Time).toLowerCase();
                } catch (IllegalArgumentException ex) {
                    alerter.alert("Invalid date: " + End_Date_Time);
                }
            }
        });
    }

    private void parseStartDate(TextBox start_date_time) {
        start_date_time.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                Start_Date_Time = getInsertedValues(start_date_time);
                try {
                    PhoneBillGwt.this.Date_Start_Date_Time = format.parse(Start_Date_Time);
                    Start_Date_Time = format.format(Date_Start_Date_Time).toLowerCase();
                } catch (IllegalArgumentException ex) {
                    alerter.alert("Invalid date: " + Start_Date_Time);
                }
            }
        });
    }

    private void setUpUncaughtExceptionHandler() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(Throwable throwable) {
                alertOnException(throwable);
            }
        });
    }

    @VisibleForTesting
    interface Alerter {
        void alert(String message);
    }

}
