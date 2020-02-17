## FRC 2020

[![Build Status](https://travis-ci.com/Team4159/FRC-2020.svg?branch=master)](https://travis-ci.com/Team4159/FRC-2020)

Robot code for the 2020 FRC game, Infinite Recharge.

### Testing Trajectories

`./gradlew test` will test that trajectories generate correctly and save them to `paths/` for analysis.

To plot a trajectory, install [gnuplot](http://www.gnuplot.info/) and use the provided gnuplot script: `gnuplot -e "path='paths/TRAJECTORY_NAME.csv'" paths/path.plt`
