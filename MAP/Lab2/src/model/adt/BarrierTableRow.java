package model.adt;

public class BarrierTableRow {

    private final Integer index;
    private final Integer value;
    private final String list;

    public BarrierTableRow(Integer index, Integer value, String list) {
        this.index = index;
        this.value = value;
        this.list = list;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getValue() {
        return value;
    }

    public String getList() {
        return list;
    }
}
