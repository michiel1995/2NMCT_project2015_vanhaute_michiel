package be.howest.nmct.beerprice;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import be.howest.nmct.admin.BeerPrice;

/**
 * Created by Michiel on 2/04/2015.
 */
public class BeerBrandFragment extends ListFragment {
    public interface OnSendBackBrand{
        void onSendBackBrand(BeerPrice.BEERBRANDS brand);
    }
    private OnSendBackBrand sendBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            sendBack = (OnSendBackBrand) activity;
        }catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString() + " must implement OnStudentListItem");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        BeerPrice.BEERBRANDS brand = BeerPrice.BEERBRANDS.values()[position];
        sendBack.onSendBackBrand(brand);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new BeerbrandAdapter());
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    class BeerbrandAdapter extends ArrayAdapter<BeerPrice.BEERBRANDS>{

      public BeerbrandAdapter(){
           super(getActivity(),android.R.layout.simple_list_item_1,BeerPrice.BEERBRANDS.values());
       }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView,parent);
            BeerPrice.BEERBRANDS bbr = BeerPrice.BEERBRANDS.values()[position];

            Viewholder vwh = (Viewholder) row.getTag();
            if(vwh ==null){
                vwh = new Viewholder(row);
                row.setTag(vwh);}
            vwh.setContent(bbr);
            return row;
            }

        }
        class Viewholder{
            private TextView tv;

            public Viewholder(View row){
                tv = (TextView) row.findViewById(android.R.id.text1);
            }
            public void setContent(BeerPrice.BEERBRANDS br){
                tv.setText(br.getNaam());
            }
        }
    }


