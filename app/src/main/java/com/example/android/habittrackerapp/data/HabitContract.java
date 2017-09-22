package com.example.android.habittrackerapp.data;

import android.provider.BaseColumns;

/**
 * Created by Toshiba on 20/09/17.
 */

public class HabitContract {

    private HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns {

        public final static String TABLE_NAME = "habits";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_NAME = "name";

        public final static String COLUMN_REPEAT = "repeat";
    }

}
