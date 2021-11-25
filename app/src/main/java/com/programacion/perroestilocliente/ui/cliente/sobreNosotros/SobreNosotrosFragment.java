package com.programacion.perroestilocliente.ui.cliente.sobreNosotros;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.programacion.perroestilocliente.R;

public class SobreNosotrosFragment extends Fragment  {

    private SobreNosotrosViewModel mViewModel;
    //=== YouTubePlayerView videoView;
    private YouTubePlayerView youTubePlayerView;
    VideoView videoView;
    private  String claveYoutube="AIzaSyBS0_YxGOmA4f1FeZTnZo0-d5jr5iCn7LE";

    public static SobreNosotrosFragment newInstance() {
        return new SobreNosotrosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_sobre_nosotros, container, false);
        youTubePlayerView=(YouTubePlayerView) root.findViewById(R.id.videoViewSobreNosotros);
        getLifecycle().addObserver(youTubePlayerView);
       /* youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onApiChange(@NonNull YouTubePlayer youTubePlayer) {
                super.onApiChange(youTubePlayer);
                String videoId="sWwKVDgCW08";
                youTubePlayer.loadVideo(videoId,0);
            }
        });*/
        //=== videoView.initialize(claveYoutube,this);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SobreNosotrosViewModel.class);
        // TODO: Use the ViewModel
    }
/* ==
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean fueRestaurado) {
        if(!fueRestaurado){
            youTubePlayer.cueVideo("azxDhcKYku4");//https://www.youtube.com/watch?v=azxDhcKYku4
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(getActivity(),1).show();
        }else{
            String erro="Error al inicializar youbute"+ youTubeInitializationResult.toString();
            Toast.makeText(getActivity().getApplication(),erro,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            getYoutubePlayerProvaider().initialize(claveYoutube,this);
        }
    }

    protected  YouTubePlayer.Provider getYoutubePlayerProvaider(){
        return videoView;
    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
    */

}