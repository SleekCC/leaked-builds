package today.sleek.client.rank

import net.minecraft.util.EnumChatFormatting

enum class UserRank(val displayName: String, val color: EnumChatFormatting) {
    DEVELOPER("Developer", EnumChatFormatting.GOLD), BETA("Beta", EnumChatFormatting.LIGHT_PURPLE), USER(
        "Normal",
        EnumChatFormatting.GRAY
    );

}