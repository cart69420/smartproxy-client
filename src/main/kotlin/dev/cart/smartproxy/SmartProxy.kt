package dev.cart.smartproxy

import me.bush.eventbuskotlin.Config
import me.bush.eventbuskotlin.EventBus
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(name = "SmartProxy", modid = "smartproxy-client", version = "1.0")
class SmartProxy {
    val LOGGER: Logger = LogManager.getLogger("SmartProxy/1.0")
    val BUS = EventBus(config = Config(logger = LOGGER))

    companion object {
        @JvmField
        @Mod.Instance
        var INSTANCE = SmartProxy()
    }
}