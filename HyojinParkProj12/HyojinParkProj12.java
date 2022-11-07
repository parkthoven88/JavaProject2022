
/**
 * Proj12
 * 
 * Account Class
 * Modify bank Account family claases from Project Eleven.
 * You May modify the following class diagram if it is needed
 * This time, handle Exception in bank business.
 * Two new class shall be added: IllegalAmountException, NoSufficientFundsException
 * 
 * 
 * Hyojin Park
 * 05/06/2021
 */

import java.util.ArrayList;
import java.util.Scanner;


class Account {
    private int id;
    private double balance;


    public Account() {
        id = 0;
        balance = 0.0;
    }

    public Account(int id, double balance) {
        this.id = id;
        
        if(balance>0.0) // if the balance is valid
        this.balance = balance;
    }
    
    public void setBalance(double newBalance)
    {
        this.balance = newBalance;
    }
    
    public double getBalance()
    {
        return balance;
    }
    
    public void setId(int newId)
    {
        this.id=newId;
    }
    
    public double getId()
    {
        return id;
    }

    public void deposit(double amount) throws IllegalAmountException
    {
        if(amount > 0){
            balance += amount;
        }
        else{
            throw new IllegalAmountException("This is not valid amount for deposit. You need to enter positive amount.");
        }
    }

    public void withdraw(double amount) throws NoSufficientFundsException
    {
        double tempBalance = this.getBalance();
        if (amount > 0 && amount <= this.balance){
            balance = balance - amount;
        }
        else{
            throw new NoSufficientFundsException(tempBalance);
            }
    }
    
    public String toString(){
        return "accounts[" + this.id + "] balance is $" + this.balance;
    }
   

}

class CheckingAccount extends Account{

    public CheckingAccount(int id, double balance){
        super(id,balance);
    }
    
    public void withdraw(double amount) throws NoSufficientFundsException
    {
        double tempBalance = super.getBalance();
        if(super.getBalance()-amount>=0.00){
            super.withdraw(amount);
        }
        else{
            throw new NoSufficientFundsException(tempBalance); 
        }
        
     }
    
     public String toString() {
         return "Your checking "+ super.toString();
    }
    
}
    
class SavingAccount extends Account{
    private double interestRate;

    public SavingAccount(double rate){
        interestRate= rate;
   
    }
    
    public SavingAccount(int id, double balance){
        super(id, balance);
    }
    
    public void withdraw(double amount) throws NoSufficientFundsException
    {
        double tempBalance = super.getBalance();
        if(super.getBalance()-amount>=500.00){
            super.withdraw(amount);
        }
        else{
            System.out.println("Your minimum balance is $500.0.");
            throw new NoSufficientFundsException(tempBalance);
        }
    }    
    
    public void setInterestRate(double newInterestRate){
        this.interestRate = newInterestRate*0.01; //convert to decimal point from %
    }
    
    public double getInterestRate(){
       return this.interestRate;
    }
    
    public void addInterest() throws IllegalAmountException {
        double interest = super.getBalance() * this.interestRate; //calculate interest specified
        super.deposit(interest); //add interest to current balance
    }
    
    public String toString(){
        return " \nInterest rate: " +
            String.format("%.3f", this.getInterestRate())+ " \nYour saving "+ super.toString();
            
    }
}

class IllegalAmountException extends Exception
{
    private String message;
    
    public IllegalAmountException(String message)
    {
        this.message = message;
    }
    
    public String getMassage()
    {
        return message;
    }
}

class NoSufficientFundsException extends Exception
{
    private double b;
    private String message;
    
    public NoSufficientFundsException(String message)
    {
        this.message = message;
    }
    
    public String getMassage()
    {
        return "This is not valid amount. Your current balance is $";
    }
    
    public NoSufficientFundsException(double b)
    {
        this.b=b;
    }

    public double getBalance()
    {
        return b;
    }
}

public class HyojinParkProj12{

