package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.web.HttpRequestHelper;

import static junit.framework.TestCase.assertEquals;

public class PhoneBillServiceSyncProxyIT extends HttpRequestHelper {

  private final int httpPort = Integer.getInteger("http.port", 8080);
  private String webAppUrl = "http://localhost:" + httpPort + "/phonebill";

  /*
  @Test
  public void gwtWebApplicationIsRunning() throws IOException {
    Response response = get(this.webAppUrl);
    assertEquals(200, response.getCode());
  }

  @Test
  public void canInvokePhoneBillServiceWithGwtSyncProxy() {
    String moduleName = "phonebill";
    SyncProxy.setBaseURL(this.webAppUrl + "/" + moduleName + "/");

    PhoneBillService service = SyncProxy.createSync(PhoneBillService.class);
    PhoneBill bill = service.addNewPhoneCall();
    assertEquals("CS410J", bill.getCustomer());
    assertEquals(1, bill.getPhoneCalls().size());
  }*/

}
