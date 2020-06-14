package navigationdrawer.navigationdrawer.ui.play;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.inputmethod.InputConnectionCompat;
import androidx.fragment.app.Fragment;


import navigationdrawer.navigationdrawer.R;
import navigationdrawer.navigationdrawer.ui.Adapter.SearchAdapter;

public class PlayFragment extends Fragment {


    SeekBar positionBar;
    SeekBar volumeBar;
    TextView elapsedTimeLabel;
    TextView remainingTimeLabel;
    public static ImageView play_album_image;
    public static TextView play_singer;
    public static TextView play_title;
    //Play화면의 버튼들
    public static ImageButton playBtn;
    public static ImageButton play_prev_Btn;
    public static ImageButton play_next_Btn;
    public static ImageButton play_repeat_Btn;
    //MediaPlayer
    public static MediaPlayer mp;
    int totalTime;
    private View v;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_play, container, false);


        //
        play_album_image = (ImageView) v.findViewById(R.id.imageAlbumArt);
        play_singer = (TextView) v.findViewById(R.id.textArtist);
        play_title = (TextView) v.findViewById(R.id.textTitle);

        //버튼기능추가
        playBtn = (ImageButton) v.findViewById(R.id.buttonPlay);
        play_prev_Btn = (ImageButton) v.findViewById(R.id.buttonPrevious);
        play_next_Btn = (ImageButton) v.findViewById(R.id.buttonNext);
        elapsedTimeLabel = (TextView) v.findViewById(R.id.textCurrentTime);
        remainingTimeLabel = (TextView) v.findViewById(R.id.textTotalTime);

        //
        if (SearchAdapter.Play_Title != null) {
            play_album_image.setImageBitmap(SearchAdapter.Play_Album_Cover);
            play_title.setText(SearchAdapter.Play_Title);
            play_singer.setText(SearchAdapter.Play_Singer);
            mp = SearchAdapter.mediaPlayer;
            playBtn.setImageResource(R.drawable.ic_pause);


            //Previous 버튼 눌렀을 때
            PlayFragment.play_next_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SearchAdapter.b < SearchAdapter.taenies.size() - 1) {
                        //check if the current position of the song in the list is less than the total song present in the list
                        //increase the position by one to play next song in the list
                        SearchAdapter.b++;
                    } else {
                        //if the position is greater than or equal to the number of songs on the list
                        //set the position to zero
                        SearchAdapter.b = 0;
                    }
                    SearchAdapter.initMusicPlayer(SearchAdapter.b);
                    play_album_image.setImageBitmap(SearchAdapter.Play_Album_Cover);
                    play_title.setText(SearchAdapter.Play_Title);
                    play_singer.setText(SearchAdapter.Play_Singer);
                }
            });
            //Next 버튼 눌렀을때
            PlayFragment.play_prev_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SearchAdapter.b <= 0) {
                        //if the position of the song on the list is less or equal to 0

                        SearchAdapter.b = SearchAdapter.taenies.size() - 1;
                    } else {
                        SearchAdapter.b--;
                    }

                    SearchAdapter.initMusicPlayer(SearchAdapter.b);
                    play_album_image.setImageBitmap(SearchAdapter.Play_Album_Cover);
                    play_title.setText(SearchAdapter.Play_Title);
                    play_singer.setText(SearchAdapter.Play_Singer);
                }
            });

            //Random 버튼을 눌렀을때

            // Media Player
//        mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.vos_lovewell);
            mp.setLooping(true);
            mp.seekTo(0);
            mp.setVolume(10000.0f, 10000.0f);
            totalTime = mp.getDuration();

            // Position Bar
            positionBar = (SeekBar) v.findViewById(R.id.playerSeekBar);
            positionBar.setMax(totalTime);
            positionBar.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                mp.seekTo(progress);
                                positionBar.setProgress(progress);
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    }
            );


            // Volume Bar

            volumeBar = (SeekBar) v.findViewById(R.id.volumeBar);
            volumeBar.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            float volumeNum = progress / 100f;
                            mp.setVolume(volumeNum, volumeNum);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    }
            );

            // Thread (Update positionBar & timeLabel)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mp != null) {
                        try {
                            Message msg = new Message();
                            msg.what = mp.getCurrentPosition();
                            handler.sendMessage(msg);
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }).start();

        }


        return v;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            // Update positionBar.
            positionBar.setProgress(currentPosition);

            // Update Labels.
            String elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime = createTimeLabel(totalTime - currentPosition);
            remainingTimeLabel.setText("- " + remainingTime);
        }
    };

    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }




}