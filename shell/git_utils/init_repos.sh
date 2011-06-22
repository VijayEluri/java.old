#!/bin/bash
# Init git repositories on the local.

# check args
if [ $# -ne 1 ]; then
    echo "Usage: $0  project-file-list"
    exit 1
fi

base_dir=`pwd`

while read line
do
    echo $line
    repo_dir=${base_dir}/${line}

    cd ${repo_dir}
    if [ $? -ne 0 ]; then
        echo "Can't entry directory [${repo_dir}]."
        continue
    fi    

    git init
    git add *
    if [ -f .gitignore ]; then
        git add .gitignore
    fi
    git commit -m "First commit"
done < $1
