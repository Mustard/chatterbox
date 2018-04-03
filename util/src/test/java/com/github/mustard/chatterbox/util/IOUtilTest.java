package com.github.mustard.chatterbox.util;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class IOUtilTest {

    @Test
    void shouldConvertInputStreamToString() throws IOException {
        String data = "data_to_convert";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        String result = IOUtil.toString(inputStream);
        assertEquals(data, result);
    }

}