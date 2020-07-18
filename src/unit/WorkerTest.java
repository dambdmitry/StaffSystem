package unit;

import com.company.Worker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {
    Worker worker;
    int id;
    String name;

    @Before
    public void setUp() throws Exception {
        id = 1;
        name = "worker";
        worker = new Worker(id, name);
    }

    @Test
    public void testToString() {
        String expectedString = id + " " + name;
        assertEquals(expectedString, worker.toString());
    }

    @Test
    public void testEquals() {
        Worker newWorker = new Worker(id, name);
        assertEquals(newWorker, worker);
    }
}