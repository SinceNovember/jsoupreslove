package loginjiaowuwang;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class clienteduSystem {
		
	private static String username="15051704086";
	private static String password="liuyudong2";
	private static String url="http://www.renren.com/PLogin.do";
	private static HttpResponse response;
	public static HttpClient client=new DefaultHttpClient();
	
	public static boolean login()
	{
	HttpPost post=new HttpPost(url);
	List<NameValuePair> list=new ArrayList<NameValuePair>();
	list.add(new BasicNameValuePair("domain", "renren.com"));
	list.add(new BasicNameValuePair("submit", "登录")); 
	list.add(new BasicNameValuePair("email",username));
	list.add(new BasicNameValuePair("password",password));
	try
	{
		post.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
		 response= client.execute(post);
		 System.out.println(response);
		
	}catch(Exception e)
	{
		e.printStackTrace();
		return false;
	}
	finally
	{
		post.abort();
	}
	return true;
	}

			private String geturl()
			{
				Header location=response.getFirstHeader("Location");
				if(location==null)
				{
					return null;
				}
				System.out.println(location);
				System.out.println(location.getValue());
				return location.getValue();
				
			}
			private String getText(String url)
			{
			    HttpGet httpget = new HttpGet(url);  
		        // Create a response handler  
		        ResponseHandler<String> responseHandler = new BasicResponseHandler();  
		        String responseBody = "";  
		        try {  
		            responseBody = client.execute(httpget, responseHandler);  
		        } catch (Exception e) {  
		            e.printStackTrace();  
		            responseBody = null;  
		        } finally {  
		            httpget.abort();  
		            client.getConnectionManager().shutdown();  
		        }  
		        return responseBody;  
				
			}
			public void printtext()
			{
				if(login())
				{
					String redireca=geturl();
					if(redireca!=null)
					{
						System.out.println("1111:"+getText(redireca));
					}
				}
			}
			public static void main(String []args)
			{
				clienteduSystem renren=new clienteduSystem();
				renren.printtext();
			}
		
		}