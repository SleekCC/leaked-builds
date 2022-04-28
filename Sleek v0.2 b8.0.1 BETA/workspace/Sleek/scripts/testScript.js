//var script = Java.type("today.sleek.base.scripting.base.ScriptAPI")
var module = script.registerModule("Spinbot")
var sloke = Java.type("today.sleek.client.modules.impl.combat.CopsAndCrims")
var Gson = Java.type("com.google.gson.Gson")
var GsonB = Java.type("com.google.gson.GsonBuilder")

module.on('enable', function() {
chat.chat('Script made by Divine')
})
var yaw = 0;
var gson = new Gson();

module.on('update', function(event) {
if (sloke.target != null || sloke.target != undefined) {
chat.chat('currently targetting someone')
return;
}
    if (event.isPre()) {
        yaw += 180;

        event.setRotationYaw(yaw);
        event.setRotationPitch(90);
    }
})

var mod = script.registerModule("viper");
mod.on('packet', function(event) {
chat.chat(event.getPacketDirection())
//if (event.getPacketDirection() == "OUTBOUND")
//{chat.chat(event.getPacket() + " " + gson.toJson(event.getPacket()))}
})