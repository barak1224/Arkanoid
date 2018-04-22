package animations;

/**
 * The class which define the Selection.
 * @author barak
 * @param <T> - generic object
 */
public class SelectionInfo<T> {
    private String key;
    private String message;
    private T returnVal;
    private Menu<T> subMenu;

    /**
     * The constructor of the SelectionInfo.
     * @param key - the press keyboard
     * @param message - the name of selection
     * @param returnVal - the object of the selection
     */
    public SelectionInfo(String key, String message, T returnVal) {
        this.key = key;
        this.message = message;
        this.returnVal = returnVal;
    }

     /**
     * The constructor of the SelectionInfo.
     * @param key - the press keyboard
     * @param message - the name of selection
     * @param subMenu - the object of the menu
     */
     public SelectionInfo(String key, String message, Menu<T> subMenu) {
     this.key = key;
     this.message = message;
     this.subMenu = subMenu;
     }

    /**
     * The method returns the key.
     * @return key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * The method returns the message.
     * @return message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * The method returns the returnVal.
     * @return returnVal
     */
    public T getReturnVal() {
        return this.returnVal;
    }

    /**
     * The method returns the returnVal.
     * @return Menu<T>
     */
    public Menu<T> getSubMenu() {
        return this.subMenu;
    }
}
