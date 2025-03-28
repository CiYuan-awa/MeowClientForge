package net.ciyuan.meowclient.module

import net.ciyuan.meowclient.config.MainConfig
import net.ciyuan.meowclient.enum.ChatChannel
import net.ciyuan.meowclient.`interface`.IChat
import net.ciyuan.meowclient.util.*
import net.minecraft.event.ClickEvent
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object ChatCommands : IChat
{
    override val filter: Regex = Regex("^(?:Party > (\\[[^]]*?])? ?(\\w{1,16})(?: [ቾ⚒])?: ?(.+)\$|Guild > (\\[[^]]*?])? ?(\\w{1,16})(?: \\[([^]]*?)])?: ?(.+)\$|From (\\[[^]]*?])? ?(\\w{1,16}): ?(.+)\$)")
    override val name: String = "聊天指令"
    override val skipRegexCheck: Boolean = false
    lateinit var channel: ChatChannel

    override fun onMessage(message: String) {
        channel = when (message.split(" ")[0]) {
            "From" -> if (!MainConfig.enableDMChatCommands) ChatChannel.NONE else ChatChannel.PRIVATE
            "Party" -> if (!MainConfig.enablePMChatCommands) ChatChannel.NONE else ChatChannel.PARTY
            "Guild" -> if (!MainConfig.enableGMChatCommands) ChatChannel.NONE else ChatChannel.GUILD
            else -> ChatChannel.NONE
        }

        if (channel == ChatChannel.NONE) return
        val match = filter.find(message) ?: return
        val ign = match.groups[2]?.value ?: match.groups[5]?.value ?: match.groups[9]?.value ?: return
        val msg = match.groups[3]?.value ?: match.groups[7]?.value ?: match.groups[10]?.value ?: return

        //blacklist?

        handleChatCommands(msg, ign, channel)
    }

    private fun handleChatCommands(message: String, name: String, channel: ChatChannel) {

        if (!message.startsWith("!")) return
        val input: String = message.split(" ")[0].drop(1).lowercase()
        when (input) {
            "coords", "co", "where" -> if (MainConfig.coordCommand) channelMessage(PlayerUtils.getPositionString(), name, channel)
            "meow" -> if (MainConfig.meowCommand) channelMessage("喵呜～", name, channel)
            "boop" -> if (MainConfig.boopCommand) sendCommand("boop ${message.substringAfter("boop ")}")
            "ping" -> if (MainConfig.pingCommand) channelMessage("当前我的 Ping 是: ${Math.round(ServerUtils.averagePing) - 20}ms", name, channel)
            "tps" -> if (MainConfig.tpsCommand) channelMessage("当前服务器的 TPS 是: ${String.format("%.2f", ServerUtils.averageTps)}", name, channel)
            "fps" -> if (MainConfig.fpsCommand) channelMessage("当前我的 FPS 是: ${ServerUtils.fps} (不会有人还比我低吧喵)", name, channel)
            "time" -> if (MainConfig.timeCommand) channelMessage("当前时间是: ${ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"))} 喵～", name, channel)
            "holding" -> if (MainConfig.showCommand) channelMessage("我现在手上拿的是: ${mc.thePlayer?.heldItem?.displayName?.clearColors() ?: "啥也没有"} 喵～", name, channel)

            // Party cmds only

            "2s", "3s", "4s", "4v4", "mm", "mm2" -> if (MainConfig.quickPlayCommand && channel == ChatChannel.PARTY)
            {
                sendCommand("quickplay ${input.removePrefix("!")}", true)
                runIn(10)
                {
                    channelMessage("$name 执行了快速游玩指令: $input", name, channel)
                    channelMessage("Good Luck!", name, channel)
                }
            }
            "warp", "w" -> if (MainConfig.warpCommand && channel == ChatChannel.PARTY) sendCommand("p warp")
            "warptransfer", "wt" -> if (MainConfig.wtCommand && channel == ChatChannel.PARTY) {
                sendCommand("p warp")
                runIn(10) {
                    sendCommand("p transfer $name")
                }
            }
            "allinvite", "allinv" -> if (MainConfig.allinvCommand && channel == ChatChannel.PARTY) sendCommand("p settings allinvite")
            "pt", "ptme", "transfer" -> if (MainConfig.transferCommand && channel == ChatChannel.PARTY) sendCommand("p transfer $name")
            "promote" -> if (MainConfig.promoteCommand && channel == ChatChannel.PARTY) sendCommand("p promote $name")
            "demote" -> if (MainConfig.demoteCommand && channel == ChatChannel.PARTY) sendCommand("p demote $name")

            // Private cmds only
            "invite", "inv" -> if (MainConfig.inviteCommand && channel == ChatChannel.PRIVATE) {
                sendCommand("party invite $name")
                PlayerUtils.playLoudSound("note.pling", 100f, 1f)
            }
            "requestinvite", "rinv" -> if (MainConfig.requestInviteCommand && channel == ChatChannel.PRIVATE) {
                modMessage("§a§n点击此处来将§r §d§l§n$name§r §a§n邀请至你的组队！§r", chatStyle = createClickStyle(
                    ClickEvent.Action.RUN_COMMAND, "/party invite $name"))
                PlayerUtils.playLoudSound("note.pling", 100f, 1f)
            }
        }
    }
}