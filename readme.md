# Debts Client

It is a work assignment for handling the sample customer debt data.

## step-by-step instruction
1. Unzip henry_kang_trueaccord.zip file
2. Run maven command to build
on the terminal:
```bash
cd trueaccoud
mvn clean install
```
3. Check the test result
```bash
Results :

Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
```
4. Run the application
```bash
java -cp target/trueaccord-0.0.1-SNAPSHOT-jar-with-dependencies.jar trueaccord.DebtsClient
```
5. Check the result
```bash
Here is the Result, Debt List : 
id : 0, amount : 123.46, is_in_payment_plan : true, remaining_amount : 0.0, next_payment_due_date : null
id : 1, amount : 100.0, is_in_payment_plan : true, remaining_amount : 50.0, next_payment_due_date : 2020-08-15
id : 2, amount : 4920.34, is_in_payment_plan : true, remaining_amount : 607.67, next_payment_due_date : 2020-08-12
id : 3, amount : 12938.0, is_in_payment_plan : true, remaining_amount : 622.415, next_payment_due_date : 2020-08-22
id : 4, amount : 9238.02, is_in_payment_plan : false, remaining_amount : 9238.02, next_payment_due_date : null
```

## development process
1. Based on the requirements, the DataManager class has been constructed to load data from the data source(restful api)
   1. Developed loadData to access the HTTP API endpoints and read the data

2. Created model classes (Debt, PaymentPlan, and Payment)

3. Made loadDebts, loadPaymentPlans, and loadPayments methods in the DataManager
   1. loadDebts : load the debts data from https://my-json-server.typicode.com/druska/trueaccord-mock-payments-api/debts and convert json data to Debt object and add it to List.
   2. loadPaymentPlans : load the payment plan data from https://my-json-server.typicode.com/druska/trueaccord-mock-payments-api/payment_plans and convert json data to PaymentPlan and save it to Map collection data.
   3.  loadPayments : load the payment data from https://my-json-server.typicode.com/druska/trueaccord-mock-payments-api/payments and convert json data and merge by payment_plan_id and save it to Map collection data.

4. Made loadAllData method to DebtsClient class, which invokes loadDebts, loadPaymentPlans and loadPayments methods of DataManager.

5. Made calcNextPaymentDate method, which calculates the next payment date using <em>start_date</em> and <em>installment_frequency</em> of payment_plans. 

6. Made calcDebt method, which calculates the <em>remaining_amount</em> and set <em>is_in_payment_plan</em> and <em>next_payment_due_date</em> from calcNextPaymentDate method

7. Made getAllDebtData method, which prints out the debt list and returns Debts list.

8. Added two test classes(8 test cases) for DebtsClient and DataManager
