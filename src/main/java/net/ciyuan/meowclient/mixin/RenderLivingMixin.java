package net.ciyuan.meowclient.mixin;

import net.minecraft.entity.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.entity.RenderLiving;

@Mixin(RenderLiving.class)
public abstract class RenderLivingMixin {
    @Inject(method = "doRender*", at = @At("HEAD"), cancellable = true)
    private void onDoRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (entity.getEntityData().getBoolean("DeathAnimationDisabled")) {
            // 跳过渲染流程
            ci.cancel();
        }
    }
}