package loginjiaowuwang;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.swing.text.Document;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.jsoup.Jsoup;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class getdooubanimage {
	 private static HttpClient client=new DefaultHttpClient();
	 public static int i=1;
	   public static void login() throws ClientProtocolException
	   {
		  // HttpPost post=new HttpPost();
		   String url="";
		   
		   String loginurl="https://accounts.douban.com/login";
		   //账号密码
		   String form_email="83428190@qq.com";
		   String form_password="liuyudong2";
		   //验证码
		   String captcha_solution="";
		   String captcha_id=getImageId();
		   String login="登陆";
		   System.out.println("请输入验证码");
		   BufferedReader buff=new BufferedReader(new InputStreamReader(System.in));
		   try
		   {
			   captcha_solution=buff.readLine();
			   
		   }catch(Exception e)
		   {
			   e.printStackTrace();
		   }
		   List<NameValuePair> list=new ArrayList<NameValuePair>();
		   list.add(new BasicNameValuePair("form_email",form_email));
		   list.add(new BasicNameValuePair("form_password",form_password));
		   list.add(new BasicNameValuePair("captcha-solution",captcha_solution));
		   list.add(new BasicNameValuePair("captcha-id",captcha_id));
		  HttpPost post=new HttpPost(loginurl);
		  try
		  {
			  post.setEntity(new UrlEncodedFormEntity(list));
			  HttpResponse response=client.execute(post);
			  HttpEntity entity=response.getEntity();
			  String result=EntityUtils.toString(entity,"utf-8");
			  //System.out.println("result1:"+entity);
			//System.out.println("result:"+result);
			  
		  }catch(ClientProtocolException e)
		  {
			  System.out.println("login发生错误");
			  e.printStackTrace();
		  }
		  catch(IOException e)
		  {
			  e.printStackTrace();
		  }
		   
	   }
	   public static String getImageId()
	   {
		String imageurl="https://www.douban.com/j/misc/captcha";
		HttpGet httpget=new HttpGet(imageurl);
		String token="";
		try
		{
			HttpResponse response=client.execute(httpget);
			HttpEntity entity=response.getEntity();
			String content=EntityUtils.toString(entity,"utf-8");
			Map<String,String> map=getResultList(content);
			token=map.get("token");
			//System.out.println(token);
			String url="https:"+map.get("url");
		//	System.out.println(url);
			download(url);
			}catch(Exception e)
		{
				  System.out.println("getImageId发生错误");
				e.printStackTrace();
		}
		return token;
		
		   
	   }
	   public static Map<String,String> getResultList(String content)
	   {
		   Map<String,String> maplist=new HashMap<String,String>();
		   try
		   {
		   JSONObject json=JSONObject.fromObject(content);
		   Iterator it=json.keys();
		   while(it.hasNext())
		   {
			   String key=(String)it.next();
			   String value=json.getString(key);
			   maplist.put(key, value);
		   }
		   }catch(JSONException e)
		   {
			   System.out.println("getResultlist发生错误");
			   e.printStackTrace();
		   }
		   return maplist;
	   }
	   public static void download(String url) throws IOException
	   {
		   File file=new File("E:\\文件\\验证码1");
		   if(!file.exists())
		   {
			   file.mkdirs();
		   }
		   File file1=new File("E:\\文件\\验证码1\\yzm.png");
		   if(file.exists())
		   {
			   file1.delete();
		   }
		   java.io.InputStream input=null;
		   FileOutputStream output=null;
		   HttpGet httpget=new HttpGet(url);
		   try
		   {
			   HttpResponse response=client.execute(httpget);
			   HttpEntity entity=response.getEntity();
			   input=entity.getContent();
			   int i=-1;
			   byte [] byte1=new byte[1024];
			   output=new FileOutputStream(file1);
			   while((i=input.read(byte1))!=-1)
			   {
				   output.write(byte1);
			   }
			   System.out.println("图片下载成功");
		   }catch(ClientProtocolException e)
		   {
			   System.out.println("下载发生错误");
			   e.printStackTrace();
		   }
		   catch(IOException e)
		   {
			   System.out.println("下载发生错误");
			   e.printStackTrace();
		   }
		   output.close();
	   }
	 public static String gethtml(String htmlurl) throws IOException
	 {
		
		 
		 HttpGet httpget=new HttpGet(htmlurl);
		 ResponseHandler<String> responsehandler=new BasicResponseHandler();
		 String responsebody="";
		 try
		 {
			 responsebody=client.execute(httpget, responsehandler);
			// org.jsoup.nodes.Document doc=Jsoup.parse(responsebody);
			//System.out.println(doc.text());
			 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		// return responsebody;
		 return responsebody ;
		
	 }
	 public static void downloadimageurl(String html) throws IOException
	 {
		 List<String> list=new ArrayList<String>();
		 org.jsoup.nodes.Document doc=Jsoup.parse(html);
		// System.out.println(doc);
		 org.jsoup.select.Elements element=doc.select("div#wrapper").select("#content").select(".grid-16-8").select(".article");
		 for(org.jsoup.nodes.Element element1:element)
		 {
			 org.jsoup.select.Elements element2=element1.children();
			 System.out.println("开始下载图片");
			 for(org.jsoup.nodes.Element element3:element2)
			 {
				 String s=element3.select(".obu").select("img[src$=.jpg]").attr("src");
				 if(!(s.equals("")))
				 {
				 list.add(s);
				 }
				
			 }
			 //System.out.println(list.get(0));
			 Iterator it=list.iterator();
			 while(it.hasNext())
			 {
				 String s=(String) it.next();
				 
				 downloadimage(s);
			 }
			 
		 }
		// System.out.println(element);
		 /*for(org.jsoup.nodes.Element element1:element)
		 {
			 String imagehref=element1.attr("href");
			 System.out.println(imagehref);
		 }*/
		 
	 }
	 public static void downloadimage(String url) throws IOException
	 {
		
		 URL url1=new URL(url);
		 String imagename=url.substring(url.lastIndexOf("/")+1,url.length());
		 File file=new File("E:\\文件\\loadimage");
		 if(!file.exists())
		 {
			 file.mkdirs();
		 }
		 FileOutputStream output;
		 java.io.InputStream input;
		 output=new FileOutputStream(file.getPath()+"\\"+imagename);
		 input=url1.openStream();
		 int length=0;
		 byte[] byt=new byte[1024];
		 while((length=input.read(byt))!=-1)
		 {
			 
			 output.write(byt);
		 }
		 System.out.println("图片"+i+"下载完成");
		 i++;
		 
		 
	 }
	 public static void main(String []args) throws IOException
	 {
		 login();
		 String url="https://www.douban.com/people/conanemily/contacts";
		String cc=gethtml(url);
		//System.out.println(cc);
		downloadimageurl(cc);
		 
	 }
		
}