package repository;

import model.state.ProgramState;
import java.util.List;

public interface IRepository {

    public List<ProgramState> getProgramsList();
    public void setProgramsList(List<ProgramState> programs);
    public void clear();
    public ProgramState getCurrentProgram();
    public void addProgram(ProgramState program);
    public void logProgramState(ProgramState program);
}
