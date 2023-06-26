package baldursbones.bb;

/** Location.
 * @author Braden Rogers
 * @version 2023-TermProject
 * Note: Abstract class has no tests
 */
public abstract class Location {
    /** integer to track the type value of the location.
     */
    protected int locationType;
    /** Initializes a new location object.
     * @param newLocationType an integer representing what location type it is and if it has been explored
     */
    public Location(final int newLocationType) {
        locationType = newLocationType;
    }
    /** Gets the description of the location based on its location type.
     * @return a boolean determining whether to make a new enemy object
     */
    public abstract boolean getDescription();
    /** Prints the text for a fight encounter in the location type.
     * @return if the player encounters an enemy at the location
     */
    protected abstract boolean fightLocation();
    /** Prints the text for an exploration encounter in the location type.
     */
    protected abstract void exploreLocation();
}
