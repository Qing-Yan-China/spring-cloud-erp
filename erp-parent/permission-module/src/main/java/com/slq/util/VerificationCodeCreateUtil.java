package com.slq.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/***
 * 验证码生成工具
 * @author 孙陆泉
 *
 */
public class VerificationCodeCreateUtil {

	
	public static final String DEFAULT_VERIFICATION_CODE_KEY=VerificationCodeCreateUtil.class.getName();
	/**
     * 生成验证码的范围：a-zA-Z0-9
     *      去掉0(数字)和O(拼音)容易混淆的(小写的1和L也可以去掉,大写不用了)
     */
    private static  char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
 
    private static BufferedImage bufferedImage;
    
    public static String generateVerificationCode(int width,int height,int codeCount) {
    	//1.根据宽度高度  和类型 创建bufferedImage图片对象
    	bufferedImage=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	//2.获取图片对象的画笔对象Graphics
    	Graphics2D graphics=bufferedImage.createGraphics();
    	// 3、画笔画入数据（背景色，边框，字体，字体位置，颜色等）
        // 背景色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        //边框颜色
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, width-1, height-1);
        // 字体
        Font font = new Font("Fixedsys", Font.PLAIN, height - 2);
//        Font font = new Font("微软雅黑", Font.ROMAN_BASELINE, height - 2);
        graphics.setFont(font);
 
        // 添加干扰线：坐标/颜色随机
        Random random = new Random();
        for (int i = 0; i < (codeCount * 2); i++) {
            graphics.setColor(getRandomColor());
            graphics.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
        }
        // 添加噪点:
        for(int i = 0;i < (codeCount * 3);i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            graphics.setColor(getRandomColor());
            graphics.fillRect(x, y, 2,2);
        }
 
        // 画随机数：颜色随机，宽高自定义
        StringBuffer randomCode = new StringBuffer();
        int charWidth = width / (codeCount + 2);
        int charHeight = height - 5;
        // 随机产生codeCount个字符的验证码。
        for (int i = 0; i < codeCount; i++) {
            int x = (i + 1) * charWidth;
            String strRandom = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            randomCode.append(strRandom);
            graphics.setColor(getRandomColor());
            //设置字体旋转角度
            int degree = random.nextInt() % 30;  //角度小于30度
            graphics.rotate(degree * Math.PI / 180, x, 45);  //正向旋转
            graphics.drawString(strRandom, x, charHeight);
            graphics.rotate(-degree * Math.PI / 180, x, 45); //反向旋转
        }
     // 4、最后通过ImageIO.write()方法将图片对象写入OutputStream
        //ImageIO.write(bufferedImage,imgFormat,outputStream);
        return randomCode.toString();
    }
    private static Color getRandomColor() {
    	Random ran=new Random();
    	return new Color(ran.nextInt(256),ran.nextInt(256), ran.nextInt(256));
    }
    
    public static boolean write(OutputStream outputStream,String formatName) throws IOException {
    	return ImageIO.write(bufferedImage, formatName, outputStream);
    }
}
