package kiuya.english.game;
import java.util.Scanner;

public class EnglishGame {
	static GameModel game = null;
	static int mod,replay;
	static Scanner key;
	
    public static void main(String[] args) {
    	introduce(); //��C��������
    	while(mod != 3){
    		if(game != null){
    			System.out.println("");
        		game.runSetting(); //�}�l���沣�ͥX�Ӫ��Ҧ����
        		game.process();
    		}
    		replay = 0;
    		while(replay != 2){
    			choiceReplay();
    			if(replay == 1)
    				game.process();
    			else if(replay != 2)
    				System.out.println("��J���Ȥ��A�d��!!");
    			System.out.println("");
    		}
    		choiceModel();
    	}
    	
    }
    
    static void introduce(){
    	System.out.println("�w��i�J��u�^���r���s�v�p�C��");
        System.out.println("���C���O�ѡuKiuya�v�һs�@ Ver1.0");
        System.out.println("");
        choiceModel();
    }
    
    static void choiceModel(){
    	System.out.println("/*...................................*/");
        System.out.println("�i�J�C���e�Х���ܡu�C���Ҧ��v");
        System.out.println("1.��H�C��");
        System.out.println("2.�h�H�C��");
        System.out.println("3.���}�C��");
        input(1); //���ϥΪ̿�J�C���Ҧ�����
    }
    
    static void choiceReplay(){
    	System.out.println("�O�_���s�@��?");
        System.out.println("1.�O");
        System.out.println("2.�_");
        input(2); //���ϥΪ̿�J�O�_���s�@������
    }
    
    static void input(int choice){
    	boolean check = true;
    	while(check){
    		System.out.print("�п�J�N�X:");
    		key = new Scanner(System.in);
    		if(choice == 1){
    			mod = checkInput(); //�ˬd��J���ȬO�_����ƫ��A
        		if(mod != 0)
            		check = chooseModel(mod); //��User�ҿ�J���Ȳ��͹������C���Ҧ�
    		}else if(choice == 2){
    			replay = checkInput(); //�ˬd��J���ȬO�_����ƫ��A
    			check = false;
    		}
    	}
    }
    
    static int checkInput(){
    	int value = 0;
    	try{
    		value = key.nextInt();
    	}catch(Exception e){
    		return 104;
    	}
       	return value;
    }
    
    static boolean chooseModel(int mod){
    	if(mod == 1){
    		game = new UserVsCom();
    	}else if(mod == 2){
    		game = new UserVsUser();
    	}else if(mod == 3){
    		System.out.println("�C�������A�ФU���A��!!");
    	}else if(mod == 104){
    		System.out.println("��J���A���~!!");
    		return true;
    	}else{
    		System.out.println("��J���Ȥ��A�d��!!");
    		return true;
    	}
    	return false;
    }
}

