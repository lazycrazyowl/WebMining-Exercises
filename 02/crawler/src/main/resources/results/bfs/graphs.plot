set ylabel "# of all urls"
set xlabel "# of new urls"
set terminal svg
set output "newVsFoundUrls.svg"
plot [0:500][0:500] "newVsFoundUrls.gp" title 'BFS', "../dfs/newVsFoundUrls.gp" title 'DFS'

set ylabel "# of found Links"
set xlabel "# of pages"
set logscale x
set logscale y
set output "histogram.svg"
plot [1:2000][1:100] "histogram.gp" title 'BFS', "../dfs/histogram.gp" title 'DFS'

set ylabel "# of occurences"
set xlabel "links that appear x times"
set logscale x
set logscale y
set output "distLinks.svg"
plot [1:1000][1:20000] "distLinks.gp" title 'BFS', "../dfs/distLinks.gp" title 'DFS'
