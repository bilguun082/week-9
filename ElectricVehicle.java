import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Represents a specialized electric vehicle (EV) extending {@link Vehicle}
 * and implementing {@link ElectricInterface}.
 * Models battery capacity and heat pump climate efficiency options.
 */
public class ElectricVehicle extends Vehicle implements ElectricInterface {
    private static final Logger LOGGER = Logger.getLogger(ElectricVehicle.class.getName());

    static {
        Dealership.setupLogging();
    }

    /** The battery pack capacity in kilowatt-hours (kWh). */
    protected int batteryCapacity;

    /** Indicates whether the electric vehicle is equipped with an energy-saving heat pump. */
    protected boolean hasHeatPump;

    /**
     * Default constructor creating an electric vehicle with default zero values and HYBRID fuel type.
     */
    public ElectricVehicle() {
        super("", "", 0.0, 0, 0.0, FuelType.HYBRID);
        this.batteryCapacity = 0;
        this.hasHeatPump = false;
        LOGGER.info("Executing ElectricVehicle default constructor");
    }

    /**
     * Constructs an electric vehicle with full attribute specification including explicit fuel type.
     *
     * @param brand           The vehicle manufacturer brand
     * @param model           The vehicle model name
     * @param basePrice       The catalog base price
     * @param age             The age of the vehicle in years
     * @param km              The total kilometers driven
     * @param fuelType        The fuel type (defaults to HYBRID if null)
     * @param batteryCapacity The battery pack capacity in kWh
     * @param hasHeatPump     {@code true} if equipped with a heat pump, {@code false} otherwise
     */
    public ElectricVehicle(String brand, String model, double basePrice, int age, double km, FuelType fuelType, int batteryCapacity, boolean hasHeatPump) {
        super(brand, model, basePrice, age, km, fuelType != null ? fuelType : FuelType.HYBRID);
        this.batteryCapacity = batteryCapacity;
        this.hasHeatPump = hasHeatPump;
        LOGGER.info(String.format("Executing ElectricVehicle constructor: %s %s, battery=%d kWh, heatPump=%b",
                this.brand, this.model, this.batteryCapacity, this.hasHeatPump));
    }

    /**
     * Constructs an electric vehicle with default HYBRID fuel type.
     *
     * @param brand           The vehicle manufacturer brand
     * @param model           The vehicle model name
     * @param basePrice       The catalog base price
     * @param age             The age of the vehicle in years
     * @param km              The total kilometers driven
     * @param batteryCapacity The battery pack capacity in kWh
     * @param hasHeatPump     {@code true} if equipped with a heat pump, {@code false} otherwise
     */
    public ElectricVehicle(String brand, String model, double basePrice, int age, double km, int batteryCapacity, boolean hasHeatPump) {
        super(brand, model, basePrice, age, km, FuelType.HYBRID);
        this.batteryCapacity = batteryCapacity;
        this.hasHeatPump = hasHeatPump;
        LOGGER.info(String.format("Executing ElectricVehicle constructor: %s %s, battery=%d kWh, heatPump=%b",
                this.brand, this.model, this.batteryCapacity, this.hasHeatPump));
    }

    /**
     * Constructs an electric vehicle from a combined brand and model string with explicit fuel type.
     *
     * @param brandAndModel   Combined brand and model string (e.g. "Tesla Model 3")
     * @param basePrice       The catalog base price
     * @param age             The age of the vehicle in years
     * @param km              The total kilometers driven
     * @param fuelType        The fuel type
     * @param batteryCapacity The battery pack capacity in kWh
     * @param hasHeatPump     {@code true} if equipped with a heat pump, {@code false} otherwise
     */
    public ElectricVehicle(String brandAndModel, double basePrice, int age, double km, FuelType fuelType, int batteryCapacity, boolean hasHeatPump) {
        super(brandAndModel, basePrice, age, km, fuelType != null ? fuelType : FuelType.HYBRID);
        this.batteryCapacity = batteryCapacity;
        this.hasHeatPump = hasHeatPump;
        LOGGER.info(String.format("Executing ElectricVehicle constructor from combined name '%s': battery=%d kWh, heatPump=%b",
                brandAndModel, this.batteryCapacity, this.hasHeatPump));
    }

    /**
     * Constructs an electric vehicle from a combined brand and model string with default HYBRID fuel type.
     *
     * @param brandAndModel   Combined brand and model string (e.g. "Nissan Leaf")
     * @param basePrice       The catalog base price
     * @param age             The age of the vehicle in years
     * @param km              The total kilometers driven
     * @param batteryCapacity The battery pack capacity in kWh
     * @param hasHeatPump     {@code true} if equipped with a heat pump, {@code false} otherwise
     */
    public ElectricVehicle(String brandAndModel, double basePrice, int age, double km, int batteryCapacity, boolean hasHeatPump) {
        super(brandAndModel, basePrice, age, km, FuelType.HYBRID);
        this.batteryCapacity = batteryCapacity;
        this.hasHeatPump = hasHeatPump;
        LOGGER.info(String.format("Executing ElectricVehicle constructor from combined name '%s': battery=%d kWh, heatPump=%b",
                brandAndModel, this.batteryCapacity, this.hasHeatPump));
    }

