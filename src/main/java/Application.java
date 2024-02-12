import beans.CsvBean;
import beans.NamedColumnBean;
import beans.SimplePositionBean;
import utils.BeanExamples;
import utils.CSVReaderExamples;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Application {

    // CSV Reader Examples
    public static List<String[]> readLineByLineExample() throws Exception {
        Path path = Paths.get("twoColumn.csv");
        return CSVReaderExamples.readLineByLine(path);
    }

    public static List<String[]> readAllLinesExample() throws Exception {
        Path path = Paths.get("twoColumn.csv");
        return CSVReaderExamples.readAllLines(path);
    }

    public static List<CsvBean> simplePositionBeanExample() throws Exception {
        Path path = Paths.get("twoColumn.csv");
        return BeanExamples.beanBuilderExample(path, SimplePositionBean.class);
    }

    public static List<CsvBean> namedColumnBeanExample() throws Exception {
        Path path = Paths.get("namedColumn.csv");
        return BeanExamples.beanBuilderExample(path, NamedColumnBean.class);
    }



    public static void main(String[] args) throws Exception {
        System.out.println(readLineByLineExample());
        System.out.println(readAllLinesExample());
        System.out.println(simplePositionBeanExample());
        System.out.println(namedColumnBeanExample());
    }

}
