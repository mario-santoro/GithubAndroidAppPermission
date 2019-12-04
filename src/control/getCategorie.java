package control;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class getCategorie
 */
@WebServlet("/getCategorie")
public class getCategorie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getCategorie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		 FileReader fr =  new FileReader("C://Users/Utente/università/GithubPermission/WebContent/file/categorieApp.txt"); 
			  
			  
			   Scanner in2=new Scanner(fr);
			   response.getWriter().append("[");
			   String output="";
				while(in2.hasNext()){
					
					output+="{\"categoria\":\""+in2.nextLine()+"\"},";
					
				}
				int c=output.length();
				String tmp=output.substring(0,c-1);
				response.getWriter().append(tmp);
				response.getWriter().append("]");
				in2.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
