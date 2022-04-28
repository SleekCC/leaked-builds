package today.sleek.base.scripting.base.lib

import today.sleek.base.scripting.base.ScriptMod
import com.google.common.eventbus.Subscribe
import today.sleek.Sleek
import today.sleek.base.event.impl.*
import today.sleek.base.modules.ModuleCategory
import today.sleek.client.modules.impl.Module

object ScriptAPI {

    fun registerModule(name: String): ScriptMod {
        val mod = ScriptMod(name, "Test...")
        Sleek.getInstance().moduleManager.registerModule(object : Module(name, ModuleCategory.SCRIPT) {
            override fun onEnable() {
                mod.onEnable()
            }

            override fun onDisable() {
                mod.onDisable()
            }

            @Subscribe
            fun onUpdate(event: UpdateEvent) {
                mod.onEvent("update", event)
            }
            @Subscribe
            fun onChat(event: ChatEvent) {
                mod.onEvent("chat", event)
            }
            @Subscribe
            fun onLivingEntityRenderEvent(event: EntityLivingRenderEvent) {
                mod.onEvent("entityRender", event)
            }
            @Subscribe
            fun onKeyboard(event: KeyboardEvent) {
                mod.onEvent("keyboard", event)
            }
            @Subscribe
            fun onMove(event: MoveEvent) {
                mod.onEvent("move", event)
            }
            @Subscribe
            fun onSlow(event: NoSlowEvent) {
                mod.onEvent("slow", event)
            }
            @Subscribe
            fun onPacket(event: PacketEvent) {
                mod.onEvent("packet", event)
            }
            @Subscribe
            fun onRender3D(event: Render3DEvent) {
                mod.onEvent("render3d", event)
            }
            @Subscribe
            fun onRenderOverlay(event: RenderOverlayEvent) {
                mod.onEvent("render2d", event)
            }
            @Subscribe
            fun onJoin(event: ServerJoinEvent) {
                mod.onEvent("join", event)
            }
            @Subscribe
            fun onStep(event: StepEvent) {
                mod.onEvent("step", event)
            }
            @Subscribe
            fun onTick(event: TickEvent) {
                mod.onEvent("tick", event)
            }
        })
        mod.onLoad()
        return mod
    }
}