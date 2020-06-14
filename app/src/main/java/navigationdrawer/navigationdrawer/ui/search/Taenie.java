package navigationdrawer.navigationdrawer.ui.search;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Taenie {
    public int rank;
    public String singer, songtitle;
    public Bitmap album_cover;
    public byte[] music = new byte[20];
    public Taenie(int rank, String singer, String songtitle, Bitmap album_cover, byte[] music){
        this.rank=rank;
        this.singer=singer;
        this.songtitle=songtitle;
        this.album_cover=album_cover;
        this.music=music;
    }
    public Taenie(){
    }

    public int getRank(){ return rank; }
    public void setRank(int rank){ this.rank = rank; }
    public String getSinger(){ return singer; }
    public void setSinger(String singer){
        this.singer = singer;
    }
    public String getSongTitle(){
        return songtitle;
    }
    public void setSongtitle(String songtitle){
        this.songtitle = songtitle;
    }
    public Bitmap getAlbumCover(){return album_cover;}
    public void setAlbum_cover(byte[] album_cover){
        byte[] image = album_cover;
        Bitmap bm = BitmapFactory.decodeByteArray(image,0,image.length);
        this.album_cover = bm;
    }

    public byte[] getMusic(){return music;}
    public void setMusic(byte[] music){
        this.music = music;
    }
}
