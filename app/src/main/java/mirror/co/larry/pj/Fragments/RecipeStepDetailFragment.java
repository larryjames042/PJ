package mirror.co.larry.pj.Fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import mirror.co.larry.pj.R;
import okhttp3.internal.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeStepDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeStepDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeStepDetailFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_VIDEO = "videoUrl";
    public static final String ARG_THUMBNAIL= "thumbnail";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_POSITION = "position";
    // TODO: Rename and change types of parameters
    private String mVideoUrl;
    private String mShortDescription;
    private String mThumbnail;
    private int mPosition;
    private OnFragmentInteractionListener mListener;
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    TextView mDescription;
    ImageView mNoVideoImageView;
    ImageButton mForwardButton;
    ImageButton mBackButton;



    public RecipeStepDetailFragment() {
        // Required empty public constructor
    }


    public static RecipeStepDetailFragment newInstance(int position, String video,String thumbnail, String description) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putString(ARG_VIDEO, video);
        args.putString(ARG_THUMBNAIL, thumbnail);
        args.putString(ARG_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mPosition = getArguments().getInt(ARG_POSITION);
            mVideoUrl = getArguments().getString(ARG_VIDEO);
            mThumbnail = getArguments().getString(ARG_THUMBNAIL);
            mShortDescription = getArguments().getString(ARG_DESCRIPTION);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            // hide description view
            mDescription.setVisibility(View.GONE);
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
            if(((AppCompatActivity)getActivity()).getSupportActionBar()!=null){
                ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
            }
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) exoPlayerView.getLayoutParams();
            params.width= ViewGroup.LayoutParams.MATCH_PARENT;
            params.height= ViewGroup.LayoutParams.MATCH_PARENT;
            exoPlayerView.setLayoutParams(params);
        }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            ((AppCompatActivity)getActivity()).getSupportActionBar().show();
            if(((AppCompatActivity)getActivity()).getSupportActionBar()!=null){
                ((AppCompatActivity)getActivity()).getSupportActionBar().show();
            }
            //unhide description
            mDescription.setVisibility(View.VISIBLE);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) exoPlayerView.getLayoutParams();
            params.width= ViewGroup.LayoutParams.MATCH_PARENT;
            params.height=350;
            exoPlayerView.setLayoutParams(params);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        exoPlayerView = v.findViewById(R.id.player_view);
        mDescription = v.findViewById(R.id.tv_description);
        mForwardButton = v.findViewById(R.id.bt_next);
         mBackButton = v.findViewById(R.id.bt_back);
        mDescription.setText(mShortDescription);
        mNoVideoImageView = v.findViewById(R.id.imageview_no_video);
        exoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background));


        if(mVideoUrl==""){
            if(mThumbnail==""){
                mNoVideoImageView.setVisibility(View.VISIBLE);
                exoPlayerView.setVisibility(View.GONE);
            }else{
                mNoVideoImageView.setVisibility(View.GONE);
                exoPlayerView.setVisibility(View.VISIBLE);
                initializeThePlayer(Uri.parse(mThumbnail));
            }
        }else{
            mNoVideoImageView.setVisibility(View.GONE);
            exoPlayerView.setVisibility(View.VISIBLE);
            initializeThePlayer(Uri.parse(mVideoUrl));
        }

        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onNextButtonClick(mPosition++);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onNextButtonClick(mPosition--);
            }
        });

        return v;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer(){
        if(exoPlayer!=null){
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }

    }
    private void initializeThePlayer(Uri mediaUri){
        if(exoPlayer==null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            exoPlayerView.setPlayer(exoPlayer);
            String userAgent = com.google.android.exoplayer2.util.Util.getUserAgent(getActivity(), "RecipeStepDetailActivity");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getActivity(),userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void onNextButtonClick(int position);
        void onBackButtonClick(int position);
    }

}
