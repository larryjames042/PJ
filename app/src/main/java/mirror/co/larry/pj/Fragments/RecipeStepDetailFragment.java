package mirror.co.larry.pj.Fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
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

import mirror.co.larry.pj.NetworkUtil.NetworkUtils;
import mirror.co.larry.pj.R;
import mirror.co.larry.pj.databinding.FragmentRecipeStepDetailBinding;
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

    // Final String to store state informaion
    public static final String ISTWOPANE = "istwopane";
    public static final String POSITION = "position";
    public static final String VIDEO_URL = "videourl";
    public static final String THUMBNAIL_URL = "thumbnailurl";
    public static final String DESCRIPTION = "description";
    public static final String PLAYER_POSITION = "player_position";


    private static final String ARG_VIDEO = "videoUrl";
    public static final String ARG_THUMBNAIL= "thumbnail";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_POSITION = "position";
    private static final String ARG_IS_TWOPANE = "twopane";
    public static final String ARG_LIST_SIZE = "list_size";

    private String mVideoUrl;
    private String mShortDescription;
    private String mThumbnail;
    private int mPosition;
    private int mListSize;
    private OnFragmentInteractionListener mListener;
    SimpleExoPlayer exoPlayer;
    boolean isTwoPane;
    long playerPosition;
    FragmentRecipeStepDetailBinding binding;


    public RecipeStepDetailFragment() {
        // Required empty public constructor
    }


    public static RecipeStepDetailFragment newInstance(boolean isTwoPane,int position, String video,String thumbnail, String description) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_TWOPANE, isTwoPane);
        args.putInt(ARG_POSITION, position);
        args.putString(ARG_VIDEO, video);
        args.putString(ARG_THUMBNAIL, thumbnail);
        args.putString(ARG_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    public static RecipeStepDetailFragment newInstance(int position, int listSize, String video,String thumbnail, String description) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putString(ARG_VIDEO, video);
        args.putInt(ARG_LIST_SIZE, listSize);
        args.putString(ARG_THUMBNAIL, thumbnail);
        args.putString(ARG_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            if (getArguments() != null) {
                isTwoPane = getArguments().getBoolean(ARG_IS_TWOPANE);
                mPosition = getArguments().getInt(ARG_POSITION);
                mListSize = getArguments().getInt(ARG_LIST_SIZE);
                mVideoUrl = getArguments().getString(ARG_VIDEO);
                mThumbnail = getArguments().getString(ARG_THUMBNAIL);
                mShortDescription = getArguments().getString(ARG_DESCRIPTION);
            }
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putLong(PLAYER_POSITION, playerPosition);
        outState.putBoolean(ISTWOPANE,isTwoPane);
        outState.putInt(POSITION, mPosition);
        outState.putString(VIDEO_URL, mVideoUrl);
        outState.putString(THUMBNAIL_URL, mThumbnail);
        outState.putString(DESCRIPTION, mShortDescription);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(exoPlayer!=null){
            playerPosition = exoPlayer.getCurrentPosition();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState!=null){
            isTwoPane = savedInstanceState.getBoolean(ISTWOPANE);
            mPosition = savedInstanceState.getInt(POSITION);
            mVideoUrl = savedInstanceState.getString(VIDEO_URL);
            mThumbnail = savedInstanceState.getString(THUMBNAIL_URL);
            mShortDescription = savedInstanceState.getString(DESCRIPTION);
            playerPosition = savedInstanceState.getLong(PLAYER_POSITION, C.TIME_UNSET);

        }
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_step_detail, container, false);
        if(isTwoPane){
            binding.btBack.setVisibility(View.GONE);
            binding.btNext.setVisibility(View.GONE);
        }else{
            binding.btBack.setVisibility(View.VISIBLE);
            binding.btNext.setVisibility(View.VISIBLE);
        }

        binding.tvDescription.setText(mShortDescription);
        View v = binding.getRoot();

        binding.playerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(),R.drawable.cake));

        if(mVideoUrl==""){
            if(mThumbnail==""){
                binding.imageviewNoVideo.setVisibility(View.VISIBLE);
                binding.playerView.setVisibility(View.GONE);
            }else{
                binding.imageviewNoVideo.setVisibility(View.GONE);
                binding.playerView.setVisibility(View.VISIBLE);
                initializeThePlayer(Uri.parse(mThumbnail));
            }
        }else{
            binding.imageviewNoVideo.setVisibility(View.GONE);
            binding.playerView.setVisibility(View.VISIBLE);
            initializeThePlayer(Uri.parse(mVideoUrl));
        }

        // check the current orientation
        int orientation = getActivity().getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            if(isTwoPane){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.playerView.getLayoutParams();
                params.width= ViewGroup.LayoutParams.MATCH_PARENT;
                params.height= 700;
                binding.playerView.setLayoutParams(params);
            }else{
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.playerView.getLayoutParams();
                params.width= ViewGroup.LayoutParams.MATCH_PARENT;
                params.height= ViewGroup.LayoutParams.MATCH_PARENT;
                binding.playerView.setLayoutParams(params);
                binding.btNext.setVisibility(View.GONE);
                binding.btBack.setVisibility(View.GONE);
                binding.tvDescription.setVisibility(View.GONE);
                ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
            }

        }else{
            ((AppCompatActivity)getActivity()).getSupportActionBar().show();
            if(isTwoPane){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.playerView.getLayoutParams();
                params.width= ViewGroup.LayoutParams.MATCH_PARENT;
                params.height=500;
                binding.playerView.setLayoutParams(params);
            }else{
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.playerView.getLayoutParams();
                params.width= ViewGroup.LayoutParams.MATCH_PARENT;
                params.height=ViewGroup.LayoutParams.WRAP_CONTENT;
                binding.playerView.setLayoutParams(params);

                // check if step position is less than list size
                if(mPosition==mListSize-1){
                    binding.btNext.setVisibility(View.GONE);
                }else{
                    binding.btNext.setVisibility(View.VISIBLE);
                }
                binding.btNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPosition = mPosition+1;
                        mListener.onNextButtonClick(mPosition);
                    }
                });

                if(mPosition==(mListSize-mListSize)){
                    binding.btBack.setVisibility(View.GONE);
                }else{
                    binding.btBack.setVisibility(View.VISIBLE);
                }
                binding.btBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPosition = mPosition-1;
                        mListener.onBackButtonClick(mPosition);
                    }
                });
            }
        }
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
            binding.playerView.setPlayer(exoPlayer);
            String userAgent = com.google.android.exoplayer2.util.Util.getUserAgent(getActivity(), "RecipeStepDetailActivity");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getActivity(),userAgent), new DefaultExtractorsFactory(), null, null);
            if(playerPosition!= C.TIME_UNSET){
                exoPlayer.seekTo(playerPosition);
            }
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);

        }
    }


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
