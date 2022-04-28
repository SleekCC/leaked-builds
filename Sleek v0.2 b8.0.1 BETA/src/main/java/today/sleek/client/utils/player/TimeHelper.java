package today.sleek.client.utils.player;

/**
 * @author Kansio
 * Credit: Flux
 *
 * Why did I take this from Flux? Because I'm at school using remote desktop and its laggy so im too lazy to make our current one work with the blur utility.
 */

public class TimeHelper {

    public long getLastMS() {
        return lastMs;
    }

    public long lastMs;

    public TimeHelper() {
        this.lastMs = 0L;
    }

    public void reset() {
        this.lastMs = System.currentTimeMillis();
    }

    public boolean delay(long nextDelay) {
        return System.currentTimeMillis() - lastMs >= nextDelay;
    }

    public boolean delay(float nextDelay, boolean reset) {
        if (System.currentTimeMillis() - lastMs >= nextDelay) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }

    public boolean isDelayComplete(double valueState) {
        return System.currentTimeMillis() - lastMs >= valueState;
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - this.lastMs;
    }

    public long getLastMs() {
        return lastMs;
    }
}
