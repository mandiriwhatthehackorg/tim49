package id.wth.celenganmandiri.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import id.wth.celenganmandiri.R;
import id.wth.celenganmandiri.activity.DashboardActivity;

public class OptionFragment extends Fragment {

    LinearLayout lyt_pendidikan, ic_pendidikan, lyt_old, ic_old, lyt_event, ic_event, cardPendidikan,
            cardEvent, cardOld;

    Button verivyBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_option, container, false);

        lyt_pendidikan = rootView.findViewById(R.id.lyt_pendidikan);
        ic_pendidikan = rootView.findViewById(R.id.ic_pendidikan);
        lyt_old = rootView.findViewById(R.id.lyt_old);
        ic_old = rootView.findViewById(R.id.ic_old);
        lyt_event = rootView.findViewById(R.id.lyt_event);
        ic_event = rootView.findViewById(R.id.ic_event);
        cardOld = rootView.findViewById(R.id.cardOld);
        cardEvent = rootView.findViewById(R.id.cardEvent);
        cardPendidikan = rootView.findViewById(R.id.cardPendidikan);

        verivyBtn = rootView.findViewById(R.id.verivyBtn);

        verivyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DashboardActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        cardPendidikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ic_pendidikan.setBackgroundResource(R.drawable.btn_rounded);
                lyt_pendidikan.setBackgroundResource(R.drawable.edt_rounded_gold);
                ic_old.setBackgroundResource(R.drawable.btn_rounded_grey);
                lyt_old.setBackgroundResource(R.drawable.edt_rounded);
                ic_event.setBackgroundResource(R.drawable.btn_rounded_grey);
                lyt_event.setBackgroundResource(R.drawable.edt_rounded);
            }
        });

        cardOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ic_pendidikan.setBackgroundResource(R.drawable.btn_rounded_grey);
                lyt_pendidikan.setBackgroundResource(R.drawable.edt_rounded);
                ic_old.setBackgroundResource(R.drawable.btn_rounded);
                lyt_old.setBackgroundResource(R.drawable.edt_rounded_gold);
                ic_event.setBackgroundResource(R.drawable.btn_rounded_grey);
                lyt_event.setBackgroundResource(R.drawable.edt_rounded);
            }
        });

        cardEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ic_pendidikan.setBackgroundResource(R.drawable.btn_rounded_grey);
                lyt_pendidikan.setBackgroundResource(R.drawable.edt_rounded);
                ic_old.setBackgroundResource(R.drawable.btn_rounded_grey);
                lyt_old.setBackgroundResource(R.drawable.edt_rounded);
                ic_event.setBackgroundResource(R.drawable.btn_rounded);
                lyt_event.setBackgroundResource(R.drawable.edt_rounded_gold);
            }
        });



        return rootView;
    }
}
