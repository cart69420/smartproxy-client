package dev.cart.smartproxy

object Communicator {
    var ENABLED = false
    var tokenLogin = false
    var IP = "localhost"
    var pPORT = 25566
    var sPORT = 6969

    fun sendAccessToken() {
        httpGetRequest("http://$IP:$sPORT/access?token=${mc.session.token}") {
            info(if (it == 200) "Authenticated with your access token!" else "Cannot authenticate with access token.")
        }
    }

    fun sendServerData(host: String, port: Int = pPORT) {
        httpGetRequest("http://$IP:$sPORT/requestserver?host=$host&port=$port") {
            info((if (it == 200) "Now connecting" else "Cannot connect") + " to $host:$port")
        }
    }
}