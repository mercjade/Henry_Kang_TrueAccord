package trueaccord.model;

public class PaymentPlan
{
   int id = 0;
   int debt_id = 0;
   double amount_to_pay = 0.0;
   double installment_amount = 0.0;
   String installment_frequency = null;
   String start_date = null;
   public int getId()
   {
      return id;
   }
   public void setId( int id )
   {
      this.id = id;
   }
   public int getDebt_id()
   {
      return debt_id;
   }
   public void setDebt_id( int debt_id )
   {
      this.debt_id = debt_id;
   }
   public double getAmount_to_pay()
   {
      return amount_to_pay;
   }
   public void setAmount_to_pay( double amount_to_pay )
   {
      this.amount_to_pay = amount_to_pay;
   }
   public double getInstallment_amount()
   {
      return installment_amount;
   }
   public void setInstallment_amount( double installment_amount )
   {
      this.installment_amount = installment_amount;
   }
   public String getInstallment_frequency()
   {
      return installment_frequency;
   }
   public void setInstallment_frequency( String installment_frequency )
   {
      this.installment_frequency = installment_frequency;
   }
   public String getStart_date()
   {
      return start_date;
   }
   public void setStart_date( String start_date )
   {
      this.start_date = start_date;
   }
}
