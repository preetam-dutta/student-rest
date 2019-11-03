@echo off

cd build/docker

docker build -t student-rest:%1 .
if %errorlevel% NEQ 0 (
   echo docker build failed.
   exit /b %errorlevel%
)
docker build -t student-rest:latest .
if %errorlevel% NEQ 0 (
   echo docker build failed.
   exit /b %errorlevel%
)

exit /b 0