#!/usr/bin/env bash

statusCheck() {
  statusCode=$1
  msgIfFailed=$2
  (( $statusCode != 0 )) && {
    echo "ERROR: $msgIfFailed"
    exit $statusCode
  }
}

banner() {
  msg=$1
  echo ""
  echo "=========================================================="
  echo $1
  echo "----------------------------------------------------------"
}

log() {
  echo "INFO: $1"
}

dockerBuild() {
  banner "docker image build"
  docker build -t student-rest:$1 .
  statusCheck $? "docker build failed"
  docker build -t student-rest:latest .
  statusCheck $? "docker build failed"
}


main() {
  cd build/docker || exit 1
  dockerBuild $1
  banner "test environment setup completed"
}

main $1
exit 0
