package com.liu;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main {
	private static final String URL="http://www.nipic.com/show/17606223.html";
	private static final String IMGURL_REG="<img.*src=(.*?)[^>]*?>";
	private static final String IMGSRC_REG="http:\"?(.*?)(\"|>|\\s+)";
	public static void main(String []args)
	{
		try
		{
			Main cm=new Main();
			String HTML=cm.getHtml(URL);
			List<String> imgURL=cm.getImageUrl(HTML);
			List<String> imgSRC=cm.getImageSrc(imgURL);
			cm.Download(imgSRC);
			
		}catch(Exception e)
		{
			System.out.println("发生错误");
		}
	}
	private String getHtml(String url)throws Exception
	{
		//FileOutputStream file=new FileOutputStream(new File("e://文件/kkk.txt"));
		//OutputStreamWriter file1=new OutputStreamWriter(file);
		//BufferedWriter file2=new BufferedWriter(file1);
		URL url1=new URL(url);
		URLConnection connection=url1.openConnection();
		InputStream in=connection.getInputStream();
		InputStreamReader isr=new InputStreamReader(in);
		BufferedReader br=new BufferedReader(isr);
		String line;
		StringBuffer sb=new StringBuffer();
		while((line=br.readLine())!=null)
		{
			//file2.write(line+"\n");
			sb.append(line,0,line.length());
			sb.append('\n');
		}
		br.close();
		isr.close();
		in.close();
		return sb.toString();
	}
	public List<String> getImageUrl(String html)
	{
		Matcher matcher=Pattern.compile(IMGURL_REG).matcher(html);
		List<String> listimageurl=new ArrayList<String>();
		while(matcher.find())
		{
			listimageurl.add(matcher.group());
			//System.out.println(matcher.group());
		}
		return listimageurl;
	}
	public List<String> getImageSrc(List<String> listimageurl)
	{
		//System.out.println("imageSrc:");
		List<String> listimageSrc=new ArrayList<String>();
		for(String image:listimageurl)
		{
			Matcher matcher=Pattern.compile(IMGSRC_REG).matcher(image);
			while(matcher.find())
			{
				listimageSrc.add(matcher.group().substring(0,matcher.group().length()-1));
				System.out.println(matcher.group().substring(0,matcher.group().length()-1));
			}
		}
		return listimageSrc;
	}
	private void Download(List<String> listImageSrc)
	{
		int i=1;
		try {
            //开始时间
            Date begindate = new Date();
            for (String url : listImageSrc) {
                //开始时间
                Date begindate2 = new Date();
                String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
                URL uri = new URL(url);
                InputStream in = uri.openStream();
                File sf=new File("E:\\文件\\");  
     	       i++;
     	       OutputStream os = new FileOutputStream(sf.getPath()+"\\"+i+".jpg"); 
                byte[] buf = new byte[1024];
                int length = 0;
                System.out.println("开始下载:" + url);

    	        while ((length = in.read(buf,0,buf.length)) != -1) {  
    	          os.write(buf, 0, length);  
    	        } 
                
                //关闭流
                os.close();
                in.close();
                System.out.println(imageName + "下载完成");
                //结束时间
                Date overdate2 = new Date();
                double time = overdate2.getTime() - begindate2.getTime();
                System.out.println("耗时：" + time / 1000 + "s");
            }
            Date overdate = new Date();
            double time = overdate.getTime() - begindate.getTime();
            System.out.println("总耗时：" + time / 1000 + "s");
        } catch (Exception e) {
            System.out.println("下载失败");
        }
}
}


