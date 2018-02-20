package com.rs.sg.datagen.utils;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamString extends InputStream {

    final byte[] content;
    final int len;
    int pointer = 0;

    public InputStreamString(String s) {
        this.content = s.getBytes();
        this.len = content.length;
    }

    @Override
    public int read() throws IOException {
        return pointer < len ? content[pointer++] : -1;
    }
}
