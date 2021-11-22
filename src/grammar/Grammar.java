package grammar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Grammar {
    Set<String> terminals,nonTerminals;
    String startingSymbol, filename;
    List<ProductionRule> productionRules;

    public Grammar(String filename) {
        this.filename = filename;
        this.nonTerminals = new HashSet<>();
        this.terminals = new HashSet<>();
        this.productionRules = new ArrayList<>();
    }

    private void readFromFile() throws FileNotFoundException {
        File file = new File(this.filename);
        Scanner fileScanner = new Scanner(file);
        while(fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            List<String> splitLine = Arrays.asList(line.split(" = "));
            String nonTerminal = splitLine.get(0);
            nonTerminals.add(nonTerminal);

            String[] productionRule = splitLine.get(1).split(" ");
            for(String token: productionRule){
                if(token.startsWith("\"") && token.endsWith("\""))
                    terminals.add(token.substring(1,token.length()-1));

            }



        }
    }
}
