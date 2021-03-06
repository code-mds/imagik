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

### Personalizzazioni
* UI ispirata a HIG GNOME 3
* Istruzioni di base presentate nello sfondo dell'applicazione
* Il programma mostra le informazioni della cartella corrente: percorso e numero immagini
* La lista delle immagini e' composta da una miniatura e le informazioni base del file.
* Un'area piu' grande mostra l'anteprima dell'immagine selezionata.
* Il programma ricorda l'ultima cartella aperta e all'avvio mostra il suo contenuto.

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
