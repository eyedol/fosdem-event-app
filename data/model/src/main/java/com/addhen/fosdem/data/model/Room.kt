package com.addhen.fosdem.data.model

data class Room(
    val name: String,
    val building: String
) {
    enum class Building {
        J, K, H, U, AW, Unknown;

        companion object {

            fun fromRoomName(roomName: String): Building {
                if (roomName.isEmpty()) {
                    return Unknown
                }
                when (Character.toUpperCase(roomName[0])) {
                    'K' -> return K
                    'H' -> return H
                    'U' -> return U
                }
                return when {
                    "AW".equals(roomName.substring(0, 2), ignoreCase = true) -> AW
                    "Janson".equals(roomName, ignoreCase = true) -> J
                    "Ferrer".equals(roomName, ignoreCase = true) -> H
                    "Chavanne".equals(roomName, ignoreCase = true) ||
                        "Lameere".equals(roomName, ignoreCase = true) ||
                        "Guillissen".equals(roomName, ignoreCase = true) -> U
                    else -> Unknown
                }
            }
        }
    }
}

