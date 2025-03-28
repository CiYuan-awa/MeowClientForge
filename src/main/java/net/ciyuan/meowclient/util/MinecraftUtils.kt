package net.ciyuan.meowclient.util

import net.minecraft.client.Minecraft

val mc: Minecraft = Minecraft.getMinecraft()
fun runOnMCThread(run: () -> Unit) {
    if (!mc.isCallingFromMinecraftThread) mc.addScheduledTask(run) else run()
}