import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ListMaker {

    private static List<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentFileName = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("A - Add an item");
            System.out.println("D - Delete an item");
            System.out.println("I - Insert an item");
            System.out.println("V - View the list");
            System.out.println("M - Move an item");
            System.out.println("O - Open a list file");
            System.out.println("S - Save the current list");
            System.out.println("C - Clear the list");
            System.out.println("Q - Quit");

            String choice = scanner.nextLine().toUpperCase();

            try {
                switch (choice) {
                    case "A":
                        addItem(scanner);
                        break;
                    case "D":
                        deleteItem(scanner);
                        break;
                    case "I":
                        insertItem(scanner);
                        break;
                    case "V":
                        viewList();
                        break;
                    case "M":
                        moveItem(scanner);
                        break;
                    case "O":
                        openFile(scanner);
                        break;
                    case "S":
                        saveFile();
                        break;
                    case "C":
                        clearList();
                        break;
                    case "Q":
                        quitProgram();
                        return; // Exit the program
                    default:
                        System.out.println("Invalid option. Try again.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    // Add item to the list
    private static void addItem(Scanner scanner) {
        System.out.print("Enter the item to add: ");
        String item = scanner.nextLine();
        list.add(item);
        needsToBeSaved = true;
    }

    // Delete item from the list
    private static void deleteItem(Scanner scanner) {
        System.out.print("Enter the index of the item to delete: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index < list.size()) {
            list.remove(index);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    // Insert item into the list
    private static void insertItem(Scanner scanner) {
        System.out.print("Enter the index to insert the item at: ");
        int index = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the item to insert: ");
        String item = scanner.nextLine();
        if (index >= 0 && index <= list.size()) {
            list.add(index, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    // View the list
    private static void viewList() {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + ": " + list.get(i));
            }
        }
    }

    // Move item in the list
    private static void moveItem(Scanner scanner) {
        System.out.print("Enter the index of the item to move: ");
        int fromIndex = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the new index for the item: ");
        int toIndex = Integer.parseInt(scanner.nextLine());

        if (fromIndex >= 0 && fromIndex < list.size() && toIndex >= 0 && toIndex < list.size()) {
            String item = list.remove(fromIndex);
            list.add(toIndex, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid indices.");
        }
    }

    // Open a list file from disk
    private static void openFile(Scanner scanner) throws IOException {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Do you want to save them? (Y/N): ");
            String saveChoice = scanner.nextLine();
            if (saveChoice.equalsIgnoreCase("Y")) {
                saveFile();
            }
        }

        System.out.print("Enter the filename to open: ");
        String fileName = scanner.nextLine();
        currentFileName = fileName;

        try {
            list = Files.readAllLines(Paths.get(fileName));
            needsToBeSaved = false;
        } catch (IOException e) {
            System.out.println("Error opening the file: " + e.getMessage());
        }
    }

    // Save the current list to disk
    private static void saveFile() throws IOException {
        if (currentFileName.isEmpty()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the filename to save as: ");
            currentFileName = scanner.nextLine();
        }

        Files.write(Paths.get(currentFileName), list);
        needsToBeSaved = false;
    }

    // Clear the list
    private static void clearList() {
        list.clear();
        needsToBeSaved = true;
    }

    // Prompt the user to save before quitting
    private static void quitProgram() throws IOException {
        if (needsToBeSaved) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("You have unsaved changes. Do you want to save them? (Y/N): ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                saveFile();
            }
        }
    }
}
