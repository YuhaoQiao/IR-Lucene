import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Index {
    void createIndex(){
        try{

            IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
            //Tokenizes based on sophisticated grammar that recognizes e-mail addresses, acronyms, etc.
            //lowercases and removes stop words (optional);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            Directory dir = FSDirectory.open(Paths.Index_Path);
            //Local file storage
            IndexWriter writer = new IndexWriter(dir,config);
            //Create IndexWriter for index file write
            List<Document> documents = ReadFiledata.Documentfiles();
            //　　The Iterator feature in Java is simple and can only move in one direction：
            //
            //　　(1) The method iterator () requires the container to return an Iterator.
            //
            //　　(2) Use next () to get the next element in the sequence.
            //
            //　　(3) Use hasNext () to check if there are any more elements in the sequence.
            //
            //　　(4) Use remove () to remove the newly returned element from the iterator.
            //
            //　　Iterator traverses the List from both directions and inserts and deletes elements from the List.
            Iterator<Document> iter = documents.iterator();
            while(iter.hasNext()) {
                Document o = iter.next();
                org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
                //Content extraction, index storage.
                //Index opening mode
                StringField idfield = new StringField("id", o.id, Field.Store.YES);
                TextField titlefield = new TextField("title", o.Title, Field.Store.YES);
                TextField authorfield = new TextField("author", o.Author, Field.Store.YES);
                TextField biblifield = new TextField("bibliography", o.Bibliography, Field.Store.YES);
                TextField contentfield = new TextField("content", o.content, Field.Store.YES);
                doc.add(idfield);
                doc.add(titlefield);
                doc.add(authorfield);
                doc.add(biblifield);
                doc.add(contentfield);
                writer.addDocument(doc);
            }
            writer.close();
        }catch (NullPointerException e){
            Logger.getGlobal().log(Level.SEVERE,"iteration failed");
            e.printStackTrace();
            System.exit(0);
        }catch (Exception e){
            Logger.getGlobal().log(Level.SEVERE,"build index failed");
            e.printStackTrace();
            System.exit(0);
        }
    }
}
