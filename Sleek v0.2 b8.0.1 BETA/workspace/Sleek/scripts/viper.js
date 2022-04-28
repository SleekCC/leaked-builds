var module = script.registerModule("Viper Fly")

module.on('update', function(event) {
    player.setMotionY(0);
    play.setSpeed(0.275);
    event.setOnGround(true);
})