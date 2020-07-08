package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class TxtDataFile extends DataFile {


    @Override
    public void saveToFile(String path, Set<Worker> allWorker) throws FileSaveException {
        File file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new FileSaveException("Не удалось создать файл");
            }
        }
        try(FileWriter writer = new FileWriter(path, true)){
            for(Worker worker: allWorker){
                writer.write(worker.toString() + "\n");
            }
            writer.flush();
        }catch (IOException e) {
            throw new FileSaveException("Ошибка сохранения в файл");
        }
    }


    //Добавление сотрудника по строке из файла.
    private Worker getFromFile(String data) throws FileException {
        int amountOfData = 4; //Сколько данных должна содержать строка чтобы добавить сотрудника.
        String[] workerData = data.split(" ");
        if(workerData.length == amountOfData){
            int id;
            try{
                id = Integer.parseInt(workerData[0]);
            }catch (NumberFormatException e){
                throw new FileLoadException("Неправильная запись в файле: " + data);
            }
            String name = "";
            for(int i = 1; i <= 3; i++){
                name += workerData[i] + " ";
            }
            name = name.trim();
            Worker worker = new Worker(id, name);
            return worker;

        }
        else{
            throw new FileLoadException("Неправильная запись в файле: " + data);
        }
    }


    @Override
    public Set<Worker> loadFormFile(String path) throws FileException {
        Set<Worker> allWorker = new LinkedHashSet<Worker>();
        try(Scanner reader = new Scanner(new File(path))){
            while(reader.hasNextLine()){
                String text = reader.nextLine();
                allWorker.add(getFromFile(text));
            }
        }catch (FileNotFoundException e){
            throw new FileLoadException("Ошибка открытия файла");
        }
        return allWorker;
    }
}
