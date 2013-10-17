package substringTest;

import java.net.URLDecoder;
import java.util.Arrays;

public class SubstringTest
{

	/**
	 * public static void main(String[] args) throws Exception { String contentDisposition = "filename='%E8%8B%B1%E8%AF%AD%E6%A8%A1%E7%89%88.doc'"; String fileName = contentDisposition.substring(contentDisposition.lastIndexOf("filename=")); fileName = fileName.substring(fileName.indexOf("'") + 1, fileName.length() - 1); // System.out.println(URLEncoder.encode(fileName, "utf-8")); System.out.println(URLDecoder.decode(fileName, "utf-8")); }
	 **/
	public static void main(String[] args)
	{
		String fileName = "%E8%8B%B1%E8%AF%AD%E6%A8%A1%E7%89%88.doc";
		String extName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		String uploadFileExts = "txt,rar,zip,doc,docx,xls,xlsx,jpg,jpeg,gif,png,swf,wmv,avi,wma,mp3,mid";
		System.out.println(Arrays.<String> asList(uploadFileExts.split(",")).contains(extName));
	}

}
