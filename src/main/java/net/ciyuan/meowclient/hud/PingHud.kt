package net.ciyuan.meowclient.hud

import cc.polyfrost.oneconfig.hud.SingleTextHud
import net.ciyuan.meowclient.util.ServerUtils

class PingHud : SingleTextHud("Ping", true) {
    public override fun getText(input: Boolean): String {
        return Math.round(ServerUtils.averagePing - 20).toString()
    }
}