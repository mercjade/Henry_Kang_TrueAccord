package trueaccord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import trueaccord.model.Debt;
import trueaccord.model.Payment;
import trueaccord.model.PaymentPlan;

public class DataManager
{
   public static final String DEBTS_URL = "https://my-json-server.typicode.com/druska/trueaccord-mock-payments-api/debts";
   public static final String PAYMENT_PLAN_URL = "https://my-json-server.typicode.com/druska/trueaccord-mock-payments-api/payment_plans";
   public static final String PAYMENTS_URL = "https://my-json-server.typicode.com/druska/trueaccord-mock-payments-api/payments";
   
   public static final String FREQUENCY_WEEKLY = "WEEKLY";
   public static final String FREQUENCY_BI_WEEKLY = "BI_WEEKLY";
   
   public JSONArray loadData(String apiURL)
   {
      JSONArray data_obj = null;
      try
      {
         URL url = new URL( apiURL );
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setRequestProperty( "Accept", "application/json" );
         conn.setRequestMethod( "GET" );

         if ( conn.getResponseCode() != 200 )
         {
            throw new RuntimeException( "Please check the network status : HTTP error code : " + conn.getResponseCode() );
         }

         BufferedReader br = new BufferedReader( new InputStreamReader( ( conn.getInputStream() ) ) );

         StringBuilder output = new StringBuilder();
         String line = null;
         while ( ( line = br.readLine() ) != null )
         {
            output.append( line );
         }
         
         JSONParser parse = new JSONParser(JSONParser.MODE_PERMISSIVE);
         data_obj = (JSONArray) parse.parse(output.toString());
         
         System.out.println( data_obj );

         conn.disconnect();

      }      
      catch ( MalformedURLException e )
      {
         e.printStackTrace();
      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }
      catch ( ParseException e )
      {
         e.printStackTrace();
      }
      
      return data_obj;
   }
   
   public List<Debt> loadDebts() {
      JSONArray jsonArray = loadData(DEBTS_URL);
      
      List<Debt> debts = new ArrayList<Debt>();
      
      if (jsonArray != null && jsonArray.size() > 0) {
         Iterator attIterator = jsonArray.iterator();
         while (attIterator.hasNext()) {
            JSONObject jsonDebtElement = (JSONObject)attIterator.next();
            Debt debt = new Debt();
            
            debt.setId( (Integer)jsonDebtElement.get( "id" ) );
            Object x = jsonDebtElement.get("amount");
            debt.setAmount( ((Number) x).doubleValue() );
            debts.add( debt );
            System.out.println(jsonDebtElement);
         }
      }
      
      return debts;
   }
   
   // return Map to search the payment plan data easily
   public Map<Integer, PaymentPlan> loadPaymentPlans() {
      JSONArray jsonArray = loadData(PAYMENT_PLAN_URL);
      
      Map<Integer, PaymentPlan> paymentPlanMap = new HashMap<Integer, PaymentPlan>();
      
      if (jsonArray != null && jsonArray.size() > 0) {
         Iterator attIterator = jsonArray.iterator();
         while (attIterator.hasNext()) {
            JSONObject jsonPPElement = (JSONObject)attIterator.next();
            PaymentPlan paymentPlan = new PaymentPlan();
            
            Integer id = (Integer)jsonPPElement.get( "id" );
            paymentPlan.setDebt_id( ((Number) jsonPPElement.get( "debt_id" )).intValue() );
            paymentPlan.setAmount_to_pay( ((Number) jsonPPElement.get("amount_to_pay")).doubleValue() );
            paymentPlan.setInstallment_amount( ((Number) jsonPPElement.get("installment_amount")).doubleValue() );
            paymentPlan.setInstallment_frequency( (String)jsonPPElement.get( "installment_frequency" ) );
            paymentPlan.setStart_date( (String)jsonPPElement.get( "start_date" ) );
            paymentPlanMap.put( id, paymentPlan );
            System.out.println(jsonPPElement);
         }
      }
      
      return paymentPlanMap;
   }
   
   public Map<Integer, Payment> loadPayments() {
      JSONArray jsonArray = loadData(PAYMENTS_URL);
      
      Map<Integer, Payment> paymentMap = new HashMap<Integer, Payment>();      

      Payment payment = null;
      if (jsonArray != null && jsonArray.size() > 0) {
         Iterator attIterator = jsonArray.iterator();
         while (attIterator.hasNext()) {
            JSONObject jsonPaymentElement = (JSONObject)attIterator.next();
            
            Integer ppId = (Integer)jsonPaymentElement.get( "payment_plan_id" );
            
            //merge payment data by the payment_plan_id
            if (paymentMap.get( ppId ) == null) {
               payment = new Payment();
               payment.setPayment_plan_id( ppId );
               payment.setAmount( ((Number) jsonPaymentElement.get("amount")).doubleValue() );
               payment.setLast_payment_date( (String)jsonPaymentElement.get( "date" ) );
               
               paymentMap.put( ppId, payment );
            } else {
               //to sum all the payments customer made
               payment.addAmount( ((Number) jsonPaymentElement.get("amount")).doubleValue() );
               String newDate = (String)jsonPaymentElement.get( "date" );
               // only save the latest payment date
               if (newDate.compareTo( payment.getLast_payment_date() ) > 0) {
                  payment.setLast_payment_date( newDate );;
               }
            }
            System.out.println(jsonPaymentElement);
         }
      }
      
      return paymentMap;
   }
   
   public static void main( String[] args )
   {
      DataManager dm = new DataManager();
      dm.loadDebts();

      dm.loadPaymentPlans();
      dm.loadPayments();
   }

}
