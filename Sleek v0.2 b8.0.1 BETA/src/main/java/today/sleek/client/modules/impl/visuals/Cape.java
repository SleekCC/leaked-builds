package today.sleek.client.modules.impl.visuals;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import today.sleek.Sleek;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.ModeValue;
import today.sleek.client.modules.impl.Module;

@ModuleData(
        name = "Cape",
        category = ModuleCategory.VISUALS,
        description = "Custom client capes"
)
public class Cape extends Module {

    private ModeValue capemode = new ModeValue("Cape", this, "Kitler", "HateFoo", "Sleek", "Sleek Test", "2016");

    public ResourceLocation getCape() {
        switch (capemode.getValue()) {
            case "Kitler": {
                return new ResourceLocation("sleek/images/capes/kitler.png");
            }
            case "HateFoo": {
                return new ResourceLocation("sleek/images/capes/hatefoo.png");
            }
            case "Sleek": {
                return new ResourceLocation("sleek/images/capes/sleekcape.png");
            }
            case "Sleek Test": {
                return new ResourceLocation("sleek/images/capes/sleektest.png");
            }
            case "2016": {
                return new ResourceLocation("sleek/images/capes/2016.png");
            }

            default: {
                throw new IllegalStateException("Unexpected value: " + capemode.getValue());
            }
        }
    }

    public boolean canRender(AbstractClientPlayer player) {
        return player == mc.thePlayer || Sleek.getInstance().getFriendManager().isFriend(player.getName()) || Sleek.getInstance().getUsers().containsKey(player.getName());
    }

}
