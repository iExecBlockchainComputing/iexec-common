package com.iexec.common.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileHelperTest {

    @Test
    public void shouldRemoveZipExtension() {
        String fileName = FileHelper.removeZipExtension("/some/where/file.zip");
        assertEquals("/some/where/file", fileName);
    }

    @Test
    public void shouldNotRemoveZipExtensionSinceNoExtension() {
        String fileName = FileHelper.removeZipExtension("/some/where/file");
        assertEquals(fileName, "");
    }

    @Test
    public void shouldNotRemoveZipExtensionSinceWrongExtension() {
        String fileName = FileHelper.removeZipExtension("/some/where/file.bla");
        assertEquals(fileName, "");
    }
}