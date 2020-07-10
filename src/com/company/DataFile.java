package com.company;

import java.util.Set;

interface DataFile {
    public abstract void saveToFile(String path, Set<Worker> allWorker) throws FileSaveException;
    public abstract Set<Worker> loadFormFile(String path) throws FileException;
}
