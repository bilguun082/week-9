import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Represents a standard road vehicle in the car dealership system.
 * Implements {@link VehicleInterface} for business pricing and mileage calculations,
 * and {@link Comparable} for sorting and ordering in inventory structures.
 */
public class Vehicle implements VehicleInterface, Comparable<Vehicle> {
    private static final Logger LOGGER = Logger.getLogger(Vehicle.class.getName());

    static {
        Dealership.setupLogging();
    }

    /** The manufacturer or make of the vehicle. */
    protected String brand;

    /** The model designation of the vehicle. */
    protected String model;

    /** The original base catalog price of the vehicle before depreciation or adjustments. */
    protected double basePrice;

    /** The age of the vehicle in years. */
    protected int age;

    /** The total odometer reading of the vehicle in kilometers. */
    protected double km;

    /** The powertrain / fuel type of the vehicle. */
    protected FuelType fuelType;

    /**
     * Default constructor creating an empty vehicle with default values.
     */
    public Vehicle() {
        this("", "", 0.0, 0, 0.0, FuelType.PETROL);
        LOGGER.info("Executing Vehicle default constructor");
    }

    /**
     * Constructs a vehicle with separate brand and model specifications.
     *
     * @param brand     The manufacturer brand
     * @param model     The model name
     * @param basePrice The catalog base price
     * @param age       The age in years
     * @param km        The total kilometers driven
     * @param fuelType  The engine fuel type
     */
    public Vehicle(String brand, String model, double basePrice, int age, double km, FuelType fuelType) {
        this.brand = brand != null ? brand : "";
        this.model = model != null ? model : "";
        this.basePrice = basePrice;
        this.age = age;
        this.km = km;
        this.fuelType = fuelType;
        LOGGER.info(String.format("Executing Vehicle constructor: brand='%s', model='%s', basePrice=%.0f, age=%d, km=%.0f, fuelType=%s",
                this.brand, this.model, this.basePrice, this.age, this.km, this.fuelType));
    }

    /**
     * Constructs a vehicle parsing brand and model from a single combined string.
     *
     * @param brandAndModel Combined brand and model string (e.g., "Toyota Corolla")
     * @param basePrice     The catalog base price
     * @param age           The age in years
     * @param km            The total kilometers driven
     * @param fuelType      The engine fuel type
     */
    public Vehicle(String brandAndModel, double basePrice, int age, double km, FuelType fuelType) {
        this.basePrice = basePrice;
        this.age = age;
        this.km = km;
        this.fuelType = fuelType;
        if (brandAndModel != null && brandAndModel.contains(" ")) {
            int firstSpace = brandAndModel.indexOf(' ');
            this.brand = brandAndModel.substring(0, firstSpace).trim();
            this.model = brandAndModel.substring(firstSpace + 1).trim();
        } else {
            this.brand = brandAndModel != null ? brandAndModel : "";
            this.model = "";
        }
        LOGGER.info(String.format("Executing Vehicle constructor from combined name: brand='%s', model='%s', basePrice=%.0f, age=%d, km=%.0f, fuelType=%s",
                this.brand, this.model, this.basePrice, this.age, this.km, this.fuelType));
    }

    /**
     * Retrieves the brand/make of the vehicle.
     *
     * @return The brand name
     */
    public String getBrand() {
        LOGGER.info("Executing getBrand() -> " + brand);
        return brand;
    }

    /**
     * Updates the brand/make of the vehicle.
     *
     * @param brand The new brand name
     */
    public void setBrand(String brand) {
        LOGGER.info("Executing setBrand(brand='" + brand + "') for " + this.brand + " " + this.model);
        this.brand = brand;
    }

    /**
     * Retrieves the model designation of the vehicle.
     *
     * @return The model name
     */
    public String getModel() {
        LOGGER.info("Executing getModel() -> " + model);
        return model;
    }

    /**
     * Updates the model designation of the vehicle.
     *
     * @param model The new model name
     */
    public void setModel(String model) {
        LOGGER.info("Executing setModel(model='" + model + "') for " + this.brand + " " + this.model);
        this.model = model;
    }

    /**
     * Retrieves the base price of the vehicle.
     *
     * @return The catalog base price
     */
    public double getBasePrice() {
        LOGGER.info("Executing getBasePrice() -> " + basePrice + " for " + brand + " " + model);
        return basePrice;
    }

    /**
     * Updates the base price of the vehicle.
     *
     * @param basePrice The new base price
     */
    public void setBasePrice(double basePrice) {
        LOGGER.info("Executing setBasePrice(basePrice=" + basePrice + ") for " + brand + " " + model);
        this.basePrice = basePrice;
    }

    /**
     * Retrieves the age of the vehicle in years.
     *
     * @return The age in years
     */
    public int getAge() {
        LOGGER.info("Executing getAge() -> " + age + " for " + brand + " " + model);
        return age;
    }

    /**
     * Updates the age of the vehicle in years.
     *
     * @param age The new age
     */
    public void setAge(int age) {
        LOGGER.info("Executing setAge(age=" + age + ") for " + brand + " " + model);
        this.age = age;
    }

    /**
     * Retrieves the current odometer mileage in kilometers.
     *
     * @return The mileage in km
     */
    public double getKm() {
        LOGGER.info("Executing getKm() -> " + km + " for " + brand + " " + model);
        return km;
    }

