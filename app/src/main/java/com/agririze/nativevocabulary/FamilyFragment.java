package com.agririze.nativevocabulary;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class FamilyFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT ||
                    focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    public FamilyFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list,container,false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(R.string.family_father, R.string.telugu_family_father,
                R.drawable.family_father, R.raw.number_one));
        words.add(new Word(R.string.family_mother, R.string.telugu_family_mother,
                R.drawable.family_mother, R.raw.number_one));
        words.add(new Word(R.string.family_son, R.string.telugu_family_son,
                R.drawable.family_son, R.raw.number_one));
        words.add(new Word(R.string.family_daughter, R.string.telugu_family_daughter,
                R.drawable.family_daughter, R.raw.number_one));
        words.add(new Word(R.string.family_older_brother, R.string.telugu_family_older_brother,
                R.drawable.family_older_brother, R.raw.number_one));
        words.add(new Word(R.string.family_younger_brother, R.string.telugu_family_younger_brother,
                R.drawable.family_younger_brother, R.raw.number_one));
        words.add(new Word(R.string.family_older_sister, R.string.telugu_family_older_sister,
                R.drawable.family_older_sister, R.raw.number_one));
        words.add(new Word(R.string.family_younger_sister, R.string.telugu_family_younger_sister,
                R.drawable.family_younger_sister, R.raw.number_one));
        words.add(new Word(R.string.family_grandmother, R.string.telugu_family_grandmother,
                R.drawable.family_grandmother, R.raw.number_one));
        words.add(new Word(R.string.family_grandfather, R.string.telugu_family_grandfather,
                R.drawable.family_grandfather, R.raw.number_one));

        WordAdapter adapter = new WordAdapter(getActivity(),words,R.color.category_family);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word word = words.get(position);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(getActivity(),word.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
