package net.ciyuan.meowclient.handler

import net.ciyuan.meowclient.util.TickUtils
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object TickEventHandler {

    @SubscribeEvent
    fun onServerTick(event: TickEvent.ServerTickEvent) {
        if (event.phase === TickEvent.Phase.END) {
            TickUtils.processTasks(TickUtils.serverTasks)
        }
    }

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase === TickEvent.Phase.END) {
            TickUtils.processTasks(TickUtils.clientTasks)
        }
    }
}
