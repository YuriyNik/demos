package utils;

import beans.CsvBean;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import pojos.CsvTransfer;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BeanExamples {
    public static List<CsvBean> beanBuilderExample(Path path, Class<? extends CsvBean> clazz) throws Exception {
        CsvTransfer csvTransfer = new CsvTransfer();
        try (Reader reader = Files.newBufferedReader(path)) {
            CsvToBean<CsvBean> cb = new CsvToBeanBuilder<CsvBean>(reader)
                    .withType(clazz)
                    .build();

            csvTransfer.setCsvList(cb.parse());
        }
        return csvTransfer.getCsvList();
    }
}
