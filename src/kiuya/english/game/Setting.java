package kiuya.english.game;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.text.SimpleDateFormat;

import kiuya.hibernate.sqlite.*;

class Setting {
	GameModel game;
	String WordArray[][][];
	
	Setting(GameModel gm){
		game = gm;
	}
	
	//********************將設定檔的內容讀進來********************//
    void setConfig(){
		String line;
		String valueArr[];
		try{
			FileReader fr;
			BufferedReader br;
			fr = new FileReader("./confing/charsettings.properties");
    		br = new BufferedReader(fr);
    		while((line=br.readLine()) != null){
    			valueArr=line.split("=");
    			judge(valueArr[0], valueArr[1]);
    		}
    		br.close();
    		fr.close();
    		System.out.println("設定檔匯入成功!!");
		}catch(Exception e){
			System.out.println("讀檔出現錯誤!!");
		}
    }
    
    //****************判斷對應的變數值，並將其存入****************//
    void judge(String key, String value){
    	if(key.equals("MaxHelp"))
    		game.maxHelp = Integer.valueOf(value);
    	else if(key.equals("MaxUsers"))
    		game.maxUsers = Integer.valueOf(value);
    	else if(key.equals("HistoryWord"))
    		game.historyWord = Boolean.valueOf(value);
    	else if(key.equals("ComplementWord"))
    		game.complementWord = Boolean.valueOf(value);
    	else if(key.equals("MaxComplementWord"))
    		game.maxComplementWord = Integer.valueOf(value);
    	else
    		System.out.println("設定檔有問題!!");
    }
    
    //*************************宣告陣列大小*************************//
    void setWordArray(){
    	Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		WordArray = new String[26][][];
		
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:EnglishWord.db");
			conn.setAutoCommit(false);

			stmt = conn.createStatement();
			for(int i=0; i<=25; i++){ //從記事本1開始~26，給予每個字母不同的陣列大小
				char c = (char)(i+97);
				rs = stmt.executeQuery("SELECT COUNT(*) AS COUNT FROM WORD WHERE WORD LIKE '"+c+"%';"); //取得各英文字首底下的單字數量
				int count = 0;
				while (rs.next()) count = rs.getInt("count");
				WordArray[i] = new String[count][4]; //個別宣告26個字母的單字多寡
			}
		} catch (Exception e) {
			System.err.println("取得「英文單字數量」失敗");
		}finally{
			try{
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){}
		}
    }
    
    //*************************儲存陣列單字*************************//
    String[][][] inputAlph(){
    	Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:EnglishWord.db");
			conn.setAutoCommit(false);

			stmt = conn.createStatement();
			for(int i=0; i<=25; i++){ //從記事本1開始~26，給予每個字母不同的陣列大小
				char c = (char)(i+97);
				rs = stmt.executeQuery("SELECT WORD,EXPLAIN FROM WORD WHERE WORD LIKE '"+c+"%';");
				int count = 0;
				while (rs.next()) {
					String word = rs.getString("word");
					String explain = rs.getString("explain");
					SaveRowToArray(word,explain,i,count); //將字母一行一行存入陣列的函數
					count++;
				}
			}
			System.out.println("「英文單字」寫入成功!!");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("取得「英文單字」失敗");
		}finally{
			try{
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){}
		}
    	return WordArray;
    }
    void SaveRowToArray(String word,String explain,int wordnum,int count){
    	WordArray[wordnum][count][0] = word;		//存入字母
    	WordArray[wordnum][count][1] = explain;	//存入中文註解
    	WordArray[wordnum][count][2] = "n";		//存入n代表還沒被使用過
    	WordArray[wordnum][count][3] = "n";		//存入n代表非任何人輸入，0表示電腦，1表示玩家1
    }
    //******************************************************//
    
    //*****************將遊戲中使用的單字寫入到記事本中****************//
    void SaveWordToFile(String arr[][][]){
    	Date date = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_hh-mm-ss");
    	
    	if(game.historyWord){
    		String name = "HistoryWord_" + formatter.format(date) + ".txt"; //以日期當作檔名
    		try{
            	BufferedWriter bw = new BufferedWriter(new FileWriter("./RecordWord/HistoryWord/" + name));
            	for(int i=0; i<arr.length; i++){		
            		for(int j=0; j<arr[i].length; j++){
            			if(arr[i][j][2].equals("y")){
            				bw.write(arr[i][j][0] + " = " + arr[i][j][1]);
            				bw.newLine();
            			}
            		}
            	}
            	bw.close();
        	}catch(Exception e){
        		System.out.println("寫檔1出現錯誤!!");
        	}
    	}
    	
    	if(game.hasRecord){
    		String name = "ComputerWord_" + formatter.format(date) + ".txt"; //以日期當作檔名
    		try{
            	BufferedWriter bw = new BufferedWriter(new FileWriter("./RecordWord/ComputerWord/" + name));
            	for(int i=0; i<arr.length; i++){		
            		for(int j=0; j<arr[i].length; j++){
            			if(arr[i][j][3].equals("0")){
            				bw.write(arr[i][j][0] + " = " + arr[i][j][1]);
            				bw.newLine();
            			}
            		}
            	}
            	bw.close();
        	}catch(Exception e){
        		System.out.println("寫檔2出現錯誤!!");
        	}
    	}
    }
}