import domain.Scanner;
import exceptions.InvalidTokenException;
import fa.*;
import grammar.Grammar;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(
                "C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab3\\perr.in",
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

        ImplFiniteAutomata faIdentifiers = new ImplFiniteAutomata("C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab4\\FAidentifiers.in");
        ImplFiniteAutomata faIntConstants = new ImplFiniteAutomata("C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab4\\FAintconstants.in");

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

        Grammar grammar = new Grammar("C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab5\\g1.txt");
        grammar.showMenu();


    }
}
