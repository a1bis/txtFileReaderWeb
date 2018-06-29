package lt.at.assignment.view;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import lt.at.assignment.core.App;
import lt.at.assignment.core.App.WordGroups;
import lt.at.assignment.data.WordWithOccurrences;

@ManagedBean(name = "textFileReaderView")
@ViewScoped
public class TextFileReaderView implements Serializable {

	private static final long serialVersionUID = 930103037626507302L;
	private static final String LINE_BREAK = "\r\n";
	private static final String TXT_EXTENSION = ".txt";
	private static final String FILENAME_A_TO_G = "a_g";
	private static final String FILENAME_H_TO_N = "h_n";
	private static final String FILENAME_O_TO_U = "o_u";
	private static final String FILENAME_V_TO_Z = "v_z";
	private static final String CONTENT_TYPE = "text/plain";
	private static final String COLON_SEPARATOR = " : ";

	@ManagedProperty("#{app}")
	private App app;

	private StreamedContent file;
	private List<WordWithOccurrences> wordsAG;
	private List<WordWithOccurrences> wordsHN;
	private List<WordWithOccurrences> wordsOU;
	private List<WordWithOccurrences> wordsVZ;

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		try {
			InputStream fileInputStream = event.getFile().getInputstream();
			app.readInputStream(fileInputStream);
			updateWordLists();
		} catch (IOException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Failed to read text file.");
			RequestContext.getCurrentInstance().showMessageInDialog(msg);
			e.printStackTrace();
		}
	}

	private void updateWordLists() {
		this.wordsAG = app.getWordWithOccurencesList(app.getGroupA_G());
		this.wordsHN = app.getWordWithOccurencesList(app.getGroupH_N());
		this.wordsOU = app.getWordWithOccurencesList(app.getGroupO_U());
		this.wordsVZ = app.getWordWithOccurencesList(app.getGroupV_Z());
	}

	public void downloadFile(int n) {
		InputStream stream;
		String fileName;
		if (n == WordGroups.GROUP_A_TO_G.getWordGroup()) {
			stream = buildInputStreamForDownload(wordsAG);
			fileName = FILENAME_A_TO_G;
		} else if (n == WordGroups.GROUP_H_TO_N.getWordGroup()) {
			stream = buildInputStreamForDownload(wordsHN);
			fileName = FILENAME_H_TO_N;
		} else if (n == WordGroups.GROUP_O_TO_U.getWordGroup()) {
			stream = buildInputStreamForDownload(wordsOU);
			fileName = FILENAME_O_TO_U;
		} else {
			stream = buildInputStreamForDownload(wordsVZ);
			fileName = FILENAME_V_TO_Z;
		}
		file = new DefaultStreamedContent(stream, CONTENT_TYPE, fileName + TXT_EXTENSION);
	}

	private InputStream buildInputStreamForDownload(List<WordWithOccurrences> words) {
		StringBuilder builder = new StringBuilder();
		for (WordWithOccurrences wwo : words) {
			builder.append(wwo.getWord()).append(COLON_SEPARATOR).append(wwo.getOccurrences()).append(LINE_BREAK);
		}
		return new ByteArrayInputStream(builder.toString().getBytes(StandardCharsets.UTF_8));
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public List<WordWithOccurrences> getWordsAG() {
		return wordsAG;
	}

	public List<WordWithOccurrences> getWordsHN() {
		return wordsHN;
	}

	public List<WordWithOccurrences> getWordsOU() {
		return wordsOU;
	}

	public List<WordWithOccurrences> getWordsVZ() {
		return wordsVZ;
	}

	public StreamedContent getFile() {
		return file;
	}

}
