package today.sleek.client.utils.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import today.sleek.Sleek;
import today.sleek.client.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class FightUtil extends Util {

    public static boolean canHit(double chance) {
        return Math.random() <= chance;
    }

    public static List<EntityLivingBase> getMultipleTargets(double range, boolean players, boolean friends, boolean animals, boolean walls, boolean mobs, boolean invis) {
        List<EntityLivingBase> list = new ArrayList<>();
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (!(entity instanceof EntityLivingBase))
                continue;
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            if (entityLivingBase == mc.thePlayer ||
                    mc.thePlayer.getDistanceToEntity(entityLivingBase) > range
                    || !entityLivingBase.canEntityBeSeen(mc.thePlayer) && !walls
                    || entityLivingBase.isDead
                    || entityLivingBase instanceof EntityArmorStand
                    || entityLivingBase instanceof EntityVillager
                    || entityLivingBase instanceof EntityAnimal && !animals
                    || entityLivingBase instanceof EntitySquid && !animals
                    || entityLivingBase instanceof EntityPlayer && !players
                    || entityLivingBase instanceof EntityMob && !mobs
                    || entityLivingBase instanceof EntitySlime && !mobs
                    || Sleek.getInstance().getFriendManager().isFriend(entityLivingBase.getName()) && !friends
                    || entityLivingBase.isInvisible() && !invis) continue;
            if (list.size() > 5)
                continue;
            list.add(entityLivingBase);
        }
        return list;
    }

    public static double[] getRotationToEntity(Entity entity) {
        double pX = mc.thePlayer.posX;
        double pY = mc.thePlayer.posY + (mc.thePlayer.getEyeHeight());
        double pZ = mc.thePlayer.posZ;

        double eX = entity.posX;
        double eY = entity.posY + (entity.height/2);
        double eZ = entity.posZ;

        double dX = pX - eX;
        double dY = pY - eY;
        double dZ = pZ - eZ;
        double dH = Math.sqrt(Math.pow(dX, 2) + Math.pow(dZ, 2));

        double yaw = (Math.toDegrees(Math.atan2(dZ, dX)) + 90);
        double pitch = (Math.toDegrees(Math.atan2(dH, dY)));

        return new double[]{yaw, 90 - pitch};
    }

    public static List<EntityLivingBase> getMultipleTargetsSafe(double range, int max, boolean players, boolean friends, boolean animals, boolean walls, boolean mobs, boolean invis) {
        List<EntityLivingBase> list = new ArrayList<>();
        mc.theWorld.loadedEntityList.stream().filter(entity -> {
            if (!(entity instanceof EntityLivingBase)) {
                return false;
            }
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            if (entityLivingBase == mc.thePlayer ||
                    mc.thePlayer.getDistanceToEntity(entityLivingBase) > range
                    || !entityLivingBase.canEntityBeSeen(mc.thePlayer) && !walls
                    || entityLivingBase.isDead
                    || entityLivingBase instanceof EntityArmorStand
                    || entityLivingBase instanceof EntityVillager
                    || entityLivingBase instanceof EntityAnimal && !animals
                    || entityLivingBase instanceof EntitySquid && !animals
                    || entityLivingBase instanceof EntityPlayer && !players
                    || entityLivingBase instanceof EntityMob && !mobs
                    || entityLivingBase instanceof EntitySlime && !mobs
                    || Sleek.getInstance().getFriendManager().isFriend(entityLivingBase.getName()) && !friends
                    || entityLivingBase.isInvisible() && !invis) return false;
            return true;
        }).forEach(entity -> {
            if (list.size() > max) {
                return;
            }
            list.add((EntityLivingBase) entity);
        });
        return list;
    }

    public static boolean isValid(EntityLivingBase entityLivingBase, double range, boolean invis, boolean players, boolean animals, boolean mobs) {
        return !(mc.thePlayer.getDistanceToEntity(entityLivingBase) > range
                || entityLivingBase.isDead
                || entityLivingBase instanceof EntityArmorStand
                || entityLivingBase instanceof EntityVillager
                || entityLivingBase instanceof EntityPlayer && !players
                || entityLivingBase instanceof EntityAnimal && !animals
                || entityLivingBase instanceof EntityMob && !mobs
                || entityLivingBase.isInvisible() && !invis
                || entityLivingBase == mc.thePlayer);
    }

    public static boolean isOnSameTeam(EntityLivingBase entity) {
        if (entity.getTeam() != null && mc.thePlayer.getTeam() != null) {
            Team team1 = entity.getTeam();
            Team team2 = mc.thePlayer.getTeam();

            if (entity.getName().contains("UPGRADES")) {
                return false;
            }

            if (entity.getName().contains("SHOP")) {
                return false;
            }

            return team1 == team2;
        }
        return false;
    }
    // Credit: i have no idea i was given this
    public static boolean isOnSameTeam(Entity entity) {
        if (!(entity instanceof EntityLivingBase)) return false;
        if (((EntityLivingBase) entity).getTeam() != null && mc.thePlayer.getTeam() != null) {
            char c1 = entity.getDisplayName().getFormattedText().charAt(1);
            char c2 = mc.thePlayer.getDisplayName().getFormattedText().charAt(1);
            return c1 == c2;
        }
        return false;
    }
}