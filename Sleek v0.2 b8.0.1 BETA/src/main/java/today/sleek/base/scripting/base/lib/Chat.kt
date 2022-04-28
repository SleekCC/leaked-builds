package today.sleek.base.scripting.base.lib

import today.sleek.client.utils.chat.ChatUtil

object Chat {

    fun chat(msg: String) {
        ChatUtil.log(msg)
    }
    fun chat(msg: String, prefix: String) {
        ChatUtil.log("$prefix §7» §f$msg")
    }

}