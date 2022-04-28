package today.sleek.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.player.EntityPlayer;
import today.sleek.Sleek;
import today.sleek.base.event.impl.MouseEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.client.friend.Friend;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.chat.ChatUtil;

@ModuleData(
        name = "MCF",
        category = ModuleCategory.PLAYER,
        description = "Middle click a player to add them as a friend"
)
public class MCF extends Module {

    @Subscribe
    public void onMouse(MouseEvent event) {
        if (event.getButton() == 2 && mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) mc.objectMouseOver.entityHit;
            String name = player.getName();
            if (Sleek.getInstance().getFriendManager().isFriend(name)) {
                Sleek.getInstance().getFriendManager().removeFriend(name);
                ChatUtil.log("Removed " + name + " as a friend!");
            } else {
                Sleek.getInstance().getFriendManager().addFriend(new Friend(name, name));
                ChatUtil.log("Added " + name + " as a friend!");
            }
        }
    }
}
