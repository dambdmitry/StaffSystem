package com.company;

import java.io.*;
import java.util.*;

public class Staff {
    private Map<Integer, Worker> staff = new HashMap<Integer, Worker>(); //Список всех сотрудников.

    //Добавить сотрудника в состав.
    public void add(String name, int id) throws StaffException {
        if(!staff.containsKey(id)){
            Worker worker = new Worker(id, name);
            staff.put(id, worker);
        }else{
            throw new RepeatedIdException("Сотрудник с таким номером уже есть");
        }
    }


    //Удалить сотрудника с данным id.
    public void remove(int id) throws StaffException {
        if(staff.containsKey(id)){
            staff.remove(id);
        }else{
            throw new NotFoundWorkerException("Работника с таким номером нет");
        }
    }


    public Map<Integer, Worker> getStaff(){
        return staff;
    }


    //Сохранить данные o сотрудниках в файл.
    public void save(String path) throws StaffException {

        try(FileWriter writer = new FileWriter(path, true)){
            for(int key: staff.keySet()){
                Worker worker = staff.get(key);
                String text = key + " " + worker.genName() + "\n";
                writer.write(text);
            }
            writer.flush();
        } catch (IOException e) {
            throw new FileSaveException("Ошибка сохранения в файл");
        }
    }

    //Добавление сотрудника по строке из файла.
    private void addFromFile(String data) throws StaffException {
        int amountOfData = 4; //Сколько данных должна содержать строка чтобы добавить сотрудника.
        String[] workerData = data.split(" ");

        if(workerData.length == amountOfData){
            int id;

            try{
                id = Integer.parseInt(workerData[0]);
            }catch (NumberFormatException e){
                throw new FileLoadException("Неправильная запись в файле: " + data);
            }

            if(!staff.containsKey(id)){
                String name = "";
                for(int i = 1; i <= 3; i++){
                    name += workerData[i] + " ";
                }
                name = name.trim();
                Worker worker = new Worker(id, name);
                staff.put(id, worker);
            }
        }
        else{
            throw new FileLoadException("Неправильная запись в файле: " + data);
        }
    }


    //Загрузка сотрудников из файла.
    public void load(String path) throws StaffException {
        try(Scanner reader = new Scanner(new File(path))){
            while(reader.hasNextLine()){
                String text = reader.nextLine();
                addFromFile(text);
            }
        }catch (FileNotFoundException e){
            throw new FileLoadException("Ошибка открытия файла");
        }
    }
}
