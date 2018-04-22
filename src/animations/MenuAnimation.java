package animations;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The menuAnimation class.
 * @author Barak Talmor
 * @param <T> - the type of task that will receive
 */
public class MenuAnimation<T> implements Menu<T> {
    private String menuTitle;
    private AnimationRunner ar;
    private KeyboardSensor keyboard;
    private List<SelectionInfo<T>> sIL;
    private boolean stop;
    private T currentReturnVal;
    private Map<String, Menu<T>> subMenus;
    private boolean alreadyPressed;

    /**
     * The constructor of the MenuAnimation.
     * @param menuTitle - the title of the menu
     * @param ar - for running the subMenu
     * @param keyboard - the keyboard sensor
     */
    public MenuAnimation(String menuTitle, AnimationRunner ar, KeyboardSensor keyboard) {
        this.menuTitle = menuTitle;
        this.ar = ar;
        this.keyboard = keyboard;
        this.sIL = new ArrayList<>();
        this.stop = false;
        this.currentReturnVal = null;
        this.subMenus = new TreeMap<String, Menu<T>>();
        this.alreadyPressed = true;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.gray);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.BLACK);
        d.drawText(70, 60, this.menuTitle, 40);
        d.setColor(Color.WHITE);
        d.drawText(71, 60, this.menuTitle, 40);
        d.setColor(Color.BLACK);
        d.drawText(72, 60, this.menuTitle, 40);
        int i = 0;
        for (SelectionInfo<T> s : this.sIL) {
            d.setColor(Color.BLACK);
            d.drawText(121, 130 + i * 40, "(" + s.getKey() + ") " + s.getMessage(), 30);
            d.setColor(Color.BLUE);
            d.drawText(123, 130 + i * 40, "(" + s.getKey() + ") " + s.getMessage(), 30);
            i++;
        }
        boolean enter = false;
        for (SelectionInfo<T> s : this.sIL) {
            if (this.keyboard.isPressed(s.getKey())) {
                enter = true;
                if (!this.alreadyPressed) {
                    this.currentReturnVal = s.getReturnVal();
                    this.stop = true;
                    break;
                }
            }            
        }
        if (!enter) {
            this.alreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.sIL.add(new SelectionInfo<T>(key, message, returnVal));
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.subMenus.put(key, subMenu);
        this.sIL.add(new SelectionInfo<T>(key, message, subMenu));
    }

    @Override
    public T getStatus() {
        this.alreadyPressed = true;
        if (this.subMenus.containsValue(this.currentReturnVal)) {
            return null;
        } else {
            return this.currentReturnVal;
        }
    }

    @Override
    public void resetStop() {
        this.stop = false;
    }
}
