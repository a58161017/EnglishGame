package kiuya.hibernate.sqlite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateFileToSQLite {
	public static void main(String[] args) {
		
		// �}��Session�A�۷��}��JDBC��Connection
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		//insertData(session);
		insertDataBatch(session);
		//updateData(session);
		//selectDataByHQL(session);
		
		session.close();

		System.out.println("�s�W���OK!�Х���SQLite Browser�[�ݵ��G�I");
		//log.debug("�s�W���OK!�Х���SQLite Browser�[�ݵ��G�I");

		HibernateUtil.shutdown();
	}
	
	public static void insertData(Session session){
		Word word = new Word();
		
		//word.setId(new Long(1));
		word.setWord("apple");
		word.setExplain("ī�G");
		
		// Transaction��ܤ@�շ|�ܾާ@
		Transaction tx = session.beginTransaction();
		
		// �N����M�g�ܸ�Ʈw��椤�x�s
		session.save(word);
		tx.commit();
	}
	
	public static void insertDataBatch(Session session){
	    try{
	    	FileReader fr;
	    	BufferedReader br;
	    	String name, line;
	    	
	    	for(int i=1; i<=26; i++){ //�q�O�ƥ�1�}�l~26�A�����C�Ӧr�����P���}�C�j�p
	    		name = "./word/" + String.valueOf(i).toString() + "word.txt";
	    		fr = new FileReader(name);	//Ū���O�ƥ�
	    		br = new BufferedReader(fr);	//�ϥνw��Ū��
	    		while ((line = br.readLine()) != null){	//�O�ƥ��@��@��Ū��
	    			String[] words = line.split("%");	//�H%�����r���M�������
	    	    	Word word = new Word();
	    			
	    			word.setWord(words[0]); //�s�J�r��
	    			word.setExplain(words[1]); //�s�J�������
	    			
	    			// Transaction��ܤ@�շ|�ܾާ@
	    			Transaction tx = session.beginTransaction();
	    			
	    			// �N����M�g�ܸ�Ʈw��椤�x�s
	    			session.save(word);
	    			tx.commit();
	    		}
	    		br.close();	//�����w��Ū��
	        	fr.close();	//�����O�ƥ�
	        	System.out.println(String.valueOf(i).toString() + "word.txt �g�J����");
	    	}
	    }catch(IOException e){
	    	System.out.println("Ū�ɥX�{���~!!");
	    }
	    System.out.println("��r�� �g�J���\!!");
	}
	
	public static void updateData(Session session){
		Word word = new Word();
		
		word.setId(new Long(1));
		word.setWord("apple");
		word.setExplain("ī�G");
		
		// Transaction��ܤ@�շ|�ܾާ@
		Transaction tx = session.beginTransaction();
		
		// �N����M�g�ܸ�Ʈw��椤�x�s
		session.update(word);
		tx.commit();
	}
	
	public static void selectDataByHQL(Session session){
		
		Query query = session.createQuery("select id,word,explain from Word order by word"); 
		List words = query.list();
		Iterator iterator =  words.iterator();
		while(iterator.hasNext()) {
			Object[] obj = (Object[])iterator.next();
		    System.out.println(obj[0]+"/"+obj[1]+"/"+obj[2]);
		}
		
		//Query query = session.createQuery("from Word where word = 'apple'"); 
		/*Query query = session.getNamedQuery("kiuya.hibernate.QueryWord");
		
		List words = query.list();
		Iterator iterator =  words.iterator();
		while(iterator.hasNext()) {
			Word word = (Word)iterator.next();
		    System.out.println(word.getWord()+"/"+word.getExplain());
		}*/
		
	}
}