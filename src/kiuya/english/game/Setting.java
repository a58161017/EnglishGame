package kiuya.english.game;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

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
    	WordArray = new String[26][][];
    	String name;
    	try{
    		FileReader fr;
    		BufferedReader br;
    		for(int i=1; i<=26; i++){ //從記事本1開始~26，給予每個字母不同的陣列大小
    			int count = 0; //count計算每個英文字母的單字數量
    			name = "./word/" + String.valueOf(i).toString() + "word.txt";
    			fr = new FileReader(name);	//讀取記事本
    			br = new BufferedReader(fr);	//使用緩衝讀取
    			while (br.readLine() != null)	//記事本一行一行讀取
    				count++;
    			WordArray[i-1] = new String[count][4];	//個別宣告26個字母的單字多寡
    			br.close();	//關閉緩衝讀取
        		fr.close();	//關閉記事本
    		}
    	}catch(IOException e){
    		System.out.println("讀檔1出現錯誤!!");
    	}
    }
    
    //*************************儲存陣列單字*************************//
    String[][][] inputAlph(){
    	String name, line;
    	try{
    		FileReader fr;
    		BufferedReader br;
    		for(int i=1; i<=26; i++){ //從記事本1開始~26，給予每個字母不同的陣列大小
    			int count = 0; //count計算每個英文字母的單字數量
    			name = "./word/" + String.valueOf(i).toString() + "word.txt";
    			fr = new FileReader(name);	//讀取記事本
    			br = new BufferedReader(fr);	//使用緩衝讀取
    			while ((line = br.readLine()) != null){	//記事本一行一行讀取
    				LineWordSplit(line,i,count);	//將字母一行一行存入陣列的函數
    				count++;
    			}
    			br.close();	//關閉緩衝讀取
        		fr.close();	//關閉記事本
    		}
    	}catch(IOException e){
    		System.out.println("讀檔2出現錯誤!!");
    	}
    	System.out.println("單字檔匯入成功!!");
    	return WordArray;
    }
    void LineWordSplit(String str,int wordnum,int count){
    	String[] words = str.split("%");	//以%分成字母和中文解釋
    	WordArray[wordnum-1][count][0] = words[0];	//存入字母
    	WordArray[wordnum-1][count][1] = words[1];	//存入中文註解
    	WordArray[wordnum-1][count][2] = "n";	//存入n代表還沒被使用過
    	WordArray[wordnum-1][count][3] = "n";	//存入n代表非任何人輸入，0表示電腦，1表示玩家1
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