package today.sleek.client.modules.impl.player

import today.sleek.base.modules.ModuleData
import today.sleek.base.modules.ModuleCategory
import today.sleek.base.value.value.ModeValue
import com.google.common.eventbus.Subscribe
import net.minecraft.event.HoverEvent
import today.sleek.base.event.impl.PacketEvent
import net.minecraft.network.play.server.S2FPacketSetSlot
import net.minecraft.item.ItemStack
import net.minecraft.network.play.client.C09PacketHeldItemChange
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
import net.minecraft.network.play.server.S2DPacketOpenWindow
import today.sleek.base.event.impl.UpdateEvent
import net.minecraft.item.ItemSkull
import net.minecraft.network.Packet
import net.minecraft.network.play.client.C01PacketChatMessage
import net.minecraft.network.play.server.S02PacketChat
import net.minecraft.network.play.server.S45PacketTitle
import net.minecraft.util.StringUtils
import net.minecraft.util.Util
import today.sleek.base.value.value.BooleanValue
import today.sleek.base.value.value.NumberValue
import today.sleek.base.value.value.StringValue
import today.sleek.client.gui.notification.Notification
import today.sleek.client.gui.notification.NotificationManager
import today.sleek.client.modules.impl.Module
import today.sleek.client.utils.chat.ChatUtil
import today.sleek.client.utils.network.PacketSleepThread

@ModuleData(
    name = "Auto Server",
    category = ModuleCategory.PLAYER,
    description = "Automatically does actions on certain servers"
)
class AutoServer : Module() {
    private var hasClickedAutoPlay = false
    private var hasSelectedKit = false
    private var hasWorldChanged = false
    private var hasClickedKitSelector = false
    private val modeValue = ModeValue("Server", this, "BlocksMC", "Hypixel", "Viper")

    //blocksmc
    private val kitValue = ModeValue("Kit", this, modeValue, arrayOf("BlocksMC"), "Armorer", "Knight")

    //hypixel
    private val autoGG = BooleanValue("Auto GG", this, true, modeValue, "Hypixel")
    private val ggMessage = StringValue("GG Message", this, "gg")
    private val hideGGS = BooleanValue("Hide GGS", this, true, modeValue, "Hypixel")
    private val autoPlay = BooleanValue("Auto Play", this, true, modeValue, "Hypixel")
    private val autoPlayDelay = NumberValue("Auto Play Delay", this, 3000, 0, 15000, 1, autoPlay)


    //vipermc
    private val autoChatGames = BooleanValue("Auto Chat Game", this, true, modeValue, "Viper")

