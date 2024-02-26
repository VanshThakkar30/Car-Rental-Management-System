import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Car class to represent a car
class Car {
    private String make;
    private String model;
    private String licensePlate;
    private boolean available;

    public Car(String make, String model, String licensePlate) {
        this.make = make;
        this.model = model;
        this.licensePlate = licensePlate;
        this.available = true;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void rent() {
        available = false;
    }

    public void returnCar() {
        available = true;
    }

    @Override
    public String toString() {
        return make + " " + model + " (" + licensePlate + ")";
    }
}

// CarRentalSystem class to manage cars and reservations
class CarRentalSystem {
    private List<Car> cars = new ArrayList<>();

    public void addCar(Car car) {
        cars.add(car);
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Car> getAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            if (car.isAvailable()) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }
}

// CarRentalGUI class for the graphical user interface
class CarRentalGUI {
    private CarRentalSystem rentalSystem;

    public CarRentalGUI(CarRentalSystem rentalSystem) {
        this.rentalSystem = rentalSystem;

        JFrame frame = new JFrame("Car Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        frame.add(panel);

        JButton rentButton = new JButton("Rent Car");
        JButton returnButton = new JButton("Return Car");
        JTextArea textArea = new JTextArea(10, 30);

        panel.add(rentButton);
        panel.add(returnButton);
        panel.add(textArea);

        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Car> availableCars = rentalSystem.getAvailableCars();
                if (availableCars.isEmpty()) {
                    textArea.setText("No cars available for rent.");
                } else {
                    // Implement code to handle renting a car
                    // You can display availableCars to the user and let them choose a car to rent
                }
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement code to handle returning a rented car
            }
        });

        frame.setVisible(true);
    }
}

public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("Toyota", "Camry", "ABC123");
        Car car2 = new Car("Honda", "Civic", "XYZ789");
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CarRentalGUI(rentalSystem);
            }
        });
    }
}
