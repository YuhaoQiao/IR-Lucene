import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadFiledata {

    static List<Document> Documentfiles(){
        try{
            ArrayList<Document> docFiles = new ArrayList<>();
            //ArrayList is an important class in the Java collection framework.
            // It inherits from AbstractList and implements the List interface.
            // It is a variable-length collection that provides functions for adding, deleting,
            List<String> line = Files.readAllLines(Paths.Document_File);
            String linetext = String.join(" ", line);
            linetext = linetext.replace("?", "");
            //Remove "?"
            String articles[] = linetext.split(".I");
            //Divide ".I" from linetext;

            int i = 1;
            while(i<=1400){
                String doclines[] = articles[i].split(".T|.A|.B|.W");
                // Multiple delimiters, you can use | as a hyphen;
                Document document = new Document();

                document.id = doclines[0];
                document.Title = doclines[1];
                document.Author = doclines[2];
                document.Bibliography = doclines[3];
                document.content = doclines[4];
                docFiles.add(document);
                i++;
            }

            return docFiles;
        }catch (IOException e){
            Logger.getGlobal().log(Level.SEVERE,"read document failed");
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    static ArrayList<String> readQueries(){
       try{
           List<String> line = Files.readAllLines(Paths.Query_File);
           //java8 read the file only one line of code;
           String linetext =  String.join(" ", line);
           linetext = linetext.replace("?", "");
           String lines[] = linetext.split("\\.I.*?.W");
           // ., | And * escape characters must be added \\;
           ArrayList<String> cranqueries = new ArrayList<>(Arrays.asList(lines));
           // To convert an array to a List object, this method returns an object of type ArrayList that is not a static java.util.
           // ArrayList class but a static inner class of the Arrays class
           cranqueries.remove(0);
           return cranqueries;
       }catch (Exception e){
           Logger.getGlobal().log(Level.SEVERE,"read query failed");
           e.printStackTrace();
           System.exit(0);
       }
       return null;
    }

    static ArrayList<ArrayList<Integer>> readQrelScore(){
        ArrayList<ArrayList<Integer>> cranqrel = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(Paths.Query_Score_File.toFile()));
            //When a BufferedReader reads a text file,
            // it first tries to read the character data from the file into the buffer,
            // and then read it from the buffer after using the read () method.
            //If the buffer data is insufficient, will read again from the file
            String line = null;
            int id = 1;
            ArrayList<Integer> Lists = new ArrayList<>();
            while((line = reader.readLine()) != null){
                // Suppose the input stream from a file, and then read each line of output until the read is completed;
                // ReadLine () with BufferedReader in Java IO;
                String[] string = line.split("\\s+");
                // Split this string according to the match of the given regular expression;
                // \\ s represents space, carriage return, line feed and other blank characters;
                // split("\\s+") Can achieve more than one blank cutting effect;
                int queryID = Integer.parseInt(string[0]);
                int docID = Integer.parseInt(string[1]);
                int relev = Integer.parseInt(string[2]);
                //Split queryID, docID, relev in cranqrel
                if (queryID!=id){
                    id = queryID;
                    cranqrel.add(Lists);
                    Lists = new ArrayList<>();
                }
                if (relev<= Paths.Boundary_value){
                    Lists.add(docID);
                }
            }
            cranqrel.add(Lists);

        } catch (IOException e){
            Logger.getGlobal().log(Level.SEVERE,"read relevance failed");
            e.printStackTrace();
            System.exit(0);
        }
        return cranqrel;
    }


}
