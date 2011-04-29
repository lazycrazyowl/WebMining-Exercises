set xlabel "word rank"
set ylabel "# of occurrences"
set terminal png
set output "zipf-lin.png"
plot [0:16000][0:5100] "zipf-all.txt.gp"

set logscale x
set logscale y
set output "zipf-log.png"
plot [1:16000][1:5100] "zipf-all.txt.gp"
