package com.auzen.model

class AudioMetadataModel {
    var duration: Long? = 0
    var accuired_time: Long = 0;
    var engine: AudioEngineModel? = null
    var config: Config = Config()

    class Config {
        var speaker_name: String? = null
        var pitch: Long = 0;
        var speed: Long = 0;
    }
}