package net.ciyuan.meowclient

import cc.polyfrost.oneconfig.utils.commands.CommandManager
import net.ciyuan.meowclient.command.MainCommand
import net.ciyuan.meowclient.config.MainConfig
import net.ciyuan.meowclient.handler.ChatHandler
import net.ciyuan.meowclient.handler.DeathAnimationHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

/**
 * The entrypoint of the Example Mod that initializes it.
 *
 * @see Mod
 *
 * @see InitializationEvent
 */
@Mod(modid = MeowClient.MODID, name = MeowClient.NAME, version = MeowClient.VERSION)
class MeowClient {
    // Register the config and commands.
    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent?) {
        config = MainConfig()
        CommandManager.INSTANCE.registerCommand(MainCommand())
        MinecraftForge.EVENT_BUS.register(DeathAnimationHandler())
        MinecraftForge.EVENT_BUS.register(ChatHandler())
    }

    companion object {
        // Sets the variables from `gradle.properties`. See the `blossom` config in `build.gradle.kts`.
        const val MODID: String = "meowclient"
        const val NAME: String = "MeowClient"
        const val VERSION: String = "1.0.0"

        @Mod.Instance(MODID)
        var INSTANCE: MeowClient? = null // Adds the instance of the mod, so we can access other variables.
        var config: MainConfig? = null
    }
}