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
//        Scanner scanner = new Scanner(
//                "C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab3\\perr.in",
//                "C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab1\\lab1b\\token.in",
//                "C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab4\\FAidentifiers.in",
//                "C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab4\\FAintconstants.in"
//        );
//        scanner.startScanning();
//        System.out.println(scanner.getPif());
//        System.out.println(scanner.getSymbolTable().toString());
//        if (scanner.getExceptionList().size() != 0)
//            for (InvalidTokenException exception : scanner.getExceptionList()) {
//                System.out.println(exception);
//            }
//
//        ImplFiniteAutomata faIdentifiers = new ImplFiniteAutomata("C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab4\\FAidentifiers.in");
//        ImplFiniteAutomata faIntConstants = new ImplFiniteAutomata("C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab4\\FAintconstants.in");

//        System.out.println("Menu:");
//        System.out.println("11. Print set of states for identifiers");
//        System.out.println("12. Print the alphabet for identifiers");
//        System.out.println("13. Print the transitions for identifiers");
//        System.out.println("14. Print the final states for identifiers");
//        System.out.println("21. Print set of states for integer constants");
//        System.out.println("22. Print the alphabet for integer constants");
//        System.out.println("23. Print the transitions for integer constants");
//        System.out.println("24. Print the final states for integer constants");
//
//        java.util.Scanner scanner1 = new java.util.Scanner(System.in);
//
//        try {
//            while (true) {
//                System.out.println("Input the option: ");
//                String option = scanner1.nextLine();
//
//                switch (option) {
//                    case ("11") -> {
//                        List<State> stateList = faIdentifiers.getSetOfStates();
//                        stateList.forEach(System.out::println);
//                    }
//                    case ("12") -> {
//                        System.out.println("Alphabet:");
//                        System.out.println(faIdentifiers.getAlphabet());
//                    }
//                    case ("13") -> {
//                        Map<State,Transition> transitionList = faIdentifiers.getTransitions();
//                        transitionList.forEach((key,value)-> System.out.println(key+ "\n" + value));
//                    }
//                    case ("14") -> {
//                        List<State> stateList = faIdentifiers.getFinalStates();
//                        System.out.println("Final states:");
//                        stateList.forEach(System.out::println);
//                    }
//                    case ("21") -> {
//                        List<State> stateList = faIntConstants.getSetOfStates();
//                        stateList.forEach(System.out::println);
//                    }
//                    case ("22") -> {
//                        System.out.println("Alphabet:");
//                        System.out.println(faIntConstants.getAlphabet());
//                    }
//                    case ("23") -> {
//                        Map<State,Transition> transitionList = faIntConstants.getTransitions();
//                        transitionList.forEach((key,value)-> System.out.println(key+ "\n" + value));
//                    }
//                    case ("24") -> {
//                        List<State> stateList = faIntConstants.getFinalStates();
//                        System.out.println("Final states:");
//                        stateList.forEach(System.out::println);
//                    }
//                    case ("x") -> {
//                        throw new RuntimeException();
//                    }
//                    default -> {
//                        System.out.println("Invalid option");
//                    }
//                }
//            }
//        } catch (RuntimeException ignored) {
//            System.out.println("Exiting");
//        }

        Grammar grammar = new Grammar("C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab5\\g3.txt");
//        grammar.showMenu();
        EnhancedGrammar enhancedGrammar = new EnhancedGrammar(grammar);
        Parser p = new Parser(enhancedGrammar);
//        List<LrItem> lrItems = new ArrayList<>();
//        LrItem item = new LrItem("S'");
//        item.setAfterDot(List.of("S"));
//        lrItems.add(item);
//        List<LrItem> s0 = p.closure(lrItems);
//        List<LrItem> s1 = p.goTo(s0,"S");
//        List<LrItem> s2 = p.goTo(s0,"a");
//        List<LrItem> s3 = p.goTo(s2,"A");
//        List<LrItem> s4 = p.goTo(s2,"b");
//        List<LrItem> s5 = p.goTo(s2,"c");
//        List<LrItem> s6 = p.goTo(s4,"A");
//
//        System.out.println(s0);
//        System.out.println(s1);
//        System.out.println(s2);
//        System.out.println(s3);
//        System.out.println(s4);
//        System.out.println(s5);
//        System.out.println(s6);

//        System.out.println(p.colCan());

//        LrTable lrTable = new LrTable(enhancedGrammar,p);
//        System.out.println(lrTable.getActionTablePart());
//        System.out.println(lrTable.getGoToTablePart());

//        List<String> program = new ArrayList<>();
//        program.add("start");
//        program.add("declare");
//        program.add("nr1");
//        program.add("integer");
//        program.add("auxiliary");
//        program.add("*=");
//        program.add("2");
//        program.add(".");
//        program.add("declare");
//        program.add("nr2");
//        program.add("integer");
//        program.add("auxiliary");
//        program.add("*=");
//        program.add("5");
//        program.add(".");
//        program.add("declare");
//        program.add("message");
//        program.add("char");
//        program.add("[");
//        program.add("max-length");
//        program.add("*=");
//        program.add("256");
//        program.add("]");
//        program.add("auxiliary");
//        program.add("*=");
//        program.add("\"");
//        program.add("Program finished");
//        program.add("\"");
//        program.add(".");
//        program.add("verify");
//        program.add("(");
//        program.add("nr1");
//        program.add(">");
//        program.add("nr2");
//        program.add(")");
//        program.add(":");
//        program.add("case");
//        program.add("true");
//        program.add(")");
//        program.add(":");
//        program.add("nr1");
//        program.add("*=");
//        program.add("nr1");
//        program.add("-");
//        program.add("nr2");
//        program.add(".");
//        program.add("show");
//        program.add("nr1");
//        program.add(".");
//        program.add("case");
//        program.add("false");
//        program.add(":");
//        program.add("nr2");
//        program.add("*=");
//        program.add("nr2");
//        program.add("-");
//        program.add("nr1");
//        program.add(".");
//        program.add("show");
//        program.add("nr2");
//        program.add(".");
//        program.add("always");
//        program.add("show");
//        program.add("message");
//        program.add(".");
//        program.add("end");

//        List.of("a","b","b","c")

        java.util.Scanner scanner1 = null;
        try {
            scanner1 = new java.util.Scanner(new File("C:/Users/parva/OneDrive/Desktop/faculta semestrul5/lftc/LabsRepo/lab7/seq.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = scanner1.nextLine();
        List<String> sequence = new ArrayList<>(List.of(line.split(" ")));

        List<Integer> productions = p.parseSequence(sequence);
        Collections.reverse(productions);
        System.out.println(productions);

        ParserOutput output = new ParserOutput();
        output.generateTree(productions,grammar.getProductionRules());
        output.writeTree("C:/Users/parva/OneDrive/Desktop/faculta semestrul5/lftc/LabsRepo/lab7/out1.txt");
        System.out.println(output);





    }
}
