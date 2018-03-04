import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.FSDirectory;

import java.util.ArrayList;

public class Search {
    private org.apache.lucene.analysis.Analyzer analyzer = new StandardAnalyzer();
    private IndexSearcher searcher;
    //StandardAnalyzer is built in lucene "standard analyzer"，
    //The original sentence in accordance with a space for word segmentation
    //All capital letters can be converted to lowercase letters
    //You can remove some useless words, such as "is", "the", "are" and other words, but also delete all the punctuation

    public void readIndex(int index){
        try{
            //Open the storage location
            DirectoryReader reader =  DirectoryReader.open(FSDirectory.open(Paths.Index_Path));
            //Create a searcher
            searcher = new IndexSearcher(reader);
            if (index == 1){
                searcher.setSimilarity(new BM25Similarity());
            }else if(index == 2){
                searcher.setSimilarity(new ClassicSimilarity());
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }
    public ArrayList<Integer> search(String queryStr, int num){
        //Find different fields on different
        String[] a = {"Title","Bibliography","Author","content"};
        QueryParser parser = new MultiFieldQueryParser(a,analyzer);
        ArrayList<Integer> ids = new ArrayList<>();

        try{
            Query query = parser.parse(queryStr);
            ScoreDoc[] hits = searcher.search(query, num).scoreDocs;
            for (int i = 0; i < hits.length; i++) {
                Document hitDoc = searcher.doc(hits[i].doc);
                int idStr = Integer.parseInt(hitDoc.get("id").trim());
                ids.add(idStr);
            }
            //System.out.println(ids);

        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        return ids;

    }
}
