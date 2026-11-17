/**
 * Defines capabilities and domain calculations specific to electric vehicles (EVs).
 */
public interface ElectricInterface {
    /**
     * Checks if another vehicle belongs to the same range category.
     * Returns true if the other vehicle is an instance of {@link ElectricVehicle}
     * and their battery capacities differ by at most 5 kWh.
     *
     * @param other The vehicle to compare with
     * @return {@code true} if both are electric and capacities differ by at most 5 kWh, {@code false} otherwise
     */
    boolean hasSameRangeCategory(Vehicle other);

    /**
     * Calculates the estimated monetary share of the battery package
     * based on a rate of 25,000 currency units per kWh of battery capacity.
     *
     * @return The estimated financial value of the battery package
     */
    int batteryCostShare();
}
