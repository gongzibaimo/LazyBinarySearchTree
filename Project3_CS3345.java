/**
 * Project3
 * Student Name: Jiao Huang
 * Student Id: jxh170330
 * Course: CS3345.003
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Project3_CS3345 {
    // convert boolean to camelcase
    static String camelCase(boolean b){
        String result = String.valueOf(b);
        return Character.toUpperCase(result.charAt(0)) + result.substring(1);
    }
    public static void main(String[] args){
        // check if the argument is suitable
        if(args.length == 2){
            // read file
            Scanner fIn = null;
            // create a outputfile
            File fOut = new File(args[1]);
            // print stuff into the output file
            PrintWriter writer = null;
            
            
            try{ 
                fIn = new Scanner(new File(args[0])); //you can put the input file and output file in the setting for args[0] and args[1]
                
                writer = new PrintWriter(fOut);
                // create an object
                LazyBinarySearchTree mTree = new LazyBinarySearchTree();
                
                // if there does not exist an output file, then create one
                if(!fOut.exists()){
                    fOut.createNewFile();
                }
                // read the input file line by line
                while(fIn.hasNextLine()){
                    String line = fIn.nextLine().trim();
                    
                    if((line.indexOf(mInsert) == 0)){
                        try {
                            // split the line to two part, divided from ":", e.g "insert:9", then devide to "insert" and "9"
                            int key = Integer.parseInt(line.split(":", 2)[1]);
                            try{
                                writer.println(camelCase(mTree.insert(key)));
                                try{
                                    // throw exception if there exist a space
                                    if(line.contains(" ")){
                                        throw new NumberFormatException();
                                    }
                                }
                                catch(NumberFormatException n){
                                    writer.println("Error: space detected");
                                }
                            }
                            catch(IllegalArgumentException e){
                                writer.println(e.getMessage());
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException ex){
                            writer.println("Error in Line: " + line);
                        }
                        
                    }
                    else if((line.indexOf(mDelete) == 0)){
                        //int key = Integer.parseInt(line.substring(line.indexOf(mDelete) + mDelete.length()));
                        int key = Integer.parseInt(line.substring(mDelete.length()+1));
                        try{
                            writer.println(camelCase(mTree.delete(key)));
                        }
                        catch(IllegalArgumentException e){
                            writer.println(e.getMessage());
                        }
                    }
                    else if((line.indexOf(mPrint) == 0)){
                        writer.println(mTree);
                    }
                    else if((line.indexOf(mFindMin) == 0)){
                        writer.println(mTree.findMin());
                    }
                    else if((line.indexOf(mFindMax) == 0)){
                        writer.println(mTree.findMax());
                    }
                    else if((line.indexOf(mHeight) == 0)){
                        writer.println(mTree.height()-1);
                    }
                    else if((line.indexOf(mSize) == 0)){
                        writer.println(mTree.size());
                    }
                    else if((line.indexOf(mContain) == 0)){
                        //int key = Integer.parseInt(line.substring(line.indexOf(mContain) + mContain.length()));
                        int key = Integer.parseInt(line.substring(mContain.length()+1));
                        try{
                            writer.println(camelCase(mTree.contains(key))); 
                        }
                        catch(IllegalArgumentException e){
                            writer.println(e.getMessage());
                        }
                    }
                    else{
                        writer.println("Error in Line: " + line);
                    }
                }
                fIn.close();;
                writer.close();
                //....
                System.out.println("Output written to file: " + args[1]);
            }
            catch(FileNotFoundException f){
                System.out.println(f.getMessage());
            }
            catch(IOException i){
                i.printStackTrace();
            }
        }
        else{
            System.out.println("Error: Cannot find the input file");
        }
    }
    
    // strings to hold the strings in the file such as "Insert" of "Insert:9"
    private static final String mInsert = "Insert";
    private static final String mDelete = "Delete";
    private static final String mPrint = "PrintTree";
    private static final String mFindMin = "FindMin";
    private static final String mFindMax = "FindMax";
    private static final String mHeight = "Height";
    private static final String mSize = "Size";
    private static final String mContain = "Contains";
    
    
}
