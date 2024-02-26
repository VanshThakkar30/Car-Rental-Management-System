import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// define an abstract class for Vehicle
abstract class Vehicle {
    private String vehicleId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    // constructor to initialize vehicle properties
    public Vehicle(String vehicleId, String brand, String model, double basePricePerDay) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    // getter methods to access vehicle information
    public String getVehicleId() {
        return vehicleId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnVehicle() {
        isAvailable = true;
    }
}

// create a concrete class for Car, which is a type of Vehicle
class Car extends Vehicle {
    public Car(String vehicleId, String brand, String model, double basePricePerDay) {
        super(vehicleId, brand, model, basePricePerDay);
    }
}

// define a class for Customer
class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

// create an interface for Rental operations
interface Rentalable {
    void rent();

    void returnVehicle();
}

// define a class for Rental, which implements the Rentalable interface
class Rental implements Rentalable {
    private Vehicle vehicle;
    private Customer customer;
    private int days;

    // constructor to initialize rental information
    public Rental(Vehicle vehicle, Customer customer, int days) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.days = days;
    }

    // implement the rent method as specified in the interface
    public void rent() {
        vehicle.rent();
    }

    // implement the returnVehicle method as specified in the interface
    public void returnVehicle() {
        vehicle.returnVehicle();
    }

    // getter methods to access rental information
    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

// Create a class for the Car Rental System
class CarRentalSystem {
    private List<Vehicle> vehicles;
    private List<Customer> customers;
    private List<Rentalable> rentals;

    // Constructor to initialize the system
    public CarRentalSystem() {
        vehicles = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    // method to rent a vehicle
    public void rentVehicle(Vehicle vehicle, Customer customer, int days) {
        if (vehicle.isAvailable()) {
            Rentalable rental = new Rental(vehicle, customer, days);
            rental.rent();
            rentals.add(rental);
        } else {
            System.out.println("Vehicle is not available for rent.");
        }
    }

    // method to return a vehicle
    public void returnVehicle(Vehicle vehicle) {
        Rentalable rentalToRemove = null;
        for (Rentalable rental : rentals) {
            if (((Rental) rental).getVehicle() == vehicle) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentalToRemove.returnVehicle();
            rentals.remove(rentalToRemove);
        } else {
            System.out.println("Vehicle was not rented.");
        }
    }

    // method to display the menu for the rental system
    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== Vehicle Rental System =====");
            System.out.println("1. Rent a Vehicle");
            System.out.println("2. Return a Vehicle");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("\n== Rent a Vehicle ==\n");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Vehicles:");
                for (Vehicle vehicle : vehicles) {
                    if (vehicle.isAvailable()) {
                        System.out.println(
                                vehicle.getVehicleId() + " - " + vehicle.getBrand() + " " + vehicle.getModel());
                    }
                }

                System.out.print("\nEnter the vehicle ID you want to rent: ");
                String vehicleId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Vehicle selectedVehicle = null;
                for (Vehicle vehicle : vehicles) {
                    if (vehicle.getVehicleId().equals(vehicleId) && vehicle.isAvailable()) {
                        selectedVehicle = vehicle;
                        break;
                    }
                }

                if (selectedVehicle != null) {
                    double totalPrice = selectedVehicle.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Vehicle: " + selectedVehicle.getBrand() + " " + selectedVehicle.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentVehicle(selectedVehicle, newCustomer, rentalDays);
                        System.out.println("\nVehicle rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid vehicle selection or vehicle not available for rent.");
                }

            } else if (choice == 2) {

                System.out.println("\n== Return a Vehicle ==\n");
                System.out.print("Enter the vehicle ID you want to return: ");
                String vehicleId = scanner.nextLine();

                Vehicle vehicleToReturn = null;
                for (Vehicle vehicle : vehicles) {

                    if (vehicle.getVehicleId().equals(vehicleId) && !vehicle.isAvailable()) {
                        vehicleToReturn = vehicle;
                        break;
                    }
                }

                if (vehicleToReturn != null) {
                    Customer customer = null;
                    for (Rentalable rental : rentals) {
                        if (((Rental) rental).getVehicle() == vehicleToReturn) {
                            customer = ((Rental) rental).getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnVehicle(vehicleToReturn);
                        System.out.println("Vehicle returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Vehicle was not rented or rental information is missing.");
                    }

                } else {
                    System.out.println("Invalid vehicle ID or vehicle is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Vehicle Rental System!");
    }
}

// Main class for the application
public class VehicleRental {
    public static void main(String[] args) {
        // Create an instance of the CarRentalSystem
        CarRentalSystem rentalSystem = new CarRentalSystem();

        // Create and add some sample Car objects to the system
        Car car1 = new Car("C001", "Toyota", "Camry", 60.0);
        Car car2 = new Car("C002", "Honda", "Accord", 70.0);
        Car car3 = new Car("C003", "Mahindra", "Thar", 150.0);
        rentalSystem.addVehicle(car1);
        rentalSystem.addVehicle(car2);
        rentalSystem.addVehicle(car3);

        // Start the menu for the Car Rental System
        rentalSystem.menu();
    }
}
