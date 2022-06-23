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


public class PhrasesFragment extends Fragment {

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

    public PhrasesFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list,container,false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(R.string.phrase_where_are_you_going,R.string.telugu_phrase_where_are_you_going,R.raw.number_one));
        words.add(new Word(R.string.phrase_what_is_your_name,
                R.string.telugu_phrase_what_is_your_name, R.raw.number_one));
        words.add(new Word(R.string.phrase_my_name_is,
                R.string.telugu_phrase_my_name_is, R.raw.number_one));
        words.add(new Word(R.string.phrase_how_are_you_feeling,
                R.string.telugu_phrase_how_are_you_feeling, R.raw.number_one));
        words.add(new Word(R.string.phrase_im_feeling_good,
                R.string.telugu_phrase_im_feeling_good, R.raw.number_one));
        words.add(new Word(R.string.phrase_are_you_coming,
                R.string.telugu_phrase_are_you_coming, R.raw.number_one));
        words.add(new Word(R.string.phrase_yes_im_coming,
                R.string.telugu_phrase_yes_im_coming, R.raw.number_one));
        words.add(new Word(R.string.phrase_im_coming,
                R.string.telugu_phrase_im_coming, R.raw.number_one));
        words.add(new Word(R.string.phrase_lets_go,
                R.string.telugu_phrase_lets_go, R.raw.number_one));
        words.add(new Word(R.string.phrase_come_here,
                R.string.telugu_phrase_come_here, R.raw.number_one));

        WordAdapter adapter = new WordAdapter(getActivity(),words,R.color.category_phrases);
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