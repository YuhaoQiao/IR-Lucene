import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Evaluate {

    private double evaluateMAP(ArrayList<Integer> baseline, ArrayList<Integer> result) {
        HashSet<Integer> baselineSet = new HashSet<>(baseline);
        int numTruePositive = 0;
        double sumAveragePrecision = 0.0;
        for (int i = 0; i < result.size(); i++) {
            if (baselineSet.contains(result.get(i))) {
                numTruePositive++;
                double averagePrecision = (double)numTruePositive / (double) (i + 1);
                sumAveragePrecision += averagePrecision;
            }
        }

        double meanAveragePrecision = (numTruePositive == 0) ? 0.0 : sumAveragePrecision /  numTruePositive;
        //System.out.println(numTruePositive + " " + baselineSet.size() + " " + meanAveragePrecision);
        return meanAveragePrecision;
    }

    private double calculateRecall(ArrayList<Integer> baseline, ArrayList<Integer> result){
        HashSet<Integer> baselineSet = new HashSet<>(baseline);
        int numTruePositive = 0;
        for (int i = 0; i < result.size(); i++) {
            if (baselineSet.contains(result.get(i))) {
                numTruePositive++;
            }
        }

        double recall = (double)numTruePositive / baseline.size();
        //System.out.println(numTruePositive + " " + baselineSet.size() + " " + recall);
        return recall;
    }

    public double avgMAP(Search search, ArrayList<ArrayList<Integer>> base, ArrayList<String> queries){
        double sumMAP = 0.0;
        for (int idx = 0;idx<queries.size();idx++) {
            ArrayList<Integer> searchresult = search.search(queries.get(idx), Paths.Length_limit);
            Evaluate evaluate = new Evaluate();
            double MAP = evaluate.evaluateMAP(base.get(idx), searchresult);
            sumMAP += MAP;
        }
        double average = sumMAP / queries.size();
        return average;
    }

    public double avgRecall(Search search, ArrayList<ArrayList<Integer>> base, ArrayList<String> queries){
        double average=0;
        double sumRecall = 0;
        for (int idx = 0;idx<queries.size();idx++) {
            ArrayList<Integer> searchresult = search.search(queries.get(idx), Paths.Length_limit);
            Collections.sort(base.get(idx));
            //System.out.println(base.get(idx));
            Evaluate evaluate = new Evaluate();
            double recall = evaluate.calculateRecall(base.get(idx), searchresult);
            sumRecall += recall;
        }
        average = sumRecall/queries.size();
        return average;
    }
}
