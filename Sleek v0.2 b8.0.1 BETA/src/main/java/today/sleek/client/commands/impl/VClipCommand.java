package today.sleek.client.commands.impl;

import today.sleek.client.commands.Command;
import today.sleek.client.commands.CommandData;
import today.sleek.client.utils.chat.ChatUtil;

@CommandData(
        name = "vclip",
        description = "Vertically clips you a certain amount"
)
public class VClipCommand extends Command {

    @Override
    public void run(String[] args) {
        if (args.length > 0) {
            ChatUtil.log("Vclipped " + Float.parseFloat(args[0]) + " Blocks");
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + Float.parseFloat(args[0]), mc.thePlayer.posZ);
        } else {
            ChatUtil.log(".vclip <amount>");
        }
    }
}
