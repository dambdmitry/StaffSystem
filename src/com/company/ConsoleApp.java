package com.company;

import org.w3c.dom.ls.LSOutput;

import java.util.Map;
import java.util.Scanner;

public class ConsoleApp {
    private Staff staff = new Staff();
    private int freeId = 1; //Свободный номер.
    
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

    
    //Добавление сотрудника. Рекурсия для того чтобы были уникальные номера.
    private void addWorker(String[] args){
        int amountOfArgs = 3; //Колличество аргументов, необходимых для добавления сотрудников.
        if(args.length - 1 == amountOfArgs){
            String name = "";
            for(int i = 1; i <= 3; i++){
                name += args[i] + " ";
            }
            name = name.trim();
            try {
                staff.add(name, freeId);
                System.out.println(freeId);
                freeId++;
            } catch (StaffException e) {
                freeId++;
                addWorker(args);
            }
        }else{
            System.out.println("Не верный аргумент");
        }
    }


    private void removeWorker(String[] args){
        int amountOfArgs = 1;
        if(args.length - 1 == amountOfArgs){
            int id;
            try {
                id = Integer.parseInt(args[1]);
                staff.remove(id);
                System.out.println("Сотрудник удален");
            } catch (NumberFormatException e){
                System.out.println("Не верный аргумент табельного номера");
            } catch (StaffException e) {
                System.out.println(e.getMessage());
            }
        }else{
            System.out.println("Не верный аргумент");
        }
    }


    private void printAll(){
        Map<Integer, Worker> allWorkers = staff.getStaff();
        for(int id: allWorkers.keySet()){
            String name = allWorkers.get(id).genName();
            System.out.println(id + " " + name);
        }
    }


    private void print(int id){
        Map<Integer, Worker> allWorkers = staff.getStaff();
        if(allWorkers.containsKey(id)) {
            String name = allWorkers.get(id).genName();
            System.out.println(id + " " + name);
        }else{
            System.out.println("Сотрудника с таким номером нет");
        }
    }


    private void save(String[] args){
        String path = "";
        if(args.length > 1){
            for(int i = 1; i <= args.length - 1; i++){
                path += args[i];
            }
            try {
                staff.save(path);
                System.out.println("Данные успешно сохранены в файл");
            } catch (StaffException e) {
                System.out.println(e.getMessage());
            }
        }else{
            System.out.println("Укажите путь к файлу");
        }
    }


    private void load(String[] args){
        String path = "";
        if(args.length > 1){
            for(int i = 1; i <= args.length - 1; i++){
                path += args[i];
            }
            try {
                staff.load(path);
                System.out.println("Данные успешно загружены из файла");
            } catch (StaffException e) {
                System.out.println(e.getMessage());
            }
        }else{
            System.out.println("Укажите путь к файлу");
        }
    }
}
