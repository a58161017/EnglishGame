package kiuya.english.game;

class Command {
	GameModel game;
	Command(GameModel gm){
		game = gm;
	}
	
	//******************UserVsCom���O���������O******************//
	void explain(){
		System.out.println("�b�������U���O�γ~:");
		System.out.println("\\r �}��/���� �O���q���Ҩϥγ�r");
		co_explain();
	}
	
	//******************UserVsUser���O���������O******************//
	void explain2(){
		System.out.println("�b�������U���O�γ~:");
		co_explain();
	}
	
	//********************������O���@�P�������O********************//
	void co_explain(){
		System.out.println("\\c �o���H����r���ϴ�");
		System.out.println("\\e ���}�o���什");
		System.out.println("\\h �s�X���O�M��");
		System.out.println("\\s �Ӧ^�X���a�{��뭰");
		if(game.complementWord)
			System.out.println("\\z �i�J��L�H��r�ɥR�t��");
		System.out.println("");
	}
}