package trueaccord.model;

public class Debt
{
   int id = 0;
   double amount = 0d;
   boolean is_in_payment_plan = false;
   double remaining_amount = 0d;
   String next_payment_due_date = null;
   public int getId()
   {
      return id;
   }
   public void setId( int id )
   {
      this.id = id;
   }
   public double getAmount()
   {
      return amount;
   }
   public void setAmount( double amount )
   {
      this.amount = amount;
   }
   public boolean isIs_in_payment_plan()
   {
      return is_in_payment_plan;
   }
   public void setIs_in_payment_plan( boolean is_in_payment_plan )
   {
      this.is_in_payment_plan = is_in_payment_plan;
   }
   public double getRemaining_amount()
   {
      return remaining_amount;
   }
   public void setRemaining_amount( double remaining_amount )
   {
      this.remaining_amount = remaining_amount;
   }
   public String getNext_payment_due_date()
   {
      return next_payment_due_date;
   }
   public void setNext_payment_due_date( String next_payment_due_date )
   {
      this.next_payment_due_date = next_payment_due_date;
   }
   
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      
      sb.append( "id : " ).append( id );
      sb.append( ", amount : " ).append( amount );
      sb.append( ", is_in_payment_plan : " ).append( is_in_payment_plan );
      sb.append( ", remaining_amount : " ).append( remaining_amount );
      sb.append( ", next_payment_due_date : " ).append( next_payment_due_date );
      
      return sb.toString();
      
   }
}
