#!/bin/bash

(( $(ps -ef | grep java | grep student-rest | grep -v grep | grep -v sh | wc -l) != 1 )) && exit 1

exit 0