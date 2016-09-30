package kiuya.english.game;

import java.util.Scanner;

class UserVsUser extends GameModel {
	int userHelp[], userSurrender[], userComplementWord[]; //userHelp記錄各玩家的救援次數，userSurrender記錄認輸的玩家(值=0表示尚未認輸，值為1表示已認輸)
	int people; //記錄遊戲人數
	
	void StartGame(){
		people = 0;
		while(people<2 || people>maxUsers){
			System.out.print("請輸入遊戲人數2~" + maxUsers + "人:");
			people = CheckPeople();
		}
		setUser();
		while(!hasExit){
			findword = false;	//是否找到單字的變數
			System.out.print("請玩家" + flag + "輸入一個英文單字或指令：");
			userPlay();
			if(findword)
				nextUser(); //呼叫下一位玩家
			System.out.println("");
		}
	}
	
	int CheckPeople(){
		int value;
		try{
			keyboard = new Scanner(System.in);
			value = keyboard.nextInt();
			if(value<2 || value>maxUsers)
				System.out.println("輸入的值不再範圍內!!");
		}catch(Exception e){
			System.out.println("輸入的值非整數型態!!");
			return 0;
		}
		return value;
	}
	
	void setUser(){
		userSurrender = new int[people];
		userHelp = new int[people];
		userComplementWord = new int[people];
		for(int i=0; i<userHelp.length; i++){
			userSurrender[i] = 0;
			userHelp[i] = maxHelp;
			userComplementWord[i] = maxComplementWord;
		}
	}
	
	void CheckWinner(){
		int userWin = 0; //記錄獲勝的玩家
		int count = 0; //記錄已經投降的玩家數量
		
		for(int i=0; i<people; i++){
			if(userSurrender[i] == 0)
				userWin = i+1;
			else
				count++;
		}
		if(count == people-1){
			System.out.println("\n由於其他玩家已認輸，由玩家" + userWin + "獲得勝利!!");
			hasExit = true;
		}
		if(!hasExit)
			nextUser();
	}
	
	void nextUser(){
		boolean findPeople = false;
		while(!findPeople){
			if(flag == people)
				flag = 0;
			flag++;
			if(userSurrender[flag-1] != 1)
				findPeople = true;
		}
	}

	boolean CheckCmd(String word){
		if(word.equals("\\c")){
			if(userHelp[flag-1] > 0){
				if(CheckHead("noinput") || firstInput)
					CheckWord("noinput");
				if(findword){
					userHelp[flag-1]--; //扣掉一次救援次數
					if(firstInput)
						firstInput=false;
				}
			}else{
				System.out.println("玩家" + flag + "的救援次數已用完!!");
			}
			return true;
		}else if(word.equals("\\s")){
			System.out.println("玩家" + flag + "已經認輸");
			userSurrender[flag-1] = 1;
			CheckWinner();
			return true;
		}else if(word.equals("\\e")){
			System.out.println("離開對局~~");
			hasExit = true;
			return true;
		}else if(word.equals("\\h")){
			cmd.explain();
			return true;
		}else if(word.equals("\\z")){
			if(userComplementWord[flag-1] > 0){
				System.out.print("進入到印象單字補充系統!!\n本回合剩餘可用次數" + userComplementWord[flag-1] + "次!!\n請輸入印象單字(最少5個字以上)：");
				CheckComplementWord();
				userComplementWord[flag-1]--;
			}else{
				System.out.println("玩家" + flag + "的印象單字補充次數已用完!!");
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
   			wordArr[alphindex][alphword][3] = String.valueOf(flag);  //設定此單字為玩家1所用
   			System.out.println(wordArr[alphindex][alphword][0] + "(字尾：" + GetLastChar(u_word) +") " + wordArr[alphindex][alphword][1]); //印出單字
   		}else if(!errMsg){
   			System.out.println("查不到這個單字!!");
   		}
	}
}