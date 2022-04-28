package today.sleek.client.commands.impl;

import today.sleek.client.commands.Command;
import today.sleek.client.commands.CommandData;
import today.sleek.client.utils.chat.ChatUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

@CommandData(
        name = "name",
        description = "Copies the current account name"
)
public class NameCommand extends Command {

    @Override
    public void run(String[] args) {
        final String name = mc.thePlayer.getName();
        ChatUtil.log("Your Name Is: " + name + ", And Has Been Copied To Your Clipboard");
        StringSelection namefinal = new StringSelection(name);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(namefinal, namefinal);
    }
}


