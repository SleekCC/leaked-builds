package today.sleek.client.commands.impl;

import today.sleek.client.commands.Command;
import today.sleek.client.commands.CommandData;

@CommandData(name = "gc", description = "Runs System.gc()")
public class GCCommand extends Command {
    @Override
    public void run(String[] args) {
        System.gc();
    }
}
