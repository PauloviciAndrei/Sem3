package model.state.fileTable;

import exceptions.ExpressionException;
import model.adt.*;
import exceptions.AppException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import exceptions.CouldNotOpenFIleException;
import exceptions.FileNotFoundException;
import exceptions.InvalidFileFormatException;



public class FileTable implements IFileTable {

    private MyMap<String,BufferedReader> fileTable;

    public FileTable() {
        this.fileTable = new MyMap<>();
    }

    public  MyMap<String,BufferedReader> getContent() {
        return this.fileTable;
    }

    @Override
    public void openFile(String name) throws AppException {
        if(fileTable.containsKey(name)){
            throw new AppException("File already opened!");
        }
        try{
            BufferedReader reader = new BufferedReader(new FileReader(name));
            fileTable.insert(name, reader);
        }catch(Exception ex){
            throw new AppException("Error opening file!");
        }
    }

    public void closeFile(String name) throws AppException {
        try {
            fileTable.remove(name);
        } catch (ExpressionException ex) {
            throw new FileNotFoundException("File not found, and therefore could not be closed!");
        }
    }

    @Override
    public int readFile(String name) throws AppException{
        BufferedReader reader = null;
        try{
            reader = fileTable.get(name);
        }catch(ExpressionException exception){
            throw new CouldNotOpenFIleException("Cannot read from file" + name);
        }

        String data;
        try {
            data = reader.readLine();
        } catch (IOException e) {
            throw new InvalidFileFormatException("Invalid line in file");
        }

        if(data == null){
            data = "0";
        }

        int answer = 0;
        try {
            answer = Integer.parseInt(data);
        } catch (NumberFormatException exception) {
            throw new InvalidFileFormatException("Invalid line in file");
        }
        return answer;
    }

    public String toString(){
        StringBuilder answer = new StringBuilder();
        answer.append("File Table:\n");
        for(String name: fileTable.keySet()){
            answer.append(name).append("\n");
        }
        return answer.toString();
    }

}
