package code;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.time.Instant;
import java.security.SecureRandom;
import java.io.*;
import java.util.*;

/**
 * This block class represents one block on the blockchain. 
 */
public class Block implements Serializable{
    // Instance vars: 
    private MerkleTree currMerk;
    private Data currTree;
    private byte[] prevHeader;
    private byte[] rootHash;
    private long time; 
    private BigInteger target;
    private byte[] nonce;
    private byte[] shaHash;
    private String serializedFileName;
    private long unixStart;

    /**
     * Constructor.  
     * @param merk merkle tree to save to 
     * @param prev previous header byte array
     */

    public Block(Data d, byte[] prev){
        this.serializedFileName = "serializedfile.ser";
        this.currTree = d;
        this.prevHeader = prev;
        byte[] twoByte = {2};
        this.target = new BigInteger(twoByte);
        this.target = target.pow(256);
        this.target = target.divide(new BigInteger(twoByte));
        this.unixStart = d.getStart();
        generateHeaderT();
    }
    

    /**
     * Constructor. 
     * @param hash hash string
     * @param prev previous header hash. 
     */
    public Block(String hash, String prev){
        this.serializedFileName = "serializedfile.ser";
        byte[] temp = {0};
        this.rootHash = temp;
        this.nonce = temp;
        shaHash = hash.getBytes();
        prevHeader = prev.getBytes();
    }

    /**
     * Generates the header 
     */

    public void generateHeaderT(){
        this.rootHash =  currTree.getMerkleRootByteArray();
        this.time = Instant.now().getEpochSecond();
        this.nonce = getNonce(rootHash,target);
    }

    /**
     * Generates the nonce given a target. Target varies the diffulcty of generating a nonce within the parameters. 
     * 
     * @param root Root byte array. 
     * @param target target big integer. 
     * @return byte[] for t
     */
    public byte[] getNonce(byte[] root, BigInteger target){
        boolean check = true;
        byte[] non = new byte[32];
        // mining function 
        while(check){
            // get random 32 bytes
            SecureRandom rand = new SecureRandom();
            rand.nextBytes(non);
            // create array to concatonate teh root and nonce into. 
            byte[] concatonatedByteArray = new byte[root.length + non.length];
            int i = 0;
            int j = 0;
            // add nonce to the array
            for(; i<concatonatedByteArray.length && j<non.length; i++, j++){
                concatonatedByteArray[i] = non[j];
            }
            j = 0;
            // add root hash to array. 
            for(; j<root.length && i<concatonatedByteArray.length; i++, j++){
                concatonatedByteArray[i] = root[j];
            }
            // hash the concated byte array. 
            //here
            try{
                MessageDigest hashMe = MessageDigest.getInstance("SHA-256");
                shaHash = hashMe.digest(concatonatedByteArray);
            }catch(NoSuchAlgorithmException e){
                e.printStackTrace();
                System.exit(1);
            }
            BigInteger nonceBigInt = new BigInteger(shaHash);
            // Compare byte array. Ensure it's not negative. 
            if(nonceBigInt.compareTo(target)<=0&&nonceBigInt.compareTo(new BigInteger("0"))>0){
                check = false;
            }
        }  
        return non;
    }

    /**
     * Gets the Merkle Tree stored in this block. 
     * @return MerkleTree this.currMerk
     */
    public MerkleTree getTree(){
        return this.currMerk;
    }
    public Data getData(){
        return this.currTree;
    }
    public long getUnix(){
        return this.unixStart;
    }
    /**
     * Generates a hex string from a byte array hash. 
     * @param hash hash array to create a hex string from. 
     * @return String hex string generated from byte array. 
     */
    public String toHexStringFromSha(byte[] hash){
        BigInteger number = new BigInteger(hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while(hexString.length()<32){
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    /**
     * Prints the header. 
     */
    public void printHeader(){
        System.out.println("BEGIN HEADER");
        System.out.println(toHexStringFromSha(prevHeader));
        System.out.println(toHexStringFromSha(shaHash));
        System.out.println(time);
        System.out.println(target);
        System.out.println(getBigInteger(nonce));
        System.out.println("END HEADER");
    }

    /**
     * Gets the  big integer version of a byte string. 
     * @param byteString byte array to generate a big integer version of. 
     * @return BigInteger version of the given byte string. 
     */
    public BigInteger getBigInteger(byte[] byteString){
        return new BigInteger(byteString);
    }

    /**
     * Generates the header strings. 
     * @return String header as a string.  
     */
    public String getHeaderStringGen(){
        String headerString="";
        headerString+="BEGIN HEADER\n";
        headerString+="Previous Header: ";
        headerString+=toHexStringFromSha(prevHeader)+"\n";
        headerString+= "Merkle Hash: ";
        headerString+=toHexStringFromSha(rootHash)+"\n";
        headerString+= "Block Hash: ";
        headerString+=toHexStringFromSha(shaHash)+"\n";
        headerString+= "Time: ";
        headerString+=time+"\n";
        headerString+="Target: ";
        headerString+=target+"\n";
        headerString+="Nonce: ";
        headerString+=getBigInteger(nonce)+"\n";
        headerString+="END HEADER";
        return headerString;
    }

    //Getters and setters: 

    public byte[] getHeaderHash(){
        return shaHash;
    }

    public String getHeaderHashStr(){
        return toHexStringFromSha(this.shaHash);
    }

    public String getPrevHeaderStr(){
        return toHexStringFromSha(this.prevHeader);
    }

    public byte[] getPrevHeader(){
        return prevHeader;
    }

    /**
     * Get the concatonated array. Copy of the above. Used to verify hash validity. 
     * @return byte[] with the concatoneted array. 
     */
    public byte [] getConcatArray(){
        byte[] concatonatedByteArray = new byte[rootHash.length + nonce.length];
        int i = 0;
        int j = 0;
        for(; j<rootHash.length && i<concatonatedByteArray.length; i++, j++){
            concatonatedByteArray[i] = nonce[j];
        }
        j = 0;
        for(; i<concatonatedByteArray.length && j<nonce.length; i++, j++){
            concatonatedByteArray[i] = rootHash[j];
        }
        
        try{
            MessageDigest hashMe = MessageDigest.getInstance("SHA-256");
            return hashMe.digest(concatonatedByteArray);
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public void serializeMe(){
        try{
            FileOutputStream fout = new FileOutputStream(this.serializedFileName);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            fout.close();
            oos.close();
        }catch(IOException e){
            System.out.println("Issue serializing data in the block.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
