package projects.tmc.mycgm;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class MyCGMFragment extends Fragment{

    public static MyCGMFragment newInstance() {
        return new MyCGMFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

}
