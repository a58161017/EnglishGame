package kiuya.english.game;
import java.util.Scanner;

abstract class GameModel{
	Setting set;
	Command cmd;
	
	String wordArr[][][];
	int maxUsers, maxHelp, maxComplementWord; //maxUsers=�̦h�ѻP�C���H�ơAmaxHelp=�̤j�ϴ����ơAmaxComplementWord=�̤j�L�H��r�ɥR����
	boolean historyWord, complementWord; //historyWord=�O�����v��r�AcomplementWord=��r�ɥR
	
	Scanner keyboard = new Scanner(System.in);
	String u_word,HisWord;	//u_word�O�����a��J��r�AHisWord�����e�@�ӳ�r
	int flag;	//flag 0��ܥثe�O�q���A1��ܥثe�O���a
	boolean hasExit,hasRecord; //���O�T�{
	boolean firstInput; //�O�_�Ĥ@����J
	boolean hasCmd;	//�P�_�r���O�_�s�b
	boolean findword, findSimilar; //findword�O�_����e��r�AfindSimilar�O�_���M�L�H��rmatch����r

    GameModel() {
    	set = new Setting(this);
    	cmd = new Command(this);
    }
    
    //******************�H�U��k�ҬO���F�C���e���]�w******************//
    void runSetting(){ //�I�sClass Setting�MCommand��Method���]�w
    	try{
    		System.out.println("/*...................................*/");
        	System.out.println("�C���ǳƶ}�l!!");
    		Thread.sleep(1000);
    		System.out.println("���bŪ����Ƥ�...");
    	}catch(Exception e){
    		System.out.println("�]�w�X�{���DrunSetting");
    	}
    }
    
    void process(){
    	iniGame();
    	runGame();
    	set.SaveWordToFile(wordArr);
    }
    
    void iniGame(){ //��l�Ƹ��
    	firstInput=true; //�Ĥ@����J��r
    	HisWord=""; //�M�ž��v��r
    	flag=1; //���a1���}�l
    	hasCmd = false;	//�P�_�r���O�_�s�b
    	hasExit=false;
    	hasRecord=false;
    	
    	try{
    		Thread.sleep(1000);
    		set.setConfig(); //�I�sSetting��Method�פJ�~���]�w��
    		Thread.sleep(1000);
    		set.setWordArray(); //�I�sSetting��Method�]�w�}�C�j�p
    		Thread.sleep(1000);
    		wordArr = set.inputAlph(); //�I�sSetting��Method�]�w�x�s��r�ܰ}�C��
    		Thread.sleep(1000);
    		System.out.println("�еy���X��...\n");
    		Thread.sleep(1000);
    		if(this.getClass() == UserVsCom.class) //�I�sCommand��Method��X�i�Ϋ��O
    			cmd.explain(); //���a��ܹq�������O�M��
    		else
    			cmd.explain2(); //���a��ܪ��a�����O�M��
    		Thread.sleep(3000);
        	System.out.println("�C���]�w����!!");
    	}catch(Exception e){
    		System.out.println("�]�w�X�{���DiniGame");
    	}
    	
    	for(int i=0; i<=25; i++){
    		for(int j=0; j<wordArr[i].length; j++){
    			wordArr[i][j][2]="n";
    			wordArr[i][j][3]="n";
    		}
		}
    	System.out.println("�C����l�Ƨ���!!\n");
    	System.out.println("/*...................................*/");
    }
    
    void runGame(){
    	try{
    		Thread.sleep(1000);
    		System.out.println("~~~�C���}�l~~~");
    		Thread.sleep(1000);
    		StartGame();
    	}catch(Exception e){
    		System.out.println("����X�{���DrunGame");
    	}
    }
    //******************************************************//
    
