set title "Frequency over rank of german letters"
set term svg
set out "syllables_de.svg"
set auto x
set yrange [0:0.2]
set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set boxwidth 0.9
set xtic rotate by -45
#set bmargin 10 
plot 	'syllable_de.csv' using 2:xtic(1) title 'dict(de-DE)', \
	'syllable_de.csv' using 3:xtic(1) title 'de.wikipedia/Buchstabenhäufigkeit';
#
