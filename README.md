# Test Permission of Android App
<img  height="500" src="https://github.com/mario-santoro/GithubAndroidAppPermission/blob/master/img/GitHubpermission.png">

# Introduzione
Github Permission of Android app è un applicativo che si propone di controllare i permessi delle applicazioni Android andando ad analizzare il file AndridManifest.xml. Essa confronta i permessi dell’app in analisi con i permessi di tutte le altre app della stessa categoria (le categorie sono raggruppate dal PlayStore ufficiale) già precedentemente analizzati, dando visione all’utente il numero di quante applicazioni hanno usato lo stesso permesso. Riusciamo cosi ad analizzare se un relativo permesso è utilizzato da più o meno applicazioni, avendo cosi un feedback visivo di quale permesso può essere inusuale per quella categoria, e quindi potenzialmente pericoloso o meno (cioè più comune e quindi uno standard per quella categoria di applicazioni). Oltre a questo proposito, l’applicativo porta anche una maggior consapevolezza di quali permessi vengono utilizzati dalle varie applicazioni, e quindi a cosa accedono, e a cosa noi utenti diamo il permesso di accedere.
# Descrizione
L’applicativo web accede ai permessi dell’applicazione in analisi grazie all’URL, dato in input dall’utente, della pagina AndroidManifest.xml presente su Github. Per completare le informazioni essenziali all’analisi l’utente deve selezionare la categoria dell’applicazione che si vuole sviscerare, tale categoria, per verificare che sia corretta in modo da non falsare i risultati, può essere controllata dal PlayStore android. 
Terminata la fase di interazione con l’utente l’applicativo effettua web scraping  per estrarre i dati dall’URL inserito cercando i permessi al suo interno, quest’ultimi una volta trovati (se il file è valido) andranno inseriti in una struttura dati.  In caso di prima analisi per la categoria in questione  viene creata la cartella della relativa categoria e il file “mediaPerNomeCategoria” dove verranno inseriti, man mano che vengono analizzate applicazioni, i permessi da loro utilizzati, aggiornando il numero di occorrenze del permesso se esso è già presente nel file “mediaPerNomeCategoria”.
Una volta aggiornato questo file verrà mostrato all’utente una tabella contenete tutti i permessi dell’applicazione analizzata e il numero di quante applicazioni hanno lo stesso permesso (1 se è solo quella relativa all’applicazione in analisi, maggiore di 1 se è stato usato da più applicazioni) evidenziandogli in rosso quelli con minor occorrenze e quindi potenzialmente pericolosi. 
