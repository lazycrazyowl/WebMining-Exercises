set ylabel "# words with same occurrences"
set xlabel "# of occurrences"
set terminal png

set logscale x
set logscale y
set output "freq-de-en.png"

plot [1:16000][1:110] "freq-huckleberry-finn.txt.gp", "freq-fontane-effi-briest.txt.gp



set output "freq-de-de.png"
plot [1:16000][1:110] "freq-goethe-faust.txt.gp", "freq-fontane-effi-briest.txt.gp