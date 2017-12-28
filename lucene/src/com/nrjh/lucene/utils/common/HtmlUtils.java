package com.nrjh.lucene.utils.common;

/**
 * html工具类
 * @author huawei
 *
 */
public class HtmlUtils {
	
	    /**
	     * 转义html
	     * @param source
	     * @return
	     */
	    public static String htmlEncode(String source) {
	        if (source == null) {
	            return "";
	        }
	        String html = "";
	        StringBuffer buffer = new StringBuffer();
	        for (int i = 0; i < source.length(); i++) {
	            char c = source.charAt(i);
	            switch (c) {
	            case '<':
	                buffer.append("&lt;");
	                break;
	            case '>':
	                buffer.append("&gt;");
	                break;
	            case '&':
	                buffer.append("&amp;");
	                break;
	            case '"':
	                buffer.append("&quot;");
	                break;
	            case 10:
	            case 13:
	                break;
	            default:
	                buffer.append(c);
	            }
	        }
	        html = buffer.toString();
	        return html;
	    }
	    /**
	     * 反转义html
	     * @param source
	     * @return
	     */
	    public static String htmlDecode(String source) {
	        if (source == null) {
	            return "";
	        }
	        String html = source.replaceAll("&lt;", "<")
	        		.replaceAll("&gt;", ">")
	        		.replaceAll("&amp;", "&")
	        		.replaceAll("&quot;", "\"");
	        return html;
	    }
}
