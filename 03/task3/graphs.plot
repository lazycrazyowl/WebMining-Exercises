set ylabel "Accuracy"
set xlabel "# of features"
set terminal svg
set output "accuracy_tokens.svg"
plot "token" using 1:2 title 'Accuracy[Words]' with lines

set output "accuracy_comparison.svg"
plot "token" using 1:2 title 'Accuracy[Words]' with lines,\
     "stem"  using 1:2 title 'Accuracy[Stem]' with lines, \
     "tokenWOStopword" using 1:2 title 'Accuracy [w/o Stopwords]' with lines

set ylabel "Execution Time"
set output "exec_tokens.svg"
plot "token" using 1:3 title 'ExecutionTime[ms]' with lines

set output "exec_comparison.svg"
plot "token" using 1:3 title 'ExecutionTime[Words]/ms' with lines,\
     "stem"  using 1:3 title 'ExecutionTime[Stem]/ms' with lines, \
     "tokenWOStopword" using 1:3 title 'ExecutionTime[w/o Stopwords]/ms' with lines
