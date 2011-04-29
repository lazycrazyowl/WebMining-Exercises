set ylabel "# words with same occurrences"
set xlabel "# of occurrences"
set terminal png
set output "freq-lin.png"
plot [0:10000][0:5100] "freq.gp"

set logscale x
set logscale y
set output "freq-log.png"
plot [1:10000][1:5100] "freq.gp"
