package com.seyeong.musicplaylist

import android.net.Uri
import android.provider.MediaStore

class Music(id: String, title: String?, artist: String?, albumId: String?,
                duration: Long?) {
    // 음원 데이터에 대한 클래스
    var id: String = ""
    var title: String?
    var artist: String?
    var albumId: String?
    var duration: Long?

    init {
        this.id = id
        this.title = title
        this.artist = artist
        this.albumId = albumId
        this.duration = duration
    }

    fun getMusicUri(): Uri { // 음원의 URI를 생성하는 getMusicUri() 메서드.
        // 음원 URI는 기본 MediaStore의 주소와 음원 ID를 조합해서 만들기 때문에 만들어 놓고 사용하는 것이 편리하다.
        return Uri.withAppendedPath(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id
        )
    }

    fun getAlbumUri(): Uri { // 앨범 썸네일을 생성하는 getAlbumUri() 메서드.
        // 앨범 썸네일의 URI 문자열을 Uri.parse() 메서드로 해석해서 URI를 생성합니다.
        return Uri.parse (
            "content://media/external/audio/albumart/" + albumId
                )
    }

}