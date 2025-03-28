package net.ciyuan.meowclient

import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.utils.commands.CommandManager
import net.ciyuan.meowclient.command.*
import net.ciyuan.meowclient.config.MainConfig
import net.ciyuan.meowclient.handler.ChatHandler
import net.ciyuan.meowclient.handler.DeathAnimationHandler
import net.ciyuan.meowclient.handler.TickEventHandler
import net.ciyuan.meowclient.module.ChatCommands
import net.ciyuan.meowclient.util.ServerUtils
import net.ciyuan.meowclient.util.TaskExecutor
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
        MainConfig
        initCommands()
        initHandlers()
        ChatHandler.registerModule(ChatCommands)
    }

    private fun initCommands()
    {
        listOf(MainCommand, TpsCommand, FpsCommand,
            PingCommand, QuickPlayCommand).forEach {
            CommandManager.INSTANCE.registerCommand(it)
        }
    }

    private fun initHandlers()
    {
        listOf(DeathAnimationHandler, ChatHandler, TickEventHandler,
            ServerUtils, TaskExecutor).forEach {
                MinecraftForge.EVENT_BUS.register(it)
        }
    }

    companion object {
        // Sets the variables from `gradle.properties`. See the `blossom` config in `build.gradle.kts`.
        const val MODID: String = "@ID@"
        const val NAME: String = "@NAME@"
        const val VERSION: String = "@VER@"
        val mod by lazy { cc.polyfrost.oneconfig.config.data.Mod(NAME, ModType.UTIL_QOL) }

//        @Mod.Instance(MODID)
//        var Instance: MeowClient? = null // Adds the instance of the mod, so we can access other variables.
    }
}