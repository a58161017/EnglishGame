package kiuya.english.game;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

class Setting {
	GameModel game;
	String WordArray[][][];
	
	Setting(GameModel gm){
		game = gm;
	}
	
	//********************�N�]�w�ɪ����eŪ�i��********************//
    void setConfig(){
		String line;
		String valueArr[];
		try{
			FileReader fr;
			BufferedReader br;
			fr = new FileReader("./confing/charsettings.properties");
    		br = new BufferedReader(fr);
    		while((line=br.readLine()) != null){
    			valueArr=line.split("=");
    			judge(valueArr[0], valueArr[1]);
    		}
    		br.close();
    		fr.close();
    		System.out.println("�]�w�ɶפJ���\!!");
		}catch(Exception e){
			System.out.println("Ū�ɥX�{���~!!");
		}
    }
    
    //****************�P�_�������ܼƭȡA�ñN��s�J****************//
    void judge(String key, String value){
    	if(key.equals("MaxHelp"))
    		game.maxHelp = Integer.valueOf(value);
    	else if(key.equals("MaxUsers"))
    		game.maxUsers = Integer.valueOf(value);
    	else if(key.equals("HistoryWord"))
    		game.historyWord = Boolean.valueOf(value);
    	else if(key.equals("ComplementWord"))
    		game.complementWord = Boolean.valueOf(value);
    	else if(key.equals("MaxComplementWord"))
    		game.maxComplementWord = Integer.valueOf(value);
    	else
    		System.out.println("�]�w�ɦ����D!!");
    }
    
    //*************************�ŧi�}�C�j�p*************************//
    void setWordArray(){
    	WordArray = new String[26][][];
    	String name;
    	try{
    		FileReader fr;
    		BufferedReader br;
    		for(int i=1; i<=26; i++){ //�q�O�ƥ�1�}�l~26�A�����C�Ӧr�����P���}�C�j�p
    			int count = 0; //count�p��C�ӭ^��r������r�ƶq
    			name = "./word/" + String.valueOf(i).toString() + "word.txt";
    			fr = new FileReader(name);	//Ū���O�ƥ�
    			br = new BufferedReader(fr);	//�ϥνw��Ū��
    			while (br.readLine() != null)	//�O�ƥ��@��@��Ū��
    				count++;
    			WordArray[i-1] = new String[count][4];	//�ӧO�ŧi26�Ӧr������r�h��
    			br.close();	//�����w��Ū��
        		fr.close();	//�����O�ƥ�
    		}
    	}catch(IOException e){
    		System.out.println("Ū��1�X�{���~!!");
    	}
    }
    
    //*************************�x�s�}�C��r*************************//
    String[][][] inputAlph(){
    	String name, line;
    	try{
    		FileReader fr;
    		BufferedReader br;
    		for(int i=1; i<=26; i++){ //�q�O�ƥ�1�}�l~26�A�����C�Ӧr�����P���}�C�j�p
    			int count = 0; //count�p��C�ӭ^��r������r�ƶq
    			name = "./word/" + String.valueOf(i).toString() + "word.txt";
    			fr = new FileReader(name);	//Ū���O�ƥ�
    			br = new BufferedReader(fr);	//�ϥνw��Ū��
    			while ((line = br.readLine()) != null){	//�O�ƥ��@��@��Ū��
    				LineWordSplit(line,i,count);	//�N�r���@��@��s�J�}�C�����
    				count++;
    			}
    			br.close();	//�����w��Ū��
        		fr.close();	//�����O�ƥ�
    		}
    	}catch(IOException e){
    		System.out.println("Ū��2�X�{���~!!");
    	}
    	System.out.println("��r�ɶפJ���\!!");
    	return WordArray;
    }
    void LineWordSplit(String str,int wordnum,int count){
    	String[] words = str.split("%");	//�H%�����r���M�������
    	WordArray[wordnum-1][count][0] = words[0];	//�s�J�r��
    	WordArray[wordnum-1][count][1] = words[1];	//�s�J�������
    	WordArray[wordnum-1][count][2] = "n";	//�s�Jn�N���٨S�Q�ϥιL
    	WordArray[wordnum-1][count][3] = "n";	//�s�Jn�N��D����H��J�A0��ܹq���A1��ܪ��a1
    }
    //******************************************************//
    
    //*****************�N�C�����ϥΪ���r�g�J��O�ƥ���****************//
    void SaveWordToFile(String arr[][][]){
    	Date date = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_hh-mm-ss");
    	
    	if(game.historyWord){
    		String name = "HistoryWord_" + formatter.format(date) + ".txt"; //�H�����@�ɦW
    		try{
            	BufferedWriter bw = new BufferedWriter(new FileWriter("./RecordWord/HistoryWord/" + name));
            	for(int i=0; i<arr.length; i++){		
            		for(int j=0; j<arr[i].length; j++){
            			if(arr[i][j][2].equals("y")){
            				bw.write(arr[i][j][0] + " = " + arr[i][j][1]);
            				bw.newLine();
            			}
            		}
            	}
            	bw.close();
        	}catch(Exception e){
        		System.out.println("�g��1�X�{���~!!");
        	}
    	}
    	
    	if(game.hasRecord){
    		String name = "ComputerWord_" + formatter.format(date) + ".txt"; //�H�����@�ɦW
    		try{
            	BufferedWriter bw = new BufferedWriter(new FileWriter("./RecordWord/ComputerWord/" + name));
            	for(int i=0; i<arr.length; i++){		
            		for(int j=0; j<arr[i].length; j++){
            			if(arr[i][j][3].equals("0")){
            				bw.write(arr[i][j][0] + " = " + arr[i][j][1]);
            				bw.newLine();
            			}
            		}
            	}
            	bw.close();
        	}catch(Exception e){
        		System.out.println("�g��2�X�{���~!!");
        	}
    	}
    }
}