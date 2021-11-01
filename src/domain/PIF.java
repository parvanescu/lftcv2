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

    private static String centerString(String s1, String s2) {
        return String.format("|%-" + 13 + "s|%-" + 13 +"s|\n",
                String.format("%" + (s1.length() + (13 - s1.length()) / 2) + "s", s1),
                String.format("%" + (s2.length() + (13 - s2.length()) / 2) + "s", s2)
                );
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("|-------------|-------------|\n");
        for(PIFtoken token : tokenList){
//            stringBuilder.append(String.format("|%13s|%13s|\n",token.getToken(),token.getPosition()));
            stringBuilder.append(centerString(token.getToken(),token.getPosition().toString()));
        }
        stringBuilder.append("|-------------|-------------|\n");
        return stringBuilder.toString();
    }
}
