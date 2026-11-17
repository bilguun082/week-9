/**
 * Defines the core contract and standard operations for all vehicles in the car dealership system.
 */
public interface VehicleInterface {
    /**
     * Reduces the base price of the vehicle by the specified integer percentage.
     *
     * @param percentage The discount percentage to apply (e.g., 10 for a 10% discount)
     */
    void applyDiscount(int percentage);

    /**
     * Calculates and returns the final commercial selling price of the vehicle.
     * Applies age-related depreciation, a minimum price floor, and fuel type adjustments.
     *
     * @return The final selling price in currency units
     */
    int getFinalPrice();

    /**
     * Calculates the average annual distance driven in kilometers (mileage divided by age).
     * If the vehicle's age is 0 (new vehicle), the total mileage is returned.
     *
     * @return The average kilometers driven per year
     */
    double averageKmPerYear();

    /**
     * Returns a formatted string representation of the vehicle's state,
     * including technical specifications, pricing, and mileage.
     *
     * @return Formatted string representation of the vehicle
     */
    @Override
    String toString();
}
