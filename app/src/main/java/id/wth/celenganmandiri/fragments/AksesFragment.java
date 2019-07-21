package id.wth.celenganmandiri.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import id.wth.celenganmandiri.R;

public class AksesFragment extends Fragment {

    Button submitBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_akses, container, false);
        submitBtn = rootView.findViewById(R.id.nextBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.client_content, new OptionFragment(), "NewFragmentTag");
                ft.commit();
            }
        });

        return rootView;
    }
}
