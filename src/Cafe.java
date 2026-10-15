import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Cafe {
    private static final String FILE_PATH = "data/menu.csv";
    private ArrayList<Drink> coffeeMenu = new ArrayList<>();
    private Scanner scan = new Scanner(System.in);

    public void loadData() {
        coffeeMenu.clear();
        try {
            Scanner fileScan = new Scanner(new File(FILE_PATH));
            if (fileScan.hasNextLine()) {
                fileScan.nextLine();
            }
            while (fileScan.hasNextLine()) {
                String line = fileScan.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] values = line.split(",");
                try {
                    String name = values[0].trim();
                    int price = Integer.parseInt(values[1].trim());
                    int sold = Integer.parseInt(values[2].trim());
                    coffeeMenu.add(new Drink(name, price, sold));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Springer ugyldig linje over: " + line);
                }
            }
            fileScan.close();
        } catch (FileNotFoundException e) {
            System.out.println("Filen blev ikke fundet: " + FILE_PATH);
        }
    }

    public void saveData() {
        try {
            PrintWriter writer = new PrintWriter(new File(FILE_PATH));
            writer.println("Name, Price, SoldNumber");
            for (Drink drink : coffeeMenu) {
                writer.println(drink);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Kunne ikke gemme til filen: " + FILE_PATH);
        }
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("--- Menu ---");
            for (int i = 0; i < coffeeMenu.size(); i++) {
                Drink drink = coffeeMenu.get(i);
                System.out.println((i + 1) + ". " + drink.getName() + " - " + drink.getPrice() + " kr");
            }
            System.out.println("0. Afslut");

            int choice = promptNumber("Vælg et nummer:");
            if (choice == 0) {
                running = false;
            } else if (choice < 1 || choice > coffeeMenu.size()) {
                System.out.println("Ugyldigt valg, vælg mellem 0 og " + coffeeMenu.size());
            } else {
                Drink drink = coffeeMenu.get(choice - 1);
                System.out.println("Du skal betale " + drink.getPrice() + " kr for en " + drink.getName());
                drink.setSoldNumber(drink.getSoldNumber() + 1);
                saveData();
            }
        }
        System.out.println("Tak for besøget!");
    }

    public int promptNumber(String msg) {
        System.out.println(msg);
        try {
            return Integer.parseInt(scan.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Skriv venligst et heltal");
            return promptNumber(msg);
        }
    }

    public void start() {
        loadData();
        showMenu();
    }
}
