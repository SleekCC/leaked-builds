package today.sleek.client.modules.impl.settings;

import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.glsl.BlurUtil;

/**
 * @author Kansio
 */
@ModuleData(name = "Blur", description = "Blurs certain overlay elements", category = ModuleCategory.SETTINGS)
public class Blur extends Module {

    public static void startBlur() {
        BlurUtil.toBlurBuffer.bindFramebuffer(false);
        BlurUtil.setupBuffers();
        BlurUtil.renderGaussianBlur(5, 2, true, false);

        mc.getFramebuffer().bindFramebuffer(false);
    }

}
