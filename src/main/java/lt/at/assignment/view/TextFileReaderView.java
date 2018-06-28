package lt.at.assignment.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import lt.at.assignment.core.App;
import lt.at.assignment.data.WordWithOccurrences;

@ManagedBean(name="textFileReaderView")
@ViewScoped
public class TextFileReaderView implements Serializable {

	private static final long serialVersionUID = 930103037626507302L;

	@ManagedProperty("#{app}")
	private App app;
	
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

}
