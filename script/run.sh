#!/bin/bash

work_dir="$(pwd)"
script_dir="$(dirname "$(readlink -f "$0")")"
project_dir="$(dirname "$script_dir")"

source "$script_dir/common.sh"


rm -rf target
mvn clean spring-boot:run

cd "$work_dir" || exit 1