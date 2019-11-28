package accessFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class AccessPermesso {
	
	
	
	
	public  String estraiNomeApp(String linkManifestApplicazione) {
		String nome = null;
		int j=0;
		int subs = 0;
		int sostituisci =0;

		String nomeApplicazione = linkManifestApplicazione.substring(19);
		do {
			if (nomeApplicazione.subSequence(subs, subs+1).equals("/")) {

				j++;
			}
			if(j==1 && sostituisci == 0) {

				sostituisci= subs;

			}

			subs++;


		}while(j<2);

		nome = nomeApplicazione.substring(0, sostituisci-1)+"-"+nomeApplicazione.substring(sostituisci+1,subs-1);

		return nome;
	}

	public void eseguiComando(String comando) {
		System.out.println(comando);
		try {
			Process pr = Runtime.getRuntime().exec("/bin/bash");
			BufferedWriter outCommand = new BufferedWriter(new OutputStreamWriter(pr.getOutputStream()));
			outCommand.write(comando);
			outCommand.flush();
		} catch (IOException ex) {
			System.out.println("errore nel comando bash "+ex);
		}

	}

	public ArrayList<String> trovaPermessi(File fileApp, String[] tuttiPermessi) throws IOException {

		ArrayList<String> permessiApp = new ArrayList<String>();
		String[] words;
		String stringaFileApp=null;
		FileReader readerFileApp = new FileReader(fileApp);  //Creation of File Reader object
		BufferedReader bufferFileApp = new BufferedReader(readerFileApp); //Creation of BufferedReader object

		while((stringaFileApp=bufferFileApp.readLine())!=null){   //Reading Content from the file

			words=stringaFileApp.split("permission.");  //Split the word using permission.

			for (String word : words){

				for (String w : tuttiPermessi){
					if (word.startsWith(w)){   //Search for the given word
						permessiApp.add(w);
					}
				}
			}
		}

		readerFileApp.close();
		return permessiApp;

	}

}
