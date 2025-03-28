package net.ciyuan.meowclient.command

import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.commands.annotations.Main
import net.ciyuan.meowclient.util.ServerUtils
import net.ciyuan.meowclient.util.mc
import net.ciyuan.meowclient.util.modMessage


@Command(value = "tps", description = "TPS.")
object TpsCommand {
    @Main
    private fun handle() {
        modMessage("TPS: " + ServerUtils.averageTps)
    }
}
@Command(value = "ping", description = "Ping.")
object PingCommand {
    @Main
    private fun handle() {
        modMessage("Ping: " + (ServerUtils.averagePing - 20))
    }
}
@Command(value = "fps", description = "FPS.")
object FpsCommand {
    @Main
    private fun handle() {
        modMessage(("FPS: " + ServerUtils.fps))
    }
}