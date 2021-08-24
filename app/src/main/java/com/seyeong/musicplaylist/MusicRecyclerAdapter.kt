package com.seyeong.musicplaylist

import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seyeong.musicplaylist.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class MusicRecyclerAdapter: RecyclerView.Adapter<MusicRecyclerAdapter.Holder>() {
    var musicList = mutableListOf<Music>() // 리사이클러뷰에 담을 Music 형태의 데이터 변수
    var mediaPlayer: MediaPlayer? = null // 리사이클러뷰를 클릭했을 때 실행되는 MediaPlayer를 담는 변수

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // 화면에 보이는 아이템 레이아웃의 바인딩을 생성하는 onCreateViewHolder()를 구현
        return Holder(binding)
    }

    override fun getItemCount(): Int { // 목록의 개수를 알려주는 getItemCount()
        return musicList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val music = musicList.get(position)
        // 아이템 레이아웃에 데이터를 출력하는 onBindViewHolder()를 구현
        holder.setMusic(music)
    }

    inner class Holder(val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {

        var musicUri: Uri? = null // 음원 주소

        fun setMusic(music: Music) {
            binding.run { // run 함수를 사용하면 매번 binding. 을 입력하지 않아도 된다.
                // imageAlbum.setImageURI(music.getAlbumUri()) // 이미지 세팅
                textArtist.text = music.artist // 아티스트 세팅
                textTitle.text = music.title // 제목 세팅

                val duration = SimpleDateFormat("mm:ss").format(music.duration)
                textDuration.text = duration // 음악 재생 시간 세팅
            }
            this.musicUri = music.getMusicUri()
        }

        init {
            itemView.setOnClickListener { // itemView라는 아이디를 가진 객체는 만들지 않았는데 리사이클러뷰를 터치하는것이라 이해.
                if (mediaPlayer != null) { // 현재 mediaPlayer에 설정된 값이 있으면 해제한 후
                    mediaPlayer?.release()
                    mediaPlayer = null
                }
                mediaPlayer = MediaPlayer.create(itemView.context, musicUri) // mediaPlayer에 새로운 값을 넣고
                mediaPlayer?.start() // 해당 미디어를 실행하도록.
            }
        }
    }

}

