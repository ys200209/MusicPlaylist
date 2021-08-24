package com.seyeong.musicplaylist

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.seyeong.musicplaylist.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    val binding by lazy { ActivityMainBinding.inflate( layoutInflater )}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        requirePermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 999)
    }

    override fun permissionGranted(requestCode: Int) {
        startProcess()
    }

    override fun permissionDenied(requestCode: Int) {
        Toast.makeText(this, "외부 저장소 권한 승인이 필요합니다. 앱을 종료합니다.", Toast.LENGTH_LONG).show()
        finish()
    }

    fun startProcess() { // 권한이 승인되었을때 실행되는 메서드
        val adapter = MusicRecyclerAdapter()
        adapter.musicList.addAll(getMusicList())

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        var decoration = DividerItemDecoration(this, VERTICAL) // 리사이클러뷰에 기본형 Divider를 넣는것
        binding.recyclerView.addItemDecoration(decoration)
    }

    fun getMusicList(): List<Music> { // 음원을 읽어오는 getMusicList() 메서드
        val listUrl = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI // 음원 정보의 주소
        val proj = arrayOf( // 음원 정보 테이블에서 읽어올 컬럼명을 배열로 정의. ( MediaStore에 상수로 이미 정의되어 있다. )
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION
        )

        // 컨텐트 리졸버의 query() 메서드에 음원 정보의 주소와 컬럼명을 담아서 호출하면 실행결과를 커서로 반환해준다.
        val cursor = contentResolver.query(listUrl, proj, null, null, null)

        val musicList = mutableListOf<Music>()

        while (cursor?.moveToNext() == true) {
            val id = cursor.getString(0)
            val title = cursor.getString(1)
            val artist = cursor.getString(2)
            val albumId = cursor.getString(3)
            val duration = cursor.getLong(4)

            val music = Music(id, title, artist, albumId, duration)
            musicList.add(music)
        }
        return musicList
    }

}