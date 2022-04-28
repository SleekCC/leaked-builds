package today.sleek.client.utils.rotations;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import today.sleek.client.utils.Util;
import today.sleek.client.utils.player.Location;

import javax.vecmath.Vector2f;

/**
 * Created on 07/09/2020 Package me.rhys.lite.util
 */
public class RotationUtil extends Util {

    public static Location location, lastLocation;
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    public static Vector2f getNormalRotations(Entity entity) {
        return getNormalRotations(minecraft.thePlayer.getPositionVector().addVector(0.0D,
                minecraft.thePlayer.getEyeHeight(), 0.0D), entity.getPositionVector().addVector(0.0D, entity.getEyeHeight() / 2, 0.0D));
    }

    public static Vector2f getNormalRotations(Vec3 origin, Vec3 position) {
        Vec3 org = new Vec3(origin.xCoord, origin.yCoord, origin.zCoord);
        Vec3 difference = position.subtract(org);

        double distance = difference.flat().lengthVector();

        float yaw = ((float) Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0F);
        float pitch = (float) (-Math.toDegrees(Math.atan2(difference.yCoord, distance)));

        return new Vector2f(yaw, pitch);
    }

    public static float getDirFromMovement(final float forward, final float strafing, float yaw) {
        if (forward == 0.0F && strafing == 0.0F) return yaw;

        boolean reversed = forward < 0.0f;
        float strafingYaw = 90.0f *
                (forward > 0.0f ? 0.5f : reversed ? -0.5f : 1.0f);

        if (reversed)
            yaw += 180.0f;
        if (strafing > 0.0f)
            yaw -= strafingYaw;
        else if (strafing < 0.0f)
            yaw += strafingYaw;

        return yaw;
    }

    public static Vector2f getRotations(Vec3 origin, Vec3 position) {

        Vec3 org = new Vec3(origin.xCoord, origin.yCoord, origin.zCoord);
        Vec3 difference = position.subtract(org);
        double distance = difference.flat().lengthVector();
        float yaw = ((float) Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0F);
        float pitch = (float) (-Math.toDegrees(Math.atan2(difference.yCoord, distance)));

        return new Vector2f(yaw, pitch);
    }


    public static void applySmoothing(final float[] lastRotations, final float smoothing, final float[] dstRotation) {
        if (smoothing > 0.0F) {
            final float yawChange = MathHelper.wrapAngleTo180_float(dstRotation[0] - lastRotations[0]);
            final float pitchChange = MathHelper.wrapAngleTo180_float(dstRotation[1] - lastRotations[1]);

            final float smoothingFactor = Math.max(1.0F, smoothing / 10.0F);

            dstRotation[0] = lastRotations[0] + yawChange / smoothingFactor;
            dstRotation[1] = Math.max(Math.min(90.0F, lastRotations[1] + pitchChange / smoothingFactor), -90.0F);
        }
    }

    public static Vector2f getRotations(Entity entity) {
        return getRotations(minecraft.thePlayer.getPositionVector().addVector(0.0D,
                minecraft.thePlayer.getEyeHeight(), 0.0D), entity.getPositionVector().addVector(0.0D, entity.getEyeHeight() / 2, 0.0D));
    }

    public static Vector2f getRotations(Vec3 position) {
        return getRotations(minecraft.thePlayer.getPositionVector().addVector(0.0D, minecraft.thePlayer.getEyeHeight(), 0.0D), position);
    }

    public static Vector2f clampRotation(Vector2f rotation) {
        float f = minecraft.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        float f1 = f * f * f * 1.2f;

        return new Vector2f(rotation.x - (rotation.x % f1), rotation.y - (rotation.y % f1));
    }

    public static float updateYawRotation(float playerYaw, float targetYaw, float maxSpeed) {
        float speed = MathHelper.wrapAngleTo180_float(((targetYaw - playerYaw)));
        if (speed > maxSpeed)
            speed = maxSpeed;
        if (speed < -maxSpeed)
            speed = -maxSpeed;
        return (playerYaw + speed);
    }

    public static float updatePitchRotation(float playerPitch, float targetPitch, float maxSpeed) {
        float speed = MathHelper.wrapAngleTo180_float(((targetPitch - playerPitch)));
        if (speed > maxSpeed)
            speed = maxSpeed;
        if (speed < -maxSpeed)
            speed = -maxSpeed;
        return (playerPitch + speed);
    }

    public static float[] getRotations(final double posX, final double posY, final double posZ) {
        final EntityPlayerSP player = mc.thePlayer;
        final double x = posX - player.posX;
        final double y = posY - (player.posY + player.getEyeHeight());
        final double z = posZ - player.posZ;
        final double dist = MathHelper.sqrt_double(x * x + z * z);
        final float yaw = (float) (Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float) (-(Math.atan2(y, dist) * 180.0 / 3.141592653589793));
        return new float[]{yaw, pitch};
    }

    public static float[] getRotation(Entity a1) {
        double v1 = a1.posX - mc.thePlayer.posX;
        double v3 = a1.posY + (double) a1.getEyeHeight() * 0.9 - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        double v5 = a1.posZ - mc.thePlayer.posZ;

        double v7 = MathHelper.ceiling_float_int((float) (v1 * v1 + v5 * v5));
        float v9 = (float) (Math.atan2(v5, v1) * 180.0 / 3.141592653589793) - 90.0f;
        float v10 = (float) (-(Math.atan2(v3, v7) * 180.0 / 3.141592653589793));
        return new float[]{mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(v9 - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(v10 - mc.thePlayer.rotationPitch)};
    }

    public static boolean isVisibleFOV(final Entity e, final float fov) {
        return ((Math.abs(RotationUtil.getRotation(e)[0] - mc.thePlayer.rotationYaw) % 360.0f > 180.0f) ? (360.0f - Math.abs(RotationUtil.getRotation(e)[0] - mc.thePlayer.rotationYaw) % 360.0f) : (Math.abs(RotationUtil.getRotation(e)[0] - mc.thePlayer.rotationYaw) % 360.0f)) <= fov;
    }
}