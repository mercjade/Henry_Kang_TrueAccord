package trueaccord;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import io.restassured.RestAssured;
import net.minidev.json.JSONArray;

public class DataManagerTest
{

   private static final DataManager dataManager = new DataManager();

   // test restful api first
   @Test
   public void testLoadData() 
   {
      RestAssured.given().when().get( DataManager.DEBTS_URL ).then().statusCode( 200 ).log().all();
      RestAssured.given().when().get( DataManager.PAYMENT_PLAN_URL ).then().statusCode( 200 ).log().all();
      RestAssured.given().when().get( DataManager.PAYMENTS_URL ).then().statusCode( 200 ).log().all();
   }

   @Test
   public void testLoadDebts() throws JSONException
   {
      JSONArray jsonArrayDebts = dataManager.loadData( DataManager.DEBTS_URL );
      String expectedDebtsJson = "[{\"amount\": 123.46,\"id\": 0}, " 
               + "{\"amount\": 100,\"id\": 1}, " 
               + "{\"amount\": 4920.34,\"id\": 2}, " 
               + "{\"amount\": 12938,\"id\": 3}, " 
               + "{\"amount\": 9238.02,\"id\": 4}]";

      JSONAssert.assertEquals( expectedDebtsJson, jsonArrayDebts.toJSONString(), true );
   }

   @Test
   public void testLoadPaymentPlans() throws JSONException
   {
      JSONArray jsonArrayPaymentPlan = dataManager.loadData( DataManager.PAYMENT_PLAN_URL );
      String expectedPPJson = "[  {    \"amount_to_pay\": 102.5,    \"debt_id\": 0,    \"id\": 0,    \"installment_amount\": 51.25,    \"installment_frequency\": \"WEEKLY\",    \"start_date\": \"2020-09-28\"  },  "
               + "{    \"amount_to_pay\": 100,    \"debt_id\": 1,    \"id\": 1,    \"installment_amount\": 25,    \"installment_frequency\": \"WEEKLY\",    \"start_date\": \"2020-08-01\"  },  "
               + "{    \"amount_to_pay\": 4920.34,    \"debt_id\": 2,    \"id\": 2,    \"installment_amount\": 1230.085,    \"installment_frequency\": \"BI_WEEKLY\",    \"start_date\": \"2020-01-01\"  },  "
               + "{    \"amount_to_pay\": 4312.67,    \"debt_id\": 3,    \"id\": 3,    \"installment_amount\": 1230.085,    \"installment_frequency\": \"WEEKLY\",    \"start_date\": \"2020-08-01\"  }]";

      JSONAssert.assertEquals( expectedPPJson, jsonArrayPaymentPlan.toJSONString(), true );
   }

   @Test
   public void testLoadPayments() throws JSONException
   {
      JSONArray jsonArrayPayments = dataManager.loadData( DataManager.PAYMENTS_URL );
      String expectedPaymentsJson = "[  {    \"amount\": 51.25,    \"date\": \"2020-09-29\",    \"payment_plan_id\": 0  },  "
               + "{    \"amount\": 51.25,    \"date\": \"2020-10-29\",    \"payment_plan_id\": 0  },  "
               + "{    \"amount\": 25,    \"date\": \"2020-08-08\",    \"payment_plan_id\": 1  },  "
               + "{    \"amount\": 25,    \"date\": \"2020-08-08\",    \"payment_plan_id\": 1  },  "
               + "{    \"amount\": 4312.67,    \"date\": \"2020-08-08\",    \"payment_plan_id\": 2  },  "
               + "{    \"amount\": 1230.085,    \"date\": \"2020-08-01\",    \"payment_plan_id\": 3  },  "
               + "{    \"amount\": 1230.085,    \"date\": \"2020-08-08\",    \"payment_plan_id\": 3  },  "
               + "{    \"amount\": 1230.085,    \"date\": \"2020-08-15\",    \"payment_plan_id\": 3  }]";

      JSONAssert.assertEquals( expectedPaymentsJson, jsonArrayPayments.toJSONString(), true );
   }

}
