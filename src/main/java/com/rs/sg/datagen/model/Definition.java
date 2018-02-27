package com.rs.sg.datagen.model;

public enum Definition {

    STRING {
        @Override
        public String[] varNames() {
            return new String[]{"minLen", "maxLen"};
        }
    },

    INTEGER {
        @Override
        public String[] varNames() {
            return new String[]{"startNumber", "endNumber"};
        }
    },

    DOUBLE {
        @Override
        public String[] varNames() {
            return new String[]{"startNumber", "endNumber"};
        }
    },

    DATE {
        @Override
        public String[] varNames() {
            return new String[]{"start", "end"};
        }
    },

    LINK {
        @Override
        public String[] varNames() {
            return new String[]{"table", "column"};
        }
    },

    SEQUENCE {
        @Override
        public String[] varNames() {
            return new String[]{"entries"};
        }
    },

    QUERY {
        @Override
        public String[] varNames() {
            return new String[]{"query"};
        }
    },

    SKIP,

    INDEX;

    public String[] varNames() {
        return new String[0];
    }
}
