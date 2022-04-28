package today.sleek.client.modules.impl.player.hackerdetect;

import today.sleek.client.modules.impl.player.hackerdetect.checks.Check;
import today.sleek.client.modules.impl.player.hackerdetect.checks.exploit.PhaseA;
import today.sleek.client.modules.impl.player.hackerdetect.checks.movement.FlightA;
import today.sleek.client.modules.impl.player.hackerdetect.checks.movement.SpeedA;

import java.util.ArrayList;

public class CheckManager {
    private ArrayList<Check> checks = new ArrayList<>();

    public CheckManager() {
        // Combat
        // Movement
        checks.add(new SpeedA());
        checks.add(new FlightA());
        //Exploit
        checks.add(new PhaseA());
    }

    @SuppressWarnings("all")
    public ArrayList<Check> getChecks() {
        return this.checks;
    }
}
