package com.rs.sg.datagen.model;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.rs.sg.datagen.utils.Constants.SDF_FORMAT;
import static com.rs.sg.datagen.utils.Constants.SDF_FORMAT_MINIFY;

public class Constraint {

    private static final Map<Definition, Class<? extends Constraint>> constraintsMap = new HashMap<>();

    static {
        // built-in
        constraintsMap.put(Definition.STRING,   Constraint.String.class);
        constraintsMap.put(Definition.INTEGER,  Constraint.Integer.class);
        constraintsMap.put(Definition.DOUBLE,   Constraint.Double.class);
        constraintsMap.put(Definition.DATE,     Constraint.Date.class);
        // custom
        constraintsMap.put(Definition.LINK,     Constraint.Link.class);
        constraintsMap.put(Definition.SEQUENCE, Constraint.Sequence.class);
        constraintsMap.put(Definition.INDEX,    Constraint.String.class);
        constraintsMap.put(Definition.QUERY,    Constraint.Query.class);
    }

    public java.lang.String minify() {
        return this.toString();
    }

    public static  <T extends Constraint> T createConstraint(Definition definition) throws Exception {
        return (T) constraintsMap.get(definition).newInstance();
    }

    static class Integer extends Constraint {
        int startNumber;
        int endNumber;
        @Override
        public java.lang.String toString() {
            return java.lang.String.format("%d..%d", startNumber, endNumber);
        }
    }

    static class String extends Constraint {
        int minLen;
        int maxLen;
        @Override
        public java.lang.String toString() {
            return java.lang.String.format("%d..%d", minLen, maxLen);
        }
    }

    static class Double extends Constraint {
        double startNumber;
        double endNumber;
        @Override
        public java.lang.String toString() {
            return java.lang.String.format("%f..%f", startNumber, endNumber);
        }
    }

    static class Date extends Constraint {
        static SimpleDateFormat sdfMini = new SimpleDateFormat(SDF_FORMAT_MINIFY);
        java.util.Date start = new java.util.Date();
        java.util.Date end = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat(SDF_FORMAT);
        @Override
        public java.lang.String toString() {
            return java.lang.String.format("%s..%s", sdf.format(start), sdf.format(end));
        }
        @Override
        public java.lang.String minify() {
            return java.lang.String.format("%s..%s", sdfMini.format(start), sdfMini.format(end));
        }
    }

    static class Link extends Constraint {
        java.lang.String table;
        java.lang.String column;
        @Override
        public java.lang.String toString() {
            return java.lang.String.format("%s.%s", table, column);
        }
    }

    static class Query extends Constraint {
        java.lang.String query;
        @Override
        public java.lang.String toString() {
            return query;
        }
        @Override
        public java.lang.String minify() {
            int len = 7;
            java.lang.String q = "";
            if (query != null) {
                q = query.length() <= len ? query : query.substring(0, len);
            }
            return java.lang.String.format("%s...", q);
        }
    }

    static class Sequence extends Constraint {
        List<java.lang.String> entries = new ArrayList<>();
        @Override
        public java.lang.String toString() {
            List<java.lang.String> qEntries = entries.stream().map(s -> java.lang.String.format("\"%s\"", s)).collect(Collectors.toList());
            java.lang.String[] arr = qEntries.toArray(new java.lang.String[qEntries.size()]);
            return java.lang.String.format("%s", Arrays.toString(arr));
        }
        @Override
        public java.lang.String minify() {
            return java.lang.String.format("[%d]", entries.size());
        }
    }

    static class Index extends Constraint {
        @Override
        public java.lang.String toString() {
            return "";
        }
    }
}
