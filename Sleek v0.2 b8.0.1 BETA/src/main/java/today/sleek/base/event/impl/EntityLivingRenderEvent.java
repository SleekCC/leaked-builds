package today.sleek.base.event.impl;

import net.minecraft.entity.EntityLivingBase;
import today.sleek.base.event.Event;

public class EntityLivingRenderEvent extends Event {

    private boolean isPre;
    private boolean isPost;
    private EntityLivingBase entityLivingBase;

    public EntityLivingRenderEvent(boolean pre, EntityLivingBase entityLivingBase) {
        this.entityLivingBase = entityLivingBase;
        isPre = pre;
        isPost = !pre;
    }

    public boolean isPre() {
        return isPre;
    }
    public boolean isPost() {
        return isPost;
    }
    public EntityLivingBase getEntity() {
        return entityLivingBase;
    }
}