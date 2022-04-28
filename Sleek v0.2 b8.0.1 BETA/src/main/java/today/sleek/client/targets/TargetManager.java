package today.sleek.client.targets;

import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;

public class TargetManager {
    private final ArrayList<String> target = new ArrayList<>();

    public boolean isTarget(EntityPlayer ent) {
        return target.contains(ent.getName());
    }

    public boolean isTarget(String ent) {
        return target.contains(ent);
    }

    @SuppressWarnings("all")
    public ArrayList<String> getTarget() {
        return this.target;
    }
}
