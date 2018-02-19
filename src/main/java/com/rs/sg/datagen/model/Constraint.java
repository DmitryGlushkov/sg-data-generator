package com.rs.sg.datagen.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    }

    public static  <T extends Constraint> T createConstraint(Definition definition) throws Exception {
        return (T) constraintsMap.get(definition).newInstance();
    }

    static class Integer extends Constraint {
        int startNumber;
        int endNumber;
        @Override
        public java.lang.String toString() {
            return java.lang.String.format("%d...%d", startNumber, endNumber);
        }
    }

    static class String extends Constraint {
        int minLen;
        int maxLen;
        @Override
        public java.lang.String toString() {
            return java.lang.String.format("%d...%d", minLen, maxLen);
        }
    }

    static class Double extends Constraint {
        double startNumber;
        double endNumber;
        @Override
        public java.lang.String toString() {
            return java.lang.String.format("%d...%d", startNumber, endNumber);
        }
    }

    static class Date extends Constraint {
        Date start;
        Date end;
        @Override
        public java.lang.String toString() {
            return java.lang.String.format("%s...%s",
                    start != null ? start.toString() : "-",
                    end   != null ? end.toString()   : "-");
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

    static class Sequence extends Constraint {
        List<java.lang.String> entries;
        @Override
        public java.lang.String toString() {
            return java.lang.String.format("[%d]", entries.size());
        }
    }

    static class Index extends Constraint {
        int startIndex;
        @Override
        public java.lang.String toString() {
            return java.lang.String.format("from %d", startIndex);
        }
    }
}
