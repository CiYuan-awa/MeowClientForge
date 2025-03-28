package net.ciyuan.meowclient.handler

import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.ciyuan.meowclient.`interface`.IChat
import net.ciyuan.meowclient.util.clearColors

object ChatHandler {
    @SubscribeEvent
    fun onChatReceived(event: ClientChatReceivedEvent) {
        if (event.type.toInt() != 0) return

        val rawMessage = event.message.unformattedText.clearColors()

        chatModules.forEach { module ->
            if (module.filter.containsMatchIn(rawMessage) || module.skipRegexCheck) {
                module.onMessage(rawMessage)
            }
        }
    }

    private val chatModules = mutableListOf<IChat>()
    fun registerModule(module: IChat)
    {
        chatModules.add(module)
    }
}