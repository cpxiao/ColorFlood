package com.cpxiao.colorflood.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cpxiao.R;
import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.colorflood.mode.Extra;
import com.cpxiao.gamelib.fragment.BaseFragment;

/**
 * @author cpxiao on 2017/09/01.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment fragment = new HomeFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Button btnCasual = (Button) view.findViewById(R.id.btn_casual);
        Button btnStepChallenge = (Button) view.findViewById(R.id.btn_step_challenge);
        Button btnVSComputer = (Button) view.findViewById(R.id.btn_vs_computer);
        Button btn2player = (Button) view.findViewById(R.id.btn_2player);
        Button btnSettings = (Button) view.findViewById(R.id.btn_settings);
        Button btnBestScore = (Button) view.findViewById(R.id.btn_best_score);
        Button btnQuit = (Button) view.findViewById(R.id.btn_quit);

        btnCasual.setOnClickListener(this);
        btnStepChallenge.setOnClickListener(this);
        btnVSComputer.setOnClickListener(this);
        btn2player.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnBestScore.setOnClickListener(this);
        btnQuit.setOnClickListener(this);


        btnVSComputer.setVisibility(View.GONE);
        btnBestScore.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Context context = getHoldingActivity();
        String size = PreferencesUtils.getString(context, Extra.GridSize.SIZE_KEY, Extra.GridSize.SIZE_DEFAULT);
        int sizeX = Extra.GridSize.getGridCountX(size);
        int sizeY = Extra.GridSize.getGridCountY(size);
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
        } else if (id == R.id.btn_vs_computer) {
            Bundle bundle = new Bundle();
            bundle.putInt(Extra.Name.GAME_DIFFICULTY_X, sizeX);
            bundle.putInt(Extra.Name.GAME_DIFFICULTY_Y, sizeY);
            addFragment(VsComputerGameFragment.newInstance(bundle));
        } else if (id == R.id.btn_2player) {
            Bundle bundle = new Bundle();
            bundle.putInt(Extra.Name.GAME_DIFFICULTY_X, sizeX);
            bundle.putInt(Extra.Name.GAME_DIFFICULTY_Y, sizeY);
            addFragment(TwoPlayersGameFragment.newInstance(bundle));
        } else if (id == R.id.btn_settings) {
            addFragment(SettingsFragment.newInstance(null));
        } else if (id == R.id.btn_best_score) {
            addFragment(BestScoreFragment.newInstance(null));
        } else if (id == R.id.btn_quit) {
            removeFragment();
        }
    }
}
