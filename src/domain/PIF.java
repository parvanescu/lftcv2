package domain;

import java.util.ArrayList;
import java.util.List;

public class PIF {
    private final List<PIFtoken> tokenList;

    public PIF() {
        tokenList = new ArrayList<>();
    }

    public List<PIFtoken> getTokenList() {
        return tokenList;
    }

    public void put(PIFtoken token){
        tokenList.add(token);
    }
}
