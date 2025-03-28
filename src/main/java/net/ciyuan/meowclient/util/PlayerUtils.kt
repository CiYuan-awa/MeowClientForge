package net.ciyuan.meowclient.util

import net.minecraft.util.Vec3

object PlayerUtils
{
    var shouldBypassVolume = false
    private inline val posX get() = mc.thePlayer?.posX ?: 0.0
    private inline val posY get() = mc.thePlayer?.posY ?: 0.0
    private inline val posZ get() = mc.thePlayer?.posZ ?: 0.0
    fun getPositionString() = "x: ${posX.toInt()}, y: ${posY.toInt()}, z: ${posZ.toInt()}"

    fun playLoudSound(sound: String?, volume: Float, pitch: Float, pos: Vec3? = null) {
        mc.addScheduledTask {
            shouldBypassVolume = true
            mc.theWorld?.playSound(pos?.xCoord ?: mc.thePlayer.posX, pos?.yCoord ?: mc.thePlayer.posY, pos?.zCoord  ?: mc.thePlayer.posZ, sound, volume, pitch, false)
            shouldBypassVolume = false
        }
    }
}