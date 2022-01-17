package javakit.minhphuc.dto;

public class PointDTO extends BaseDTO<PointDTO>{

    private String owner;
    private int row;
    private int column;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
