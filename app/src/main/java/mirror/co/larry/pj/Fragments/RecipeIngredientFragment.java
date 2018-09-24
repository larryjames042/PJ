package mirror.co.larry.pj.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import mirror.co.larry.pj.Adapters.RecipeIngredientAdapter;
import mirror.co.larry.pj.Models.Ingredients;
import mirror.co.larry.pj.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeIngredientFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeIngredientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeIngredientFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_INGREDIENT_LIST = "ingredient_list";

    // TODO: Rename and change types of parameters
    private List<Ingredients> mIngredientsList;


    private OnFragmentInteractionListener mListener;
    RecyclerView mIngredientRecyclerView;
    RecipeIngredientAdapter mIngredientAdapter;

    public RecipeIngredientFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RecipeIngredientFragment newInstance(List<Ingredients> ingredients) {
        RecipeIngredientFragment fragment = new RecipeIngredientFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_INGREDIENT_LIST, (Serializable) ingredients);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIngredientsList = (List<Ingredients>) getArguments().getSerializable(ARG_INGREDIENT_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_ingredient, container, false);
        mIngredientRecyclerView = v.findViewById(R.id.rv_ingredient);
        mIngredientRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mIngredientRecyclerView.setHasFixedSize(true);
        mIngredientAdapter = new RecipeIngredientAdapter(getActivity(), mIngredientsList);
        mIngredientRecyclerView.setAdapter(mIngredientAdapter);
        return v;
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
    }
}
