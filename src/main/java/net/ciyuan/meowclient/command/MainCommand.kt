package net.ciyuan.meowclient.command

import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.commands.annotations.Main
import net.ciyuan.meowclient.MeowClient
import net.ciyuan.meowclient.config.MainConfig

/**
 * An example command implementing the Command api of OneConfig.
 * Registered in ExampleMod.java with `CommandManager.INSTANCE.registerCommand(new ExampleCommand());`
 *
 * @see Command
 *
 * @see Main
 *
 * @see MeowClient
 */
@Command(value = MeowClient.MODID, description = "Access the " + MeowClient.NAME + " GUI.")
object MainCommand {
    @Main
    private fun handle() {
        MainConfig.openGui()
    }
}