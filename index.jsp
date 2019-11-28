<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test App permission</title>
<script src="js/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script type="text/javascript">

	  $(document).ready(function(){ 
		  

	           
		  $.get("getCategorie", function(data, status){
			  stampa(data);
						});

		
			
	});
		function stampa(data){
				var i=0;
				 var d = JSON.parse(data);
				 
				  var s="<select name=\"cat\" id=\"cat\">";
				  for (i = 0; i <d.length; i++) { 
					
					s+=" <option value=\""+d[i].categoria+"\">"+d[i].categoria+"</option>";
				  
				}
				 s+="</select>";
				  document.getElementById("categoria").innerHTML = s;
				  
				}

	
	
	</script>
</head>
<body>


  <div class="container">
  <br>
  <p>Per esaminare un app presente in github e controllare se ha permessi anomali, inserisci il link della repository nell'apposito campo di testo e inserisci la categoria dell'app tra le opzioni. </p>


<div>
		<input type="text" placeholder="inserisci URL" name="url" id="url" required> <br><br>
		<div id="categoria"></div>
		<br>
		<button onclick='graficoApp()'>confronta</button>
		<!-- <option value="" selected disabled hidden>categorie</option> -->
		
	</div>

    <canvas id="myChart"></canvas>
    <br> <br>
  </div>
  
<script type="text/javascript">
function graficoApp(){


    let myChart = document.getElementById('myChart').getContext('2d');

    // Global Options
    Chart.defaults.global.defaultFontFamily = 'Lato';
    Chart.defaults.global.defaultFontSize = 14;
    Chart.defaults.global.defaultFontColor = '#777';
   
    let massPopChart = new Chart(myChart, {
      type:'bar', // bar, horizontalBar, pie, line, doughnut, radar, polarArea
      data:{
        labels:[],
        datasets:[{
          label:'Permessi App',
          data:[
               
                ],
          
                  
          //backgroundColor:'green',
          backgroundColor:[
          
          ],
          borderWidth:1,
          borderColor:'#777',
          hoverBorderWidth:3,
          hoverBorderColor:'#000'
        },
        {
            label:'Permessi App per Categorie',
            data:[
                 1,
                 3,
                 5,
                 12,
                 4
                  ],
            
                    
            //backgroundColor:'green',
            backgroundColor:[
              'rgba(54, 162, 235, 0.6)',
              'rgba(54, 162, 235, 0.6)',
              'rgba(54, 162, 235, 0.6)',
              'rgba(54, 162, 235, 0.6)',
              'rgba(54, 162, 235, 0.6)',
              'rgba(54, 162, 235, 0.6)'
            ],
            borderWidth:1,
            borderColor:'#777',
            hoverBorderWidth:3,
            hoverBorderColor:'#000'
          }
        
        ]
      },
      options:{
    	 // events: ['click'],
        title:{
          display:true,
          text:'Permessi dell\'app',
          fontSize:25
        },
        legend:{
          display:true,
          position:'right',
          labels:{
            fontColor:'#000'
          }
        },
        layout:{
          padding:{
            left:50,
            right:0,
            bottom:0,
            top:0
          }
        },
        tooltips:{
          enabled:true
        }
      }
      
      
    });
    

    $(document).ready(function(){ 
    	var url=document.getElementById("url").value;
    	var categoria=document.getElementById("cat");
    	var c= categoria.options[categoria.selectedIndex].value;
    	
        if (!validateUrl(url)){
    		alert("inserisci url valido di una repository di Github");
    	}else{
    
    	$.get("VisualizzaGraficoApp?url="+url+"&cat="+c, function(data, status){
    		
    		  perm(data);

    					});
    	}

    	});
    	
    	function perm(data){
    		var i=0;
  
    		 var d = JSON.parse(data);
			for (i = 0; i <d.length; i++) { 
    		    massPopChart.data.datasets[0].data.push('1');
    		    massPopChart.data.datasets[0].backgroundColor.push('rgba(255, 206, 86, 0.6)');
    		    massPopChart.data.labels.push(d[i].permesso);
			}
    		    massPopChart.data.datasets[0].data.push(0);
    		    massPopChart.update();
    	   
    		}
    	
    	function validateUrl(url){
			var Regexp = /[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&/=]*)?/;
			var bol;
			if(url.match(Regexp)){
				bol= true;
			}else{
		
				bol= false;
			}
			return bol;
		}
}
  </script>
</body>
</html>