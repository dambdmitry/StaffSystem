package com.company;

import java.util.Scanner;
import java.util.Set;

public class ConsoleApp {
    private Staff staff = new Staff();
    private static final int REQUIRED_NUMBER_OF_ARGS_TO_ADD = 3;
    private static final int REQUIRED_NUMBER_OF_ARGS_TO_REMOVE = 1;
    
    public void consoleRun(){
        Scanner in = new Scanner(System.in);
        String command = "";
        while(!command.equals("exit")){
            System.out.print("Введите комманду: ");
            String[] commandAndArguments = in.nextLine().split(" ");
            command = commandAndArguments[0];
            switch (command){
                case "add":
                    addWorker(commandAndArguments);
                    break;
                case "remove":
                    removeWorker(commandAndArguments);
                    break;
                case "print":
                    if(commandAndArguments.length == 1){
                        printAll();
                    }else if(commandAndArguments.length == 2){
                        int id;
                        try{
                            id = Integer.parseInt(commandAndArguments[1]);
                            print(id);
                        }catch (NumberFormatException e){
                            System.out.println("Не верный аргумент");
                        }
                    }else{
                        System.out.println("Не верно введен аргумент");
                    }
                    break;
                case "save":
                    save(commandAndArguments);
                    break;
                case "load":
                    load(commandAndArguments);
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("Не верно введена команда");
                    break;
            }
        }
    }

    
    //Добавление сотрудника.
    private void addWorker(String[] args){
        if(args.length - 1 == REQUIRED_NUMBER_OF_ARGS_TO_ADD){
            String name = "";
            int id;
            for(int i = 1; i <= 3; i++){
                name += args[i] + " ";
            }
            name = name.trim();
            id = staff.add(name);
            System.out.println(id);
        }else{
            System.out.println("Не верный аргумент");
        }
    }


    private void removeWorker(String[] args){
        if(args.length - 1 == REQUIRED_NUMBER_OF_ARGS_TO_REMOVE){
            int id;
            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException e){
                System.out.println("Не верный аргумент табельного номера");
                return;
            }

            if((staff.hasId(id))){
                staff.remove(id);
                System.out.println("Сотрудник успешно удален");
            }else {
                System.out.println("Сотрудника с таким номером нет");
            }

        }else{
            System.out.println("Не верный аргумент");
        }
    }


    private void printAll(){
        Set<Worker> allWorker = staff.getAllWorker();
        for(Worker worker: allWorker){
            System.out.println(worker.toString());
        }
    }


    private void print(int id){
        if(staff.hasId(id)){
            Worker worker = staff.getWorker(id);
            System.out.println(worker.toString());
        }else{
            System.out.println("Сотрудника с таким номером нет");
        }
    }


    private DataFile getDataFileByFormat(String format){
        return switch (format) {
            case "txt" -> new TxtDataFile();
            case "xml" -> new XmlDataFile();
            case "json" -> new JsonDataFile();
            default -> null;
        };
    }


    private void save(String[] args){
        String path = "";
        DataFile file = getDataFileByFormat(args[args.length - 1]);

        //Если расширение не задано, то по умолчанию сохраняем в txt
        if(file == null){
            if(args.length > 1){
                for(int i = 1; i <= args.length - 2; i++){
                    path += args[i];
                }
                try {
                    staff.save(path, new TxtDataFile());
                    System.out.println("Данные успешно сохранены в файл");
                } catch (FileException e) {
                    System.out.println(e.getMessage());
                }
            }else{
                System.out.println("Укажите путь к файлу");
            }
        }else{
            if(args.length > 2){
                for(int i = 1; i <= args.length - 2; i++){
                    path += args[i];
                }
                try {
                    staff.save(path, file);
                    System.out.println("Данные успешно сохранены в файл");
                } catch (FileException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }


    private void load(String[] args){
        DataFile file = getDataFileByFormat(args[args.length - 1]);
        String path = "";

        if(file == null){
            if(args.length > 1){
                for(int i = 1; i <= args.length - 1; i++){
                    path += args[i];
                }
                try {
                    staff.load(path, new TxtDataFile());
                    System.out.println("Данные успешно загружены из файла");
                } catch (FileException e) {
                    System.out.println(e.getMessage());
                }
            }else{
                System.out.println("Укажите путь к файлу");
            }
        }else{
            if(args.length > 2){
                for(int i = 1; i <= args.length - 2; i++){
                    path += args[i];
                }
                try {
                    staff.load(path, file);
                    System.out.println("Данные успешно загружены");
                } catch (FileException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
