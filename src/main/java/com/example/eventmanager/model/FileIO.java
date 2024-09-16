package com.example.eventmanager.model;


import java.io.File;

public interface FileIO<T>
{
    void writeToFile(T t, File file) throws Exception;
    void writeToFile(T t) throws Exception;
    T readFromFile(File file) throws Exception;
    T readFromFile() throws Exception;
}
