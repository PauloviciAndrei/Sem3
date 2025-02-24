package repository;


import exceptions.AppException;
import model.state.ProgramState;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository implements IRepository {

    List<ProgramState> programs;
    String logFilePath;

    public Repository(String logFilePath) {
        programs = new LinkedList<>();
        this.logFilePath = logFilePath;
    }

    public Repository(List<ProgramState> programs) {
        this.programs = programs;
    }

    public List<ProgramState> getProgramsList() {
        return this.programs;
    }

    public void setProgramsList(List<ProgramState> programs){
        this.programs = programs;
    }

    @Override
    public void clear() {
        this.programs.clear();
    }

    @Override
    public ProgramState getCurrentProgram() {
        if(programs.isEmpty())
            throw new AppException("There is no current program");
        return programs.get(0);
    }

    public void addProgram(ProgramState program){
        this.programs.add(program);
    }

    @Override
    public void logProgramState(ProgramState program) {

        if(this.logFilePath != null) {
            PrintWriter logFile = null;
            try {
                logFile = new PrintWriter(
                        new BufferedWriter(new FileWriter(logFilePath, true)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            logFile.println(program.toString());
            logFile.close();
        }
    }


}
