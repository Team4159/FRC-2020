set datafile separator ','
set style line 1 \
    linecolor rgb '#0060ad' \
    linetype 1 linewidth 2 \
    pointtype 7 pointsize 0.1
plot path using 2:3 with linespoints linestyle 1
pause -1
