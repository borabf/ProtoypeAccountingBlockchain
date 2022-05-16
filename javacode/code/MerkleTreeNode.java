package code;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.io.Serializable;
import java.util.*;

public class MerkleTreeNode implements Serializable{
    private Transaction tr;
	private String phash;
    private transient MessageDigest hasher;
    private byte[] shaHash;
    private MerkleTreeNode left;
    private MerkleTreeNode right;

    // Constructor when we create parent nodes
    public MerkleTreeNode(String hash, MerkleTreeNode left, MerkleTreeNode right){
        this.phash = hash;
        try{
            this.hasher = MessageDigest.getInstance("SHA-256");
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            System.exit(1);
        }
        this.shaHash = getSha(hash);
        this.left = left;
        this.right = right;
    }
    
    public MerkleTreeNode(Transaction t){
        this.tr = t;
        
        try{
            this.hasher = MessageDigest.getInstance("SHA-256");
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            System.exit(1);
        }
        this.setShaHashHelperT(t.getID(),t.getDate(),t.getAmount(),t.getType() );
        
        this.left = null;
        this.right = null;
    }

 
    private void setShaHashHelperT(int tID, long tDate, int tAmount,String tType){
        
    	String toConcat = Integer.toString(tID) + String.valueOf(tDate) + Integer.toString(tAmount) + tType;
    	System.out.println(toConcat);
    	System.out.println();

        this.shaHash = getSha(toConcat);
        System.out.println(toHexStringFromSha(shaHash));
    	System.out.println();
    }

    /**
     * Taken from https://www.geeksforgeeks.org/sha-256-hash-in-java/ 
     * @param input input to create sha256 hash. 
     * @return byte[] hashed output. 
     */
    public byte[] getSha(String input){
        return this.hasher.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Taken from https://www.geeksforgeeks.org/sha-256-hash-in-java/
     * @param hash byte[] to unhash. 
     * @return String of original message. 
     */
    public String toHexStringFromSha(byte[] hash){
        BigInteger number = new BigInteger(hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while(hexString.length()<32){
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }


    //Getters and setters

    public String getPhash() {
    	return this.phash;
    }
    public int gettID(){
        return this.tr.getID();
    }
    public long gettDate(){
        return this.tr.getDate();
    }
    public int gettAmount(){
        return this.tr.getAmount();
    }
    public String gettType(){
        return this.tr.getType();
    }
    public String getStringHash(){
        return toHexStringFromSha(this.shaHash);
    }

    public MerkleTreeNode getLeftChild(){
        return this.left;
    }

    public MerkleTreeNode getRightChild(){
        return this.right;
    }


    public byte[] getShaHash(){
        return this.shaHash;
    }
}
