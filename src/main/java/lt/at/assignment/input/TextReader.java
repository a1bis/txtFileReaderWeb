package lt.at.assignment.input;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class TextReader implements Runnable {

	private static final String WORD_SEPARATOR = "\\W+";
	private static final String ONLY_ALPHABET_PATTERN = "[a-zA-Z]+";
	private final String line;
	private final ConcurrentHashMap<String, Integer> groupA_G;
	private final ConcurrentHashMap<String, Integer> groupH_N;
	private final ConcurrentHashMap<String, Integer> groupO_U;
	private final ConcurrentHashMap<String, Integer> groupV_Z;

	public TextReader(String line, ConcurrentHashMap<String, Integer> groupA_G,
			ConcurrentHashMap<String, Integer> groupH_N, ConcurrentHashMap<String, Integer> groupO_U,
			ConcurrentHashMap<String, Integer> groupV_Z) {
		this.line = line;
		this.groupA_G = groupA_G;
		this.groupH_N = groupH_N;
		this.groupO_U = groupO_U;
		this.groupV_Z = groupV_Z;
	}

	@Override
	public void run() {
		String[] wordArray = line.split(WORD_SEPARATOR);
		goThroughWordArray(wordArray);
	}

	private void goThroughWordArray(String[] wordArray) {
		for (String word : wordArray) {
			if (Pattern.matches(ONLY_ALPHABET_PATTERN, word)) {
				addWordToGroup(word);
			}
		}
	}

	private void addWordToGroup(String word) {
		char ch = word.charAt(0);
		if (ch >= 'a' && ch <= 'g') {
			addWordToMap(word, groupA_G);
		} else if (ch >= 'h' && ch <= 'n') {
			addWordToMap(word, groupH_N);
		} else if (ch >= 'o' && ch <= 'u') {
			addWordToMap(word, groupO_U);
		} else {
			addWordToMap(word, groupV_Z);
		}
	}

	private void addWordToMap(String word, Map<String, Integer> groupMap) {
		Integer numberOfOccurrences = groupMap.get(word);
		if (numberOfOccurrences != null) {
			groupMap.put(word, ++numberOfOccurrences);
		} else {
			groupMap.put(word, 1);
		}
	}

}
