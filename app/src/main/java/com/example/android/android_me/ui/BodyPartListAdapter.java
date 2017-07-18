package com.example.android.android_me.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by antlap on 13/07/2017.
 */

public class BodyPartListAdapter extends RecyclerView.Adapter<BodyPartListAdapter.BodyImagesRowHolder> {
    private static final String TAG = BodyPartListAdapter.class.getSimpleName();

    final private List<Integer> mAllImages = AndroidImageAssets.getAll();
    private static int currentImageIndex = 0;

    public BodyPartListAdapter(){
//        mAllImages.addAll(AndroidImageAssets.getAll());
//        mAllImages.addAll(AndroidImageAssets.getAll());
//        mAllImages.addAll(AndroidImageAssets.getAll());
    }

    @Override
    public BodyImagesRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_images_body_part_row, parent, false);
        BodyImagesRowHolder holder = new BodyImagesRowHolder(view);

        holder.setRowImages(getNextImages());

        return holder;
    }

    public int[] getNextImages() {
        int[] images = new int[3];

        for (int i = 0; i < 3; i++) {
            int imgIdx = currentImageIndex + i;
            Log.d(TAG, "getNextImages: imgIdx = " + imgIdx);
            images[i] = mAllImages.get(imgIdx);
        }

        currentImageIndex = (currentImageIndex + 3) % mAllImages.size();
        return images;
    }

    @Override
    public void onBindViewHolder(BodyImagesRowHolder holder, int position) {
        holder.setRowImages(getImagesForPosition(position));
    }

    public int[] getImagesForPosition(int position) {
        int[] images = new int[3];

        for(int i = 0; i < 3; i++){
            int imgIdx = position * 3 + i;
            Log.d(TAG, "getImagesForPosition: imgIdx = " + imgIdx);
            images[i] = mAllImages.get(imgIdx);
        }

        return images;
    }

    @Override
    public int getItemCount() {
        return mAllImages.size() / 3;
    }


    class BodyImagesRowHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewFirstColumn;
        private ImageView imageViewSecondColumn;
        private ImageView imageViewThirdColumn;

        public BodyImagesRowHolder(View itemView) {
            super(itemView);
            imageViewFirstColumn = (ImageView) itemView.findViewById(R.id.iv_column1);
            imageViewSecondColumn = (ImageView) itemView.findViewById(R.id.iv_column2);
            imageViewThirdColumn = (ImageView) itemView.findViewById(R.id.iv_column3);
        }

        public void setRowImages(int[] column){
            if(column[0] > 0){
//                imageViewFirstColumn.setImageResource(column[0]);
                Picasso.with(itemView.getContext())
                        .load(column[0])
                        .placeholder(R.drawable.ic_android_placeholder)
                        .error(R.drawable.ic_android_placeholder)
                        .fit()
                        .into(imageViewFirstColumn);
            }
            if(column[1] > 0){
//                imageViewSecondColumn.setImageResource(column[1]);
                Picasso.with(itemView.getContext())
                        .load(column[1])
                        .placeholder(R.drawable.ic_android_placeholder)
                        .error(R.drawable.ic_android_placeholder)
                        .fit()
                        .into(imageViewSecondColumn);
            }
            if(column[2] > 0){
//                imageViewThirdColumn.setImageResource(column[2]);
                Picasso.with(itemView.getContext())
                        .load(column[2])
                        .placeholder(R.drawable.ic_android_placeholder)
                        .error(R.drawable.ic_android_placeholder)
                        .fit()
                        .into(imageViewThirdColumn);
            }
        }

    }

}
