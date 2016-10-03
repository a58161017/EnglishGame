package kiuya.hibernate.sqlite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SQLiteMethod {
	//尚未使用
	public void insertData(){
		// 開啟Session，相當於開啟JDBC的Connection
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Word word = new Word();
		
		//word.setId(new Long(1));
		word.setWord("apple");
		word.setExplain("蘋果");
		
		// Transaction表示一組會話操作
		Transaction tx = session.beginTransaction();
		
		// 將物件映射至資料庫表格中儲存
		session.save(word);
		tx.commit();
		
		session.close();

		System.out.println("新增資料OK!請先用SQLite Browser觀看結果！");
		//log.debug("新增資料OK!請先用SQLite Browser觀看結果！");

		HibernateUtil.shutdown();
	}
	
	public void insertDataBatch(){
		// 開啟Session，相當於開啟JDBC的Connection
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
	    	FileReader fr;
	    	BufferedReader br;
	    	String name, line;
	    	
	    	for(int i=1; i<=26; i++){ //從記事本1開始~26，給予每個字母不同的陣列大小
	    		name = "./word/" + String.valueOf(i).toString() + "word.txt";
	    		fr = new FileReader(name);	//讀取記事本
	    		br = new BufferedReader(fr);	//使用緩衝讀取
	    		while ((line = br.readLine()) != null){	//記事本一行一行讀取
	    			String[] words = line.split("%");	//以%分成字母和中文解釋
	    	    	Word word = new Word();
	    			
	    			word.setWord(words[0]); //存入字母
	    			word.setExplain(words[1]); //存入中文註解
	    			
	    			// Transaction表示一組會話操作
	    			Transaction tx = session.beginTransaction();
	    			
	    			// 將物件映射至資料庫表格中儲存
	    			session.save(word);
	    			tx.commit();
	    		}
	    		br.close();	//關閉緩衝讀取
	        	fr.close();	//關閉記事本
	        	System.out.println(String.valueOf(i).toString() + "word.txt 寫入完成");
	    	}
	    }catch(IOException e){
	    	System.out.println("讀檔出現錯誤!!");
	    }
	    System.out.println("單字檔 寫入成功!!");
	    
	    session.close();

		System.out.println("新增資料OK!請先用SQLite Browser觀看結果！");
		//log.debug("新增資料OK!請先用SQLite Browser觀看結果！");

		HibernateUtil.shutdown();
	}
	
	//尚未使用
	public void updateData(){
		// 開啟Session，相當於開啟JDBC的Connection
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Word word = new Word();
		
		word.setId(new Long(1));
		word.setWord("apple");
		word.setExplain("蘋果");
		
		// Transaction表示一組會話操作
		Transaction tx = session.beginTransaction();
		
		// 將物件映射至資料庫表格中儲存
		session.update(word);
		tx.commit();
		
		session.close();

		System.out.println("新增資料OK!請先用SQLite Browser觀看結果！");
		//log.debug("新增資料OK!請先用SQLite Browser觀看結果！");

		HibernateUtil.shutdown();
	}
	
	//暫停使用Hibernate查詢單字，由於hibernate的log訊息會蓋掉遊戲訊息
	public int selectCountByWord(char c){
		// 開啟Session，相當於開啟JDBC的Connection
		Session session = HibernateUtil.getSessionFactory().openSession();
		int count = 0;
		
		Query query = session.createQuery("select id,word,explain from Word where word like '"+c+"%'");
		List words = query.list();
		Iterator iterator =  words.iterator();
		while(iterator.hasNext()) {
			Object[] obj = (Object[])iterator.next();
			System.out.println(obj[0]+"/"+obj[1]+"/"+obj[2]);
		    count++;
		}
		
		//Query query = session.createQuery("from Word where word = 'apple'"); 
		/*Query query = session.getNamedQuery("kiuya.hibernate.QueryWord");
		
		List words = query.list();
		Iterator iterator =  words.iterator();
		while(iterator.hasNext()) {
			Word word = (Word)iterator.next();
		    System.out.println(word.getWord()+"/"+word.getExplain());
		}*/
		
		session.close();
		HibernateUtil.shutdown();
		
		return count;
	}
}
