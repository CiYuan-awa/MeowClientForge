package net.ciyuan.meowclient.hud

import cc.polyfrost.oneconfig.hud.SingleTextHud
import net.ciyuan.meowclient.MeowClient

/**
 * An example OneConfig HUD that is started in the config and displays text.
 *
 * @see Config.hud
 */
class MainHud : SingleTextHud(MeowClient.NAME, true) {
    public override fun getText(input: Boolean): String {
        return "Meow!"
    }
}