package be.howest.nmct.beerprice;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Michiel on 30/03/2015.
 */
public class MainFragment extends Fragment{



    public interface ButtonCLickedMainFragment {
        void onClickListShow();
        void onClickMapShow();
        void onClickNewShow();
    }


    private ImageButton btnList;
    private ImageButton btnMap;
    private ImageButton btnNew;
    ButtonCLickedMainFragment clicked;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            clicked = (ButtonCLickedMainFragment) activity;
        }catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString() + " must implement OnStudentListItem");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main,container, false);
        btnList = (ImageButton) v.findViewById(R.id.btnList);
        btnMap = (ImageButton) v.findViewById(R.id.btnMap);
        btnNew = (ImageButton) v.findViewById(R.id.btnNew);

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked.onClickListShow();
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked.onClickMapShow();
            }
        });
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked.onClickNewShow();
            }
        });
        return v;
    }
}
