package kiuya.hibernate.sqlite;

public class Word {
	private Long id;
	private String word;
	private String explain;

	// �����n���@�ӹw�]���غc��k
	// �H�ϱoHibernate�i�H�ϥ�Constructor.newInstance()�إߪ���
	public Word() {
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}
}
