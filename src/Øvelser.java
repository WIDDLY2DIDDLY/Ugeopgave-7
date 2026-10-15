import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Øvelser {
    private Scanner scan = new Scanner(System.in);

    public int promptNumber(String msg) {
        System.out.println(msg);
        try {
            return Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Skriv venligst et heltal");
            return promptNumber(msg);
        }
    }

    static class PricedDrink {
        private double price;

        public void setPrice(double price) {
            if (price < 0) {
                throw new IllegalArgumentException("Pris må ikke være negativ: " + price);
            }
            this.price = price;
        }

        public double getPrice() {
            return price;
        }
    }

    public String getItem(ArrayList<String> items, int index) {
        if (index < 0 || index >= items.size()) {
            throw new IllegalArgumentException(
                    "Indeks " + index + " er uden for range 0-" + (items.size() - 1)
            );
        }
        return items.get(index);
    }

    public ArrayList<String> readLines(String path) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();
        Scanner fileScan = new Scanner(new File(path));
        while (fileScan.hasNextLine()) {
            lines.add(fileScan.nextLine());
        }
        fileScan.close();
        return lines;
    }

    public void parseDrinks() {
        ArrayList<String> data = new ArrayList<>();
        data.add("Espresso, En kraftfuld og koncentreret kaffe, 25.0");
        data.add("Latte, Espresso med dampet mælk, 45.0");
        data.add("Cola, Kold og brusende, 30.0");
        data.add("Vand, Stille kildevand, gratis");

        double total = 0;
        for (String s : data) {
            String[] values = s.split(",");
            String name = values[0].trim();
            String description = values[1].trim();
            try {
                double price = Double.parseDouble(values[2].trim());
                total += price;
                System.out.println(name + " - " + description + " (" + price + " kr)");
            } catch (NumberFormatException e) {
                System.out.println(name + " - kunne ikke læse pris: " + values[2].trim());
            }
        }
        System.out.println("Samlet pris: " + total + " kr");
    }

    public static void main(String[] args) {
        Øvelser ex = new Øvelser();

        int number = ex.promptNumber("Skriv et tal:");
        System.out.println("Du skrev " + number);

        PricedDrink drink = new PricedDrink();
        try {
            drink.setPrice(25.0);
            System.out.println("Pris sat til " + drink.getPrice());
            drink.setPrice(-5.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Ugyldig pris: " + e.getMessage());
        }

        ArrayList<String> items = new ArrayList<>();
        items.add("Espresso");
        items.add("Latte");
        items.add("Americano");
        try {
            System.out.println(ex.getItem(items, 1));
            System.out.println(ex.getItem(items, 5));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            for (String line : ex.readLines("data/menu.csv")) {
                System.out.println(line);
            }
            ex.readLines("data/findes-ikke.csv");
        } catch (FileNotFoundException e) {
            System.out.println("Filen blev ikke fundet");
        }

        ex.parseDrinks();
    }
}
