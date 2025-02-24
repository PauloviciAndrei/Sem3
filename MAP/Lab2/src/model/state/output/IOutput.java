package model.state.output;

import java.util.List;

public interface IOutput {

    public String getOutput();

    public void setOutput(String string);

    public void appendToOutput(String string);

    public String toString();
}
