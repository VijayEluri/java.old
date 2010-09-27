#!/bin/bash
# delete string's suffix

# the basic style 
function del_suffix1()
{
    local str=$1
    local suffix=$2
    local strlen=${#str}
    local suffixlen=${#suffix}
     
    echo ${str:0:$strlen-$suffixlen}
}

# the recommended style
function del_suffix2()
{
    local strlen=${#1}
    local suffixlen=${#2}
     
    echo ${1:0:$strlen-$suffixlen}
}

# the shortest style
function del_suffix3()
{
    echo ${1:0:${#1}-${#2}}
}


str="12345"
suffix="45"

echo "del_suffix1 change [$str] to [`del_suffix1 $str $suffix`]"
echo "del_suffix2 change [$str] to [`del_suffix2 $str $suffix`]"
echo "del_suffix3 change [$str] to [`del_suffix3 $str $suffix`]"

