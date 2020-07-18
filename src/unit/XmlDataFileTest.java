package unit;

import com.company.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class XmlDataFileTest {
    XmlDataFile file = new XmlDataFile();
    String path = System.getProperty("user.dir") + "/src/unit/resources/TestXML";
    String testFileName = "/test.xml";



    @After
    public void tearDown() throws Exception {
        File f = new File(path + testFileName);
        f.delete();
    }

    @Test
    public void saveToFile() {
        Set<Worker> workers = new LinkedHashSet<>();

        workers.add(new Worker(1, "Шилова Галина Николаевна"));
        workers.add(new Worker(2, "Задворная Ольга Николаевна"));
        workers.add(new Worker(3, "Пучков Данил Евгеньевич"));

        try {
            file.saveToFile(path + testFileName, workers);
            Set<Worker> expectedWorkers = file.loadFormFile(path + "/fileToSave.xml");
            Set<Worker> savedWorkers = file.loadFormFile(path + testFileName);
        } catch (FileSaveException | FileLoadException e) {
            fail();
        }
    }

    @Test
    public void loadFormFile() {
        Worker[] expectedWorkers = new Worker[]{new Worker(1, "Строеньев Степан Дмитриевич"),
                new Worker(2, "Глазов Александр Михайлович"),
                new Worker(3, "Озеров Дмитрий Андреевич")};
        try {
            Set<Worker> workers = file.loadFormFile(path + "/fileToLoad.xml");
            assertEquals(expectedWorkers, workers.toArray());
        } catch (FileException e) {
            fail();
        }
    }
}