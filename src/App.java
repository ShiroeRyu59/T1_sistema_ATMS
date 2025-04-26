import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class App {

    public static void clearScreen()
    {
        System.out.println("--------------------------");
        System.out.flush();
    }
    
    public static void lectArchivo(String docto)
    {
       try (FileInputStream file = new FileInputStream(docto);
            DataInputStream saldo = new DataInputStream(file))
            {
                int saldoR = saldo.readInt();
                System.out.println("Saldo: " + saldoR);
            } catch (IOException e) {
                System.out.println("Error en la lectura del archivo: " + e.getMessage());
            }
    }

    public static void retFondos(String docto, int retiro)
    {
        try (FileInputStream fileIn = new FileInputStream(docto);
             DataInputStream dis = new DataInputStream(fileIn)) {
            int saldo = dis.readInt();
            if (saldo >= retiro) {
                saldo -= retiro;
                try (FileOutputStream fileOut = new FileOutputStream(docto);
                     DataOutputStream dos = new DataOutputStream(fileOut)) {
                    dos.writeInt(saldo);
                    System.out.println("Retiro exitoso. Su nuevo saldo es: " + saldo);
                }
            } else {
                System.out.println("Fondos insuficientes.");
            }
        } catch (IOException e) {
            System.out.println("Error en la lectura/escritura del archivo: " + e.getMessage());
        } 
    }
    public static void pressEnterToContinue(Scanner scanner)
    {
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();  
    }

    public static void main(String[] args) throws Exception {
        String docto = "saldo.dat"; //archivo de saldos
        int salInitial = 1000;
        try (FileOutputStream file = new FileOutputStream(docto);
             DataOutputStream dos = new DataOutputStream(file)) {
            dos.writeInt(salInitial);
        } catch (IOException e) {
            System.out.println("Error al escribir el saldo inicial: " + e.getMessage());
        }        
        clearScreen();

        Scanner scanner = new Scanner(System.in);
        boolean repeat = true;

        while (repeat)
        {
            clearScreen();
            System.out.println("------ATMS------");
            System.out.println("Seleccione la consulta a generar");
            System.out.println("1. Consulta de Saldos");
            System.out.println("2. Retiro de fondos");
            System.out.println("3. Salir");
            int selection = scanner.nextInt();
            scanner.nextLine();

            switch (selection)
            {
                case 1:
                    clearScreen();
                    System.out.println("El saldo de su cuenta es:\n\n");
                    lectArchivo(docto);
                    break;

                case 2:
                    clearScreen();
                    System.out.println("Ingrese la cantidada a retirar: ");
                    int retiro = scanner.nextInt();
                    retFondos(docto, retiro);
                    break;

                case 3:
                    clearScreen();
                    System.out.println("Hasta la proxima...");
                    scanner.close();
                    return;
                default:
                System.out.println("Opcion ingresada no valida, intente nuevamente.");
            }
            pressEnterToContinue(scanner);
        }
        scanner.close();
       

    }
}
