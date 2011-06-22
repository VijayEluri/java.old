#!/bin/bash
# Add remote to existing local git repositories.

# check args
if [ $# -ne 3 ]; then
    echo "Usage: $0  project-file-list  remote-base-url  remote-name"
    exit 1
fi

base_dir=`pwd`
remote_base_url=$2
remote_name=$3

while read line
do
    echo $line
    repo_dir=${base_dir}/${line}
    cd ${repo_dir}
    if [ $? -ne 0 ]; then
        echo "Can't entry directory [${repo_dir}]."
        continue
    fi

    git remote add ${remote_name}  ${remote_base_url}/${line}.git
done < $1

