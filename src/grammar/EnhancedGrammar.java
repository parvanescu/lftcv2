package grammar;

public class EnhancedGrammar {

    private Grammar grammar;

    public EnhancedGrammar(Grammar grammar){
        this.grammar = new Grammar(grammar);
        ProductionRule productionRule = new ProductionRule("S'");
        productionRule.addProduction(this.grammar.startingSymbol);
        this.grammar.productionRules.add(productionRule);
        this.grammar.startingSymbol = "S'";
    }

    public Grammar getGrammar() {
        return grammar;
    }
}
