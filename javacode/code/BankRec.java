package code;

import java.util.*;
import java.io.*;

public class BankRec implements Serializable{
    
    private ArrayList<Transaction> transactions;
    private Date reconDate;		
    private int startBalance;
    private int endBalance;
    private int totalDeposits;
    private int totalWithdrawals;
    private byte[] bankID;


    
}
