import java.nio.file.*;

public class Paths {
    public static final int Boundary_value = 3;
    public static final int Length_limit = 60;
    private static final Path File_Path = java.nio.file.Paths.get("files/");
    public static final Path Index_Path = File_Path.resolve("index/");
    public static final Path Document_File = File_Path.resolve("cran.all.1400");
    public static final Path Query_File = File_Path.resolve("cran.qry");
    public static final Path Query_Score_File = File_Path.resolve("cranqrel");


}
