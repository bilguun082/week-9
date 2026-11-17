import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Manages the inventory, reporting, and file I/O operations of the car dealership.
 * Maintains vehicles in a sorted {@link TreeSet} according to pricing and tie-breaker criteria.
 */
public class Dealership {
    private static final Logger LOGGER = Logger.getLogger(Dealership.class.getName());

    static {
        setupLogging();
    }

    /** The sorted inventory of vehicles managed by the dealership. */
    private TreeSet<Vehicle> vehicles;

    /**
     * Initializes the logging framework, adding a {@link FileHandler} for {@code cardealerApp.log}
     * and setting the logging level to {@link Level#INFO}.
     */
    public static synchronized void setupLogging() {
        try {
            Logger rootLogger = Logger.getLogger("");
            boolean hasFileHandler = false;
            for (Handler handler : rootLogger.getHandlers()) {
                if (handler instanceof FileHandler) {
                    hasFileHandler = true;
                    break;
                }
            }

            if (!hasFileHandler) {
                FileHandler fileHandler = new FileHandler("cardealerApp.log", true);
                fileHandler.setFormatter(new SimpleFormatter());
                fileHandler.setLevel(Level.INFO);
                rootLogger.addHandler(fileHandler);
            }
            rootLogger.setLevel(Level.INFO);
            LOGGER.info("Executing setupLogging(): logging initialized successfully with output to 'cardealerApp.log'");
        } catch (IOException | SecurityException e) {
            LOGGER.log(Level.SEVERE, "Exception caught while initializing FileHandler for 'cardealerApp.log': " + e.getMessage(), e);
        }
    }

    /**
     * Constructs a new empty Dealership instance with an empty inventory.
     */
    public Dealership() {
        this.vehicles = new TreeSet<>();
        LOGGER.info("Executing Dealership constructor: initialized empty inventory TreeSet");
    }

    /**
     * Retrieves the set of vehicles currently in inventory.
     *
     * @return A {@link TreeSet} containing all vehicles sorted by final price and attributes
     */
    public TreeSet<Vehicle> getVehicles() {
        LOGGER.info("Executing getVehicles(): returning inventory containing " + vehicles.size() + " vehicles");
        return vehicles;
    }

    /**
     * Adds a vehicle to the dealership inventory.
     *
     * @param vehicle The {@link Vehicle} to add (ignored if null)
     */
    public void addVehicle(Vehicle vehicle) {
        if (vehicle != null) {
            vehicles.add(vehicle);
            LOGGER.info(String.format("Executing addVehicle(): added %s %s to inventory (total count: %d)",
                    vehicle.getBrand(), vehicle.getModel(), vehicles.size()));
        } else {
            LOGGER.info("Executing addVehicle(): ignored null vehicle argument");
        }
    }