    /**
     * Retrieves the battery capacity in kWh.
     *
     * @return The battery capacity in kWh
     */
    public int getBatteryCapacity() {
        LOGGER.info("Executing getBatteryCapacity() -> " + batteryCapacity + " kWh for " + brand + " " + model);
        return batteryCapacity;
    }

    /**
     * Updates the battery capacity in kWh.
     *
     * @param batteryCapacity The new battery capacity in kWh
     */
    public void setBatteryCapacity(int batteryCapacity) {
        LOGGER.info("Executing setBatteryCapacity(batteryCapacity=" + batteryCapacity + " kWh) for " + brand + " " + model);
        this.batteryCapacity = batteryCapacity;
    }

    /**
     * Checks if the vehicle has a heat pump installed (Bean-style getter).
     *
     * @return {@code true} if heat pump is present; {@code false} otherwise
     */
    public boolean isHasHeatPump() {
        LOGGER.info("Executing isHasHeatPump() -> " + hasHeatPump + " for " + brand + " " + model);
        return hasHeatPump;
    }

    /**
     * Checks if the vehicle has a heat pump installed.
     *
     * @return {@code true} if heat pump is present; {@code false} otherwise
     */
    public boolean hasHeatPump() {
        LOGGER.info("Executing hasHeatPump() -> " + hasHeatPump + " for " + brand + " " + model);
        return hasHeatPump;
    }

    /**
     * Updates the heat pump equipment status.
     *
     * @param hasHeatPump {@code true} to equip heat pump; {@code false} otherwise
     */
    public void setHasHeatPump(boolean hasHeatPump) {
        LOGGER.info("Executing setHasHeatPump(hasHeatPump=" + hasHeatPump + ") for " + brand + " " + model);
        this.hasHeatPump = hasHeatPump;
    }

    /**
     * Computes the final price for an electric vehicle.
     * Extends {@link Vehicle#getFinalPrice()}:
     * - Multiplies price by 1.10 (10% premium) if battery capacity is at least 60 kWh.
     * - Multiplies price by 1.05 (5% premium) if equipped with a heat pump.
     *
     * @return Final calculated selling price rounded to nearest integer
     */
    @Override
    public int getFinalPrice() {
        double price = super.getFinalPrice();
        if (batteryCapacity >= 60) {
            price *= 1.10;
        }
        if (hasHeatPump) {
            price *= 1.05;
        }
        int finalPrice = (int) Math.round(price);
        LOGGER.info(String.format("Executing ElectricVehicle.getFinalPrice() for %s %s -> %,d", brand, model, finalPrice));
        return finalPrice;
    }

    /**
     * Evaluates whether another vehicle belongs to the same range category (within 5 kWh difference).
     *
     * @param other The vehicle to compare against
     * @return {@code true} if other is an ElectricVehicle and battery capacity differs by at most 5 kWh
     */
    @Override
    public boolean hasSameRangeCategory(Vehicle other) {
        LOGGER.info(String.format("Executing hasSameRangeCategory() comparing %s %s with %s",
                brand, model, (other != null ? other.getBrand() + " " + other.getModel() : "null")));
        if (other instanceof ElectricVehicle) {
            ElectricVehicle ev = (ElectricVehicle) other;
            boolean sameCategory = Math.abs(this.batteryCapacity - ev.batteryCapacity) <= 5;
            LOGGER.info(String.format("Range category comparison result between %s (%d kWh) and %s (%d kWh): %b",
                    brand + " " + model, this.batteryCapacity, ev.brand + " " + ev.model, ev.batteryCapacity, sameCategory));
            return sameCategory;
        }
        return false;
    }

    /**
     * Calculates estimated financial value of the battery package (25,000 units per kWh).
     *
     * @return The monetary value share of the battery package
     */
    @Override
    public int batteryCostShare() {
        int share = this.batteryCapacity * 25000;
        LOGGER.info(String.format("Executing batteryCostShare() for %s %s (%d kWh) -> %,d", brand, model, batteryCapacity, share));
        return share;
    }

    /**
     * Checks equality with another object including electric vehicle specific fields.
     *
     * @param o Object to compare with
     * @return {@code true} if objects are identical; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        LOGGER.info("Executing equals() on ElectricVehicle " + brand + " " + model);
        if (this == o) return true;
        if (!(o instanceof ElectricVehicle)) return false;
        if (!super.equals(o)) return false;
        ElectricVehicle that = (ElectricVehicle) o;
        return batteryCapacity == that.batteryCapacity && hasHeatPump == that.hasHeatPump;
    }

    /**
     * Generates hash code including electric vehicle specific fields.
     *
     * @return Hash code integer
     */
    @Override
    public int hashCode() {
        LOGGER.info("Executing hashCode() on ElectricVehicle " + brand + " " + model);
        return Objects.hash(super.hashCode(), batteryCapacity, hasHeatPump);
    }

    /**
     * Returns string representation appended with battery and heat pump details.
     *
     * @return Detailed string representation
     */
    @Override
    public String toString() {
        LOGGER.info("Executing toString() on ElectricVehicle " + brand + " " + model);
        return super.toString() + String.format(Locale.US,
                " | Battery: %d kWh | Heat Pump: %s",
                batteryCapacity, hasHeatPump ? "Yes" : "No");
    }
}
