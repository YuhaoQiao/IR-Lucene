import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try{
            Index index = new Index();
            index.createIndex();
            //Create index;
            Search search = new Search();
            search.readIndex(1);
            //Read index;
            ArrayList<ArrayList<Integer>> base = ReadFiledata.readQrelScore();
            ArrayList<String> queries =  ReadFiledata.readQueries();
            //Read baseline， queries;
            Evaluate evaluate = new Evaluate();
            double averageMap = evaluate.avgMAP(search,base,queries);
            double averageRecall = evaluate.avgRecall(search,base,queries);
            //Calculate MAP, Recall;
            System.out.println("MAP of BM25 is: "+averageMap);
            System.out.println("Recall of BM25 is: "+averageRecall);

            Search search1 = new Search();
            search1.readIndex(2);
            ArrayList<ArrayList<Integer>> base1 = ReadFiledata.readQrelScore();
            ArrayList<String> queries1 =  ReadFiledata.readQueries();
            Evaluate evaluate1 = new Evaluate();
            double averageMap1 = evaluate1.avgMAP(search1,base1,queries1);
            double averageRecall1 = evaluate1.avgRecall(search1,base1,queries1);
            System.out.println("MAP of VSM is: "+averageMap1);
            System.out.println("Recall of VSM is: "+averageRecall1);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
