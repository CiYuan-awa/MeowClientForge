package net.ciyuan.meowclient.handler

import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.regex.Pattern

class ChatHandler {
    @SubscribeEvent
    fun onChatReceived(event: ClientChatReceivedEvent) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(event.message)
        if (event.type.toInt() == 0) { // 只处理普通聊天消息
            val rawMessage = event.message.unformattedText
            val matcher = PARTY_PATTERN.matcher(rawMessage)

            if (matcher.find()) {
                val extractedContent = matcher.group(1)
                sendToChat("[提取内容] $extractedContent")
            }
        }
    }

    private fun sendToChat(message: String) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(
            ChatComponentText(EnumChatFormatting.GREEN.toString() + message)
        )
    }

    companion object {
        private val PARTY_PATTERN: Pattern = Pattern.compile("^Party > (.+)$")
    }
}