    /**
     * Reads and parses vehicle records from a delimited text file into the inventory TreeSet.
     * Expected format:
     * <ul>
     *   <li>VEHICLE#Brand Model#basePrice#age#km#fuelType</li>
     *   <li>ELECTRIC#Brand Model#basePrice#age#km#fuelType#batteryCapacity#hasHeatPump</li>
     * </ul>
     * Falls back to predefined hardcoded instances if the file cannot be read or parsed.
     *
     * @param filename Path to the file (e.g., "vehicles.txt")
     * @return {@code true} if loaded successfully from file, {@code false} if fallback was used
     */
    public boolean loadVehiclesFromFile(String filename) {
        LOGGER.info("Executing loadVehiclesFromFile() with filename: " + filename);
        File file = new File(filename);
        if (!file.exists() || !file.canRead()) {
            LOGGER.info("Warning: Unable to read file '" + filename + "'. Invoking fallback predefined instances.");
            System.err.println("Warning: Unable to read file '" + filename + "'. Falling back to predefined instances.");
            loadPredefinedInstances();
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("//")) {
                    continue;
                }

                try {
                    String[] parts = line.split("#");
                    if (parts.length < 6) {
                        LOGGER.info("Skipping invalid record format at line " + lineNumber + ": '" + line + "'");
                        continue;
                    }

                    String type = parts[0].trim().toUpperCase();
                    String brandAndModel = parts[1].trim();
                    double basePrice = Double.parseDouble(parts[2].trim());
                    int age = Integer.parseInt(parts[3].trim());
                    double km = Double.parseDouble(parts[4].trim());
                    FuelType fuelType = FuelType.valueOf(parts[5].trim().toUpperCase());

                    if (type.equals("ELECTRIC") && parts.length >= 8) {
                        int batteryCapacity = Integer.parseInt(parts[6].trim());
                        String heatPumpStr = parts[7].trim().toLowerCase();
                        boolean hasHeatPump = heatPumpStr.equals("yes") || heatPumpStr.equals("true");

                        ElectricVehicle ev = new ElectricVehicle(brandAndModel, basePrice, age, km, fuelType, batteryCapacity, hasHeatPump);
                        vehicles.add(ev);
                        LOGGER.info("Parsed ElectricVehicle: " + brandAndModel + " from line " + lineNumber);
                    } else if (type.equals("VEHICLE")) {
                        Vehicle v = new Vehicle(brandAndModel, basePrice, age, km, fuelType);
                        vehicles.add(v);
                        LOGGER.info("Parsed Vehicle: " + brandAndModel + " from line " + lineNumber);
                    }
                } catch (Exception lineEx) {
                    LOGGER.log(Level.SEVERE, "Exception caught while parsing vehicle record at line " + lineNumber + " ('" + line + "'): " + lineEx.getMessage(), lineEx);
                }
            }
            LOGGER.info(String.format("Successfully finished loading file '%s'. Inventory now has %d vehicles.", filename, vehicles.size()));
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception caught while reading '" + filename + "': " + e.getMessage() + ". Falling back to predefined instances.", e);
            System.err.println("Error reading '" + filename + "': " + e.getMessage() + ". Falling back to predefined instances.");
            vehicles.clear();
            loadPredefinedInstances();
            return false;
        }
    }

    /**
     * Loads predefined hardcoded vehicle instances into the inventory as a fallback.
     */
    public void loadPredefinedInstances() {
        LOGGER.info("Executing loadPredefinedInstances(): clearing inventory and loading fallback sample vehicles");
        vehicles.clear();
        vehicles.add(new Vehicle("Toyota Corolla", 5500000, 4, 75000, FuelType.HYBRID));
        vehicles.add(new Vehicle("Volkswagen Golf", 4200000, 6, 120000, FuelType.DIESEL));
        vehicles.add(new Vehicle("Ford Focus", 3100000, 8, 145000, FuelType.PETROL));
        vehicles.add(new Vehicle("BMW 320d", 7800000, 3, 60000, FuelType.DIESEL));
        vehicles.add(new Vehicle("Opel Astra", 2200000, 11, 190000, FuelType.PETROL));
        vehicles.add(new ElectricVehicle("Nissan Leaf", 4800000, 5, 65000, FuelType.HYBRID, 40, false));
        vehicles.add(new ElectricVehicle("Tesla Model 3", 11500000, 2, 38000, FuelType.HYBRID, 75, true));
        vehicles.add(new ElectricVehicle("Renault Zoe", 3900000, 6, 82000, FuelType.HYBRID, 52, true));
        vehicles.add(new ElectricVehicle("Kia EV6", 13200000, 1, 15000, FuelType.HYBRID, 77, true));
        vehicles.add(new ElectricVehicle("VW ID.3", 7100000, 4, 54000, FuelType.HYBRID, 58, false));
        LOGGER.info("Completed loading predefined fallback instances. Total vehicles: " + vehicles.size());
    }

    /**
     * Main application entry point. Loads inventory, generates analytical report,
     * logs operations to console and 'cardealerApp.log', and writes output to 'outputDealership.txt'.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        LOGGER.info("Executing Dealership.main(): Starting Car Dealership Management System application");
        Dealership dealership = new Dealership();
        String inputFilename = "vehicles.txt";
        dealership.loadVehiclesFromFile(inputFilename);

        List<String> outputLines = new ArrayList<>();

        outputLines.add("================================================================================");
        outputLines.add("                  CAR DEALERSHIP MANAGEMENT SYSTEM - REPORT                     ");
        outputLines.add("================================================================================");
        outputLines.add("");

        // 1. Fleet Average Mileage
        double totalKm = 0.0;
        for (Vehicle v : dealership.getVehicles()) {
            totalKm += v.getKm();
        }
        double avgKm = dealership.getVehicles().isEmpty() ? 0.0 : totalKm / dealership.getVehicles().size();
        outputLines.add(String.format(Locale.US, "1. Fleet Average Mileage: %,.2f km", avgKm));
        outputLines.add("");
        LOGGER.info(String.format("Calculated Fleet Average Mileage: %,.2f km", avgKm));

        // 2. Cheapest Vehicle (TreeSet first element since sorted by price)
        outputLines.add("2. Cheapest Vehicle:");
        if (!dealership.getVehicles().isEmpty()) {
            Vehicle cheapest = dealership.getVehicles().first();
            outputLines.add("   " + cheapest.toString());
            LOGGER.info("Identified Cheapest Vehicle: " + cheapest.getBrand() + " " + cheapest.getModel());
        } else {
            outputLines.add("   No vehicles in inventory.");
            LOGGER.info("No vehicles available in inventory to determine cheapest.");
        }
        outputLines.add("");

        // 3. Top Electric Vehicle (highest final price among EVs)
        ElectricVehicle topElectric = null;
        for (Vehicle v : dealership.getVehicles()) {
            if (v instanceof ElectricVehicle) {
                ElectricVehicle ev = (ElectricVehicle) v;
                if (topElectric == null || ev.getFinalPrice() > topElectric.getFinalPrice()) {
                    topElectric = ev;
                }
            }
        }
        outputLines.add("3. Top Electric Vehicle:");
        if (topElectric != null) {
            String name = (topElectric.getModel() == null || topElectric.getModel().isEmpty())
                    ? topElectric.getBrand()
                    : topElectric.getBrand() + " " + topElectric.getModel();
            outputLines.add(String.format(Locale.US,
                    "   Vehicle: %s | Final Price: %,d | Battery Capacity: %d kWh | Average Annual Mileage: %,.2f km/yr",
                    name, topElectric.getFinalPrice(), topElectric.getBatteryCapacity(), topElectric.averageKmPerYear()));
            LOGGER.info(String.format("Identified Top Electric Vehicle: %s with final price %,d", name, topElectric.getFinalPrice()));
        } else {
            outputLines.add("   No electric vehicles found.");
            LOGGER.info("No electric vehicles found in inventory.");
        }
        outputLines.add("");

        // 4. Total Fleet Inventory Value
        long totalInventoryValue = 0L;
        for (Vehicle v : dealership.getVehicles()) {
            totalInventoryValue += v.getFinalPrice();
        }
        outputLines.add(String.format(Locale.US, "4. Total Fleet Inventory Value: %,d", totalInventoryValue));
        outputLines.add("");
        LOGGER.info(String.format("Calculated Total Fleet Inventory Value: %,d", totalInventoryValue));

        // 5. Filtered Hybrid Inventory
        double averagePrice = dealership.getVehicles().isEmpty() ? 0.0 : (double) totalInventoryValue / dealership.getVehicles().size();
        outputLines.add(String.format(Locale.US,
                "5. Filtered Hybrid Inventory (FuelType == HYBRID and Final Price <= Average Price of %,.2f):",
                averagePrice));
        boolean foundHybrid = false;
        for (Vehicle v : dealership.getVehicles()) {
            if (v.getFuelType() == FuelType.HYBRID && v.getFinalPrice() <= averagePrice) {
                outputLines.add("   - " + v.toString());
                foundHybrid = true;
            }
        }
        if (!foundHybrid) {
            outputLines.add("   (None)");
        }
        outputLines.add("");
        LOGGER.info(String.format("Filtered Hybrid Inventory processed with average price cutoff %,.2f", averagePrice));

        // 6. Young Inventory Count
        int youngCount = 0;
        for (Vehicle v : dealership.getVehicles()) {
            if (v.getAge() < 5) {
                youngCount++;
            }
        }
        outputLines.add("6. Young Inventory Count (age < 5): " + youngCount);
        outputLines.add("");
        LOGGER.info("Counted Young Inventory (< 5 yrs): " + youngCount);

        // 7. Discount Simulation
        outputLines.add("7. Discount Simulation:");
        if (!dealership.getVehicles().isEmpty()) {
            Vehicle targetVehicle = dealership.getVehicles().first();
            for (Vehicle v : dealership.getVehicles()) {
                if (v.getBrand().contains("Toyota") || v.getModel().contains("Corolla")) {
                    targetVehicle = v;
                    break;
                }
            }

            String targetName = (targetVehicle.getModel() == null || targetVehicle.getModel().isEmpty())
                    ? targetVehicle.getBrand()
                    : targetVehicle.getBrand() + " " + targetVehicle.getModel();

            int priceBeforeDiscount = targetVehicle.getFinalPrice();
            double basePriceBefore = targetVehicle.getBasePrice();

            outputLines.add("   Selected Vehicle: " + targetName);
            outputLines.add(String.format(Locale.US, "   Original Base Price: %,.0f | Original Final Price: %,d",
                    basePriceBefore, priceBeforeDiscount));

            targetVehicle.applyDiscount(10);

            int priceAfterDiscount = targetVehicle.getFinalPrice();
            double basePriceAfter = targetVehicle.getBasePrice();

            outputLines.add(String.format(Locale.US,
                    "   After 10%% Discount -> Updated Base Price: %,.0f | Updated Final Price: %,d",
                    basePriceAfter, priceAfterDiscount));
            LOGGER.info(String.format("Discount Simulation performed on %s: base price %.0f -> %.0f, final price %,d -> %,d",
                    targetName, basePriceBefore, basePriceAfter, priceBeforeDiscount, priceAfterDiscount));
        } else {
            outputLines.add("   No vehicle available for discount simulation.");
            LOGGER.info("No vehicle available for discount simulation.");
        }
        outputLines.add("");
        outputLines.add("================================================================================");

        // Display to console and write to outputDealership.txt
        String outputFilename = "outputDealership.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilename))) {
            for (String line : outputLines) {
                System.out.println(line);
                writer.println(line);
            }
            System.out.println("\n[Output successfully written to " + outputFilename + "]");
            LOGGER.info("Successfully wrote final report to " + outputFilename);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception caught while writing output report to '" + outputFilename + "': " + e.getMessage(), e);
            System.err.println("Error writing to " + outputFilename + ": " + e.getMessage());
        }
    }
}
