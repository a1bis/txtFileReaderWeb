package lt.at.assignment.core;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lt.at.assignment.data.WordWithOccurrences;
import lt.at.assignment.input.TextFileReader;

@ManagedBean(name = "app")
@ViewScoped
public class App {

	public enum WordGroups {

		GROUP_A_TO_G(0),
		GROUP_H_TO_N(1),
		GROUP_O_TO_U(2),
		GROUP_V_TO_Z(3);

		private final int wordGroup;

		private WordGroups(int wordGroup) {
			this.wordGroup = wordGroup;
		}

		public int getWordGroup() {
			return wordGroup;
		}
	}

	private final ConcurrentHashMap<String, Integer> groupA_G;
	private final ConcurrentHashMap<String, Integer> groupH_N;
	private final ConcurrentHashMap<String, Integer> groupO_U;
	private final ConcurrentHashMap<String, Integer> groupV_Z;

	public App() {
		this.groupA_G = new ConcurrentHashMap<>();
		this.groupH_N = new ConcurrentHashMap<>();
		this.groupO_U = new ConcurrentHashMap<>();
		this.groupV_Z = new ConcurrentHashMap<>();
	}

	public void readInputStream(InputStream fileInputStream) {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		executor.execute(new TextFileReader(fileInputStream, groupA_G, groupH_N, groupO_U, groupV_Z));
		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public List<WordWithOccurrences> getWordWithOccurencesList(ConcurrentHashMap<String, Integer> wordsMap) {
		List<WordWithOccurrences> results = new ArrayList<>();
		wordsMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(m -> {
			results.add(new WordWithOccurrences(m.getKey(), m.getValue()));
		});
		return results;
	}

	public ConcurrentHashMap<String, Integer> getGroupA_G() {
		return groupA_G;
	}

	public ConcurrentHashMap<String, Integer> getGroupH_N() {
		return groupH_N;
	}

	public ConcurrentHashMap<String, Integer> getGroupO_U() {
		return groupO_U;
	}

	public ConcurrentHashMap<String, Integer> getGroupV_Z() {
		return groupV_Z;
	}

}
