package net.ciyuan.meowclient.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Dropdown
import cc.polyfrost.oneconfig.config.annotations.HUD
import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.config.data.OptionSize
import net.ciyuan.meowclient.MeowClient
import net.ciyuan.meowclient.hud.MainHud

/**
 * The main Config entrypoint that extends the Config type and inits the config options.
 * See [this link](https://docs.polyfrost.cc/oneconfig/config/adding-options) for more config Options
 */
class MainConfig : Config(Mod(MeowClient.NAME, ModType.UTIL_QOL), MeowClient.MODID + ".json") {
    @HUD(name = MeowClient.NAME, category = "HUD")
    var hud: MainHud = MainHud()

    init {
        initialize()
    }

    companion object {
        @Switch(name = "Disable mob death animation", size = OptionSize.SINGLE, category = "Misc")
        var disableMobDeathAnimation: Boolean = false // The default value for the boolean Switch.

        @Slider(name = "Example Slider", min = 0f, max = 100f, step = 10, category = "Misc")
        var exampleSlider: Float = 50f // The default value for the float Slider.

        @Dropdown(
            name = "Example Dropdown",
            options = ["Option 1", "Option 2", "Option 3", "Option 4"],
            category = "Misc"
        )
        var exampleDropdown: Int = 1 // Default option (in this case "Option 2")
    }
}