package com.example.wyacheslav.testappforinternetfregat.events;

import android.graphics.Bitmap;

/**
 * Событие смены иконки
 * Created by wyacheslav on 12.12.17.
 */
public class SetIconEvent {
    /**
     * Иконка
     */
    private Bitmap bitmapIcon;

    public SetIconEvent(Bitmap bitmapIcon) {
        this.bitmapIcon = bitmapIcon;
    }

    public Bitmap getBitmapIcon() {
        return bitmapIcon;
    }

    public void setBitmapIcon(Bitmap bitmapIcon) {
        this.bitmapIcon = bitmapIcon;
    }
}
