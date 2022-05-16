package code;


import java.util.*;
import java.io.*;

public class CompanyRec implements Serializable{
    
    private ArrayList<Transaction>  transactions;
    private Date recordDate;
    private int startBalance;
    private int endBalance;
    private int deposits; 
    private int expenses;
    private byte[] employeeID;

}
