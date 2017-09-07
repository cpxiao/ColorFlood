package com.cpxiao.colorflood.mode.extra;

/**
 * @author cpxiao on 2017/01/12.
 */
public class Extra {
//    public static final class BlockColor {
//        public static final int colorBg = 0xffffffff;
//        private static final int color0 = 0xffff1744;
//        private static final int color1 = 0xffffa726;
//        private static final int color2 = 0xffab47bc;
//        private static final int color3 = 0xff29b6f6;
//        private static final int color4 = 0xffffee58;
//        private static final int color5 = 0xff66bb6a;
////        private static final int color6 = 0xff66bb6a;
////        private static final int color7 = 0xff66bb6a;
//        public static final int colorGray = 0xffaaaaaa;
//        public static final int[] _6colorArray = {color0, color1, color2, color3, color4, color5};
////        public static final int[] _8colorArray = {color0, color1, color2, color3, color4, color5, color6, color7};
//    }
//
//    public static final class GridSize {
//
//        private static final boolean DEBUG = AppConfig.DEBUG;
//        /**
//         * 方格数量
//         */
//        public static final String SIZE_KEY = "SIZE_KEY";
//        private static final String SIZE_12 = "12 x 12";
//        private static final String SIZE_16 = "16 x 16";
//        private static final String SIZE_20 = "20 x 20";
//        private static final String SIZE_24 = "24 x 24";
//        private static final String SIZE_28 = "28 x 28";
//        public static final String SIZE_DEFAULT = SIZE_20;
//        private static final String[] SIZE_ARRAY = {SIZE_12, SIZE_16, SIZE_20, SIZE_24, SIZE_28};
//
//        public static int getGridCountX(String size) {
//            if (StringUtils.isEmpty(size)) {
//                return 0;
//            }
//            try {
//                String x = size.substring(0, size.indexOf("x") - 1);
//                return Integer.valueOf(x);
//            } catch (Exception e) {
//                if (DEBUG) {
//                    e.printStackTrace();
//                }
//            }
//
//            return 0;
//        }
//
//        public static int getGridCountY(String size) {
//            if (StringUtils.isEmpty(size)) {
//                return 0;
//            }
//            try {
//                String x = size.substring(size.indexOf("x") + 2, size.length());
//                return Integer.valueOf(x);
//            } catch (Exception e) {
//                if (DEBUG) {
//                    e.printStackTrace();
//                }
//            }
//
//            return 0;
//        }
//
//        public static String getNextGridSize(String gridSize) {
//            int index = -1;
//            for (int i = 0; i < SIZE_ARRAY.length; i++) {
//                if (TextUtils.equals(gridSize, SIZE_ARRAY[i])) {
//                    index = i;
//                    break;
//                }
//            }
//            if (index == -1) {
//                return SIZE_DEFAULT;
//            }
//            return SIZE_ARRAY[(index + 1) % SIZE_ARRAY.length];
//        }
//    }

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


}
