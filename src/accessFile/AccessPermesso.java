package accessFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import model.Permesso;


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
		
		FileWriter fw = new FileWriter(fileApp);
		BufferedWriter bw = new BufferedWriter(fw);
		for(String s : permessiApp){
			
			bw.write(s+"\n");
		}
		bw.close();
		fw.close();
		
		readerFileApp.close();
		return permessiApp;

	}


	//metodo controlla se esiste il file per categorie, se non esiste crea il file e mette l'unico gson Permesso del file
	//altrimenti prende quello del file esistente, e deve aggiugnere quelli nuovi, se trova un permesso uguale aggiorna il contatore,
	//altrimenti ne inserisce uno nuovo con contatore 1, e aggiorna il file riscrivendo il nuovo gson Permesso

	public ArrayList<Permesso> AggiornaCategorie(ArrayList<Permesso> ALp, String current, String categoria) throws IOException {
		Permesso p = ALp.get(0);
		ArrayList<String> permessiTMP = new ArrayList<String>();
		ArrayList<Permesso> cat = new ArrayList<Permesso>();
		String percorsoFileCategoria = current+"/"+categoria+"/mediaPer"+categoria+".txt";
		if(!(new File(percorsoFileCategoria).exists())) {

			File fileApp = new File(percorsoFileCategoria);
			FileWriter fw = new FileWriter(fileApp);
			BufferedWriter bw = new BufferedWriter(fw);
			int i=0;


			while (i<ALp.size()){

				bw.write(ALp.get(i).getPermesso()+","+ALp.get(i).getCounter()+"\n");
				i++;
			}
			bw.close();
			fw.close();
			return ALp;
		}else {
			String stringaFileApp=null;

			File fileApp = new File(percorsoFileCategoria);
			FileReader readerFileApp = new FileReader(fileApp);  //Creation of File Reader object

			BufferedReader bufferFileApp = new BufferedReader(readerFileApp); //Creation of BufferedReader object

			while((stringaFileApp=bufferFileApp.readLine())!=null){ 
				Permesso p3 = new Permesso();
				String[] lineaSplittata = stringaFileApp.split(",");
				p3.setPermesso(lineaSplittata[0]);
				p3.setCounter(Integer.parseInt(lineaSplittata[1]));
				cat.add(p3);
				permessiTMP.add(lineaSplittata[0]);

			}

			/*int i = 0;
			for(Permesso p1 : ALp){

				if(!permessiTMP.contains(p1.getPermesso())){

					cat.add(p1);
				}else{
					Permesso tmp = cat.get(i);
					int count = tmp.getCounter();

					count++;
					tmp.setCounter(count);

					cat.set(i, tmp);
					System.out.println("else "+ cat.get(i).getPermesso()+" counter: "+cat.get(i).getCounter());
					ALp.set(i, tmp);

				}
			}
			 */


			int flag=0;
			bufferFileApp.close();
			int i = 0;
			int j=0;
			for(i=0; i<ALp.size();i++){
				for(j=0; j<cat.size();j++){
					flag=0;
					
					if(ALp.get(i).getPermesso().equals(cat.get(j).getPermesso())) {
						flag=1;
						
						ALp.get(i).setCounter(ALp.get(i).getCounter()+1);
						cat.get(j).setCounter(cat.get(j).getCounter()+1);
						
						break;

					}
					
				}


				if(flag==0){
				

					cat.add(ALp.get(i));
					int c= cat.size();
				}else{
				
					flag=0;
				}


			}
	
			
			FileWriter fw = new FileWriter(fileApp);
			BufferedWriter bw = new BufferedWriter(fw);
		
			int h = 0;
			
			while (h<cat.size()){

				bw.write(cat.get(h).getPermesso() + "," + cat.get(h).getCounter()+"\n");
				h++;
			}
			bw.close();
			fw.close();
			
				
			

			return ALp;
		}



	}

}
