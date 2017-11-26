package com.cpxiao.colorflood.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.cpxiao.R;
import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.androidutils.library.utils.RateAppUtils;
import com.cpxiao.androidutils.library.utils.ShareAppUtils;
import com.cpxiao.colorflood.mode.extra.Extra;
import com.cpxiao.colorflood.mode.extra.GridSize;
import com.cpxiao.gamelib.fragment.BaseZAdsFragment;
import com.cpxiao.zads.core.ZAdPosition;

/**
 * @author cpxiao on 2017/09/01.
 */

public class HomeFragment extends BaseZAdsFragment implements View.OnClickListener {

    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment fragment = new HomeFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        loadZAds(ZAdPosition.POSITION_HOME);

        Button btnCasual = (Button) view.findViewById(R.id.btn_casual);
        Button btnStepChallenge = (Button) view.findViewById(R.id.btn_step_challenge);
        Button btn2player = (Button) view.findViewById(R.id.btn_2player);
        ImageButton btnRateApp = (ImageButton) view.findViewById(R.id.btn_rate_app);
        ImageButton btnShare = (ImageButton) view.findViewById(R.id.btn_share);
        ImageButton btnBestScore = (ImageButton) view.findViewById(R.id.btn_best_score);
        ImageButton btnSettings = (ImageButton) view.findViewById(R.id.btn_settings);

        btnCasual.setOnClickListener(this);
        btnStepChallenge.setOnClickListener(this);
        btn2player.setOnClickListener(this);
        btnRateApp.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnBestScore.setOnClickListener(this);
        btnSettings.setOnClickListener(this);

        btnBestScore.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View v) {
        Context context = getHoldingActivity();

        int id = v.getId();
        String size = PreferencesUtils.getString(context, GridSize.SIZE_KEY, GridSize.SIZE_DEFAULT);
        int sizeX = GridSize.getGridCountX(size);
        int sizeY = GridSize.getGridCountY(size);
        if (id == R.id.btn_casual) {
            Bundle bundle = new Bundle();
            bundle.putInt(Extra.Name.GAME_DIFFICULTY_X, sizeX);
            bundle.putInt(Extra.Name.GAME_DIFFICULTY_Y, sizeY);
            addFragment(CasualGameFragment.newInstance(bundle));
        } else if (id == R.id.btn_step_challenge) {
            Bundle bundle = new Bundle();
            bundle.putInt(Extra.Name.GAME_DIFFICULTY_X, sizeX);
            bundle.putInt(Extra.Name.GAME_DIFFICULTY_Y, sizeY);
            bundle.putInt(Extra.Name.LIMIT_STEP, sizeX + sizeY);
            addFragment(StepChallengeGameFragment.newInstance(bundle));
        } else if (id == R.id.btn_2player) {
            Bundle bundle = new Bundle();
            bundle.putInt(Extra.Name.GAME_DIFFICULTY_X, sizeX);
            bundle.putInt(Extra.Name.GAME_DIFFICULTY_Y, sizeY);
            addFragment(TwoPlayersGameFragment.newInstance(bundle));
        } else if (id == R.id.btn_settings) {
            addFragment(SettingsFragment.newInstance(null));
        } else if (id == R.id.btn_best_score) {
//            addFragment(BestScoreFragment.newInstance(null));
        } else if (id == R.id.btn_rate_app) {
            RateAppUtils.rate(context);
        } else if (id == R.id.btn_share) {
            String msg = getString(R.string.share_msg) +
                    getString(R.string.app_name) + "\n" +
                    "https://play.google.com/store/apps/details?id=" + context.getPackageName();
            ShareAppUtils.share(context, getString(R.string.share), msg);
        }
    }
}