    public static void main(String[] args){
        try{
            //creating Account class array of size 5
            Account accounts[] = new Account[4];
        
            //creating new accounts with assigned object
            accounts[0] = new CheckingAccount(0, 6000.00);
            accounts[1] = new CheckingAccount(1, 200.00);
            accounts[2] = new SavingAccount(2, 7000.00);
            accounts[3] = new SavingAccount(3, 980.00);
            
            //Implement account[0] - CheckingAccount
            //deposit
            System.out.println("\n\n*****Thank for coming FHSU bank.*****\n"+ accounts[0].toString());
            System.out.println("\nHow much would you like to deposit?");
            Scanner input = new Scanner(System.in);
            double deposit0 = input.nextDouble();
            accounts[0].deposit(deposit0);
            System.out.println(accounts[0].toString());
            //withdraw
            System.out.println("\nHow much would you like to withdraw?");
            double withdraw0 = input.nextDouble();
            accounts[0].withdraw(withdraw0);
            System.out.println(accounts[0].toString());
    
            //Implement account[2] - SavingAccount
            System.out.println("\nPlease input your interest rate(%)");
            double interest2 =input.nextDouble();
            ((SavingAccount)accounts[2]).setInterestRate(interest2);
            ((SavingAccount)accounts[2]).addInterest();
            System.out.println(accounts[2].toString());
            //deposit
            System.out.println("\nHow much would you like to deposit?");
            double deposit2 = input.nextDouble();
            accounts[2].deposit(deposit2);
            System.out.println(accounts[2].toString());
            //withdraw
            System.out.println("\nHow much would you like to withdraw?");
            double withdraw2 = input.nextDouble();
            accounts[2].withdraw(withdraw2);
            System.out.println(accounts[2].toString());
            
            //Implement account[1] - CheckingAccount
            System.out.println("\n"+accounts[1].toString());
            System.out.println("If you deposit $-1,000, ");
            accounts[1].deposit(-1000.0); // Invalid deposit amount
            System.out.println(accounts[1].toString());
            
            //Implement account[3] - SavingAccount
            ((SavingAccount)accounts[3]).setInterestRate(4.5);
            ((SavingAccount)accounts[3]).addInterest();
            System.out.println(accounts[3].toString());
            System.out.println("If you withdraw $600, ");
            accounts[3].withdraw(600);// amount is less than minimum balance

            
        }
        catch(IllegalAmountException ex)
        {
            System.out.println(ex.getMassage());
        }
        catch(NoSufficientFundsException e)
        {
            System.out.println(e.getMassage() + e.getBalance());
        }
        
    }
}


/*
 *** Sample Run***

*****Thank for coming FHSU bank.*****
Your checking accounts[0] balance is $6000.0

How much would you like to deposit?
300
Your checking accounts[0] balance is $6300.0

How much would you like to withdraw?
5000
Your checking accounts[0] balance is $1300.0

Please input your interest rate(%)
3
 
Interest rate: 0.030 
Your saving accounts[2] balance is $7210.0

How much would you like to deposit?
340
 
Interest rate: 0.030 
Your saving accounts[2] balance is $7550.0

How much would you like to withdraw?
7000
 
Interest rate: 0.030 
Your saving accounts[2] balance is $550.0

Your checking accounts[1] balance is $200.0
If you deposit $-1,000, 
This is not valid amount for deposit. You need to enter positive amount.


*****Thank for coming FHSU bank.*****
Your checking accounts[0] balance is $6000.0

How much would you like to deposit?
500
Your checking accounts[0] balance is $6500.0

How much would you like to withdraw?
3000
Your checking accounts[0] balance is $3500.0

Please input your interest rate(%)
4
 
Interest rate: 0.040 
Your saving accounts[2] balance is $7280.0

How much would you like to deposit?
300
 
Interest rate: 0.040 
Your saving accounts[2] balance is $7580.0

How much would you like to withdraw?
7400
Your minimum balance is $500.0.
This is not valid amount. Your current balance is $7580.0



*/

