package today.sleek.client.modules.impl.visuals;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import today.sleek.base.event.impl.EntityLivingRenderEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.modules.impl.Module;

@ModuleData(
        name = "Wallhack",
        category = ModuleCategory.VISUALS,
        description = "Shows players behind walls"
)
public class Wallhack extends Module {

    public BooleanValue fill = new BooleanValue("Fill", this, true);

    public NumberValue<Integer> alpha = new NumberValue<Integer>("Alpha", this, 255, 0, 255, 1);
    public NumberValue<Integer> r = new NumberValue<>("Red", this, 255, 0, 255, 1);
    public NumberValue<Integer> g = new NumberValue<Integer>("Green", this, 255, 0, 255, 1);
    public NumberValue<Integer> b = new NumberValue<Integer>("Blue", this, 255, 0, 255, 1);

    @Subscribe
    public void onRender(EntityLivingRenderEvent event) {
        if (event.isPre() && event.getEntity() instanceof EntityPlayer) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0F, -1100000.0F);
        } else if (event.isPost() && event.getEntity() instanceof EntityPlayer) {
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0F, 1100000.0F);
        }
    }

    @SuppressWarnings("all")
    public BooleanValue getFill() {
        return this.fill;
    }

    @SuppressWarnings("all")
    public NumberValue<Integer> getAlpha() {
        return this.alpha;
    }

    @SuppressWarnings("all")
    public NumberValue<Integer> getR() {
        return this.r;
    }

    @SuppressWarnings("all")
    public NumberValue<Integer> getG() {
        return this.g;
    }

    @SuppressWarnings("all")
    public NumberValue<Integer> getB() {
        return this.b;
    }
}
