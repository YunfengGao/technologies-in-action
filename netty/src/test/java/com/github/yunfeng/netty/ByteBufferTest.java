package com.github.yunfeng.netty;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class ByteBufferTest {
    @Test
    @DisplayName("使用ByteBuffer从文件读")
    void testReadFromFile() throws URISyntaxException {
        URL resource = ByteBufferTest.class.getResource("/data");
        assert resource != null;
        File file = new File(resource.toURI());
        byte[] bytes = new byte[13];
        int cnt = 0;
        try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (fileChannel.read(buffer) != -1) {
                // 读buffer，切换到读模式
                buffer.flip();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    bytes[cnt++] = b;
                    System.out.println("byte:" + b);
                }
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }
}
