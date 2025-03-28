package net.ciyuan.meowclient.handler

import net.ciyuan.meowclient.config.MainConfig
import net.minecraft.entity.EntityLiving
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.*

object DeathAnimationHandler {
    @SubscribeEvent
    fun onEntityDeath(event: LivingDeathEvent) {
        if (!MainConfig.disableMobDeathAnimation) return
        val entity = event.entityLiving as EntityLiving
        entity.entityData.setBoolean("DeathAnimationDisabled", true)

        // 添加定时清理标记的逻辑（防止复活类Mod干扰）
        if (entity.worldObj.isRemote) {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    entity.entityData.removeTag("DeathAnimationDisabled")
                }
            }, 1000) // 延迟1秒清理
        }
    }
}