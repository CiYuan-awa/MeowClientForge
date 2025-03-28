package net.ciyuan.meowclient.command

import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.commands.annotations.Main
import net.ciyuan.meowclient.util.modMessage
import net.ciyuan.meowclient.util.sendCommand
import java.util.*

@Command(value = "quickplay", aliases = ["q", "qp"], description = "Quick play command.")
object QuickPlayCommand
{
    @Main
    fun handle(input: String = "") {
        if (input == "")
        {
            modMessage("\u00a7cPlease input game type.")
            return
        }
        else if (input.contains(" "))
        {
            modMessage("\u00a7cWrong input!")
            return
        }
        when (input.lowercase(Locale.getDefault()))
        {
            "solo" -> sendCommand("play bedwars_eight_one")
            "2s" -> sendCommand("play bedwars_eight_two")
            "3s" -> sendCommand("play bedwars_four_three")
            "4s" -> sendCommand("play bedwars_four_four")
            "4v4" -> sendCommand("play bedwars_two_four")
            "mm" -> sendCommand("play murder_mystery_classic")
            "mm2" -> sendCommand("play murder_mystery_double_up")
        }
    }
}