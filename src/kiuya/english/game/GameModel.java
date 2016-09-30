package kiuya.english.game;
import java.util.Scanner;

abstract class GameModel{
	Setting set;
	Command cmd;
	
	String wordArr[][][];
	int maxUsers, maxHelp, maxComplementWord; //maxUsers=最多參與遊戲人數，maxHelp=最大救援次數，maxComplementWord=最大印象單字補充次數
	boolean historyWord, complementWord; //historyWord=記錄歷史單字，complementWord=單字補充
	
	Scanner keyboard = new Scanner(System.in);
	String u_word,HisWord;	//u_word記錄玩家輸入單字，HisWord紀錄前一個單字
	int flag;	//flag 0表示目前是電腦，1表示目前是玩家
	boolean hasExit,hasRecord; //指令確認
	boolean firstInput; //是否第一次輸入
	boolean hasCmd;	//判斷字母是否存在
	boolean findword, findSimilar; //findword是否找到當前單字，findSimilar是否找到和印象單字match的單字

    GameModel() {
    	set = new Setting(this);
    	cmd = new Command(this);
    }
    
    //******************以下方法皆是為了遊戲前的設定******************//
    void runSetting(){ //呼叫Class Setting和Command的Method做設定
    	try{
    		System.out.println("/*...................................*/");
        	System.out.println("遊戲準備開始!!");
    		Thread.sleep(1000);
    		System.out.println("正在讀取資料中...");
    	}catch(Exception e){
    		System.out.println("設定出現問題runSetting");
    	}
    }
    
    void process(){
    	iniGame();
    	runGame();
    	set.SaveWordToFile(wordArr);
    }
    
    void iniGame(){ //初始化資料
    	firstInput=true; //第一次輸入單字
    	HisWord=""; //清空歷史單字
    	flag=1; //玩家1先開始
    	hasCmd = false;	//判斷字母是否存在
    	hasExit=false;
    	hasRecord=false;
    	
    	try{
    		Thread.sleep(1000);
    		set.setConfig(); //呼叫Setting的Method匯入外部設定檔
    		Thread.sleep(1000);
    		set.setWordArray(); //呼叫Setting的Method設定陣列大小
    		Thread.sleep(1000);
    		wordArr = set.inputAlph(); //呼叫Setting的Method設定儲存單字至陣列中
    		Thread.sleep(1000);
    		System.out.println("請稍等幾秒中...\n");
    		Thread.sleep(1000);
    		if(this.getClass() == UserVsCom.class) //呼叫Command的Method輸出可用指令
    			cmd.explain(); //玩家對抗電腦的指令清單
    		else
    			cmd.explain2(); //玩家對抗玩家的指令清單
    		Thread.sleep(3000);
        	System.out.println("遊戲設定完成!!");
    	}catch(Exception e){
    		System.out.println("設定出現問題iniGame");
    	}
    	
    	for(int i=0; i<=25; i++){
    		for(int j=0; j<wordArr[i].length; j++){
    			wordArr[i][j][2]="n";
    			wordArr[i][j][3]="n";
    		}
		}
    	System.out.println("遊戲初始化完成!!\n");
    	System.out.println("/*...................................*/");
    }
    
    void runGame(){
    	try{
    		Thread.sleep(1000);
    		System.out.println("~~~遊戲開始~~~");
    		Thread.sleep(1000);
    		StartGame();
    	}catch(Exception e){
    		System.out.println("執行出現問題runGame");
    	}
    }
    //******************************************************//
    
    //*******************兩個子類別的共同抽象方法*******************//
    abstract void StartGame(); //開始遊戲
    abstract boolean CheckCmd(String word); //檢查輸入的字串是否為指令
    abstract void CheckFindWord(int alphword, int alphindex, boolean errMsg); //檢查是否找到單字
    //******************************************************//

    
    //********************兩個子類別的共同方法********************//
    void userPlay(){ //玩家開始行動
   		u_word = keyboard.next();
   		hasCmd = CheckCmd(u_word);	//判斷字母是否為指令
   		if(!hasCmd){
   			if(firstInput){
   				CheckWord(u_word);
   				if(findword && firstInput)
					firstInput=false;
   			}else{
   				if(CheckHead(u_word))
   					CheckWord(u_word);
   			}
   		}
	}
    
    boolean CheckHead(String s1){ //檢查字母開頭是否與上一個單字字尾相同
		if(!(s1.equals("noinput"))){
			u_word = ToLowerCase(u_word); //將字母全都轉成小寫比對
			if((GetFirstChar(u_word)).equals(GetLastChar(HisWord))){ //看是否目前單字字首和上一個單字字尾是否相同
				return true;
			}else{
				System.out.println("您輸入的單字開頭有誤!!");
				return false;
			}
		}
		return true; //表示尚未輸入任何一個單字，所以不用檢查字首
	}
    
