package birthdate;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class CsvDataProviders {
    // Приватный конструктор, чтобы предотвратить создание экземпляров
    private CsvDataProviders() {}
    @DataProvider(name = "csvData")
    public static Iterator<Object[]> csvReader(Method method){
        List<Object[]> list = new ArrayList<>();
        String pathname = "src" + File.separator + "test" + File.separator + "resources" + File.separator
                + "dataproviders" + File.separator + method.getDeclaringClass().getSimpleName() + File.separator
                + method.getName() + ".csv";
        File file = new File(pathname);

        if (!file.exists()) {
            throw new RuntimeException("File " + pathname + " was not found. ");
        }

        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] keys = reader.readNext();
            if (keys != null){
                String[] dataParts;
                while ((dataParts = reader.readNext()) != null){
                    Map<String, String> testData = new HashMap<>();
                    for (int i = 0; i < keys.length; i++){
                        testData.put(keys[i], dataParts[i]);
                    }
                    list.add(new Object[] {testData});
                }
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException("Error reading " + pathname + " file. " + ExceptionUtils.getStackTrace(e));
        }
        return list.iterator();
    }
}
