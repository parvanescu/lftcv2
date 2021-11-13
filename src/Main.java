import domain.Scanner;
import exceptions.InvalidTokenException;
import fa.FiniteStateMachine;
import fa.ImplFiniteAutomata;

// 1a => un singur symbol table

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
        if(scanner.getExceptionList().size()!=0)
            for(InvalidTokenException exception: scanner.getExceptionList()){
                System.out.println(exception);
            }
    }
}
