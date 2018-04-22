package animations;

import io.Task;

/**
 * The class Task that make the system exit.
 * @author Barak Talmor
 */
public class QuitTask implements Task<Void> {
    /**
     * Empty constructor.
     */
    public QuitTask() {
    }

    @Override
    public Void run() {
        System.exit(0);
        return null;
    }
}
