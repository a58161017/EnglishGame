package kiuya.english.game;

class UserVsCom extends GameModel {
	void StartGame(){
		while(!hasExit){
			findword = false;	//是否找到單字的變數
			if(flag == 1){
				System.out.print("請玩家輸入一個英文單字或指令：");
				userPlay();
			}else{
				System.out.println("電腦輸入一個英文單字：");
		   		CheckWord("noinput");
			}
			System.out.println("");
		}
	}
	
	boolean CheckCmd(String word){
		if(word.equals("\\r")){
			if(hasRecord){
				hasRecord = false;
				System.out.println("記錄電腦使用單字=Off");
			}else{
				hasRecord = true;
				System.out.println("記錄電腦使用單字=On");
			}
			return true;
		}else if(word.equals("\\c")){
			if(maxHelp > 0){
				if(CheckHead("noinput") || firstInput)
					CheckWord("noinput");
				if(findword){
					maxHelp--; //扣掉一次救援次數
					if(firstInput)
						firstInput=false;
				}
			}else{
				System.out.println("救援次數已用完!!");
			}
			return true;
		}else if(word.equals("\\s")){
			System.out.println("您已向電腦認輸!!\n離開對局~~");
			hasExit = true;
			return true;
		}else if(word.equals("\\e")){
			System.out.println("離開對局~~");
			hasExit = true;
			return true;
		}else if(word.equals("\\h")){
			cmd.explain();
			return true;
		}else if(word.equals("\\z")){
			if(maxComplementWord > 0){
				System.out.print("進入到印象單字補充系統!!\n本回合剩餘可用次數" + maxComplementWord + "次!!\n請輸入印象單字(最少5個字以上)：");
				CheckComplementWord();
				maxComplementWord--;
			}else{
				System.out.println("印象單字補充次數已用完!!");
			}
			return true;
		}else if(word.substring(0,1).equals("\\")){
			System.out.println("並非正確指令，可使用\\h查看指令");
			return true;
		}else{
			return false;
		}
	}
	
	void CheckFindWord(int alphword, int alphindex, boolean errMsg){
		if(findword){	//判斷是否找到單字
   			HisWord = u_word;	//將目前單字存入歷史單字
   			wordArr[alphindex][alphword][2] = "y";	//設定此單字被使用過
   			if(flag==0){
   				wordArr[alphindex][alphword][3] = "0";  //設定此單字為玩家1所用
   				flag=1;  //輪到玩家1
   			}else{
   				wordArr[alphindex][alphword][3] = "1";  //設定此單字為電腦所用
   				flag=0;  //輪到電腦
   			}
   			System.out.println(wordArr[alphindex][alphword][0] + "(字尾：" + GetLastChar(u_word) +") " + wordArr[alphindex][alphword][1]);  //印出單字
   		}else if(!errMsg){
   			System.out.println("查不到這個單字!!");
   		}
	}
}