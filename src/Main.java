import domain.Position;
import domain.Scanner;
import domain.SymbolTable;
import exceptions.InvalidTokenException;

import java.util.ArrayList;
import java.util.List;

// 1a => un singur symbol table

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(
                "C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab3\\p2.in",
                "C:\\Users\\parva\\OneDrive\\Desktop\\faculta semestrul5\\lftc\\LabsRepo\\lab1\\lab1b\\token.in"
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
