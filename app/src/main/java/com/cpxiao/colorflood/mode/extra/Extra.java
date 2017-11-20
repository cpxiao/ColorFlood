package com.cpxiao.colorflood.mode.extra;

/**
 * @author cpxiao on 2017/01/12.
 */
public class Extra {


    public static final class Key {
        /**
         * Casual Game Best Score
         */
        public static final String CASUAL_GAME_BEST_SCORE = "CASUAL_GAME_BEST_SCORE";
        public static final int CASUAL_GAME_BEST_SCORE_DEFAULT = 999;

        /**
         * 是否有圆角
         */
        public static final String IS_ROUND = "IS_ROUND";
        public static final boolean IS_ROUND_DEFAULT = true;


        /**
         * 音效开关，默认开
         */
        public static final String SETTING_SOUND = "SETTING_SOUND";
        public static final boolean SETTING_SOUND_DEFAULT = true;

        /**
         * 音乐开关，默认开
         */
        public static final String SETTING_MUSIC = "SETTING_MUSIC";
        public static final boolean SETTING_MUSIC_DEFAULT = true;


        /**
         * 边界，即游戏区域方格之间是否需要Padding
         */
        public static final String SETTING_HAS_BORDERS = "SETTING_HAS_BORDERS";
        public static final boolean SETTING_HAS_BORDERS_DEFAULT = false;

    }

    public static final class Name {
        /**
         * 方格数量
         */
        public static final String GAME_DIFFICULTY_X = "GAME_DIFFICULTY_X";
        public static final String GAME_DIFFICULTY_Y = "GAME_DIFFICULTY_Y";

        /**
         * 步数挑战模式显示步数
         */
        public static final String LIMIT_STEP = "LIMIT_STEP";
    }


}
