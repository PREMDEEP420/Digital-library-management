import java.util.*;

public class LibraryManagementSystem {
    static Scanner scanner = new Scanner(System.in);
    static List<Book> books = new ArrayList<>();
    static Map<String, User> users = new HashMap<>();
    static User currentUser = null;

    public static void main(String[] args) {
        seedData(); // Preload some data
        while (true) {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> adminMenu();
                case 2 -> userLogin();
                case 3 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Preload data
    static void seedData() {
        books.add(new Book(1, "Java Programming", "Author A", "Programming", true));
        books.add(new Book(2, "Data Structures", "Author B", "Computer Science", true));
        users.put("user@example.com", new User("user@example.com", "password", "User"));
        System.out.println("Data preloaded.");
    }

    // Admin menu
    static void adminMenu() {
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        if (!"admin123".equals(password)) {
            System.out.println("Incorrect password!");
            return;
        }

        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Remove Book");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addBook();
                case 2 -> viewBooks();
                case 3 -> removeBook();
                case 4 -> {
                    System.out.println("Logged out as Admin.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Add a book
    static void addBook() {
        System.out.print("Enter Book ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();

        books.add(new Book(id, title, author, category, true));
        System.out.println("Book added successfully!");
    }

    // Remove a book
    static void removeBook() {
        System.out.print("Enter Book ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        books.removeIf(book -> book.getId() == id);
        System.out.println("Book removed successfully!");
    }

    // View all books
    static void viewBooks() {
        System.out.println("\n--- Book List ---");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    // User login
    static void userLogin() {
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Login successful! Welcome, " + user.getRole() + ".");
            userMenu();
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    // User menu
    static void userMenu() {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> viewBooks();
                case 2 -> borrowBook();
                case 3 -> returnBook();
                case 4 -> {
                    System.out.println("Logged out as User.");
                    currentUser = null;
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Borrow a book
    static void borrowBook() {
        System.out.print("Enter Book ID to borrow: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Book book : books) {
            if (book.getId() == id && book.isAvailable()) {
                book.setAvailable(false);
                System.out.println("Book borrowed successfully!");
                return;
            }
        }
        System.out.println("Book not available or invalid ID.");
    }

    // Return a book
    static void returnBook() {
        System.out.print("Enter Book ID to return: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Book book : books) {
            if (book.getId() == id && !book.isAvailable()) {
                book.setAvailable(true);
                System.out.println("Book returned successfully!");
                return;
            }
        }
        System.out.println("Invalid book ID or the book wasn't borrowed.");
    }
}

// Book class
class Book {
    private int id;
    private String title;
    private String author;
    private String category;
    private boolean available;

    public Book(int id, String title, String author, String category, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Title: %s | Author: %s | Category: %s | Available: %b",
                id, title, author, category, available);
    }
}

// User class
class User {
    private String email;
    private String password;
    private String role;

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