    /**
     * Updates the odometer mileage in kilometers.
     *
     * @param km The new mileage
     */
    public void setKm(double km) {
        LOGGER.info("Executing setKm(km=" + km + ") for " + brand + " " + model);
        this.km = km;
    }

    /**
     * Retrieves the fuel/powertrain type.
     *
     * @return The {@link FuelType}
     */
    public FuelType getFuelType() {
        LOGGER.info("Executing getFuelType() -> " + fuelType + " for " + brand + " " + model);
        return fuelType;
    }

    /**
     * Updates the fuel/powertrain type.
     *
     * @param fuelType The new {@link FuelType}
     */
    public void setFuelType(FuelType fuelType) {
        LOGGER.info("Executing setFuelType(fuelType=" + fuelType + ") for " + brand + " " + model);
        this.fuelType = fuelType;
    }

    /**
     * Applies a percentage discount to the vehicle's base price.
     *
     * @param percentage The discount percentage (e.g., 10 for 10%)
     */
    @Override
    public void applyDiscount(int percentage) {
        double oldPrice = this.basePrice;
        this.basePrice = this.basePrice * (1.0 - percentage / 100.0);
        LOGGER.info(String.format("Executing applyDiscount(%d%%) on %s %s: base price updated from %.0f to %.0f",
                percentage, brand, model, oldPrice, this.basePrice));
    }

    /**
     * Computes the final selling price considering depreciation, price floor, and fuel type.
     * Formula:
     * - Subtracts 150,000 per year of age from base price.
     * - Ensures a minimum price floor of 200,000.
     * - Multiplies by 1.15 for HYBRID, 1.05 for DIESEL, unchanged for PETROL.
     *
     * @return The final selling price rounded to nearest integer
     */
    @Override
    public int getFinalPrice() {
        double calculatedPrice = basePrice - (age * 150000.0);
        if (calculatedPrice < 200000.0) {
            calculatedPrice = 200000.0;
        }

        if (fuelType == FuelType.HYBRID) {
            calculatedPrice *= 1.15;
        } else if (fuelType == FuelType.DIESEL) {
            calculatedPrice *= 1.05;
        }

        int finalPrice = (int) Math.round(calculatedPrice);
        LOGGER.info(String.format("Executing getFinalPrice() for %s %s -> %,d", brand, model, finalPrice));
        return finalPrice;
    }

    /**
     * Computes the average distance driven per year.
     * If the vehicle's age is 0, returns the current mileage.
     *
     * @return Average kilometers per year
     */
    @Override
    public double averageKmPerYear() {
        double avg = (age == 0) ? km : km / age;
        LOGGER.info(String.format("Executing averageKmPerYear() for %s %s -> %.2f km/yr", brand, model, avg));
        return avg;
    }

    /**
     * Compares this vehicle with another vehicle for ordering.
     * Primary order: ascending by final price.
     * Secondary tie-breakers: brand, model, mileage, and age to maintain distinct elements in collections.
     *
     * @param other The vehicle to compare with
     * @return A negative integer, zero, or a positive integer as this vehicle is less than, equal to, or greater than other
     */
    @Override
    public int compareTo(Vehicle other) {
        if (this == other) {
            return 0;
        }
        if (other == null) {
            return 1;
        }
        int priceComparison = Integer.compare(this.getFinalPrice(), other.getFinalPrice());
        if (priceComparison != 0) {
            LOGGER.info(String.format("Executing compareTo(): %s %s vs %s %s by price -> %d",
                    brand, model, other.brand, other.model, priceComparison));
            return priceComparison;
        }
        int brandComparison = (this.brand != null ? this.brand : "").compareTo(other.brand != null ? other.brand : "");
        if (brandComparison != 0) {
            return brandComparison;
        }
        int modelComparison = (this.model != null ? this.model : "").compareTo(other.model != null ? other.model : "");
        if (modelComparison != 0) {
            return modelComparison;
        }
        int kmComparison = Double.compare(this.km, other.km);
        if (kmComparison != 0) {
            return kmComparison;
        }
        return Integer.compare(this.age, other.age);
    }

    /**
     * Checks if this vehicle is equal to another object based on all attributes.
     *
     * @param o The reference object to compare with
     * @return {@code true} if identical attributes; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        LOGGER.info("Executing equals() for " + brand + " " + model);
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return Double.compare(vehicle.basePrice, basePrice) == 0 &&
                age == vehicle.age &&
                Double.compare(vehicle.km, km) == 0 &&
                Objects.equals(brand, vehicle.brand) &&
                Objects.equals(model, vehicle.model) &&
                fuelType == vehicle.fuelType;
    }

    /**
     * Computes the hash code based on vehicle attributes.
     *
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        LOGGER.info("Executing hashCode() for " + brand + " " + model);
        return Objects.hash(brand, model, basePrice, age, km, fuelType);
    }

    /**
     * Generates a descriptive string representation of the vehicle.
     *
     * @return Formatted vehicle details
     */
    @Override
    public String toString() {
        LOGGER.info("Executing toString() for " + brand + " " + model);
        String fullName = (model == null || model.isEmpty()) ? brand : (brand + " " + model);
        return String.format(Locale.US,
                "Vehicle: %s | Base Price: %,.0f | Age: %d yrs | Mileage: %,.0f km | Fuel: %s | Final Price: %,d | Avg Annual Mileage: %,.2f km/yr",
                fullName, basePrice, age, km, fuelType, getFinalPrice(), averageKmPerYear());
    }
}
