package kiuya.english.game;
import java.util.Scanner;

public class EnglishGame {
	static GameModel game = null;
	static int mod,replay;
	static Scanner key;
	
    public static void main(String[] args) {
    	introduce(); //對遊戲做介紹
    	while(mod != 3){
    		if(game != null){
    			System.out.println("");
        		game.runSetting(); //開始執行產生出來的模式實例
        		game.process();
    		}
    		replay = 0;
    		while(replay != 2){
    			choiceReplay();
    			if(replay == 1)
    				game.process();
    			else if(replay != 2)
    				System.out.println("輸入的值不再範圍內!!");
    			System.out.println("");
    		}
    		choiceModel();
    	}
    	
    }
    
    static void introduce(){
    	System.out.println("歡迎進入到「英文單字接龍」小遊戲");
        System.out.println("本遊戲是由「Kiuya」所製作 Ver1.0");
        System.out.println("");
        choiceModel();
    }
    
    static void choiceModel(){
    	System.out.println("/*...................................*/");
        System.out.println("進入遊戲前請先選擇「遊戲模式」");
        System.out.println("1.單人遊戲");
        System.out.println("2.多人遊戲");
        System.out.println("3.離開遊戲");
        input(1); //讓使用者輸入遊戲模式的值
    }
    
    static void choiceReplay(){
    	System.out.println("是否重新一局?");
        System.out.println("1.是");
        System.out.println("2.否");
        input(2); //讓使用者輸入是否重新一局的值
    }
    
    static void input(int choice){
    	boolean check = true;
    	while(check){
    		System.out.print("請輸入代碼:");
    		key = new Scanner(System.in);
    		if(choice == 1){
    			mod = checkInput(); //檢查輸入的值是否為整數型態
        		if(mod != 0)
            		check = chooseModel(mod); //用User所輸入的值產生對應的遊戲模式
    		}else if(choice == 2){
    			replay = checkInput(); //檢查輸入的值是否為整數型態
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
    		System.out.println("遊戲結束，請下次再見!!");
    	}else if(mod == 104){
    		System.out.println("輸入型態錯誤!!");
    		return true;
    	}else{
    		System.out.println("輸入的值不再範圍內!!");
    		return true;
    	}
    	return false;
    }
}

