package unit;

import com.company.NotFoundWorkerException;
import com.company.Staff;
import com.company.Worker;
import org.junit.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class StaffTest {
    Staff staff = new Staff();
    Staff initStaff = new Staff();


    @BeforeEach
    public void setUp(){
        staff = initStaff;
    }

    @Test
    public void testHasId() {
        staff.add("worker");
        assertTrue(staff.hasId(1));
        assertFalse(staff.hasId(-1));
    }

    @Test
    public void testAdd() {
        int id = staff.add("worker");

        int expectedId = 1;
        String expectedName = "worker";
        Worker expectedWorker = new Worker(expectedId, expectedName);

        assertEquals(expectedId, id); //Проверка возвращаемого значения метода.
        assertEquals(expectedWorker, staff.getWorker(expectedId)); //Проверка того, что ожидаемый работник добавился в staff.
        assertTrue(staff.hasId(expectedId));
    }


    @Test(expected = NotFoundWorkerException.class)
    public void testRemoveException() {
        staff.remove(-1);
    }


    @Test
    public void testRemove() {
        int id = staff.add("worker");
        staff.remove(id);
        assertFalse(staff.hasId(id));
    }

    @Test(expected = NotFoundWorkerException.class)
    public void testGetWorkerException() {
        staff.getWorker(-1);
    }

    @Test
    public void testGetWorker() {
        Worker expectedWorker = new Worker(1, "test"); //Имитация объекта который войдет в Staff.
        staff.add("test");
        Worker worker = staff.getWorker(1);
        assertEquals(expectedWorker, worker);
        assertEquals(expectedWorker.getName(), worker.getName());
    }

    @Test
    public void getAllWorker() {
        Set<Worker> expectedSet = new LinkedHashSet<Worker>();
        assertEquals(expectedSet,  staff.getAllWorker());
        Worker[] expectedWorkers = new Worker[]{new Worker(1, "first worker"), new Worker(2, "second worker")};
        staff.add("first worker");
        staff.add("second worker");
        assertEquals(staff.getAllWorker().toArray(), expectedWorkers);
    }

    @Test
    public void save() {
    }

    @Test
    public void load() {
    }
}