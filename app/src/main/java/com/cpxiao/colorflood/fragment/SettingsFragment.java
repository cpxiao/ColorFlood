package com.cpxiao.colorflood.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cpxiao.R;
import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.colorflood.mode.Extra;
import com.cpxiao.gamelib.fragment.BaseFragment;

/**
 * @author cpxiao on 2017/09/01.
 */

public class SettingsFragment extends BaseFragment implements View.OnClickListener {
    private TextView mGridSize;
    private TextView mSound;
    private TextView mMusic;
    private TextView mBorders;

    public static SettingsFragment newInstance(Bundle bundle) {
        SettingsFragment fragment = new SettingsFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Context context = getHoldingActivity();
        view.findViewById(R.id.btn_grid_size).setOnClickListener(this);
        view.findViewById(R.id.btn_color_scheme).setOnClickListener(this);
        view.findViewById(R.id.btn_color_transparency).setOnClickListener(this);
        view.findViewById(R.id.btn_sound).setOnClickListener(this);
        view.findViewById(R.id.btn_music).setOnClickListener(this);
        view.findViewById(R.id.btn_borders).setOnClickListener(this);

        view.findViewById(R.id.btn_color_scheme).setVisibility(View.GONE);
        view.findViewById(R.id.btn_color_transparency).setVisibility(View.GONE);

        mGridSize = (TextView) view.findViewById(R.id.tv_grid_size);
        String gridSize = PreferencesUtils.getString(context, Extra.GridSize.SIZE_KEY, Extra.GridSize.SIZE_DEFAULT);
        mGridSize.setText(gridSize);

        mSound = (TextView) view.findViewById(R.id.tv_sound);
        mMusic = (TextView) view.findViewById(R.id.tv_music);
        mBorders = (TextView) view.findViewById(R.id.tv_borders);

        boolean isSoundOn = PreferencesUtils.getBoolean(context, Extra.Key.SETTING_SOUND, Extra.Key.SETTING_SOUND_DEFAULT);
        if (isSoundOn) {
            mSound.setText(R.string.settings_on);
        } else {
            mSound.setText(R.string.settings_off);
        }


        boolean isMusicOn = PreferencesUtils.getBoolean(context, Extra.Key.SETTING_MUSIC, Extra.Key.SETTING_MUSIC_DEFAULT);
        if (isMusicOn) {
            mMusic.setText(R.string.settings_on);
        } else {
            mMusic.setText(R.string.settings_off);
        }

        boolean isBordersOn = PreferencesUtils.getBoolean(context, Extra.Key.SETTING_HAS_BORDERS, Extra.Key.SETTING_HAS_BORDERS_DEFAULT);
        if (isBordersOn) {
            mBorders.setText(R.string.settings_on);
        } else {
            mBorders.setText(R.string.settings_off);
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onClick(View v) {
        Context context = getHoldingActivity();
        int id = v.getId();
        if (id == R.id.btn_grid_size) {
            String gridSize = PreferencesUtils.getString(context, Extra.GridSize.SIZE_KEY, Extra.GridSize.SIZE_DEFAULT);
            String nextGridSize = Extra.GridSize.getNextGridSize(gridSize);
            PreferencesUtils.putString(context, Extra.GridSize.SIZE_KEY, nextGridSize);
            mGridSize.setText(nextGridSize);
        } else if (id == R.id.btn_color_scheme) {

        } else if (id == R.id.btn_color_transparency) {

        } else if (id == R.id.btn_sound) {
            boolean isSoundOn = PreferencesUtils.getBoolean(context, Extra.Key.SETTING_SOUND, Extra.Key.SETTING_SOUND_DEFAULT);
            if (isSoundOn) {
                PreferencesUtils.putBoolean(context, Extra.Key.SETTING_SOUND, false);
                mSound.setText(R.string.settings_off);
            } else {
                PreferencesUtils.putBoolean(context, Extra.Key.SETTING_SOUND, true);
                mSound.setText(R.string.settings_on);
            }
        } else if (id == R.id.btn_music) {
            boolean isMusicOn = PreferencesUtils.getBoolean(context, Extra.Key.SETTING_MUSIC, Extra.Key.SETTING_MUSIC_DEFAULT);
            if (isMusicOn) {
                PreferencesUtils.putBoolean(context, Extra.Key.SETTING_MUSIC, false);
                mMusic.setText(R.string.settings_off);
            } else {
                PreferencesUtils.putBoolean(context, Extra.Key.SETTING_MUSIC, true);
                mMusic.setText(R.string.settings_on);
            }
        } else if (id == R.id.btn_borders) {
            boolean isBordersOn = PreferencesUtils.getBoolean(context, Extra.Key.SETTING_HAS_BORDERS, Extra.Key.SETTING_HAS_BORDERS_DEFAULT);
            if (isBordersOn) {
                PreferencesUtils.putBoolean(context, Extra.Key.SETTING_HAS_BORDERS, false);
                mBorders.setText(R.string.settings_off);
            } else {
                PreferencesUtils.putBoolean(context, Extra.Key.SETTING_HAS_BORDERS, true);
                mBorders.setText(R.string.settings_on);
            }
        }
    }
}
