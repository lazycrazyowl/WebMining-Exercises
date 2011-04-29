set xlabel "word rank"
set ylabel "# of occurrences"

set logscale x
set logscale y

set terminal png
set output "zipf-de-en.png"
plot [1:100][1:80000] "zipf-huckleberry-finn.txt.gp", "zipf-fontane-effi-briest.txt.gp

set output "zipf-de-de.png"
plot [1:100][1:80000] "zipf-goethe-faust.txt.gp", "zipf-fontane-effi-briest.txt.gp
