package today.sleek.client.modules.impl.movement.flight.misc

import net.minecraft.block.BlockAir
import net.minecraft.util.AxisAlignedBB
import today.sleek.base.event.impl.BlockCollisionEvent
import today.sleek.client.modules.impl.movement.flight.FlightMode

class Collide : FlightMode("Collide") {
    override fun onCollide(event: BlockCollisionEvent) {
        if (event.block is BlockAir) {
            if (mc.thePlayer.isSneaking) return
            val x = event.x.toDouble()
            val y = event.y.toDouble()
            val z = event.z.toDouble()
            if (y < mc.thePlayer.posY) {
                event.axisAlignedBB = AxisAlignedBB.fromBounds(-5.0, -1.0, -5.0, 5.0, 1.0, 5.0).offset(x, y, z)
            }
        }
    }
}