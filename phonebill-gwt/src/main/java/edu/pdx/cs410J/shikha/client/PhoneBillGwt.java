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
import edu.pdx.cs410J.AbstractPhoneBill;

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
    private       Date                  Date_Start_Date_Time,Date_End_Date_Time,Date_Search_Start_Time,Date_Search_End_time;
    private final Alerter               alerter;
    private final PhoneBillServiceAsync phoneBillService;
    private final Logger                logger;
    private DateTimeFormat format = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
    private String Number_pattern = "^\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d";
    private String Name_pattern   = "([a-zA-Z0-9] ?)+[a-zA-Z0-9]";
    private Label final_output = new Label();
    public String sb_appended_text_final = "";

    private final DeckPanel layoutPanel = new DeckPanel();
    String Customer_Name,Caller_Number,Callee_Number,Start_Date_Time,End_Date_Time,search_customer_name_text,search_Start_Time,search_End_Time;
    Boolean search = false;
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

    /**
     * <code>addFormItems</code> is a vertical panel that helps to add a new call.
     * @return
     */
    private VerticalPanel addFormItems() {
        VerticalPanel layout_form = new VerticalPanel();

        layout_form.add(new Label("Enter the Customer Name: "));
        TextBox customer_name = new TextBox();
        layout_form.add(customer_name);
        // get the entered customer name verified.
        parseCustomerName(customer_name,false);


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


        layout_form.add(new Label("Enter the Start Date and Time in the format of MM/dd/yyyy hh:mm a :  "));
        TextBox start_date_time = new TextBox();
        layout_form.add(start_date_time);
        parseStartDate(start_date_time);

        layout_form.add(new Label("Enter the End Date and Time in the format of MM/dd/yyyy hh:mm a: "));
        TextBox end_Date_time = new TextBox();
        layout_form.add(end_Date_time);
        parseEndDate(end_Date_time);

        Button Submit = new Button("Add Call");
        layout_form.add(Submit);
        Submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if(Caller_Number !=null && Callee_Number !=null && Date_Start_Date_Time !=null && Date_End_Date_Time !=null) {
                    PhoneCall call = new PhoneCall(Caller_Number,Callee_Number,Date_Start_Date_Time,Date_End_Date_Time,Start_Date_Time,End_Date_Time);
                    showNewCall(call);
                    showOutputDeck();
                } else {
                    alerter.alert("Please insert the missing value.");
                }
            }
        });

        Button Submit1 = new Button("Try Call");
        layout_form.add(Submit1);
        Submit1.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                PhoneBillGwt.this.sb_appended_text_final = "this is trial";
                final_output.setText(sb_appended_text_final);
                showOutputDeck();
            }
        });


        return layout_form;
    }

    // shows the latest call added.

    /**
     * <code>showNewCall</code> helps to show the new call information.
     * @param call
     */
    private void showNewCall(PhoneCall call) {
        logger.info("Calling addNewPhoneCall");
        phoneBillService.addNewPhoneCall(call,Customer_Name,new AsyncCallback<AbstractPhoneBill>() {

            @Override
            public void onFailure(Throwable ex) {
                alertOnException(ex);
            }

            @Override
            public void onSuccess(AbstractPhoneBill phoneBill) {
                SortedSet<PhoneCall> call_list=new TreeSet<>();
                Collection calls = phoneBill.getPhoneCalls();
                call_list.addAll(calls);
                PhoneBillGwt.this.sb_appended_text_final = "New Call Information added to existing bill of : "+ call_list.size() + "\n " +call.toString();
                //alerter.alert("New Call Information added to existing bill of : " + call_list.size() + " \n" +  call.toString());

                //showFinalOutput(phoneBill);
            }
        });
    }

    private void showFinalOutput(AbstractPhoneBill bill) {
        this.final_output.setText(bill.toString());
    }

    /**
     * <code>showOutput</code> helps to show the final output
     */
    private void showOutputDeck() {

        this.layoutPanel.showWidget(2);
    }

    /**
     *
     * @param textBox -- passes the inserted value.
     * @return the inserted value.
     */
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
     * <code>showOutput</code> is a vertical panel to show the output generated either for added call or searched list.
     * @return
     */
    private VerticalPanel showOutput() {
        VerticalPanel layout = new VerticalPanel();

        layout.add(new Label("Welcome to the output screen!"));

        layout.add(final_output);

        Button Add_New_Call = new Button("Add one more call");
        Add_New_Call.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                showAddCallScreen();
            }
        });
        layout.add(Add_New_Call);
        return layout;
    }

    /**
     * <code>showAddCallScreen</code> helps to show the deck panel for presenting the panel to add call.
     */
    private void showAddCallScreen() {

        this.layoutPanel.showWidget(0);
    }

    // show the screen for searching functionality

    /**
     * <code>searchPhoneCalls</code> is a Vertical Panel.
     * @return the vertical panel providing the facility of Searching the PhoneCall.
     */
    private VerticalPanel searchPhoneCalls() {
        VerticalPanel layout_form = new VerticalPanel();

        layout_form.add(new Label("Enter the Customer Name: "));
        TextBox search_customer_name = new TextBox();
        layout_form.add(search_customer_name);
        search_customer_name.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                search_customer_name_text = getInsertedValues(search_customer_name);
                if(search_customer_name_text.matches(Name_pattern)) {
                    PhoneBillGwt.this.search_customer_name_text = search_customer_name_text;
                } else {
                    alerter.alert("Customer Name should not include any special characters.");
                }
            }
        });


        layout_form.add(new Label("Enter the Start Date and Time: "));
        TextBox search_start_date_time = new TextBox();
        layout_form.add(search_start_date_time);
        search_start_date_time.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                search_Start_Time = getInsertedValues(search_start_date_time);
                PhoneBillGwt.this.Date_Search_Start_Time = format.parse(search_Start_Time);
                try {
                    if(search_Start_Time == null) {
                        search_Start_Time = null;
                    }  else {
                        search_Start_Time = format.format(Date_Search_Start_Time).toLowerCase();
                    }
                } catch (IllegalArgumentException ex) {
                    alerter.alert("Invalid date: " + search_Start_Time);
                }
            }
        });

        layout_form.add(new Label("Enter the End Date and Time: "));
        TextBox search_end_Date_time = new TextBox();
        layout_form.add(search_end_Date_time);
        search_end_Date_time.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                search_End_Time = getInsertedValues(search_end_Date_time);
                PhoneBillGwt.this.Date_Search_End_time = format.parse(search_End_Time);
                try {
                    if(search_End_Time == null) {
                        search_End_Time = null;
                    } else if(Date_Search_Start_Time.compareTo(Date_Search_End_time) >= 0) {
                        alerter.alert("Your Start Date is greater than End Date.");
                    } else {
                        search_End_Time = format.format(Date_Search_End_time).toLowerCase();
                    }
                } catch (IllegalArgumentException ex) {
                    alerter.alert("Invalid date: " + search_End_Time);
                }
            }
        });

        Button Submit_Search_Criteria = new Button("Submit Criteria");
        layout_form.add(Submit_Search_Criteria);
        Submit_Search_Criteria.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                searchCallsWithinRange(search_customer_name_text,search_Start_Time,search_End_Time);
                setTextAsNull(search_start_date_time,search_end_Date_time);
                showOutputDeck();
            }
        });

        return layout_form;
    }

    private void setTextAsNull(TextBox search_start_date_time, TextBox search_end_date_time1) {
        search_start_date_time.setText(null);
        search_end_date_time1.setText(null);
    }


    /**
     * <code>searchCallsWithinRange</code> helps to ask the criteria for searching the phonecall from the bill.
     */

    private void searchCallsWithinRange(String search_customer_name, String start_date_time, String end_date_time) {
        logger.info("Calling searchPhoneCall Method.");
        phoneBillService.searchPhoneCall(search_customer_name,start_date_time,end_date_time, new AsyncCallback<AbstractPhoneBill>() {

            @Override
            public void onFailure(Throwable throwable) {
                alertOnException(throwable);
            }

            @Override
            public void onSuccess(AbstractPhoneBill phoneBill) {
                if(phoneBill == null) {
                    alerter.alert("No such bill exist for the passed customer : " + search_customer_name);
                } else {

                    showFinalOutput(phoneBill);
                    String append1;
                    if (start_date_time != null && end_date_time != null) {
                        append1 = phoneBill.toString() + " from " + start_date_time + " to " + end_date_time + " are : \n";
                    } else {
                        append1 = "Whole Phone Bill Information is : "+ "\n" +  phoneBill.toString() + " \n";
                    }

                    StringBuilder sb = new StringBuilder(append1);

                    SortedSet<PhoneCall> call_list = new TreeSet<>();
                    Collection           calls     = phoneBill.getPhoneCalls();
                    call_list.addAll(calls);

                    for (PhoneCall call : call_list) {
                        sb.append(call);
                        sb.append("\n");
                    }
                    //alerter.alert(sb.toString());
                    final_output.setText(sb.toString());

                }

            }
        });
    }

    /**
     * <code>parseCustomerName</code> helps to parse the customer name entered.
     * @param textBox - text box input of customer name.
     */
    private void parseCustomerName(TextBox textBox,Boolean search) {
        textBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {

                    Customer_Name = getInsertedValues(textBox);
                    if (Customer_Name.matches(Name_pattern)) {
                        PhoneBillGwt.this.Customer_Name = Customer_Name;
                    } else {
                        alerter.alert("Customer Name should not include any special characters.");
                    }

            }
        });
    }

    /**
     * <code>parseEndDate</code> helps to parse the EndDate provided for a new call or in search criteria.
     * @param end_Date_time - entered end date time
     */
    private void parseEndDate(TextBox end_Date_time) {
        end_Date_time.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                End_Date_Time = getInsertedValues(end_Date_time);
                PhoneBillGwt.this.Date_End_Date_Time = format.parse(End_Date_Time);
                try {
                    if(end_Date_time == null) {
                        End_Date_Time = null;
                    } else if(Date_Start_Date_Time.compareTo(Date_End_Date_Time) >= 0) {
                        alerter.alert("Your Start Date is greater than End Date.");
                    } else {
                        End_Date_Time = format.format(Date_End_Date_Time).toLowerCase();
                    }
                } catch (IllegalArgumentException ex) {
                    alerter.alert("Invalid date: " + End_Date_Time);
                }
            }
        });
    }

    /**
     * <code>parseStartDate</code> helps to parse the StartDate provided for a new Call or in search Criteria.
     * @param start_date_time - entered Start Date Time.
     */
    private void parseStartDate(TextBox start_date_time) {
        start_date_time.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                Start_Date_Time = getInsertedValues(start_date_time);
                try {
                    if(start_date_time == null) {
                        Start_Date_Time = null;
                    } else {
                        PhoneBillGwt.this.Date_Start_Date_Time = format.parse(Start_Date_Time);
                        Start_Date_Time = format.format(Date_Start_Date_Time).toLowerCase();
                    }
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
