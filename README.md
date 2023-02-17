### Modul Praktikum Objektorientierte Programmierung

## Aufgabe 5.5: Pythagoras-Bäume
<br>

### Beschreibung

Pythagoras-Bäume setzen sich aus Quadraten zusammen, welche durch rechtwinklige Dreiecke verbunden sind. Deshalb gilt, dass die Summe der Quadrate über den Katheten (den Seiten am rechten Winkel) gleich dem Quadrat über der Hypothenuse (Seite, welche dem rechten Winkel gegenüber liegt) ist: der Satz des Pythagoras. Eine ganzzahlige Lösung dieser Gleichung ist 3^2 + 4^2 = 5^2. In Abbildungen 1(a) und 1(b) ist dieser Zusammenhang über eine 2 Iterationen dargestellt. Hierbei ist bei Abbildung 1(a) die kleine Seite immer links, während bei Abbildung 1(b) die kleine Seite abwechselnd links und rechts ist.

* (a) 2 Iterationen des Pythagoras-Baums 3-4-5. Die kleinere Seite ist immer links oben, die größere immer rechts oben.

* (b) 2 Iterationen des Pythagoras-Baums 3-4-5. Die kleinere Seite ist abwechselnd links oben und rechts oben.

Abbildung 1: Zwei Varianten der Erzeugung von Pythagoras-Bäumen.
<br>

### Aufgabenstellung

Schreiben Sie ein Programm, welches Pythagoras-Bäume graphisch darstellt. Ermöglichen Sie einen Export der berechneten Pythagoras-Bäume als Bilddatei.

Verwenden Sie für die Eingabe der Parameter und für die Darstellung des Ergebnisses JavaFX Schreiben Sie ein Menue, welches es Ihnen erlaubt das Fraktal als Bilddatei zu exportieren und das Programm zu beenden.
<br>

### Testprogramme

Erzeugen Sie 3 verschiedene Bäume:

* Einen 3-4-5 Baum, welcher immer gleich verzweigt.
* Einen 3-4-5 Baum, welcher immer abwechselnd verzweigt.
* Einen symmetrischen Pythagoras-Baum.


Geben Sie eine minimale Größe der Quadrate (Seitenlänge) als Abbruchkriterium an. Färben Sie die Bäume:

* Entsprechend der Verzweigung: eine Farbe für die kleineren Quadrate, eine Farbe für die größeren Quadrate, eine Farbe für die Wurzel. Bei symmetrischen Bäumen: eine Farbe für linke und eine Farbe für rechte Teilbäume.
* Entsprechend der Tiefe, zum Beispiel: 1-5: rot, 6-10: orange, 11- grün.

### Eingabe
2 Seitenlängen des Ausgangs-Dreiecks; Minimale Größe der Quadrate; Farbschema; Farben.

### Ausgabe
Der berechnete Pythagoras-Baum; Export als Bilddatei.

### Abbruch
Das Programm wird auf Wunsch des Benutzers beendet.

### Hinweise
Seien a, b, c die drei Seiten eines rechtwinkligen Dreiecks. Der Winkel β zwischen den Seiten a und c ergibt sich zu β = arccos (a/c). Entsprechend ist der Winkel α zwischen den Seiten b und c: α = arccos (b/c). Der Winkel γ zwischen a und b ist immer γ = 90°. Es gilt: α + β + γ = 180°. Beachten Sie, dass die trigonometrischen Funktionen in Java alle im Bogenmaß rechnen: 90° = π/2

Der Punkt Pc an der Spitze des Dreiecks ergibt sich zu
  Pc.х = cos (β) • α + Pa,r
  Pc:y = sin (β) • α + Pa,y

Alle anderen Punkte lassen sich analog berechnen.
