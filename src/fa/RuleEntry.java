package fa;

import java.util.List;
import java.util.Objects;

public class RuleEntry {
    public String state;
    public List<Character> consumedValues;

    public RuleEntry(String state,List<Character> consumedValues){
        this.state = state;
        this.consumedValues = consumedValues;
    }
}
