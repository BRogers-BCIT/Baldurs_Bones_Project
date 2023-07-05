package baldursbones.bb;

/** Location Abstract Class.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public abstract class Location {

    /** An integer value used to track the area and type of the location.
     */
    protected int locationType;

    /** Creates a new location object and assigns it a location area and type.
     * @param newLocationType an integer representing the location's area and type.
     */
    public Location(final int newLocationType) {
        locationType = newLocationType;
    }

    /** Gets the description of the location based on its location type.
     * @return a boolean indicating if the current location is a combat location
     */
    public abstract boolean getDescription();

    /** Prints the text for a location fight encounter then prints based on location value.
     * Location type options: first encounter, returning encounter, or beaten encounter.
     * @return a boolean value indicating if a fight can be started at this location
     */
    protected abstract boolean fightLocation();

    /** Prints the base text for an exploration location then prints based on location value.
     * Either first visit text description or the location return text description.
     */
    protected abstract void exploreLocation();
}
