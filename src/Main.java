import domain.Position;
import domain.SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SymbolTable<String> st = new SymbolTable<>();
        List<Position> positionList = new ArrayList<>();
        positionList.add(st.pos("a"));
        positionList.add(st.pos("2"));
        positionList.add(st.pos("a"));
        positionList.add(st.pos("a"));
        positionList.add(st.pos("2"));
        System.out.println(positionList);
    }
}