    //*******************��Ӥl���O���@�P��H��k*******************//
    abstract void StartGame(); //�}�l�C��
    abstract boolean CheckCmd(String word); //�ˬd��J���r��O�_�����O
    abstract void CheckFindWord(int alphword, int alphindex, boolean errMsg); //�ˬd�O�_����r
    //******************************************************//

    
    //********************��Ӥl���O���@�P��k********************//
    void userPlay(){ //���a�}�l���
   		u_word = keyboard.next();
   		hasCmd = CheckCmd(u_word);	//�P�_�r���O�_�����O
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
    
    boolean CheckHead(String s1){ //�ˬd�r���}�Y�O�_�P�W�@�ӳ�r�r���ۦP
		if(!(s1.equals("noinput"))){
			u_word = ToLowerCase(u_word); //�N�r�������ন�p�g���
			if((GetFirstChar(u_word)).equals(GetLastChar(HisWord))){ //�ݬO�_�ثe��r�r���M�W�@�ӳ�r�r���O�_�ۦP
				return true;
			}else{
				System.out.println("�z��J����r�}�Y���~!!");
				return false;
			}
		}
		return true; //��ܩ|����J����@�ӳ�r�A�ҥH�����ˬd�r��
	}
    
    void CheckWord(String s1){ //�ˬd�ΦL�X�ثe��r
		int alphword=0,alphindex=0;	//alphword�x�s�ثe�r������r���ޡAalphindex�x�s�ثe�r������
   		boolean errMsg = false; //�Y�Ȭ�false��ܬd�L����r�A�Y��true��ܦ���r���ƨϥ�
   		String headword;
   		
   		if(firstInput && u_word.equals("\\c")){
   			alphindex = (int)(Math.random()*wordArr.length);
   		}else{
   			if(s1.equals("noinput"))
   				headword = GetLastChar(HisWord);	//���o�W�@�ӳ�r���r���A��@�o�����r��
   			else
   				headword = GetFirstChar(u_word);	//���o��J��r���r���A��@�o�����r��
   		
   			for(int i=0; i<wordArr.length; i++){	//����26�Ӧr��
   				if(wordArr[i][0][0].substring(0,1).toLowerCase().equals(headword)){	//�P�_�ثe��r�r����:a~z�䤤�@��
   					alphindex = i;	//�����r�����r������
   					break;
   				}
   			}
   		}
   		
   		if(s1.equals("noinput")){
   			int index = (int)(Math.random()*wordArr[alphindex].length);	//�H�����ͳ�r������
			while(!(wordArr[alphindex][index][2].equals("n"))){	//���ư��檽���r�S�ϥιL
				index = (int)(Math.random()*wordArr[alphindex].length);	//�H�����ͳ�r������
			}
			alphword = index;	//�N���ަs�J
			u_word = wordArr[alphindex][alphword][0];	//�N����r�s�J
			findword = true;	//����r
   		}else{
   			for(int i=0; i<wordArr[alphindex].length; i++){
   				if(wordArr[alphindex][i][0].toLowerCase().equals(s1)){
   					if(wordArr[alphindex][i][2].equals("n")){
   						alphword = i;
   	   					findword = true;
   	   					break;
   					}else{
   						errMsg = true;
   						System.out.println("����r�w�Q�ϥιL!!");
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
    		for(int i=0; i<wordArr.length; i++){	//����26�Ӧr��
    			if(wordArr[i][0][0].substring(0,1).toLowerCase().equals(headword)){	//�P�_�ثe��r�r����:a~z�䤤�@��
    				alphindex = i;	//�����r�����r������
    				break;
    			}
    		}
    		if(alphindex != 104){
    			System.out.println("�j�M���G�p�U:");
    			for(int i=0; i<wordArr[alphindex].length; i++){
    				CompareWord(word, alphindex, i);
    			}
    			if(!findSimilar)
    				System.out.println("�ܩ�p�S����������r!!");
    		}else{
    			System.out.println("��J��r���r�������D!!");
    		}
    	}else{
    		System.out.println("�A��J���L�H��r�֩�5�Ӧr!!");
    	}
    }
    
    void CompareWord(String word, int x, int y){
    	int count = 0; //�O��match�쪺�r�����X��
    	String wordTmp = word.substring(1); //�̫���match���ƥΪ�
    	word = word.substring(1); //���h���r���A�]���w�g�T�w�r�����ݭn��
    	String word2 = wordArr[x][y][0].substring(1); //�n�Q��諸��r�A�åB�h���r��
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
		return s1.toLowerCase();	//�N�ǤJ���r��p�g
	}
    
	String GetFirstChar(String s1){
		return s1.substring(0,1);	//���o�ǤJ�r���r��
	}
	
    String GetLastChar(String s1){
    	return s1.substring(s1.length()-1);	//���o�ثe��r���r��
    }
    //******************************************************//
    
    
    //********************UserVsCom�M����k********************//
    //                                                      //
    //******************************************************//
    
    
    //********************UserVsUser�M����k*******************//
    //int CheckPeople(); �ˬd�C���H�ƬO�_�ŦX�d��                                                          //
    //void setUser(); �]�w�C�Ӫ��a�����ݩ�(��:�ϴ����ơB�O�_�뭰��)      //
    //void CheckWinner(); �ˬd�O�_�����a���                                                                  //
    //void nextUser(); �M��U�@���J��r�����a                                                               //
    //******************************************************//
}