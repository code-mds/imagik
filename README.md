# IMAGIK: Image Viewer & Processor
SUPSI - Progetto Ingegneria SW 1

### Descrizione
IV&P è un software per la elaborare delle immagini in formato JPG e PNG. 
Il programma permette all’utente visualizzare le immagini di una directory (in forma di miniatura/anteprima). 
L'utente può visualizzare i metadati principali (dati dell'immagine e metadati EXIF) selezionando un'immagine. 
L'utente può filtrare la visualizzazione attraverso dei pattern applicati al nome del file. 
L'utente può visualizzare un'immagine con livelli di zoom a sua scelta. 
Il programma deve inoltre permettere all’utente di applicare alle immagini una o più modifiche (per es. scala, ruota,...) e di salvare il risultato in un nuovo file.

### Requisiti Espliciti
* Il programma deve permettere la visualizzazione (miniatura/anteprima) delle immagini di una cartella.
* L'utente può visualizzare un'immagine con livelli di zoom a sua scelta. 
* L'utente può filtrare la lista delle immagini attraverso un pattern (globbing) sul nome del file.
* L'utente deve poter cambiare cartella in qualsiasi momento (navigazione).
* Il programma visualizza le informazioni dell'immagine selezionata (tipo, risoluzione, dimensione, dimensione del file, metadati EXIF, ...).
* Il programma permette di selezionare una o più immagini per effettuare modifiche (selezione multipla).
* Il programma permette diverse operazioni di modifica (scala, ruota, converti in bianco e nero, taglia, ...).
* Il programma memorizza le operazioni di modifica effettuate su un'immagine in un file di testo (logging).
* Il programma mostra un'anteprima delle modifiche selezionate.
* L'utente permette di salvare l'immagine modificata in un nuovo file (save as).
* Il programma dovrà supportare la localizzazione in almeno due lingue (inglese, italiano).

### Requisiti Impliciti
* Il programma mostra le informazioni della cartella corrente: percorso, numero file, ... 
* La lista delle immagine e' composta da una miniatura e il nome del file.
* Un'area piu' grande mostra l'anteprima dell'immagine selezionata.
* Il programma puo' essere invocato da linea di comando. La cartella da aprire puo' essere passata come parametro opzionale.
* Il programma ricorda l'ultima cartella aperta e all'avvio mostra il suo contenuto.

### Look & Feel
http://www.fastrawviewer.com/sites/fastrawviewer.com/files/FastRawViewer_RAF.jpeg
https://is1-ssl.mzstatic.com/image/thumb/Purple124/v4/19/54/81/19548111-0424-42c7-8cf1-f597194e163d/pr_source.jpg/643x0w.jpg
https://amazingtop10.com/wp-content/uploads/2018/01/faststone-image-viewer-6.0.png
https://i.stack.imgur.com/q55yU.png
https://www.itechtics.com/wp-content/uploads/2017/06/2-22-670x387.png
https://raw.githubusercontent.com/hughsie/fedora-appstream/master/screenshots-extra/eog/b.png
http://www.debugpoint.com/blog/wp-content/uploads/2016/08/nomacs-Running-in-Ubuntu.png


### Tecnologie
* Java e JavaFX
* Librerie di manipolazione di immagini per Java (disponibili su Maven) 
* Librerie per l'estrazione dei metadati EXIF (disponibili su Maven)

### Consegna
* Repository GitHub
* Installer / Eseguibile

### Piattaforme
* Windows
* Linux
* OSX (opzionale)
