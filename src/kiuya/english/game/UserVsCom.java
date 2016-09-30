package kiuya.english.game;

class UserVsCom extends GameModel {
	void StartGame(){
		while(!hasExit){
			findword = false;	//�O�_����r���ܼ�
			if(flag == 1){
				System.out.print("�Ъ��a��J�@�ӭ^���r�Ϋ��O�G");
				userPlay();
			}else{
				System.out.println("�q����J�@�ӭ^���r�G");
		   		CheckWord("noinput");
			}
			System.out.println("");
		}
	}
	
	boolean CheckCmd(String word){
		if(word.equals("\\r")){
			if(hasRecord){
				hasRecord = false;
				System.out.println("�O���q���ϥγ�r=Off");
			}else{
				hasRecord = true;
				System.out.println("�O���q���ϥγ�r=On");
			}
			return true;
		}else if(word.equals("\\c")){
			if(maxHelp > 0){
				if(CheckHead("noinput") || firstInput)
					CheckWord("noinput");
				if(findword){
					maxHelp--; //�����@���ϴ�����
					if(firstInput)
						firstInput=false;
				}
			}else{
				System.out.println("�ϴ����Ƥw�Χ�!!");
			}
			return true;
		}else if(word.equals("\\s")){
			System.out.println("�z�w�V�q���{��!!\n���}�什~~");
			hasExit = true;
			return true;
		}else if(word.equals("\\e")){
			System.out.println("���}�什~~");
			hasExit = true;
			return true;
		}else if(word.equals("\\h")){
			cmd.explain();
			return true;
		}else if(word.equals("\\z")){
			if(maxComplementWord > 0){
				System.out.print("�i�J��L�H��r�ɥR�t��!!\n���^�X�Ѿl�i�Φ���" + maxComplementWord + "��!!\n�п�J�L�H��r(�̤�5�Ӧr�H�W)�G");
				CheckComplementWord();
				maxComplementWord--;
			}else{
				System.out.println("�L�H��r�ɥR���Ƥw�Χ�!!");
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
   			if(flag==0){
   				wordArr[alphindex][alphword][3] = "0";  //�]�w����r�����a1�ҥ�
   				flag=1;  //���쪱�a1
   			}else{
   				wordArr[alphindex][alphword][3] = "1";  //�]�w����r���q���ҥ�
   				flag=0;  //����q��
   			}
   			System.out.println(wordArr[alphindex][alphword][0] + "(�r���G" + GetLastChar(u_word) +") " + wordArr[alphindex][alphword][1]);  //�L�X��r
   		}else if(!errMsg){
   			System.out.println("�d����o�ӳ�r!!");
   		}
	}
}