    @Subscribe
    fun onPacket(event: PacketEvent) {
        when (modeValue.valueAsString) {
            "BlocksMC" -> {
                val packet = event.getPacket<Packet<*>>()
                if (event.getPacket<Packet<*>>() is S2FPacketSetSlot) {
                    val item = (event.getPacket<Packet<*>>() as S2FPacketSetSlot).func_149174_e()
                    val slot = (event.getPacket<Packet<*>>() as S2FPacketSetSlot).func_149173_d()

                    //Make sure the item isn't null to prevent npe
                    if (item == null) return
                    if (item.displayName != null && item.displayName.contains("Play Again")) {

                        //set the slot to the paper
                        mc.netHandler.addToSendQueue(C09PacketHeldItemChange(7))

                        //right click on it
                        var i = 0
                        while (i < 2) {
                            mc.netHandler.addToSendQueue(C08PacketPlayerBlockPlacement(item))
                            i++
                        }
                        hasWorldChanged = true

                        //change the slot back to what it was.
                        mc.netHandler.addToSendQueue(C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem))
                    }

                    //Auto Kit Select
                    if (!hasSelectedKit) {
                        if (item.displayName != null && item.displayName.contains("Kit Selector")) {

                            //if an inventory is open, just return
                            if (mc.currentScreen != null) return

                            //set the slot to the bow
                            mc.netHandler.addToSendQueue(C09PacketHeldItemChange(0))

                            //right click on it
                            var i = 0
                            while (i < 2) {
                                mc.netHandler.addToSendQueue(C08PacketPlayerBlockPlacement(item))
                                i++
                            }

                            //change the slot back to what it was.
                            mc.netHandler.addToSendQueue(C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem))
                        }
                    }
                }
                if (event.getPacket<Packet<*>>() is S2DPacketOpenWindow) {
                    val packetData = event.getPacket<S2DPacketOpenWindow>()

                    //automatic kit selector
                    if (packetData.windowTitle.formattedText.contains("Kits")) {
                        when (kitValue.value) {
                            "Armorer" -> {
                                mc.playerController.windowClick(packetData.windowId, 0, 1, 0, mc.thePlayer)
                            }
                            "Knight" -> {
                                mc.playerController.windowClick(packetData.windowId, 18, 1, 0, mc.thePlayer)
                            }
                        }

                        //set selected kit to true
                        hasSelectedKit = true
                    }
                }
            }
            "Hypixel" -> {
                //send gg message
                if (autoGG.value && event.getPacket<Packet<*>>() is S45PacketTitle) {
                    val packet = event.getPacket<S45PacketTitle>()
                    val title = packet.message.formattedText


                    if (title.contains("§6§lVICTORY!") || title.contains("§7You were the last man standing!")) {
                        mc.thePlayer.sendChatMessage(ggMessage.value)
                    }
                    if (title.contains("you died", ignoreCase = true) || title.contains("game over", ignoreCase = true)) {
                        mc.thePlayer.sendChatMessage("/play solo_insane")
                    }
                    if (title.contains("you win", ignoreCase = true) || title.contains("victory", ignoreCase = true)) {
                        mc.thePlayer.sendChatMessage("/play solo_insane")
                    }
                }

                //hide gg messages
                if (hideGGS.value && event.getPacket<Packet<*>>() is S02PacketChat) {
                    val message = event.getPacket<S02PacketChat>().chatComponent.formattedText

                    if (message.contains(": gg")) {
                        event.isCancelled = true
                    }
                }

                //autoplay
                if (autoPlay.value && event.getPacket<Packet<*>>() is S02PacketChat) {
                    val message = (event.getPacket<Packet<*>>() as S02PacketChat).chatComponent.unformattedText
                    if (message.contains("ClickEvent{action=RUN_COMMAND, value='/play ")) {
                        PacketSleepThread.delayPacket(
                            C01PacketChatMessage((message.split("action=RUN_COMMAND, value='")[1].split("'}")[0])),
                            autoPlayDelay.value.toLong()
                        )

                        NotificationManager.getNotificationManager().show(
                            Notification(
                                Notification.NotificationType.INFO,
                                "Auto Play",
                                "Sending you to another game in " + (autoPlayDelay.value.toInt() / 1000) + " seconds",
                                3000
                            )
                        )
                    }
                }
            }
            "Viper" -> {
                try {
                    if (autoChatGames.value && event.getPacket<Packet<*>>() is S02PacketChat) {
                        val chatComponent = event.getPacket<S02PacketChat>().chatComponent
                        val message = event.getPacket<S02PacketChat>().chatComponent.toString()


                        if (!message.contains("hoverEvent=HoverEvent{action=SHOW_TEXT, value='TextComponent{text='")) {
                            return
                        }

                        if (!message.contains("Hover for the word to type")) {
                            return
                        }

                        for (msg in chatComponent.siblings) {
                            if (msg.chatStyle.chatHoverEvent != null) {
                                mc.thePlayer.sendChatMessage(StringUtils.stripControlCodes(msg.chatStyle.chatHoverEvent.value.formattedText))
                                return
                            }
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    @Subscribe
    fun onUpdate(event: UpdateEvent) {
        //set autoplay click to false since the world changed.
        if (mc.thePlayer.ticksExisted < 5) {
            hasClickedAutoPlay = false
            hasSelectedKit = false
            hasWorldChanged = false
            hasClickedKitSelector = false
        }
        when (modeValue.value) {
            "BlocksMC" -> {
                //auto select kit
                if (!hasSelectedKit && !hasClickedKitSelector && mc.thePlayer.ticksExisted > 5) {
                    var slot = 0
                    while (slot < mc.thePlayer.inventory.mainInventory.size) {
                        val currentItem = mc.thePlayer.inventory.getStackInSlot(slot)
                        if (currentItem == null) {
                            slot++
                            continue
                        }
                        if (currentItem.displayName.contains("Kit Selector")) {
                            //if an inventory is open, just return
                            if (mc.currentScreen != null) return

                            //set the slot to the bow
                            //BlocksMC skywars teams has a skull in the first slot for picking your teammate,
                            //so check if it's a skull, if it is then click the second item in your hotbar
                            if (mc.thePlayer.inventory.getStackInSlot(0).item !is ItemSkull) {
                                mc.netHandler.addToSendQueue(C09PacketHeldItemChange(0))
                            } else {
                                mc.netHandler.addToSendQueue(C09PacketHeldItemChange(1))
                            }

                            //right click on it
                            mc.netHandler.addToSendQueue(C08PacketPlayerBlockPlacement(currentItem))

                            //change the slot back to what it was.
                            mc.netHandler.addToSendQueue(C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem))
                        }
                        slot++
                    }
                }
            }
        }
    }
}