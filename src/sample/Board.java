package sample;

public class Board {
    private Field[][] Fields;

    public Board()
    {
        Fields = new Field[17][25];
    }

    public Field[][] getFields() {
        return Fields;
    }

    public void setField(Field field)
    {
        Fields[field.getX()][field.getY()] = field;
    }
}
