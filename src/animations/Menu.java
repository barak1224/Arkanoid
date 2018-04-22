package animations;

/**
 * The interface of Menu.
 * @author Barak Talmor
 * @param <T> - the type of task that will receive
 */
public interface Menu<T> extends Animation {
    /**
     * The method add selction to the menu.
     * @param key - to press
     * @param message - string
     * @param returnVal - object
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * The method add subMenu to the menu.
     * @param key - keyboard pressing
     * @param message - name
     * @param subMenu - object menu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

    /**
     * The method return the status of the menu current.
     * @return T
     */
    T getStatus();

    /**
     * The method set stop to false.
     */
    void resetStop();
}