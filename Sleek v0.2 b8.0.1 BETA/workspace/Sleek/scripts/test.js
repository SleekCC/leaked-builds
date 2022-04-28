//var script = Java.type("today.sleek.base.scripting.base.ScriptAPI")
// var module = script.registerModule("Spinbot")
// var sloke = Java.type("today.sleek.client.modules.impl.combat.CopsAndCrims")
var Gson = Java.type("com.google.gson.Gson")
var C03 = Java.type("net.minecraft.network.play.client.C03PacketPlayer")
// var GsonB = Java.type("com.google.gson.GsonBuilder")

// module.on('enable', function() {
// chat.chat('Script made by Divine')
// })
// var yaw = 0;
// var gson = new Gson();

// module.on('update', function(event) {
// if (sloke.target != null || sloke.target != undefined) {
// chat.chat('currently targetting someone')
// return;
// }
//     if (event.isPre()) {
//         yaw += 180;

//         event.setRotationYaw(yaw);
//         event.setRotationPitch(90);
//     }
// })

var util = Java.type("today.sleek.client.utils.player.PlayerUtil")
var module = script.registerModule("Packets")
var gson = new Gson()


module.on('update', function(event) {
    if (event.isPre()) {
        chat.chat(event.getPosY() + " " + event.isOnGround())
    }
})

module.on('packet', function(event) {
    if (event.getPacket() instanceof C03) {
        chat.chat(gson.toJson(event.getPacket()))
    }
})


//if (stage == 0) {
//                        if (stage2 == 1) {
//                            mc.thePlayer.motionY = 0.05;
//                        }
//                        if (stage2 == 3) {
//                            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.22, mc.thePlayer.posZ);
//                            mc.thePlayer.posY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
//                            mc.thePlayer.lastTickPosY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
//                        }
//                        if (stage2 == 4) {
//                            mc.thePlayer.motionY = -0.481009647894567;
//                            mc.thePlayer.posY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
//                            mc.thePlayer.lastTickPosY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
//                        }
//                        if (stage2 == 5) {
//                            mc.thePlayer.motionY = -0.481009647894567;
//                            mc.thePlayer.posY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
//                            mc.thePlayer.lastTickPosY -= mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
//                            stage = 1;
//                        }
//                    }