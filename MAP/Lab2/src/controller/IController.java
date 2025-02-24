package controller;

import model.state.ProgramState;
import exceptions.AppException;
import model.statements.IStmt;

import java.util.List;

public interface IController {
    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates);
    public void setDisplayFLag(boolean state);

}
