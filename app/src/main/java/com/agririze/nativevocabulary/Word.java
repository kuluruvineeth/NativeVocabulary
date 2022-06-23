package com.agririze.nativevocabulary;

public class Word {
    private int mDefaultTranslationId;
    private int mTeluguTranslationId;
    private int mAudioResourceId;
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    public Word(int mDefaultTranslationId, int mTeluguTranslationId, int mAudioResourceId) {
        this.mDefaultTranslationId = mDefaultTranslationId;
        this.mTeluguTranslationId = mTeluguTranslationId;
        this.mAudioResourceId = mAudioResourceId;
    }

    public Word(int mDefaultTranslationId, int mTeluguTranslationId, int mImageResourceId,int mAudioResourceId) {
        this.mDefaultTranslationId = mDefaultTranslationId;
        this.mTeluguTranslationId = mTeluguTranslationId;
        this.mAudioResourceId = mAudioResourceId;
        this.mImageResourceId = mImageResourceId;
    }

    public int getDefaultTranslationId() {
        return mDefaultTranslationId;
    }

    public int getTeluguTranslationId() {
        return mTeluguTranslationId;
    }

    public int getAudioResourceId() {
        return mAudioResourceId;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}
