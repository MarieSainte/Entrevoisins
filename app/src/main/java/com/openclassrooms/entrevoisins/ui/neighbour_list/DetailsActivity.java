package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.avatar)
    ImageView avatar;

    @BindView(R.id.backArrow)
    ImageView backArrow;

    @BindView(R.id.mainName)
    TextView mainName;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.txt_localisation)
    TextView txt_localisation;

    @BindView(R.id.txt_phone)
    TextView txt_phone;

    @BindView(R.id.txt_About)
    TextView txt_About;

    @BindView(R.id.view_favorite)
    View view_favorite;

    @BindView(R.id.img_star)
    ImageView img_star;

    static Neighbour mNeighbour;
    private NeighbourApiService mApiService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mApiService = DI.getNeighbourApiService();
        ButterKnife.bind(this);
        init();
    }

    public static void setUser(Neighbour neighbour){
        mNeighbour = neighbour;
    }

    private void init(){
        Glide.with(this).load(mNeighbour.getAvatarUrl()).centerCrop().into(avatar);
        name.setText(mNeighbour.getName());
        mainName.setText(mNeighbour.getName());
        txt_localisation.setText(mNeighbour.getAddress());
        txt_About.setText(mNeighbour.getAboutMe());
        txt_phone.setText(mNeighbour.getPhoneNumber());
        if(mNeighbour.isFavorite()){
            DrawableCompat.setTint(img_star.getDrawable(), ContextCompat.getColor(this, R.color.yellow));
        }
        else{
            DrawableCompat.setTint(img_star.getDrawable(), ContextCompat.getColor(this, R.color.grey));
        }
    }

    @OnClick(R.id.view_favorite)
    void buttonClick(){
        if (mNeighbour.isFavorite()){
            mApiService.deleteFavoriteNeighbour(mNeighbour);
            DrawableCompat.setTint(img_star.getDrawable(), ContextCompat.getColor(this, R.color.grey));
        }
        else{
            mApiService.addFavoriteNeighbour(mNeighbour);
            DrawableCompat.setTint(img_star.getDrawable(), ContextCompat.getColor(this, R.color.yellow));
        }
    }

    @OnClick(R.id.backArrow)
    void backClick(){
        onBackPressed();
    }


}
