package navigationdrawer.navigationdrawer.ui.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import navigationdrawer.navigationdrawer.MainActivity;
import navigationdrawer.navigationdrawer.R;
import navigationdrawer.navigationdrawer.ui.Database.Database;

import navigationdrawer.navigationdrawer.ui.play.PlayFragment;
import navigationdrawer.navigationdrawer.ui.search.Taenie;

class SearchViewHolder extends RecyclerView.ViewHolder {

    public TextView rank, song_title, singer_name;
    public ImageView album_cover;
    public ImageButton mp3_image_button;
    public byte[] Music = new byte[20];


    public SearchViewHolder(final View itemView) {
        super(itemView);
        rank = (TextView) itemView.findViewById(R.id.rank);
        song_title = (TextView) itemView.findViewById(R.id.song_title);
        singer_name = (TextView) itemView.findViewById(R.id.singer_name);
        album_cover = (ImageView) itemView.findViewById(R.id.album_cover_image);
        mp3_image_button = (ImageButton) itemView.findViewById(R.id.mp3_image_button_1);
    }
}

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    public Context context;
    public static List<Taenie> taenies;
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    int a = -10;
    public static int b = 0;
    public Fragment fragment = null;
    public static String Play_Title;
    public static String Play_Singer;
    public static Bitmap Play_Album_Cover;

    public SearchAdapter(Context context, List<Taenie> taenies) {
        this.context = context;
        this.taenies = taenies;
    }

    @NonNull

    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from((parent.getContext()));
        View itemView = inflater.inflate(R.layout.fragment_search2, parent, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, final int position) {

        holder.rank.setText(String.valueOf(taenies.get(position).getRank()));
        holder.singer_name.setText(taenies.get(position).getSinger());
        holder.song_title.setText(taenies.get(position).getSongTitle());
        holder.album_cover.setImageBitmap(taenies.get(position).getAlbumCover());//getAddress(taenies.get(position).getAlbumCover())
        holder.Music = taenies.get(position).getMusic();

        holder.mp3_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeFragment();//프라그먼트 전환
                Play_Title = taenies.get(position).getSongTitle();//PlayFragment(Play화면)에 있는 타이틀
                Play_Singer = taenies.get(position).getSinger();//PlayFragment(Play화면)에 있는 가수
                Play_Album_Cover = taenies.get(position).getAlbumCover();//PlayFragment(Play화면)에 있는 앨범커버
                b = taenies.get(position).getRank() - 1;

                if (a != taenies.get(position).getRank() - 1) {//getRank()-1  과  변수a가 같지않다면
                    playMp3(Database.musicList.get(taenies.get(position).getRank() - 1), Database.context2);
                    a = taenies.get(position).getRank() - 1;
                }

                //변형
                //playMp3(Database.musicList.get(position), Database.context2);//젤처음
                //Toast.makeText(MainActivity.context_main, String.valueOf(position), Toast.LENGTH_SHORT).show();//Toast메세지 출력
                if (!mediaPlayer.isPlaying()) {
                    // Stopping
                    mediaPlayer.start();
                    holder.mp3_image_button.setImageResource(R.drawable.ic_pause_black_24dp);

                } else {
                    // Playing 플레이중일때
                    mediaPlayer.pause();//pause모양
                    holder.mp3_image_button.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }
//                 ///////////////////////
//                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        nextSong();
//                    }
//                });
            }
        });

//        /////////////////////////
//        private void nextSong(){
//            if(b>=taenies.size()){
//                b=0;
//            }else{
//                b++;
//                playMp3(Database.musicList.get(taenies.get(b).getRank() - 1), Database.context2);
//                mediaPlayer.start();
//            }
//        }
    }

    public static void playMp3(byte[] mp3SoundByteArray, Context context) {
        try {
            // create temp file that will hold byte array
            File tempMp3 = File.createTempFile("fmtemp", ".mp3", context.getCacheDir());
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(mp3SoundByteArray);
            fos.close();
            mediaPlayer.reset();
            FileInputStream fis = new FileInputStream(tempMp3);
            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.prepare();

            tempMp3.deleteOnExit();


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    @Override
    public int getItemCount() {
        return taenies.size();
    }

    public void changeFragment() {

        Class fragmentClass;
        fragmentClass = PlayFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MainActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();

    }

    public static void initMusicPlayer(int position) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
        mediaPlayer.reset();
        }

        Play_Title = taenies.get(b).getSongTitle();//PlayFragment(Play화면)에 있는 타이틀
        Play_Singer = taenies.get(b).getSinger();//PlayFragment(Play화면)에 있는 가수
        Play_Album_Cover = taenies.get(b).getAlbumCover();//PlayFragment(Play화면)에 있는 앨범커버

        //create a media player
        SearchAdapter.playMp3(Database.musicList.get(taenies.get(b).getRank() - 1), Database.context2);
        mediaPlayer.start();

        if(mediaPlayer.isPlaying()){
            PlayFragment.playBtn.setImageResource(R.drawable.ic_pause);
        }else{
            PlayFragment.playBtn.setImageResource(R.drawable.ic_play);
        }
    }
}


