package projects.tmc.mycgm;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyCGMActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return MyCGMFragment.newInstance();
    }
}
