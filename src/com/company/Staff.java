package com.company;

import java.util.*;

public class Staff {
    private Map<Integer, Worker> staff = new HashMap<Integer, Worker>(); //Список всех сотрудников.
    private int freeId = 1; //Свободный номер.


    //Имеется ли такой номер id в базе.
    public boolean hasId(int id){
        return staff.containsKey(id);
    }


    //Генерация номера для нового сотрудника.
    private void idGeneration(){
        while(staff.containsKey(freeId)){
            freeId++;
        }
    }


    public int getLastId(){
        return freeId - 1;
    }


    //Добавить сотрудника в состав.
    public void add(String name){
        idGeneration();
        Worker worker = new Worker(freeId, name);
        staff.put(freeId, worker);
        freeId++;
    }





    //Удалить сотрудника с данным id.
    public void remove(int id) throws StaffException {
        if(staff.containsKey(id)){
            staff.remove(id);
        }else{
            throw new NotFoundWorkerException("Работника с таким номером нет");
        }
    }


    //Возвращает объект работник по его id.
    public Worker getWorker(int id){
        if(staff.containsKey(id)){
            return staff.get(id);
        }else{
            throw new NotFoundWorkerException("Работника с таким номером нет");
        }
    }


    //Возвращает всех работников.
    public Set<Worker> getAllWorker(){
        Set<Worker> allWorker = new LinkedHashSet<Worker>();
        allWorker.addAll(staff.values());
        return allWorker;
    }

    public Map<Integer, Worker> getStaff(){
        return staff;
    }


    //Сохранить данные o сотрудниках в файл.
    public void save(String path, DataFile file) throws FileException {
        file.saveToFile(path, getAllWorker());
//        try(FileWriter writer = new FileWriter(path, true)){
//            for(int key: staff.keySet()){
//                Worker worker = staff.get(key);
//                String text = key + " " + worker.genName() + "\n";
//                writer.write(text);
//            }
//            writer.flush();
//        } catch (IOException e) {
//            throw new FileSaveException("Ошибка сохранения в файл");
//        }
    }




    //Загрузка сотрудников из файла.
    public void load(String path, DataFile file) throws FileException {
        Set<Worker> allWorker = file.loadFormFile(path);
        for(Worker worker: allWorker){
            if(!staff.containsKey(worker.getId())){
                staff.put(worker.getId(), worker);
            }
        }
//        try(Scanner reader = new Scanner(new File(path))){
//            while(reader.hasNextLine()){
//                String text = reader.nextLine();
//                addFromFile(text);
//            }
//        }catch (FileNotFoundException e){
//            throw new FileLoadException("Ошибка открытия файла");
//        }
    }
}
