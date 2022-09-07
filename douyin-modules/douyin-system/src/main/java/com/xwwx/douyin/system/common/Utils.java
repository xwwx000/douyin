package com.xwwx.douyin.system.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xwwx.douyin.common.core.utils.DateUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @author: 可乐罐
 * @date: 2022/3/19 19:50
 * @description:工具类
 */
public class Utils {
    public static class Misc {
        public static Field[] getAllFields(Object object) {
            Class<?> clazz = object.getClass();
            List<Field> fieldList = new ArrayList<>();
            while (clazz != null) {
                fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
                clazz = clazz.getSuperclass();
            }
            Field[] fields = new Field[fieldList.size()];
            fieldList.toArray(fields);
            return fields;
        }
    }

    /**
     * 封装page返回对象
     * @param page
     * @return
     */
    public static Map<String,Object> packagePage(Page page){

        Map<String,Object> rMap = new HashMap<>();
        Map<String,Object> pageMap = new HashMap<>();
        pageMap.put("total",page.getTotal());
        pageMap.put("current",page.getCurrent());
        pageMap.put("pages",page.getPages());
        pageMap.put("pageSize",page.getSize());
        rMap.put("pagination",pageMap);
        rMap.put("list",page.getRecords());

        return rMap;
    }
    /**
     * 获取重复数据列表
     * @param list
     * @return
     */
    public static List<String> getRepeatDataList(List<String> list){
        Map<String,Integer> map = new HashMap<>();
        for(String str:list){
            Integer i = 1; //定义一个计数器，用来记录重复数据的个数
            if(map.get(str) != null){
                i=map.get(str)+1;
            }
            map.put(str,i);
        }
        List<String> repeatList = new ArrayList<>();
        for(String s:map.keySet()){
            if(map.get(s) > 1){
                repeatList.add(s);
            }
        }
        return repeatList;
    }
    /**
     * 获取去重复数据列表
     * @param list
     * @return
     */
    public static List<String> getNoRepeatDataList(List<String> list){
        Map<String,Integer> map = new HashMap<>();
        for(String str:list){
            Integer i = 1; //定义一个计数器，用来记录重复数据的个数
            if(map.get(str) != null){
                i=map.get(str)+1;
            }
            map.put(str,i);
        }
        List<String> norepeatList = new ArrayList<>();
        for(String s:map.keySet()){
            norepeatList.add(s);
        }
        return norepeatList;
    }
    /**
     * 获取去重复数据列表
     * @param list
     * @return
     */
    public static List<Integer> getNoRepeatIntegerDataList(List<Integer> list){
        Map<String,Integer> map = new HashMap<>();
        for(Integer str:list){
            Integer i = 1; //定义一个计数器，用来记录重复数据的个数
            if(map.get(String.valueOf(str)) != null){
                i=map.get(String.valueOf(str))+1;
            }
            map.put(String.valueOf(str),i);
        }
        List<Integer> norepeatList = new ArrayList<>();
        for(String s:map.keySet()){
            norepeatList.add(Integer.parseInt(s));
        }
        return norepeatList;
    }

    /**
     * base64加密字符串
     * @param estr
     * @return
     */
    public static String encodeToString(String estr){
        String base64encodedString = "";
        try{
            base64encodedString = Base64.getEncoder().encodeToString(estr.getBytes("utf-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return base64encodedString;
    }
    /**
     * base64解密字符串
     * @param dstr
     * @return
     */
    public static String decodeToString(String dstr){
        String rstr = "";
        try{
            // 解码
            byte[] base64decodedBytes = Base64.getDecoder().decode(dstr);
            rstr = new String(base64decodedBytes, "utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return rstr;
    }

    /**
     * 获取五位随机数
     * @return
     */
    public static String getRandomDateFour(){
        Random random=new Random();
        int rannum= (int)(random.nextDouble()*(9999-1000 + 1))+ 1000;
        return DateUtils.Format(new Date(), DateUtils.YYYYMMddHHmmss) + rannum;
    }

    /**
     * 根据List 组装成带分隔符的字符串
     * @param list
     * @return
     */
    public static String getStringByList(List<String> list,String separate){
        if(list == null || list.isEmpty()){
            return null;
        }
        String result = String.join(separate, list.toArray(new String[list.size()]));

        return result;
    }

    /**
     * 读取网路图片
     * @param strUrl
     * @return byte[]
     */
    public static byte[] getImageFromNetByUrl(String strUrl) {
        InputStream inStream = null;
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            inStream = conn.getInputStream();// 通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(inStream != null){
                    inStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 读取本地图片
     * @param strUrl
     * @return byte[]
     */
    public static byte[] getImageFromLocalByUrl(String strUrl) {
        try {
            File imageFile = new File(strUrl);
            InputStream inStream = new FileInputStream(imageFile);
            byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 从输入流中获取数据
     *
     * @param inStream
     *            输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 将GBK字符串转换成UTF-8字符串
     * @param gbkStr
     * @return
     */
    public static String getUTF8StringFromGBKString(String gbkStr) {
        try {
            return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError();
        }
    }

    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }

    /**
     * 字符串按分隔符转换成List
     * @param str
     * @param split
     * @return
     */
    public static List<String> stringToListBySplit(String str,String split){
        List<String> list = null;
        try{
            list = Arrays.asList(str.split(split));
        }catch (Exception e){
            list = null;
        }
        return list;
    }

    /**
     * List转换成带分隔符字符串
     * @param list
     * @param split
     * @return
     */
    public static String listToStringBySplit(List<String> list,String split){
        String str = null;
        try{
            str = String.join(split, list.toArray(new String[list.size()]));
        }catch (Exception e){
            str = null;
        }
        return str;
    }

    /**
     * 将%2B转换回+
     * @param addStr
     * @return
     */
    public static String urlOrderToAdd(String addStr){
        String replace = addStr.replace("%2B", "+");
        return replace;
    }
    /**
     * 字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().equals("")
                || str.trim().equalsIgnoreCase("null");
    }
    /**
     * 字符串为空返回""
     * @param param
     * @return
     */
    public static String nullOrBlankResult(String param) {
        return (param == null || param.trim().length() == 0 || param.trim()
                .equals("")) ? "" : param.trim();
    }

    /**
     * 判断请求是否包含拦截url字符串
     * @param requestURI
     * @param urls
     * @return
     */
    public static boolean isRequestURIInUrlList(String requestURI,List<String> urls){
        for(String url : urls){
            if(requestURI.contains(url)){
                return true;
            }
        }
        return false;
    }
}
