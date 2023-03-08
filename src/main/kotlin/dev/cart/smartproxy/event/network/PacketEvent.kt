package dev.cart.smartproxy.event.network

import me.bush.eventbuskotlin.Event
import net.minecraft.network.Packet

open class PacketEvent(val packet: Packet<*>) : Event() {
    override val cancellable = false

    class Send(packet: Packet<*>) : PacketEvent(packet)
    class Receive(packet: Packet<*>) : PacketEvent(packet)
}