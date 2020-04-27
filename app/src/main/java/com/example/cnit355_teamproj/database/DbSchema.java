package com.example.cnit355_teamproj.database;

public class DbSchema {
    public static final class ScoreTable {
        public static final String NAME = "scores";

        public static final class Cols {
            public static final String DIFFICULTY = "difficulty";
            public static final String SCORE = "score";
            public static final String DATE_ACHIEVED = "date_achieved";
        }
    }
}
