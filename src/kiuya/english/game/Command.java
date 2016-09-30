package kiuya.english.game;

class Command {
	GameModel game;
	Command(GameModel gm){
		game = gm;
	}
	
	//******************UserVsCom類別的說明指令******************//
	void explain(){
		System.out.println("在此說明各指令用途:");
		System.out.println("\\r 開啟/關閉 記錄電腦所使用單字");
		co_explain();
	}
	
	//******************UserVsUser類別的說明指令******************//
	void explain2(){
		System.out.println("在此說明各指令用途:");
		co_explain();
	}
	
	//********************兩個類別的共同說明指令********************//
	void co_explain(){
		System.out.println("\\c 得到隨機單字的救援");
		System.out.println("\\e 離開這場對局");
		System.out.println("\\h 叫出指令清單");
		System.out.println("\\s 該回合玩家認輸投降");
		if(game.complementWord)
			System.out.println("\\z 進入到印象單字補充系統");
		System.out.println("");
	}
}