package birthdate;

import com.google.BaseTest;
import org.example.CheckAge;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class AdulthoodTest extends BaseTest {
    @Test(dataProvider = "csvData", dataProviderClass = CsvDataProviders.class)
    public void checkAdulthood(Map<String, String> testData) {
        String birthdate = testData.get("birthdate");
        boolean expectedResult = Boolean.parseBoolean(testData.get("expectedResult"));

        String inputData = "Enter your birthdate: " + birthdate + "\n";
        InputStream originalSystemIn = System.in;
        System.setIn(new ByteArrayInputStream(inputData.getBytes()));

        try {
            CheckAge.main(new String[]{birthdate});
            String expectedOutput = expectedResult ? "You are adult: you are more than 18 years old\n" :
                    "You are too young: you are less than 18 years old\n";
            assertEquals(getOutput(birthdate), expectedOutput, """
                Output for %s does not match expected result.
                Expected output: %s
                Actual output: %s
                """.formatted(birthdate, expectedOutput, getOutput(birthdate)));
        } catch (ParseException e) {
            throw new RuntimeException("Error in date parsing. Make sure the date is in the correct format.");
        } finally {
            System.setIn(originalSystemIn);
        }
    }
    private String getOutput(String birthdate) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalSystemOut = System.out;
        try {
            System.setOut(new PrintStream(baos));
            CheckAge.main(new String[]{birthdate});
        } catch (ParseException e) {
            throw new RuntimeException("Error in date parsing. Make sure the date is in the correct format.");
        } finally {
            System.setOut(originalSystemOut);
        }
        return baos.toString();
    }
}
