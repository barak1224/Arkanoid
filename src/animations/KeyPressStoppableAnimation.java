package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The class of the keyPressStoppableAnimation.
 * @author barak
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyboard;
    private String endKey;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * The constructor of the KeyPressStoppableAnimation.
     * @param sensor - the keyboard
     * @param key - the key that end
     * @param animation - an animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.keyboard = sensor;
        this.endKey = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt);
        if (this.keyboard.isPressed(this.endKey) && !isAlreadyPressed) {
            this.stop = true;
        }
        if (!this.keyboard.isPressed(this.endKey)) {
            this.isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
