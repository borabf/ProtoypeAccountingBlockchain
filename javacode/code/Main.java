package code;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.file.Paths;
import java.io.*;
import java.text.ParseException;  
import java.text.SimpleDateFormat;  

// C:\Users\borab\Desktop\inputs.txt
public class Main {
    public static void main(String[] args){
        ArrayList<String> files = new ArrayList<String>();
        ArrayList<Block> blocks = new ArrayList<Block>();
        // Get the file with the list of files
        System.out.println("Please enter the name of the file: ");
        Scanner scan = new Scanner(System.in);
        String filePath = scan.nextLine();
        System.out.println("Thank you for entering a file name. ");
        scan.close();
        try{
            Scanner fileScanner = new Scanner(new File(filePath));
            while(fileScanner.hasNext()){
                System.out.println("Reading...");
                String fileToAdd = fileScanner.nextLine();
                files.add(fileToAdd);
                System.out.println("File added: " + fileToAdd);
            }
            fileScanner.close();
            System.out.println("Read all files.");
        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("Could not find a file that was included. Please check your paths and try again");
            System.exit(1);
        }
        
        
        byte[] prevHeader = {0};
        
        
        // Create the trees and blocks for file
        	for(String i : files){
        		Data inData = new Data(i);
        		Block currBlock = new Block(inData, prevHeader);
        		blocks.add(currBlock);
        		prevHeader = currBlock.getHeaderHash();
        	}
        	System.out.println(blocks.size());
    }

   
    public static void printBlock(Block b, Data d){
        System.out.println("BEGIN BLOCK");
        b.printHeader();
        System.out.println(d.getNodeStringT());
        System.out.println("END BLOCK");
        System.out.println("");
    }

    public static String getBlockString(Block b, Data d){
        String blockString = "";
        blockString +="BEGIN BLOCK\n";
        blockString+=b.getHeaderStringGen()+"\n";
        blockString+=d.getNodeStringT();
        blockString+="END BLOCK\n\n";
        return blockString;
    }
    public int findNode(String s, ArrayList<Block> list){
    	int blockIndex = 0;
    	long unixDate;
    	try {
    		Date datef = new SimpleDateFormat("MM/dd/yyyy").parse(s);
    		unixDate = datef.getTime()/1000;
    		for(int i = 0; i < list.size() -1; i++){
        		if(list.get(i).getUnix() < unixDate && list.get(i+1).getUnix() > unixDate) {
        			blockIndex = i;
        		}
    		}
    	}
    	catch(ParseException e) {
    		System.out.println("Incorrect date format in uploaded files. Please use format 'MM/dd/yyyy'.");
    		e.printStackTrace();
    	}
    		
    	return blockIndex;
    	
    }
    
}
