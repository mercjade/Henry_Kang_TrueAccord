package trueaccord;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;


import trueaccord.model.Debt;
import trueaccord.model.Payment;
import trueaccord.model.PaymentPlan;

public class DebtsClientTest
{
   private static final DebtsClient debtsClient = new DebtsClient();
   
   @Before 
   public void initialize() {
      debtsClient.loadAllData();
   }
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testGetAllDebtData()
   {
      List<Debt> debts = debtsClient.getAllDebtData();
      
      // check the debt record size
      assertThat(debts, hasSize(5));
      
      // added only two assert methods
      assertThat(debts, allOf(hasItem(Matchers.<Debt>hasProperty("id", equalTo(0))), 
               hasItem(Matchers.<Debt>hasProperty("amount", equalTo(123.46))),
               hasItem(Matchers.<Debt>hasProperty("remaining_amount", equalTo(0.0))),
               hasItem(Matchers.<Debt>hasProperty("next_payment_due_date", equalTo(null))),
               hasItem(Matchers.<Debt>hasProperty("is_in_payment_plan", equalTo(true)))));
      
      assertThat(debts, allOf(hasItem(Matchers.<Debt>hasProperty("id", equalTo(2))), 
               hasItem(Matchers.<Debt>hasProperty("amount", equalTo(4920.34))),
               hasItem(Matchers.<Debt>hasProperty("remaining_amount", equalTo(607.67))),
               hasItem(Matchers.<Debt>hasProperty("next_payment_due_date", equalTo("2020-08-12"))),
               hasItem(Matchers.<Debt>hasProperty("is_in_payment_plan", equalTo(true)))));
   }

   @Test
   public void testCalcNextPaymentDate() throws ParseException
   {
      assertEquals(debtsClient.calcNextPaymentDate( "2020-01-01", "2020-08-08", DataManager.FREQUENCY_BI_WEEKLY ), "2020-08-12");
      
      assertEquals(debtsClient.calcNextPaymentDate( "2020-08-01", "2020-08-08", DataManager.FREQUENCY_WEEKLY ), "2020-08-15");
      
      assertEquals(debtsClient.calcNextPaymentDate( "2020-08-01", "2020-08-15", DataManager.FREQUENCY_WEEKLY ), "2020-08-22");
   }
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testGetPayments()
   {
      Map<Integer, Payment> payMap = debtsClient.getPayments();
      
      assertThat(payMap.size(), is(equalTo(4)));
      
      assertThat(payMap, (Matcher)Matchers.hasEntry(is(new Integer(0)),
               allOf(Matchers.<PaymentPlan>hasProperty("amount", equalTo(102.5)),
                        Matchers.<PaymentPlan>hasProperty("last_payment_date", equalTo("2020-10-29")))));
      
      assertThat(payMap, (Matcher)Matchers.hasEntry(is(new Integer(3)),
               allOf(Matchers.<PaymentPlan>hasProperty("amount", equalTo(3690.255)),
                        Matchers.<PaymentPlan>hasProperty("last_payment_date", equalTo("2020-08-15")))));
   }
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testGetPaymentPlans()
   {
      Map<Integer, PaymentPlan> ppMap = debtsClient.getPaymentPlans();
      
      assertThat(ppMap.size(), is(equalTo(4)));
      
      assertThat(ppMap, Matchers.hasEntry(is(new Integer(0)),
               allOf(Matchers.<PaymentPlan>hasProperty("debt_id", equalTo(0)),
                        Matchers.<PaymentPlan>hasProperty("installment_amount", equalTo(51.25)),
                        Matchers.<PaymentPlan>hasProperty("installment_frequency", equalTo("WEEKLY")),
                        Matchers.<PaymentPlan>hasProperty("start_date", equalTo("2020-09-28")),
                        Matchers.<PaymentPlan>hasProperty("amount_to_pay", equalTo(102.5)))));
      
      assertThat(ppMap, Matchers.hasEntry(is(new Integer(2)),
               allOf(Matchers.<PaymentPlan>hasProperty("debt_id", equalTo(2)),
                        Matchers.<PaymentPlan>hasProperty("installment_amount", equalTo(1230.085)),
                        Matchers.<PaymentPlan>hasProperty("installment_frequency", equalTo("BI_WEEKLY")),
                        Matchers.<PaymentPlan>hasProperty("start_date", equalTo("2020-01-01")),
                        Matchers.<PaymentPlan>hasProperty("amount_to_pay", equalTo(4920.34)))));
   }
   
}
