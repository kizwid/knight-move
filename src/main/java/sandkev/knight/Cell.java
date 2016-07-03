package sandkev.knight;

/**
 * Created by kevin on 4/07/2016.
 */
public class Cell {
    enum Type{Number,Letter,Empty}
    char value;
    Type type;
    public Cell(char value) {
        this.value = value;
        if(value>='0' && value<='9'){
            this.type=Type.Number;
        }else if(value>='a' && value<='z'){
            this.type=Type.Letter;
        }else if(value>='A' && value<='Z'){
            this.type=Type.Letter;
        }else if(value==' '){
            this.type=Type.Empty;
        }else {
            throw new IllegalArgumentException("Cell can only contain [0-9][a-Z[A-Z] or empty");
        }
    }
    Type getType(){return type;}
    char getValue(){return value;}
}
