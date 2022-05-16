package code;

import java.util.*;
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.io.*;

public class Transaction implements Serializable{
    
    private int transactionID;
	private long transactionDate;
	private int transactionAmount;
	private String transactionType;
	
	public Transaction(int tID, String tDate, int tAmount,String tType ){
		
		this.transactionID = tID;
		
		this.transactionAmount = tAmount;
		this.transactionType = tType;
		try {
		Date datef = new SimpleDateFormat("MM/dd/yyyy").parse(tDate);
		this.transactionDate = datef.getTime()/1000;
		}
		catch(ParseException e) {
			System.out.println("Incorrect date format in uploaded files. Please use format 'MM/dd/yyyy'.");
			e.printStackTrace();
		}
	}
	
	public int getID() {
		return this.transactionID;
	}
	public long getDate() {
		return this.transactionDate;
	}
	public int getAmount() {
		return this.transactionAmount;
	}
	public String getType() {
		return this.transactionType;
	}

 
}
