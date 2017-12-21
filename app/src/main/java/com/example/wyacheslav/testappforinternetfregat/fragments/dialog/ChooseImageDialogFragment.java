package com.example.wyacheslav.testappforinternetfregat.fragments.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wyacheslav.testappforinternetfregat.R;

/**
 * Диалог с вводом комментария
 */
public class ChooseImageDialogFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_fragment_choose_image, container, false);

        // Текстовые поля
        TextView textViewGallery = rootView.findViewById(R.id.tv_gallery);
        TextView textViewCamera = rootView.findViewById(R.id.tv_camera);
        TextView textViewClose = rootView.findViewById(R.id.tv_close);

        // Обработчики нажатий
        textViewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Открытие активити выбора фото из галереи
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
                getDialog().dismiss();
            }
        });

        textViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Открытие активити камеры
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 2);
                }
                getDialog().dismiss();
            }
        });

        textViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return rootView;
    }
}
