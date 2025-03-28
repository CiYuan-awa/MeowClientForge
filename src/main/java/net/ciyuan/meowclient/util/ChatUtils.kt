package net.ciyuan.meowclient.util

import net.ciyuan.meowclient.enum.ChatChannel
import net.ciyuan.meowclient.enum.ChatChannel.*
import net.minecraft.event.ClickEvent
import net.minecraft.event.HoverEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.Event

fun modMessage(message: Any?, prefix: String = "§dMeow §5Client §f»§r ", chatStyle: ChatStyle? = null) {
    val chatComponent = ChatComponentText("$prefix$message")
    chatStyle?.let { chatComponent.setChatStyle(it) } // Set chat style using setChatStyle method
    runOnMCThread { mc.thePlayer?.addChatMessage(chatComponent) }
}

fun sendCommand(text: Any, clientSide: Boolean = false, displaySendingMessage: Boolean = false) {
    if (!clientSide && displaySendingMessage) modMessage("Sending command: $text")
    if (clientSide) ClientCommandHandler.instance.executeCommand(mc.thePlayer, "/$text")
    else sendChatMessage("/$text")
}

fun sendChatMessage(message: Any) {
    runOnMCThread { mc.thePlayer?.sendChatMessage(message.toString()) }
}

fun channelMessage(message: Any, name: String, channel: ChatChannel) {
    when (channel) {
        GUILD -> guildMessage(message)
        PARTY -> partyMessage(message)
        PRIVATE -> privateMessage(message, name)
        ALL -> TODO()
        NONE -> TODO()
    }
}

fun allMessage(message: Any) {
    sendCommand("ac $message")
}

fun guildMessage(message: Any) {
    sendCommand("gc $message")
}

fun partyMessage(message: Any) {
    sendCommand("pc $message")
}

fun privateMessage(message: Any, name: String) {
    sendCommand("w $name $message")
}

fun createClickStyle(action: ClickEvent.Action?, value: String): ChatStyle {
    val style = ChatStyle()
    style.chatClickEvent = ClickEvent(action, value)
    style.chatHoverEvent = HoverEvent(
        HoverEvent.Action.SHOW_TEXT,
        ChatComponentText(EnumChatFormatting.LIGHT_PURPLE.toString() + value)
    )
    return style
}

fun Event.postAndCatch(): Boolean {
    return runCatching {
        MinecraftForge.EVENT_BUS.post(this)
    }.onFailure {
        it.printStackTrace()
        val style = ChatStyle()
        style.chatClickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/od copy ```${it.stackTraceToString().lineSequence().take(10).joinToString("\n")}```")
        style.chatHoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ChatComponentText("§6Click to copy the error to your clipboard."))
        modMessage("Caught an ${it::class.simpleName ?: "error"} at ${this::class.simpleName}. §cPlease click this message to copy and send it in the Odin discord!",
            chatStyle = style)}.getOrDefault(isCanceled)
}

fun startProfile(name: String) {
    mc.mcProfiler.startSection("CatGirl: $name")
}
fun endProfile() {
    mc.mcProfiler.endSection()
}
inline fun profile(name: String, func: () -> Unit) {
    startProfile(name)
    func()
    endProfile()
}