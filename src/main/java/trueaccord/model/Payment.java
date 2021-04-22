package trueaccord.model;

public class Payment
{
   double amount = 0d;
   int payment_plan_id = 0;
   String last_payment_date = null;
   public double getAmount()
   {
      return amount;
   }
   public void setAmount( double amount )
   {
      this.amount = amount;
   }
   public void addAmount( double amount )
   {
      this.amount += amount;
   }
   public int getPayment_plan_id()
   {
      return payment_plan_id;
   }
   public void setPayment_plan_id( int payment_plan_id )
   {
      this.payment_plan_id = payment_plan_id;
   }
   public String getLast_payment_date()
   {
      return last_payment_date;
   }
   public void setLast_payment_date( String last_payment_date )
   {
      this.last_payment_date = last_payment_date;
   }
}
