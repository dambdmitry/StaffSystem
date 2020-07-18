package unit;

import com.company.FileException;
import com.company.FileSaveException;
import com.company.TxtDataFile;
import com.company.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TxtDataFileTest {
    TxtDataFile file = new TxtDataFile();
    String path = System.getProperty("user.dir") + "/src/unit/resources/TestTXT";
    String testFileName = "/test.txt";

    @After
    public void tearDown() throws Exception {
        File f = new File(path + testFileName);
        f.delete();
    }

    @Test
    public void saveToFile() {
        Set<Worker> workers = new LinkedHashSet<>();

        workers.add(new Worker(1, "Яблоков Анатолий Анатольевич"));
        workers.add(new Worker(2, "Сямжина Елена Алексеевна"));
        workers.add(new Worker(3, "Михайлов Даниил Алкксандрович"));

        try {
            file.saveToFile(path + testFileName, workers);
        } catch (FileSaveException e) {
            fail();
        }
        try {
            Set<Worker> expectedWorkers = file.loadFormFile(path + "/expectedFileToSave.txt");
            Set<Worker> savedWorkers = file.loadFormFile(path + testFileName);
            assertEquals(expectedWorkers.toArray(), savedWorkers.toArray());
        } catch (FileException e) {
            fail();
        }
    }

    @Test
    public void loadFormFile() {
        Worker[] expectedWorkers = new Worker[]{new Worker(1, "Рыжов Игорь Дмитриевич"),
                new Worker(2, "Мкртычян Арман Агасиевич"),
                new Worker(3, "Бирюков Дмитрий Михайлович")};
        try {
            Set<Worker> workers = file.loadFormFile(path + "/fileToLoad.txt");
            assertEquals(expectedWorkers, workers.toArray());
        } catch (FileException e) {
            fail();
        }
    }
}