package kiuya.hibernate.sqlite;

public class Word {
	private Long id;
	private String word;
	private String explain;

	// 必須要有一個預設的建構方法
	// 以使得Hibernate可以使用Constructor.newInstance()建立物件
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
