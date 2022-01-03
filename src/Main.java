import domain.Scanner;
import exceptions.InvalidTokenException;
import fa.*;
import grammar.EnhancedGrammar;
import grammar.Grammar;
import lrParser.output.ParserOutput;
import lrParser.parsing.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(
                "C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab7\\seq2.txt",
                "C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab1\\lab1b\\token.in",
                "C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab4\\FAidentifiers.in",
                "C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab4\\FAintconstants.in"
        );
        scanner.startScanning();
        System.out.println(scanner.getPif());
        System.out.println(scanner.getSymbolTable().toString());
        if (scanner.getExceptionList().size() != 0)
            for (InvalidTokenException exception : scanner.getExceptionList()) {
                System.out.println(exception);
            }


        Grammar grammar = new Grammar("C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab5\\g2.txt");
//        grammar.showMenu();
        EnhancedGrammar enhancedGrammar = new EnhancedGrammar(grammar);
        Parser p = new Parser(enhancedGrammar);

        List<Integer> productions = p.parseSequence(List.of("a","b","b","c"));
        Collections.reverse(productions);
        System.out.println(productions);

        ParserOutput output = new ParserOutput();
        output.generateTree(productions,grammar.getProductionRules());
        output.writeTree("C:/Users/parva/OneDrive/Desktop/faculta semestrul5/lftc/LabsRepo/lab7/out2.txt");
        System.out.println(output);





    }
}
