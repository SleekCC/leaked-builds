package today.sleek.base.scripting.base.lib

import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerCapabilities
import net.minecraft.item.ItemStack
import net.minecraft.network.play.client.*
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook
import today.sleek.client.utils.Util

object Packets {

    fun C0B(entity: Entity, action: Int): C0BPacketEntityAction {
        return C0BPacketEntityAction(entity, C0BPacketEntityAction.Action.values()[action])
    }

    fun C0C(strafeSpeed: Float, fowardSpeed: Float, jumping: Boolean, sneaking: Boolean): C0CPacketInput {
        return C0CPacketInput(strafeSpeed, fowardSpeed, jumping, sneaking)
    }

    fun C0D(windowID: Int): C0DPacketCloseWindow {
        return C0DPacketCloseWindow()
    }

    fun C0E(windowId: Int, slotId: Int, usedButton: Int, mode: Int, clickedItem: ItemStack?, actionNumber: Short): C0EPacketClickWindow {
        return C0EPacketClickWindow(windowId, slotId, usedButton, mode, clickedItem, actionNumber)
    }

    fun C0F(windowId: Int, uid: Short, accepted: Boolean): C0FPacketConfirmTransaction {
        return C0FPacketConfirmTransaction(windowId, uid, accepted)
    }

    fun C00(key: Int): C00PacketKeepAlive {
        return C00PacketKeepAlive(key)
    }

    fun C01(message: String): C01PacketChatMessage {
        return C01PacketChatMessage(message)
    }

    fun C02(entity: Entity, action: Int): C02PacketUseEntity {
        return C02PacketUseEntity(entity, C02PacketUseEntity.Action.values()[action])
    }

    fun C03(entity: Entity, action: Int): C02PacketUseEntity {
        return C02PacketUseEntity(entity, C02PacketUseEntity.Action.values()[action])

    }

    fun C04(posX: Double, posY: Double, posZ: Double, isOnGround: Boolean): C04PacketPlayerPosition {
        return C04PacketPlayerPosition(posX, posY, posZ, isOnGround)
    }

    fun C05(playerYaw: Float, playerPitch: Float, isOnGround: Boolean): C05PacketPlayerLook {
        return C05PacketPlayerLook(playerYaw, playerPitch, isOnGround)
    }

    fun C06(playerX: Double, playerY: Double, playerZ: Double, playerYaw: Float, playerPitch: Float, playerIsOnGround: Boolean): C06PacketPlayerPosLook {
        return C06PacketPlayerPosLook(playerX, playerY, playerZ, playerYaw, playerPitch, playerIsOnGround)
    }

    fun C13(invulnerable: Boolean, isFlying: Boolean, allowFlying: Boolean, isCreativeMode: Boolean): C13PacketPlayerAbilities {
        val pc = PlayerCapabilities()
        pc.disableDamage = invulnerable
        pc.isFlying = isFlying
        pc.allowFlying = allowFlying
        pc.isCreativeMode = isCreativeMode
        return C13PacketPlayerAbilities(pc)
    }

    fun C18(): C18PacketSpectate {
        return C18PacketSpectate(Minecraft.getMinecraft().thePlayer.uniqueID)
    }

}