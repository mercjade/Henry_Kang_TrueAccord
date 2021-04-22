package trueaccord;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import trueaccord.model.Debt;
import trueaccord.model.Payment;
import trueaccord.model.PaymentPlan;

public class DebtsClient
{
   List<Debt>                debts        = null;
   Map<Integer, PaymentPlan> paymentPlans = null;
   Map<Integer, Payment>     payments     = null;

   public static final long  SEVEN_DAYS   = 7 * 24 * 60 * 60 * 1000L;

   public void loadAllData()
   {
      DataManager dm = new DataManager();
      debts = dm.loadDebts();
      paymentPlans = dm.loadPaymentPlans();
      payments = dm.loadPayments();
   }

   public List<Debt> getAllDebtData()
   {
      calcDebt();

      System.out.println( "\n\n\nHere is the Result, Debt List : " );
      for ( Debt debt : debts )
      {
         System.out.println( debt.toString() );
      }

      return debts;
   }

   public Map<Integer, PaymentPlan> getPaymentPlans()
   {
      return paymentPlans;
   }

   public void setPaymentPlans( Map<Integer, PaymentPlan> paymentPlans )
   {
      this.paymentPlans = paymentPlans;
   }

   public Map<Integer, Payment> getPayments()
   {
      return payments;
   }

   public void setPayments( Map<Integer, Payment> payments )
   {
      this.payments = payments;
   }

   public void calcDebt()
   {
      PaymentPlan paymentPlan = null;
      for ( Debt debt : debts )
      {
         paymentPlan = paymentPlans.get( debt.getId() );
         if ( paymentPlan != null )
         {
            debt.setIs_in_payment_plan( true );

            //use BigDecimal to fix roundup problem
            BigDecimal amtToPay = BigDecimal.valueOf( paymentPlan.getAmount_to_pay() );

            Payment payment = payments.get( paymentPlan.getDebt_id() );
            if ( payment != null )
            {
               amtToPay = amtToPay.subtract( BigDecimal.valueOf( payment.getAmount() ) );
            }
            debt.setRemaining_amount( amtToPay.doubleValue() );
            if ( debt.getRemaining_amount() > 0 )
            {
               try
               {
                  debt.setNext_payment_due_date( calcNextPaymentDate( paymentPlan.getStart_date(), payment.getLast_payment_date(), paymentPlan.getInstallment_frequency() ) );
               }
               catch ( ParseException e )
               {
                  e.printStackTrace();
               }
            }
            //false when there is no payment plan, or the payment plan is completed.
            else
            {
               debt.setNext_payment_due_date( null );
            }
         }
         else
         {
            debt.setRemaining_amount( debt.getAmount() );
            //false when there is no payment plan, or the payment plan is completed.
            debt.setNext_payment_due_date( null );
         }
      }
   }

   protected String calcNextPaymentDate( String strStartDate, String strLastPaymentDate, String frequency ) throws ParseException
   {
      SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd" );

      Date startDate = formatter.parse( strStartDate );
      Date lastPaymentDate = formatter.parse( strLastPaymentDate );

      long sTime = startDate.getTime();
      long lastTime = lastPaymentDate.getTime();

      // the new payment date should be later than the last payment date of payments data
      while ( sTime <= lastTime )
      {
         // WEEKLY, add 7 days.
         if ( frequency.equals( DataManager.FREQUENCY_WEEKLY ) )
            sTime += SEVEN_DAYS;
         // BI_WEEKLY, add 14 days.
         else if ( frequency.equals( DataManager.FREQUENCY_BI_WEEKLY ) )
            sTime += ( SEVEN_DAYS * 2 );
         else
            // should handle the error
            break;
      }

      Date newDate = new Date( sTime );
      return formatter.format( newDate );
   }

   public static void main( String[] args )
   {
      DebtsClient dc = new DebtsClient();
      dc.loadAllData();

      dc.getAllDebtData();
   }
}
