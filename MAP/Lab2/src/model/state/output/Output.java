package model.state.output;

import java.util.List;
import model.adt.MyList;

public class Output implements IOutput {

    MyList<String> data;

    public Output() {
        data = new MyList<>();
    }

    @Override
    public String getOutput() {
        StringBuilder answer = new StringBuilder();
        for(String elem:this.data.getAll()){
            answer.append(elem);
        }
        return answer.toString();
    }

    public MyList<String> getOutputList()
    {
        return data;
    }

    public void appendToOutput(String string) {
        this.data.add(string);
    }

    public void setOutput(String string) {
        this.data.clear();
        this.data.add(string);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String elem : data.getAll()) {
            result.append(elem).append("\n");
        }
        return "Output: " + result.toString() + "\n";
    }




}
