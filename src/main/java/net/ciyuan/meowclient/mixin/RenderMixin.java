package net.ciyuan.meowclient.mixin;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Render.class)
public abstract class RenderMixin<T extends Entity> {

    @Inject(
            method = "renderShadow",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onRenderShadow(
            T entity,
            double x, double y, double z,
            float shadowAlpha, float partialTicks,
            CallbackInfo ci
    ) {
        if (entity instanceof EntityLiving) {
            EntityLiving livingEntity = (EntityLiving) entity;
            // 检查是否禁用阴影
            if (livingEntity.getEntityData().getBoolean("DeathAnimationDisabled")) {
                ci.cancel(); // 取消阴影渲染
            }
        }
    }
}