    void CheckWord(String s1){ //檢查及印出目前單字
		int alphword=0,alphindex=0;	//alphword儲存目前字母的單字索引，alphindex儲存目前字母索引
   		boolean errMsg = false; //若值為false表示查無此單字，若為true表示此單字重複使用
   		String headword;
   		
   		if(firstInput && u_word.equals("\\c")){
   			alphindex = (int)(Math.random()*wordArr.length);
   		}else{
   			if(s1.equals("noinput"))
   				headword = GetLastChar(HisWord);	//取得上一個單字的字尾，當作這次的字首
   			else
   				headword = GetFirstChar(u_word);	//取得輸入單字的字首，當作這次的字首
   		
   			for(int i=0; i<wordArr.length; i++){	//執行26個字母
   				if(wordArr[i][0][0].substring(0,1).toLowerCase().equals(headword)){	//判斷目前單字字首為:a~z其中一個
   					alphindex = i;	//紀錄字首的字母索引
   					break;
   				}
   			}
   		}
   		
   		if(s1.equals("noinput")){
   			int index = (int)(Math.random()*wordArr[alphindex].length);	//隨機產生單字的索引
			while(!(wordArr[alphindex][index][2].equals("n"))){	//重複執行直到單字沒使用過
				index = (int)(Math.random()*wordArr[alphindex].length);	//隨機產生單字的索引
			}
			alphword = index;	//將索引存入
			u_word = wordArr[alphindex][alphword][0];	//將找到單字存入
			findword = true;	//找到單字
   		}else{
   			for(int i=0; i<wordArr[alphindex].length; i++){
   				if(wordArr[alphindex][i][0].toLowerCase().equals(s1)){
   					if(wordArr[alphindex][i][2].equals("n")){
   						alphword = i;
   	   					findword = true;
   	   					break;
   					}else{
   						errMsg = true;
   						System.out.println("此單字已被使用過!!");
   					}
   				}
   			}	
   		}
   		this.CheckFindWord(alphword, alphindex, errMsg);
	}
    
    void CheckComplementWord(){
    	findSimilar = false;
    	String word = keyboard.next();
    	if(word.length() >= 5){
    		int alphindex=104;
    		String headword = GetFirstChar(word);
    		for(int i=0; i<wordArr.length; i++){	//執行26個字母
    			if(wordArr[i][0][0].substring(0,1).toLowerCase().equals(headword)){	//判斷目前單字字首為:a~z其中一個
    				alphindex = i;	//紀錄字首的字母索引
    				break;
    			}
    		}
    		if(alphindex != 104){
    			System.out.println("搜尋結果如下:");
    			for(int i=0; i<wordArr[alphindex].length; i++){
    				CompareWord(word, alphindex, i);
    			}
    			if(!findSimilar)
    				System.out.println("很抱歉沒有類似的單字!!");
    		}else{
    			System.out.println("輸入單字的字首有問題!!");
    		}
    	}else{
    		System.out.println("你輸入的印象單字少於5個字!!");
    	}
    }
    
    void CompareWord(String word, int x, int y){
    	int count = 0; //記錄match到的字元有幾個
    	String wordTmp = word.substring(1); //最後比對match次數用的
    	word = word.substring(1); //先去掉字首，因為已經確定字首不需要比
    	String word2 = wordArr[x][y][0].substring(1); //要被比對的單字，並且去掉字首
    	if((word2.length() == word.length()) || (word2.length() == word.length()+1) || (word2.length() == word.length()-1)){
    		char charArr[] = word2.toCharArray();
    		while(word.length() > 0){
    			for(int i=0; i<charArr.length; i++){
        			if(word.substring(0,1).equals(String.valueOf(charArr[i]))){
        				charArr[i] = ' ';
        				count++;
        				break;
        			}
        		}
    			word = word.substring(1);
    		}
    		if((count == wordTmp.length()) || (count == wordTmp.length()-1)){
    			System.out.println(wordArr[x][y][0]);
    			findSimilar = true;
    		}
    	}
    }
    
    String ToLowerCase(String s1){
		return s1.toLowerCase();	//將傳入的字轉小寫
	}
    
	String GetFirstChar(String s1){
		return s1.substring(0,1);	//取得傳入字的字首
	}
	
    String GetLastChar(String s1){
    	return s1.substring(s1.length()-1);	//取得目前單字的字尾
    }
    //******************************************************//
    
    
    //********************UserVsCom專有方法********************//
    //                                                      //
    //******************************************************//
    
    
    //********************UserVsUser專有方法*******************//
    //int CheckPeople(); 檢查遊戲人數是否符合範圍                                                          //
    //void setUser(); 設定每個玩家的基本屬性(例:救援次數、是否投降等)      //
    //void CheckWinner(); 檢查是否有玩家獲勝                                                                  //
    //void nextUser(); 尋找下一位輸入單字的玩家                                                               //
    //******************************************************//
}