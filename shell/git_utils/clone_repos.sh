#!/bin/bash
# Clone git repositories from the server.

# create directory if it doesn't exist
function create_dir()
{
    if [ ! -d $1 ]; then
        mkdir -p $1
    fi
}

# check args
if [ $# -ne 1 ]; then
    echo "Usage: $0  repo-base-url"
    exit 1
fi

base_dir=`pwd`
repo_base_url=$1

# Clone repository .project.git
repo_url=${repo_base_url}/.project.git
git clone $repo
if [ $? -ne 0 ]; then
    echo "Can't clone $repo_url"
    exit 1
fi

project_file=".project/project.list"
if [ ! -f $project_file ]; then
    echo "Can't find file $project_file"
    exit 1
fi

while read line
do
    cd $base_dir
    echo $line
    repo=${line##*/}
    if [ $line != $repo ]; then
        path=${line%/*}
        create_dir  $path
        cd $path
    fi

    repo_url=${repo_base_url}/${line}.git
    git clone $repo_url
done < $project_file

