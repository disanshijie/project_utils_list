package com.oracle.sun.design.image.project1;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
 
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
 
/**
 * Created by wgj on 2018/7/10.
 * 图片处理工具类
 */
public class ImageUtils {
    /**
     * 获取图片的宽度
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static int getImgWidth(File file) throws Exception {
        InputStream is = new FileInputStream( file );
        BufferedImage bi = ImageIO.read( is );
        int width = bi.getWidth();
        return width;
    }
 
    /**
     * 获取图片的高度
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static int getImgheight(File file) throws Exception {
        InputStream is = new FileInputStream( file );
        BufferedImage bi = ImageIO.read( is );
        int height = bi.getHeight();
        return height;
    }
 
    /**
     * @param imgsrc     -源图片地址
     * @param imgdist    -目标图片地址
     * @param widthdist  -压缩后图片宽度
     * @param heightdist -压缩后图片高度
     * @param rate       -压缩比例
     */
    public static void reduceImg(String imgsrc, String imgdist
            , int widthdist, int heightdist, Float rate) throws Exception {
        try {
            File srcfile = new File( imgsrc );
            // 检查图片文件是否存在
            if (!srcfile.exists()) {
                System.out.println( "文件不存在" );
            }
            // 如果比例不为空则说明是按比例压缩
            if (rate != null && rate > 0) {
                //获得源图片的宽高存入数组中
                int[] results = getImgWidthHeight( srcfile );
                if (results == null || results[0] == 0 || results[1] == 0) {
                    return;
                } else {
                    //按比例缩放或扩大图片大小，将浮点型转为整型
                    widthdist = (int) (results[0] * rate);
                    heightdist = (int) (results[1] * rate);
                }
            }
            // 开始读取文件并进行压缩
            Image src = ImageIO.read( srcfile );
 
            // 构造一个类型为预定义图像类型之一的 BufferedImage
            BufferedImage tag = new BufferedImage( (int) widthdist, (int) heightdist, BufferedImage.TYPE_INT_RGB );
 
            //绘制图像  getScaledInstance表示创建此图像的缩放版本，返回一个新的缩放版本Image,按指定的width,height呈现图像
            //Image.SCALE_SMOOTH,选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
            tag.getGraphics()
                    .drawImage( src.getScaledInstance( widthdist, heightdist, Image.SCALE_SMOOTH )
                            , 0, 0, null );
 
            //创建文件输出流
            FileOutputStream out = new FileOutputStream( imgdist );
            //将图片按JPEG压缩，保存到out中
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder( out );
            encoder.encode( tag );
            //关闭文件输出流
            out.close();
        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }
 
    /**
     * 获取图片宽度和高度
     *
     * @param file-图片路径
     * @return int[]-返回图片的宽度和高度
     */
    public static int[] getImgWidthHeight(File file) throws Exception {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = {0, 0};
        try {
            // 获得文件输入流
            is = new FileInputStream( file );
            // 从流里将图片写入缓冲图片区
            src = ImageIO.read( is );
            result[0] = src.getWidth( null ); // 得到源图片宽
            result[1] = src.getHeight( null );// 得到源图片高
            is.close();  //关闭输入流
        } catch (Exception ef) {
            ef.printStackTrace();
        }
 
        return result;
    }
 
    /**
     * @param srcpath    - 源图片地址
     * @param targetPath - 目标图片地址
     * @param height     - 指定压缩后的高度
     * @param width      - 指定压缩后的宽度
     *说明:若图片横比200小，高比300小，不变;
     *若图片横比200小，高比300大，高缩小到300，图片比例不变 若图片横比200大，高比300小，横缩小到200，图片比例不变
     *若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
     * @throws Exception
     */
    public static void reduceByAssignSize(String srcpath, String targetPath
            , int height, int width) throws Exception {
        File outFile = new File( targetPath );
        if (!outFile.exists())
            outFile.mkdirs();
        Thumbnails.of( srcpath ).size( width, height ).toFile( targetPath );
 
    }
 
    /**
     * 指定比例缩放图片
     *
     * @param srcpath    -   源路径
     * @param targetPath -   目标路径
     * @param scale      -   缩放比例
     * @throws Exception
     */
    public static void reduceByAssignScale(String srcpath
            , String targetPath, float scale) throws Exception {
        Thumbnails.of( srcpath ).scale( scale ).toFile( targetPath );
    }
 
    /**
     * 指定大小缩放图片
     *
     * @param srcpath    -   源路径
     * @param targetPath -   目标路径
     * @param height     - 指定压缩后的高度
     * @param width      - 指定压缩后的宽度
     *                   keepAspectRatio(false)   - 默认按照比例缩放
     * @throws Exception
     */
    public static void reduceByActualSize(String srcpath, String targetPath
            , int height, int width) throws Exception {
        Thumbnails.of( srcpath ).size( width, height ).keepAspectRatio( false ).toFile( targetPath );
    }
 
    /**
     * 图片添加水印
     * watermark(位置，水印图，透明度)
     *
     * @param srcpath          -   源路径
     * @param targetPath       -   目标路径
     * @param height           - 指定压缩后的高度
     * @param width            - 指定压缩后的宽度
     * @param waterMarkImgPath - 水印图片地址
     * @param diaphaneity      - 水印图片透明度
     * @param quality          - 图片输出质量范围(0-1),值越大,像素越高.
     * @throws Exception
     */
    public void reduceByActualSize(String srcpath, String targetPath
            , int height, int width, String waterMarkImgPath
            , float diaphaneity, float quality) throws Exception {
        Thumbnails.of( srcpath ).size( width, height ).watermark( Positions.BOTTOM_RIGHT
                , ImageIO.read( new File( waterMarkImgPath ) ), diaphaneity )
                .outputQuality( quality ).toFile( targetPath );
    }
 
    /**
     * 图片裁剪
     * 说明:
     * sourceRegion(Positions.CENTER, 400, 400) -   图片中心400*400的区域
     * sourceRegion(Positions.BOTTOM_RIGHT, 400, 400) -   图片游侠400*400的区域
     * sourceRegion(600, 500, 400, 400) -   指定坐标裁剪
     *
     * @param srcpath      -   源路径
     * @param targetPath   - 目标路径
     * @param cutPositionX -   裁剪位置X轴
     * @param cutPositionY -   裁剪位置Y轴
     * @param cutWidth     -   裁剪后图片宽
     * @param cutHeight    -   裁剪后图片高
     * @throws Exception
     */
    public static void tailorImg(String srcpath, String targetPath, int cutPositionX
            , int cutPositionY, int cutWidth, int cutHeight) throws Exception {
        Thumbnails.of( srcpath ).sourceRegion( Positions.CENTER, cutPositionX, cutPositionY )
                .size( cutWidth, cutHeight ).keepAspectRatio( false )
                .toFile( targetPath );
    }
 
}
