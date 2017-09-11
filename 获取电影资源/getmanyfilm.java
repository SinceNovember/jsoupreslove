package com.liuyudong;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.lang.model.element.Element;
import javax.swing.text.Document;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class getmanyfilm {
	public static String[]  getzuixinfilm(String url,int i1,int j1) throws IOException
	{
		
		int k=0;
		int s=0;
		String[] filmurl=new String[10000];
		for(int m=i1;m<=j1;m++)
		{
			
			int l=0;
			String url1="http://www.6vhao.tv/dy1/index_"+m+".html";
		org.jsoup.nodes.Document doc=Jsoup.connect(url1).get();
		Elements element=doc.select("body").select("div.wrap").select("div.mainleft").select("div.channellist").select("div.listBox").select("ul").select("li")
				.select("div.listimg").select("a");
		for(org.jsoup.nodes.Element element1:element) 
			{
		//	System.out.println(element1.attr("href").toString());
			filmurl[k++]=element1.attr("href").toString();
			}
			


		}
		return filmurl;

	}
	public static String[][] getfilmrealurl(String[] a,int i) 
	{
		String filmname[][]=new String[i][2];

		//String url="http://www.dygang.net/ys/20170720/37962.htm";
		for(int j=0;j<i;j++)
		{
			
			try
			{
		org.jsoup.nodes.Document doc=Jsoup.connect(a[j]).get();
		org.jsoup.nodes.Element element=doc.select("body").select("div.wrap").select("div.mainleft").select("div.contentinfo").select("div#text").select("table[border=0][cellspacing=1][cellpadding=10]")
				.select("tbody").select("tr").select("a").first();
		filmname[j][0]=element.attr("href").toString();
		filmname[j][1]=element.text();
		
		
		System.out.println(filmname[j][0]);
		System.out.println(filmname[j][1]);
			}catch(Exception e)
			{
				System.out.println("连接为空");
			}
			
		
		}
		return filmname;
		
		
		
		
		
		
	}
	public static void inputexcel(String [][]a,int k)
	{
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet=workbook.createSheet("所有电影下载地址");
		for(int i=0;i<k;i++)
		{
			if(a[i][0]!=null)
			{
			HSSFRow row=sheet.createRow(i);
			row.createCell(0).setCellValue(a[i][0]);
			row.createCell(10).setCellValue(a[i][1]);
			//System.out.println(a[i][0]);
			}
			
		}
		try
		{
			FileOutputStream file=new FileOutputStream("E:\\电影链接.xls");
			workbook.write(file);
			System.out.println("写入成功");
		}catch(IOException e)
		{
			System.out.println("写入失败");
			e.printStackTrace();
		}
	
	}
	public static void connect(String[]a,String[]b)
	{
		int lengtha=a.length;
		int lengthb=b.length;
		System.out.println(lengtha);
		System.out.println(lengthb);
	}
		/*for(int i=0;i<k;i++)
			System.out.println(filmurl1[i]);*/
		//System.out.println(element1.attr("href"));
		
		/*for(org.jsoup.nodes.Element element1:element)
		{
			Elements element2=element1.select("td");
			for(org.jsoup.nodes.Element element3:element2)
			{
				Elements element4=element3.select("table").select("tbody").select("tr").select("td[width=132]").select("a");
					filmurl[l++]=element4.attr("href");
				
			}
			
			
		}
		for(int i=0;i<l;i++)
		{
			if(filmurl[i]!=null)
			{
				filmurl1[k++]=filmurl[i];
			}
		}
		for(int i=0;i<k;i++)
		{
			System.out.println(filmurl1[i]);
		}
			
		
	}*/

	public static void main(String []args) throws IOException
	{
		String zuixinurl="http://www.dygang.net/ys/";
		String film[]=new String [2060];
		String film1[]=new String [830];
		String film2[]=new String [50];
		String film3[]=new String [130];
		String film4[]=new String [130];
		String film5[]=new String [380];
		String film6[]=new String [660];
		String filmall[][]=new String[3015][2];
		film= getzuixinfilm(zuixinurl,2,151);
		filmall=getfilmrealurl(film,3015);
		inputexcel(filmall,3015);
		/*film1=getzuixinfilm(zuixinurl,210,293);
		getfilmrealurl(film1,830);
		//film1=getfilmrealurl(film,200);
		//inputexcel(film1);
		film2=getzuixinfilm(zuixinurl,295,300);
		film3=getzuixinfilm(zuixinurl,302,315);
		film4=getzuixinfilm(zuixinurl,321,334);
		film5=getzuixinfilm(zuixinurl,356,394);
		film6=getzuixinfilm(zuixinurl,396,462);*/
		//System.out.println("读出完成");
		//connect(film,film1);
		
	}
	
}
