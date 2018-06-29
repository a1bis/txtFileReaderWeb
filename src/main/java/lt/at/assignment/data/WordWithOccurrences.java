package lt.at.assignment.data;

public class WordWithOccurrences {

	private final String word;
	private final int occurrences;

	public WordWithOccurrences(String word, int occurrences) {
		this.word = word;
		this.occurrences = occurrences;
	}

	public String getWord() {
		return word;
	}

	public int getOccurrences() {
		return occurrences;
	}

}
