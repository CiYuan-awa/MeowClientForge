package net.ciyuan.meowclient.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Checkbox
import cc.polyfrost.oneconfig.config.annotations.HUD
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.config.data.OptionSize
import net.ciyuan.meowclient.MeowClient
import net.ciyuan.meowclient.hud.MainHud
import net.ciyuan.meowclient.hud.PingHud
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

object MainConfig : Config(MeowClient.mod, MeowClient.MODID + ".json")
{
    //Lore HUD

    @HUD(name = MeowClient.NAME + " Lore", category = "Lore HUD")
    var loreHud = MainHud()

    //Ping HUD

    
    @HUD(name = "Ping HUD", category = "Ping HUD")
    var pingHud = PingHud()

    //Quality of Life

    
    @Switch(name = "Disable mob death animation", size = OptionSize.SINGLE, category = "Misc", subcategory = "Quality of Life")
    var disableMobDeathAnimation: Boolean = false

    //Chat Commands

    
    @Switch(name = "Enable Chat Commands", size = OptionSize.SINGLE, category = "Chat Commands")
    var enableChatCommands: Boolean = true
    
    @Switch(name = "Enable Chat Commands for direct message", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "General Settings")
    var enableDMChatCommands: Boolean = true
    
    @Switch(name = "Enable Chat Commands for party message", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "General Settings")
    var enablePMChatCommands: Boolean = true
    
    @Switch(name = "Enable Chat Commands for guild message", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "General Settings")
    var enableGMChatCommands: Boolean = true

    
    @Checkbox(name = "Enable coordinate command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "General Commands")
    var coordCommand: Boolean = true
    
    @Checkbox(name = "Enable meow command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "General Commands")
    var meowCommand: Boolean = true
    
    @Checkbox(name = "Enable boop command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "General Commands")
    var boopCommand: Boolean = true
    
    @Checkbox(name = "Enable time command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "General Commands")
    var timeCommand: Boolean = true
    
    @Checkbox(name = "Enable show holding item command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "General Commands")
    var showCommand: Boolean = true

    
    @Checkbox(name = "Quick play commands", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "Party Commands")
    var quickPlayCommand: Boolean = true
    
    @Checkbox(name = "Warp command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "Party Commands")
    var warpCommand: Boolean = true
    
    @Checkbox(name = "Warp & Transfer command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "Party Commands")
    var wtCommand: Boolean = true
    
    @Checkbox(name = "Allinvite command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "Party Commands")
    var allinvCommand: Boolean = true
    
    @Checkbox(name = "Transfer command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "Party Commands")
    var transferCommand: Boolean = true
    
    @Checkbox(name = "Promote command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "Party Commands")
    var promoteCommand: Boolean = true
    
    @Checkbox(name = "Demote command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "Party Commands")
    var demoteCommand: Boolean = true
    
    @Checkbox(name = "Invite command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "Direct Message Commands")
    var inviteCommand: Boolean = true

    
    @Checkbox(name = "Request Invite command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "Direct Message Commands")
    var requestInviteCommand: Boolean = true

    
    @Checkbox(name = "Enable ping command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "General Commands")
    var pingCommand: Boolean = true
    
    @Checkbox(name = "Enable tps command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "General Commands")
    var tpsCommand: Boolean = true

    
    @Checkbox(name = "Enable fps command", size = OptionSize.SINGLE, category = "Chat Commands", subcategory = "General Commands")
    var fpsCommand: Boolean = true

    private fun setupDependencies()
    {
        MainConfig::class.memberProperties.forEach { prop ->
            // 通过 Java 字段获取注解
            prop.javaField?.annotations?.forEach { annotation ->
                when (annotation)
                {
                    is Switch -> {
                        if (annotation.category == "Chat Commands" && prop.name != "enableChatCommands") addDependency(prop.name, "enableChatCommands")
                    }
                    is Checkbox -> {
                        if (annotation.category == "Chat Commands") {
                            when (annotation.subcategory) {
                                "Party Commands" -> addDependency(prop.name, "enablePMChatCommands")
                                "Direct Message Commands" -> addDependency(prop.name, "enableDMChatCommands")
                                "Guild Message Commands" -> addDependency(prop.name, "enableGMChatCommands")
                                //else ->
                            }
                            addDependency(prop.name, "enableChatCommands")
                        }
                    }
                }
            }
        }
    }
    init {
        initialize()
        setupDependencies()
    }
}