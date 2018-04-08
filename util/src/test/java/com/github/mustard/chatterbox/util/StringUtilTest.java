package com.github.mustard.chatterbox.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class StringUtilTest {

    @Test
    void shouldTrimToEmpty() {
        assertThat(StringUtil.trimToEmpty(null)).isEqualTo("");
        assertThat(StringUtil.trimToEmpty("   ")).isEqualTo("");
        assertThat(StringUtil.trimToEmpty(" things blah ")).isEqualTo("things blah");
    }

}