package com.utsoft.jan.wtalker.frags.media;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.utsoft.jan.common.tools.UiTool;
import com.utsoft.jan.widget.GalleryView;
import com.utsoft.jan.wtalker.R;

/**
 * Created by Administrator on 2019/7/25.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.media
 */
public class GalleryFragment extends BottomSheetDialogFragment implements GalleryView.SelectedChangeListener {

    private GalleryView mGalleryView;

    private OnSelectListener mListener;


    public GalleryFragment setSelectListener(OnSelectListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public GalleryFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new TranStatusBottomSheetDialog(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        mGalleryView= rootView.findViewById(R.id.galleryView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGalleryView.setup(getLoaderManager(),this);
    }

    @Override
    public void onSelectedCountChanged(int count) {
        if (count>0){
            dismiss();
            if (mListener!=null){
                String[] selectedPath = mGalleryView.getSelectedPath();

                mListener.selectedImage(selectedPath[0]);

                mListener = null;
            }
        }
    }

    public interface OnSelectListener{
        void selectedImage(String path);
    }

    public static class TranStatusBottomSheetDialog extends BottomSheetDialog{

        public TranStatusBottomSheetDialog(@NonNull Context context) {
            super(context);
        }

        public TranStatusBottomSheetDialog(@NonNull Context context, int theme) {
            super(context, theme);
        }

        protected TranStatusBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Window window = getWindow();
            if (window==null)
                return;

            int screenHeight = UiTool.getScreenHeight(getOwnerActivity());
            int screenWidth = UiTool.getScreenWidth(getOwnerActivity());

            int statusBarHeight = UiTool.getStatusBarHeight(getOwnerActivity());

            int dialogHeight = screenHeight - statusBarHeight;

            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,dialogHeight<=0?ViewGroup.LayoutParams.MATCH_PARENT:dialogHeight);

        }
    }
}
