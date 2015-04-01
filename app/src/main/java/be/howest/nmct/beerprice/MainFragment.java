package be.howest.nmct.beerprice;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Michiel on 30/03/2015.
 */
public class MainFragment extends Fragment{

    public interface ButtonCLickedMainFragment {
        void onClickListShow();
        void onClickMapShow();
    }


    private Button btnList;
    private Button btnMap;
    ButtonCLickedMainFragment clicked;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        clicked = (ButtonCLickedMainFragment) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main,container, false);
        btnList = (Button) v.findViewById(R.id.btnList);
        btnMap = (Button) v.findViewById(R.id.btnMap);

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
        return v;
    }
}
