package code;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;



public class MerkleTree implements Serializable{
    private transient Scanner fileScanner;
    private transient String filePath;
    private MerkleTreeNode merkleRoot;
    private ArrayList<MerkleTreeNode> list;

}
