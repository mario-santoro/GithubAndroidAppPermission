package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import accessFile.AccessPermesso;
import model.Permesso;

/**
 * Servlet implementation class VisualizzaGraficoApp
 */
@WebServlet("/VisualizzaGraficoApp")
public class VisualizzaGraficoApp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VisualizzaGraficoApp() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = request.getParameter("url");
		System.out.println(url);
		String cat = request.getParameter("cat");
		System.out.println(cat);
		String controll=null;

		////////////start file///////////


		ArrayList<String> permessiApp = new ArrayList<String>();
		ArrayList<Permesso> permessi = new ArrayList<Permesso>();
		ArrayList<Permesso> p = new ArrayList<Permesso>();
		String current="C://Users/Utente/università/GithubPermission/WebContent/file";


		@SuppressWarnings("resource")
		BufferedReader readerPermessi = new BufferedReader(new FileReader(current+"/permessiApp.txt"));
		BufferedWriter bw;
		String linePermessi = readerPermessi.readLine();
		String[] tuttiPermessi = linePermessi.split("\\s+");

		String linkManifestApplicazione=url;

		AccessPermesso ap=new AccessPermesso();

		String nome = ap.estraiNomeApp(linkManifestApplicazione);
		System.out.println(nome);
		if(!(new File(current+"/"+cat)).exists()){
			(new File(current+"/"+cat)).mkdirs();
		}

		String fileTestoApplicazione = current+"/"+cat+"/"+nome+".txt";
		
		if(new File(fileTestoApplicazione).exists()){
			
			controll= "questa applicazione gia' e' stata analizzata";
		

		}else{

			File fileApp = new File(fileTestoApplicazione);


			URL cg = new URL(url);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(cg.openStream()));


			FileWriter fw = new FileWriter(fileApp);

			bw = new BufferedWriter(fw);
			String inputLine;
			while ((inputLine = in.readLine()) != null){

				bw.write(inputLine);
			}
			bw.flush();
			bw.close();






			permessiApp=ap.trovaPermessi(fileApp,tuttiPermessi);



			if(!permessiApp.isEmpty()){  //Check for count not equal to zero
					
				for (String a : permessiApp){

					Permesso perm = new Permesso();
					perm.setPermesso(a);
					perm.setCounter(1);
					permessi.add(perm);
					
					
					//System.out.println(a);

				}		
				p=ap.AggiornaCategorie(permessi, current, cat);
			}
			else{
				controll= "nessun permesso trovato nell'applicazione analizzata";
				System.out.println(controll);
			}
		}

			////////////end file///////////////	
			
			if(controll==null){
				response.getWriter().append("[");

				int i;
				for(i = 0; i < permessi.size()-1; i++){

					Permesso prova = permessi.get(i);

					if(p.get(i).getPermesso()==prova.getPermesso()){

						response.getWriter().append("{\"permesso\":\""+prova.getPermesso()+"\",\"counter\":\""+p.get(i).getCounter()+"\"},");
					}else{

						response.getWriter().append("{\"permesso\":\""+prova.getPermesso()+"\",\"counter\":\"0\"},");
					}

				}
				Permesso prova = permessi.get(i);
				response.getWriter().append("{\"permesso\":\""+prova.getPermesso()+"\",\"counter\":\""+p.get(i).getCounter()+"\"}");

				response.getWriter().append("]");
			}else{
			
				response.getWriter().append("[{\"control\":\""+controll+"\"}]");
			}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);


	}

}
