package com.example.alarmadeus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import java.util.Random;
import android.os.Handler;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    private MediaPlayer m_player;
    private Button play, pause;

    private Random rand = new Random();
    private int rand_seconds, rand_row, rand_column, rand_matrix;
    private String final_chained_str, selected_file_name;
    private double[] note_seconds = {0.25, 0.5, 1, 1.5, 2};
    private int[][] note_matrix = {
            {4, 16, 28, 40, 52, 64, 76, 88},
            {5, 17, 29, 41, 53, 65, 77},
            {6, 18, 30, 42, 54, 66, 78},
            {7, 19, 31, 43, 55, 67, 79},
            {8, 20, 32, 44, 56, 68, 80},
            {9, 21, 33, 45, 57, 69, 81},
            {10 ,22 ,34 ,46 ,58 ,70 ,82},
            {11 ,23 ,35 ,47 ,59 ,71 ,83},
            {12 ,24 ,36 ,48 ,60 ,72 ,84},
            {1 ,13 ,25 ,37 ,49 ,61 ,73 ,85},
            {2 ,14 ,26 ,38 ,50 ,62 ,74 ,86},
            {3 ,15 ,27 ,39 ,51 ,63 ,75 ,87}
    };
    private String[] music_file_names = {"a", "db", "d", "eb", "e", "f", "gb", "g", "ab", "a", "bb", "b"};
    private int default_num = 6;
    private ArrayList<Integer> playlist;
    Handler handler = new Handler();
    public String nameSelector(int row, int col){
        String file_name;
        return file_name = music_file_names[row] + Integer.toString(col);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //music handling
        //play = findViewById(R.id.play);
        //pause = findViewById(R.id.pause);
    }

    /*public void playSong(){
        m_player.reset();
        m_player.setDataSource(playlist.get());
        m_player.prepare();
    }*/
    public void play(View view){
        System.out.println("randy");
        //reset str
        final_chained_str = "";
        playlist = new ArrayList<>();
        int sound_id;
        for(int i = 0; i < default_num; i++){
            rand_seconds = rand.nextInt(5-0);
            rand_row = rand.nextInt(note_matrix.length - 0);
            rand_column = rand.nextInt(note_matrix[rand_row].length - 0);
            rand_matrix = note_matrix[rand_row][rand_column];
            final_chained_str += (rand_row + " " + Integer.toString(rand_matrix) + Integer.toString(rand_seconds) + " ");
            selected_file_name = nameSelector(rand_row, rand_column);
            System.out.println(selected_file_name);
            sound_id = this.getResources().getIdentifier(selected_file_name, "raw", this.getPackageName());
            playlist.add(sound_id);
            //put all the selected music in a array then loop and play through a mediaplay list
            //then figure out how to set timer OR handler

            //m_player.start();
            /*handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    m_player.stop();
                }
            }, 2000);*/
        }

        final CountDownTimer cntr_aCounter = new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                //m_player.start();
                System.out.println("this is a test");
            }
            public void onFinish(){
                System.out.println("rooster is done");
                m_player.stop();
                //m_player.reset();
                //m_player.release();
                m_player.start();
            }
        };

        final int[] myMusic = {R.raw.a1, R.raw.a2, R.raw.a3};
        m_player = MediaPlayer.create(MainActivity.this, myMusic[0]);
        //System.out.println(j);
        m_player.start();
        cntr_aCounter.start();
        m_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            int j = 0;
            @Override
            public void onCompletion(MediaPlayer mp) {
                j++;
                m_player.reset();
                //resets player
                if(j < myMusic.length){
                    AssetFileDescriptor afd = getResources().openRawResourceFd(myMusic[j]);
                    //gets resource
                    try{
                        m_player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        afd.close();
                        m_player.prepare();
                        m_player.start();
                        cntr_aCounter.start();
                    }catch (Exception ex){
                        System.out.print("encountered an error "+ ex);
                    }

                }else {
                    //releases and removes m_player if it reaches end of array
                    m_player.release();
                    m_player = null;
                }
            }
        });
        //
        //cntr_aCounter.start();

    }

    //MediaPlayer mediaPlayer = new MediaPlayer();

}
