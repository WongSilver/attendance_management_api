package edu.wong.attendance_management_api.util;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码工具类
 */
public class CaptchaUtil {
    private static StringBuffer code;

    public static String getCaptcha(ServletOutputStream stream) throws IOException {
        code = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            code.append(new Random().nextInt(10));
        }
        BufferedImage bi = new BufferedImage(200, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bi.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 200, 50);
        Font font = new Font("Fixedsys", Font.PLAIN, 48);
        graphics.setFont(font);
        graphics.setColor(Color.black);
        graphics.drawString(String.valueOf(code), 40, 48);
        graphics.dispose();
        ImageIO.write(bi, "jpg", stream);
        return String.valueOf(code);
    }

    public static String getCaptchaCode() {
        return String.valueOf(code);
    }
}
