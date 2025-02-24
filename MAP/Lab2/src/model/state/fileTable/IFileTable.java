package model.state.fileTable;

import java.util.List;

public interface IFileTable {

    public void openFile(String name);

    public void closeFile(String name);

    public int readFile(String name);

    public String toString();
}
