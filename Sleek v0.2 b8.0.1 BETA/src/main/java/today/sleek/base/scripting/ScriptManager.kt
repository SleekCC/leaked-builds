package today.sleek.base.scripting

import net.minecraft.client.Minecraft
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.EnumFacing
import org.apache.commons.io.FilenameUtils
import org.apache.commons.lang3.exception.ExceptionUtils
import today.sleek.Sleek
import today.sleek.base.scripting.base.lib.*
import today.sleek.base.scripting.base.ScriptFile
import today.sleek.client.utils.chat.ChatUtil
import java.io.File
import java.io.FileReader
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager


class ScriptManager(val dir: File) {

    val scripts = arrayListOf<ScriptFile>()
    val factory = ScriptEngineManager()
    var engine: ScriptEngine = factory.getEngineByName("Nashorn")

    fun unloadScripts() {
        scripts.clear()
        Sleek.instance().moduleManager.unloadScripts()
    }

    fun loadScripts() {
        for (file in dir.listFiles()!!) {
            println(file.name)
            if (FilenameUtils.getExtension(file.name) == "js") {
                scripts.add(ScriptFile(file, FilenameUtils.removeExtension(file.name).replace(" ", "")))
            }
        }
    }

    fun loadScript(name: String) {
        for (script in scripts) {
            if (script.name.equals(name, ignoreCase = true)) {
                try {
                    engine.eval(FileReader(script.file))
                } catch (nigga: Exception) {
                    val stacktrace: String = ExceptionUtils.getStackTrace(nigga)
                    ChatUtil.log(EnumChatFormatting.RED.toString() + stacktrace)
                    nigga.printStackTrace()
                }
            }
        }

    }

    init {
        if (!dir.exists()) {
            dir.mkdirs()
        }
        engine.put("script", ScriptAPI)
        engine.put("chat", Chat)
        engine.put("player", Player)
        engine.put("packets", Packets)
        engine.put("mc", Minecraft.getMinecraft())
        engine.put("stopwatch", Stopwatch)
        loadScripts()
    }

}