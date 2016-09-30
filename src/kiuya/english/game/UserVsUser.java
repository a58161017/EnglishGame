package kiuya.english.game;

import java.util.Scanner;

class UserVsUser extends GameModel {
	int userHelp[], userSurrender[], userComplementWord[]; //userHelp�O���U���a���ϴ����ơAuserSurrender�O���{�骺���a(��=0��ܩ|���{��A�Ȭ�1��ܤw�{��)
	int people; //�O���C���H��
	
	void StartGame(){
		people = 0;
		while(people<2 || people>maxUsers){
			System.out.print("�п�J�C���H��2~" + maxUsers + "�H:");
			people = CheckPeople();
		}
		setUser();
		while(!hasExit){
			findword = false;	//�O�_����r���ܼ�
			System.out.print("�Ъ��a" + flag + "��J�@�ӭ^���r�Ϋ��O�G");
			userPlay();
			if(findword)
				nextUser(); //�I�s�U�@�쪱�a
			System.out.println("");
		}
	}
	
	int CheckPeople(){
		int value;
		try{
			keyboard = new Scanner(System.in);
			value = keyboard.nextInt();
			if(value<2 || value>maxUsers)
				System.out.println("��J���Ȥ��A�d��!!");
		}catch(Exception e){
			System.out.println("��J���ȫD��ƫ��A!!");
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
		int userWin = 0; //�O����Ӫ����a
		int count = 0; //�O���w�g�뭰�����a�ƶq
		
		for(int i=0; i<people; i++){
			if(userSurrender[i] == 0)
				userWin = i+1;
			else
				count++;
		}
		if(count == people-1){
			System.out.println("\n�ѩ��L���a�w�{��A�Ѫ��a" + userWin + "��o�ӧQ!!");
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
					userHelp[flag-1]--; //�����@���ϴ�����
					if(firstInput)
						firstInput=false;
				}
			}else{
				System.out.println("���a" + flag + "���ϴ����Ƥw�Χ�!!");
			}
			return true;
		}else if(word.equals("\\s")){
			System.out.println("���a" + flag + "�w�g�{��");
			userSurrender[flag-1] = 1;
			CheckWinner();
			return true;
		}else if(word.equals("\\e")){
			System.out.println("���}�什~~");
			hasExit = true;
			return true;
		}else if(word.equals("\\h")){
			cmd.explain();
			return true;
		}else if(word.equals("\\z")){
			if(userComplementWord[flag-1] > 0){
				System.out.print("�i�J��L�H��r�ɥR�t��!!\n���^�X�Ѿl�i�Φ���" + userComplementWord[flag-1] + "��!!\n�п�J�L�H��r(�̤�5�Ӧr�H�W)�G");
				CheckComplementWord();
				userComplementWord[flag-1]--;
			}else{
				System.out.println("���a" + flag + "���L�H��r�ɥR���Ƥw�Χ�!!");
			}
			return true;
		}else if(word.substring(0,1).equals("\\")){
			System.out.println("�ëD���T���O�A�i�ϥ�\\h�d�ݫ��O");
			return true;
		}else{
			return false;
		}
	}
	
	void CheckFindWord(int alphword, int alphindex, boolean errMsg){ 		
   		if(findword){	//�P�_�O�_����r
   			HisWord = u_word;	//�N�ثe��r�s�J���v��r
   			wordArr[alphindex][alphword][2] = "y";	//�]�w����r�Q�ϥιL
   			wordArr[alphindex][alphword][3] = String.valueOf(flag);  //�]�w����r�����a1�ҥ�
   			System.out.println(wordArr[alphindex][alphword][0] + "(�r���G" + GetLastChar(u_word) +") " + wordArr[alphindex][alphword][1]); //�L�X��r
   		}else if(!errMsg){
   			System.out.println("�d����o�ӳ�r!!");
   		}
	}
}